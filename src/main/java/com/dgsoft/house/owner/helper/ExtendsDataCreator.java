package com.dgsoft.house.owner.helper;

import cc.coopersoft.house.UseType;
import com.dgsoft.common.BigMoneyUtil;
import com.dgsoft.common.TimeArea;
import com.dgsoft.common.helper.QueueJsonDataProvider;
import com.dgsoft.common.system.AuthenticationInfo;
import com.dgsoft.common.system.DictionaryWord;
import com.dgsoft.common.system.RunParam;
import com.dgsoft.common.system.SystemEntityLoader;
import com.dgsoft.common.system.model.Fee;
import com.dgsoft.house.HouseEntityLoader;
import com.dgsoft.house.model.Project;
import com.dgsoft.house.owner.model.*;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Logging;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by cooper on 10/9/15.
 */
@Name("extendsDataCreator")
@AutoCreate
public class ExtendsDataCreator {

    @In
    private QueueJsonDataProvider queueJsonDataProvider;

    @In
    private AuthenticationInfo authInfo;

    @In(create = true)
    private SystemEntityLoader systemEntityLoader;

    public final static String EXTENDS_PRINT_PROTOCOL = "ExtendsPrint://";


    @In(create = true)
    private HouseEntityLoader houseEntityLoader;


    public enum JsonFieldType {
        STRING(0), DOUBLE(1), INTEGER(2);

        int id;

        public int getId() {
            return id;
        }

        JsonFieldType(int id) {
            this.id = id;
        }
    }

    private JSONArray jsonField(String value) {
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(JsonFieldType.STRING.getId());
        jsonArray.put(value);
        return jsonArray;
    }

    private JSONArray jsonField(BigDecimal value) throws JSONException {
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(JsonFieldType.DOUBLE.getId());
        jsonArray.put(value.doubleValue());
        return jsonArray;
    }


    private JSONArray jsonField(int value) throws JSONException {
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(JsonFieldType.INTEGER.getId());
        jsonArray.put(value);
        return jsonArray;
    }

    private JSONObject projectRshipJson(OwnerBusiness ownerBusiness, MakeCard markCard) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Report", "商品房预售许可证.fr3");
        projectRshipInfo(ownerBusiness,markCard,jsonObject);

        if (!ownerBusiness.getBusinessProject().getBusinessBuilds().isEmpty()) {
            for (int i = 0; i < ownerBusiness.getBusinessProject().getBusinessBuilds().size(); i++) {
                jsonObject.put("楼号" + (i + 1), jsonField(ownerBusiness.getBusinessProject().getBusinessBuildList().get(i).getBuildNo()));
                jsonObject.put("层数" + (i + 1), jsonField(ownerBusiness.getBusinessProject().getBusinessBuildList().get(i).getFloorCount()));
                jsonObject.put("总套数" + (i + 1), jsonField(ownerBusiness.getBusinessProject().getBusinessBuildList().get(i).getHouseCount()));
                jsonObject.put("建筑面积" + (i + 1), jsonField(ownerBusiness.getBusinessProject().getBusinessBuildList().get(i).getArea()));

                BigDecimal otherArea = BigDecimal.ZERO;
                int otherCount = 0;
                BigDecimal dwellingArea = BigDecimal.ZERO;
                int dwellingConut = 0;
                BigDecimal shopArea = BigDecimal.ZERO;
                int shopCount = 0;

                for(SellTypeTotal stt: ownerBusiness.getBusinessProject().getBusinessBuildList().get(i).getSellTypeTotals()){
                    if (UseType.DWELLING_KEY.equals(stt.getUseType())){
                        dwellingArea = dwellingArea.add(stt.getArea());
                        dwellingConut = dwellingConut + stt.getCount();
                    }else if (UseType.SHOP_HOUSE_KEY.equals(stt.getUseType())){
                        shopArea = shopArea.add(stt.getArea());
                        shopCount = shopCount + stt.getCount();
                    }else {
                        otherArea = otherArea.add(stt.getArea());
                        otherCount = otherCount + stt.getCount();
                    }
                }
                jsonObject.put("住宅面积" + (i + 1), jsonField(dwellingArea));
                jsonObject.put("住宅套数" + (i + 1), jsonField(dwellingConut));

                jsonObject.put("非住宅面积" + (i + 1), jsonField(otherArea));
                jsonObject.put("非住宅套数" + (i + 1), jsonField(otherCount));


                jsonObject.put("网点面积" + (i + 1), jsonField(shopArea));
                jsonObject.put("网点套数" + (i + 1), jsonField(shopCount));
            }

        }


        return jsonObject;
    }

    public String extendsPrintprojectRship(OwnerBusiness ownerBusiness, MakeCard markCard) {
        try {
            return genPrintUrl(projectRshipJson(ownerBusiness, markCard).toString());
        } catch (JSONException e) {
            Logging.getLog(getClass()).error(e);
            return null;
        }
    }

    private void projectRshipInfo(OwnerBusiness ownerBusiness, MakeCard markCard ,JSONObject jsonObject) throws JSONException {
        jsonObject.put("预售证号", jsonField(markCard.getNumber()));
        jsonObject.put("开发商", jsonField(ownerBusiness.getBusinessProject().getDeveloperName()));
        jsonObject.put("房屋坐落地点", jsonField(ownerBusiness.getBusinessProject().getAddress()));

        Integer ProjectRshipNameType = RunParam.instance().getIntParamValue("ProjectRshipNamePrint");
        if (ProjectRshipNameType==2){
            String name = ownerBusiness.getBusinessProject().getBusinessBuilds().iterator().next().getName();
            if (ownerBusiness.getBusinessProject().getBusinessBuilds().iterator().next() !=null && ownerBusiness.getBusinessProject().getBusinessBuilds().iterator().next().getDoorNo()!=null
                    && !ownerBusiness.getBusinessProject().getBusinessBuilds().iterator().next().getDoorNo().equals("")){
                name = name +'('+ ownerBusiness.getBusinessProject().getBusinessBuilds().iterator().next().getDoorNo() +')';
            }
            jsonObject.put("项目名称", jsonField(name));
        }else if (ProjectRshipNameType==3){
            String name = ownerBusiness.getBusinessProject().getBusinessBuilds().iterator().next().getName();
            jsonObject.put("项目名称", jsonField(name));
        }else {
            jsonObject.put("项目名称", jsonField(ownerBusiness.getBusinessProject().getProjectName()));
        }


        jsonObject.put("建筑面积", jsonField(ownerBusiness.getBusinessProject().getProjectSellInfo().getArea()));
        jsonObject.put("栋", jsonField(ownerBusiness.getBusinessProject().getProjectSellInfo().getBuildCount()));
        jsonObject.put("套数", jsonField(ownerBusiness.getBusinessProject().getProjectSellInfo().getHouseCount()));

        if (ownerBusiness.getBusinessProject().getProjectSellInfo().getLicenseNumber()!=null){
            jsonObject.put("营业执照注册号", jsonField(ownerBusiness.getBusinessProject().getProjectSellInfo().getLicenseNumber()));
        }

        jsonObject.put("房屋用途性质", jsonField(ownerBusiness.getBusinessProject().getProjectSellInfo().getUseType()));
        jsonObject.put("销预售对象", jsonField(ownerBusiness.getBusinessProject().getProjectSellInfo().getSellObject()));
        if (markCard.getProjectCard()!=null && markCard.getProjectCard().getOrderNumber()!=null){
            jsonObject.put("第号", jsonField(markCard.getProjectCard().getOrderNumber()));
        }
        if (markCard.getProjectCard()!=null && markCard.getProjectCard().getYearNumber()!=null){
            jsonObject.put("年号", jsonField(markCard.getProjectCard().getYearNumber()));
        }
    }


    private JSONObject projectRshipStubJson(OwnerBusiness ownerBusiness, MakeCard markCard) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Report", "商品房预售许可证存根.fr3");

        projectRshipInfo(ownerBusiness,markCard,jsonObject);


        if (ownerBusiness.getBusinessProject().getProjectSellInfo().getProofMaterial()!=null){
            jsonObject.put("证明材料", jsonField(ownerBusiness.getBusinessProject().getProjectSellInfo().getProofMaterial()));
        }
        BigDecimal homeArea = BigDecimal.ZERO;
        BigDecimal unhomeArea = BigDecimal.ZERO;
        int homeCount = 0, unhomeCount = 0;

        if (!ownerBusiness.getBusinessProject().getBusinessBuilds().isEmpty()) {
            for (BusinessBuild businessBuild : ownerBusiness.getBusinessProject().getBusinessBuilds()) {

                for(SellTypeTotal stt: businessBuild.getSellTypeTotals()){
                    if (UseType.DWELLING_KEY.equals(stt.getUseType())){
                        homeArea = homeArea.add(stt.getArea());
                        homeCount = homeCount + stt.getCount();
                    }else{
                        unhomeArea = unhomeArea.add(stt.getArea());
                        unhomeCount = unhomeCount + stt.getCount();
                    }
                }
            }
        }
        jsonObject.put("住宅面积", jsonField(homeArea));
        jsonObject.put("住宅套数", jsonField(homeCount));
        jsonObject.put("非住宅面积", jsonField(unhomeArea));
        jsonObject.put("非住宅套数", jsonField(unhomeCount));
        jsonObject.put("建设用地规划许可证号", jsonField(ownerBusiness.getBusinessProject().getProjectSellInfo().getCreateCardNumber()));
        jsonObject.put("土地使用权证号", jsonField(ownerBusiness.getBusinessProject().getProjectSellInfo().getLandCardNo()));
        jsonObject.put("建设工程规划许可证号", jsonField(ownerBusiness.getBusinessProject().getProjectSellInfo().getCreatePrepareCardNumber()));

        return jsonObject;
    }

    public String extendsPrintProjectRshipStub(OwnerBusiness ownerBusiness, MakeCard markCard) {
        try {
            return genPrintUrl(projectRshipStubJson(ownerBusiness, markCard).toString());
        } catch (JSONException e) {
            Logging.getLog(getClass()).error(e);
            return null;
        }
    }


    private JSONObject createFeeJson(String id, String payPerson, String orgName, FactMoneyInfo factMoneyInfo,OwnerBusiness ownerBusiness) throws JSONException {

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("Report", "辽宁省非税收入统一收据.fr3");
        jsonObject.put("缴款凭证号码", jsonField(id));
        jsonObject.put("业务名称", jsonField(ownerBusiness.getDefineName()));
        jsonObject.put("缴款人", jsonField(payPerson));
        if (ownerBusiness.getMortgaegeRegiste()!=null && ownerBusiness.getMortgaegeRegiste().getFinancial()!=null){
            jsonObject.put("抵押权人", jsonField(ownerBusiness.getMortgaegeRegiste().getFinancial().getName()));
        }
        jsonObject.put("执收单位名称", jsonField(orgName));

        List<BusinessMoney> businessMoneyList = factMoneyInfo.getBusinessMoneyList();

        for (int i = 0; i < businessMoneyList.size(); i++) {
            if(businessMoneyList.get(i).getShouldMoney().compareTo(BigDecimal.ZERO)>0){
                Fee fee = systemEntityLoader.getEntityManager().find(Fee.class,businessMoneyList.get(i).getMoneyTypeId());
                if (fee!=null) {
                    jsonObject.put("收费项目" + (i + 1), jsonField(fee.getCategory().getName()));
                }
                jsonObject.put("收费金额" + (i + 1), jsonField(businessMoneyList.get(i).getShouldMoney()));
            }
        }
        jsonObject.put("合计小写", jsonField(ownerBusiness.getFactMoneyInfo().getShouldMoney()));
        jsonObject.put("合计大写", jsonField(BigMoneyUtil.getBigMoney(ownerBusiness.getFactMoneyInfo().getShouldMoney().doubleValue())));


        jsonObject.put("收款人", jsonField(authInfo.getLoginEmployee().getPersonName()));

        return jsonObject;
    }

    public String extendsPrintFee(String id, String payPerson, String orgName, FactMoneyInfo factMoneyInfo,OwnerBusiness ownerBusiness) {


        try {
            return genPrintUrl(createFeeJson(id, payPerson, orgName, factMoneyInfo,ownerBusiness).toString());
        } catch (JSONException e) {
            Logging.getLog(getClass()).error(e);
            return null;
        }
    }

    private String genPrintUrl(String data) {
        Long putId = queueJsonDataProvider.putData(data);
        try {
            return EXTENDS_PRINT_PROTOCOL + URLEncoder.encode(String.valueOf(putId), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Logging.getLog(getClass()).error(e);
            return null;
        }
    }
}
