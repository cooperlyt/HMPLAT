package com.dgsoft.house.owner.helper;

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
import java.util.List;
import java.util.Map;

/**
 * Created by cooper on 10/9/15.
 */
@Name("extendsDataCreator")
@AutoCreate
public class ExtendsDataCreator {
    @In
    private Map<String, String> messages;

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
        jsonObject.put("预售证号", jsonField(markCard.getNumber()));
        jsonObject.put("开发商", jsonField(ownerBusiness.getBusinessProject().getDeveloperName()));
        jsonObject.put("房屋坐落地点", jsonField(ownerBusiness.getBusinessProject().getAddress()));
        jsonObject.put("项目名称", jsonField(ownerBusiness.getBusinessProject().getProjectName()));
        jsonObject.put("建筑面积", jsonField(ownerBusiness.getBusinessProject().getProjectSellInfo().getArea()));
        jsonObject.put("栋", jsonField(ownerBusiness.getBusinessProject().getProjectSellInfo().getBuildCount()));
        jsonObject.put("套数", jsonField(ownerBusiness.getBusinessProject().getProjectSellInfo().getHouseCount()));
        if (ownerBusiness.getBusinessProject().getProjectSellInfo().getLicenseNumber()!=null){
            jsonObject.put("营业执照注册号", jsonField(ownerBusiness.getBusinessProject().getProjectSellInfo().getLicenseNumber()));
        }
        if (markCard.getProjectCard().getOrderNumber()!=null) {
            jsonObject.put("第号", jsonField(markCard.getProjectCard().getOrderNumber()));
        }
        jsonObject.put("房屋用途性质", jsonField(ownerBusiness.getBusinessProject().getProjectSellInfo().getUseType()));
        jsonObject.put("销预售对象", jsonField(ownerBusiness.getBusinessProject().getProjectSellInfo().getSellObject()));
        if (!ownerBusiness.getBusinessProject().getBusinessBuilds().isEmpty()) {
            for (int i = 0; i < ownerBusiness.getBusinessProject().getBusinessBuilds().size(); i++) {
                jsonObject.put("楼号" + (i + 1), jsonField(ownerBusiness.getBusinessProject().getBusinessBuildList().get(i).getBuildNo()));
                jsonObject.put("层数" + (i + 1), jsonField(ownerBusiness.getBusinessProject().getBusinessBuildList().get(i).getFloorCount()));
                jsonObject.put("总套数" + (i + 1), jsonField(ownerBusiness.getBusinessProject().getBusinessBuildList().get(i).getHouseCount()));
                jsonObject.put("建筑面积" + (i + 1), jsonField(ownerBusiness.getBusinessProject().getBusinessBuildList().get(i).getArea()));
                jsonObject.put("住宅面积" + (i + 1), jsonField(ownerBusiness.getBusinessProject().getBusinessBuildList().get(i).getHomeArea()));
                jsonObject.put("住宅套数" + (i + 1), jsonField(ownerBusiness.getBusinessProject().getBusinessBuildList().get(i).getHomeCount()));
                jsonObject.put("非住宅面积" + (i + 1), jsonField(ownerBusiness.getBusinessProject().getBusinessBuildList().get(i).getUnhomeArea()));
                jsonObject.put("非住宅套数" + (i + 1), jsonField(ownerBusiness.getBusinessProject().getBusinessBuildList().get(i).getUnhomeCount()));
                jsonObject.put("网点面积" + (i + 1), jsonField(ownerBusiness.getBusinessProject().getBusinessBuildList().get(i).getShopArea()));
                jsonObject.put("网点套数" + (i + 1), jsonField(ownerBusiness.getBusinessProject().getBusinessBuildList().get(i).getShopCount()));
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


    private JSONObject projectRshipStubJson(OwnerBusiness ownerBusiness, MakeCard markCard) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Report", "商品房预售许可证存根.fr3");
        jsonObject.put("预售证号", jsonField(markCard.getNumber()));
        jsonObject.put("开发商", jsonField(ownerBusiness.getBusinessProject().getDeveloperName()));
        jsonObject.put("项目名称", jsonField(ownerBusiness.getBusinessProject().getProjectName()));
        jsonObject.put("房屋坐落地点", jsonField(ownerBusiness.getBusinessProject().getAddress()));
        jsonObject.put("房屋用途性质", jsonField(ownerBusiness.getBusinessProject().getProjectSellInfo().getUseType()));
        jsonObject.put("销预售对象", jsonField(ownerBusiness.getBusinessProject().getProjectSellInfo().getSellObject()));
        jsonObject.put("建筑面积", jsonField(ownerBusiness.getBusinessProject().getProjectSellInfo().getArea()));
        jsonObject.put("栋", jsonField(ownerBusiness.getBusinessProject().getProjectSellInfo().getBuildCount()));
        jsonObject.put("套数", jsonField(ownerBusiness.getBusinessProject().getProjectSellInfo().getHouseCount()));
        if (ownerBusiness.getBusinessProject().getProjectSellInfo().getLicenseNumber()!=null){
            jsonObject.put("营业执照注册号", jsonField(ownerBusiness.getBusinessProject().getProjectSellInfo().getLicenseNumber()));
        }
        if (ownerBusiness.getBusinessProject().getProjectSellInfo().getProofMaterial()!=null){
            jsonObject.put("证明材料", jsonField(ownerBusiness.getBusinessProject().getProjectSellInfo().getProofMaterial()));
        }
        BigDecimal homeArea = BigDecimal.ZERO;
        BigDecimal unhomeArea = BigDecimal.ZERO;
        int homeCount = 0, unhomeCount = 0;

        if (!ownerBusiness.getBusinessProject().getBusinessBuilds().isEmpty()) {
            for (BusinessBuild businessBuild : ownerBusiness.getBusinessProject().getBusinessBuilds()) {
                homeArea = homeArea.add(businessBuild.getHomeArea());
                unhomeArea = unhomeArea.add(businessBuild.getShopArea());//商业
                unhomeArea = unhomeArea.add(businessBuild.getUnhomeArea());//其它

                homeCount = homeCount + businessBuild.getHomeCount();
                unhomeCount = unhomeCount + businessBuild.getUnhomeCount() + businessBuild.getShopCount();
                ;
            }
        }
        jsonObject.put("住宅面积", jsonField(homeArea));
        jsonObject.put("住宅套数", jsonField(homeCount));
        jsonObject.put("非住宅面积", jsonField(unhomeArea));
        jsonObject.put("非住宅套数", jsonField(unhomeCount));
        jsonObject.put("建设用地规划许可证号", jsonField(ownerBusiness.getBusinessProject().getProjectSellInfo().getCreateCardNumber()));
        jsonObject.put("土地使用权证号", jsonField(ownerBusiness.getBusinessProject().getProjectSellInfo().getLandCardNo()));
        jsonObject.put("建设工程规划许可证号", jsonField(ownerBusiness.getBusinessProject().getProjectSellInfo().getCreatePrepareCardNumber()));
        if (markCard.getProjectCard()!=null && markCard.getProjectCard().getOrderNumber()!=null){
            jsonObject.put("第号", jsonField(markCard.getProjectCard().getOrderNumber()));
        }
        if (markCard.getProjectCard()!=null && markCard.getProjectCard().getYearNumber()!=null){
            jsonObject.put("年号", jsonField(markCard.getProjectCard().getYearNumber()));
        }
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

    private JSONObject noticeMortgageJson(BusinessHouse businessHouse, MakeCard markCard, OwnerBusiness ownerBusiness, String poolInfo) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Report", "预告抵押登记.fr3");
        jsonObject.put("字", jsonField(businessHouse.getDistrictName()));
        jsonObject.put("预告登记证明号", jsonField(markCard.getNumber()));
        jsonObject.put("权利人", jsonField(ownerBusiness.getMortgaegeRegiste().getFinancial().getName()));
        jsonObject.put("义务人", jsonField(ownerBusiness.getSingleHoues().getAfterBusinessHouse().getBusinessHouseOwner().getPersonName()));
        jsonObject.put("房屋坐落", jsonField(businessHouse.getAddress()));
        jsonObject.put("图号", jsonField(businessHouse.getMapNumber()));
        jsonObject.put("丘号", jsonField(businessHouse.getBlockNo()));
        jsonObject.put("幢号", jsonField(businessHouse.getBuildNo()));
        jsonObject.put("房号", jsonField(businessHouse.getHouseOrder()));
        //判断是否是合并业务
        if (!ownerBusiness.getSubStatuses().isEmpty() && ownerBusiness.getSubStatuses().size()>0){
            jsonObject.put("预告登记权利种类", jsonField("预购商品房抵押权预告登记"));
        }else {
            jsonObject.put("预告登记权利种类", jsonField(ownerBusiness.getDefineName()));
        }



        if(ownerBusiness.getRegTime()!=null){
            jsonObject.put("登记时间", jsonField(ownerBusiness.getRegTime().toString()));
        }

        jsonObject.put("产籍号",jsonField(businessHouse.getDisplayHouseCode()));
        jsonObject.put("房屋编号", jsonField(businessHouse.getHouseCode()));
        jsonObject.put("业务编号", jsonField(ownerBusiness.getId()));
//        if (ownerBusiness.getSingleHoues().getStartBusinessHouse().getHouseRegInfo()!=null && ownerBusiness.getSingleHoues().getStartBusinessHouse().getHouseRegInfo().getHouseProperty()!=null){
//            jsonObject.put("产别", jsonField("产别 : "+DictionaryWord.instance().getWordValue(ownerBusiness.getSingleHoues().getStartBusinessHouse().getHouseRegInfo().getHouseProperty())));
//        }
//        if (ownerBusiness.getSingleHoues().getStartBusinessHouse().getHouseRegInfo()!=null && ownerBusiness.getSingleHoues().getStartBusinessHouse().getHouseRegInfo().getHouseFrom()!=null){
//            jsonObject.put("房屋来源", jsonField("产权来源 : "+DictionaryWord.instance().getWordValue(ownerBusiness.getSingleHoues().getStartBusinessHouse().getHouseRegInfo().getHouseFrom())));
//        }

        jsonObject.put("共有信息", jsonField(poolInfo));
        if(ownerBusiness.getReason(Reason.ReasonType.RECEIVE)!=null){
            jsonObject.put("受理备注", jsonField(ownerBusiness.getReason(Reason.ReasonType.RECEIVE).getReason()));
        }
        if(markCard.getCardInfo() !=null && markCard.getCardInfo().getMemo()!=null){
            jsonObject.put("缮证备注", jsonField(markCard.getCardInfo().getMemo()));
        }


        jsonObject.put("抵押时间始", jsonField(ownerBusiness.getMortgaegeRegiste().getMortgageDueTimeS().toString()));

        if (ownerBusiness.getMortgaegeRegiste().getTimeShowType().equals(TimeArea.TimeShowType.DATE_TIME)){

            jsonObject.put("抵押时间止",jsonField("至  "+ownerBusiness.getMortgaegeRegiste().getMortgageTime().toString()));
        }else{
            jsonObject.put("抵押时间止", jsonField(ownerBusiness.getMortgaegeRegiste().getTimeArea().getToSize().toString()+ownerBusiness.getMortgaegeRegiste().getTimeShowType().getLabel()));
        }



        jsonObject.put("债权数额", jsonField(ownerBusiness.getMortgaegeRegiste().getHighestMountMoney()));
        return jsonObject;
    }


    public String extendsPrintNoticeMortgage(BusinessHouse businessHouse, MakeCard markCard, OwnerBusiness ownerBusiness, String poolInfo) {
        try {
            return genPrintUrl(noticeMortgageJson(businessHouse, markCard, ownerBusiness, poolInfo).toString());
        } catch (JSONException e) {
            Logging.getLog(getClass()).error(e);
            return null;
        }
    }

    private JSONObject noticeJson(BusinessHouse businessHouse, MakeCard markCard, OwnerBusiness ownerBusiness, String poolInfo) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Report", "预告登记.fr3");
        jsonObject.put("字", jsonField(businessHouse.getDistrictName()));
        jsonObject.put("预告登记证明号", jsonField(markCard.getNumber()));
        jsonObject.put("权利人", jsonField(ownerBusiness.getSingleHoues().getAfterBusinessHouse().getBusinessHouseOwner().getPersonName()));
        if (ownerBusiness.getSingleHoues().getStartBusinessHouse().getBusinessHouseOwner() != null) {

            jsonObject.put("义务人", jsonField(ownerBusiness.getSingleHoues().getStartBusinessHouse().getBusinessHouseOwner().getPersonName()));
        }
        jsonObject.put("义务人", jsonField(ownerBusiness.getSingleHoues().getAfterBusinessHouse().getDeveloperName()));
        jsonObject.put("房屋坐落", jsonField(businessHouse.getAddress()));
        jsonObject.put("图号", jsonField(businessHouse.getMapNumber()));
        jsonObject.put("丘号", jsonField(businessHouse.getBlockNo()));
        jsonObject.put("幢号", jsonField(businessHouse.getBuildNo()));
        jsonObject.put("房号", jsonField(businessHouse.getHouseOrder()));
        //判断是否是合并业务
        if (!ownerBusiness.getSubStatuses().isEmpty() && ownerBusiness.getSubStatuses().size()>0){
            jsonObject.put("预告登记权利种类", jsonField("预购商品房预告登记"));
        }else {
            jsonObject.put("预告登记权利种类", jsonField(ownerBusiness.getDefineName()));
        }

        if(ownerBusiness.getRegTime()!=null){
            jsonObject.put("登记时间", jsonField(ownerBusiness.getRegTime().toString()));
        }
        jsonObject.put("产籍号",jsonField(businessHouse.getDisplayHouseCode()));
        jsonObject.put("房屋编号", jsonField(businessHouse.getHouseCode()));
        jsonObject.put("业务编号", jsonField(ownerBusiness.getId()));
        if (businessHouse.getHouseRegInfo()!=null && businessHouse.getHouseRegInfo().getHouseProperty()!=null){
            jsonObject.put("产别", jsonField(DictionaryWord.instance().getWordValue(businessHouse.getHouseRegInfo().getHouseProperty())));
        }
        if (businessHouse.getHouseRegInfo()!=null && businessHouse.getHouseRegInfo().getHouseFrom()!=null){
            jsonObject.put("房屋来源", jsonField(DictionaryWord.instance().getWordValue(businessHouse.getHouseRegInfo().getHouseFrom())));
        }


        jsonObject.put("共有信息", jsonField(poolInfo));
        if(ownerBusiness.getReason(Reason.ReasonType.RECEIVE)!=null){
            jsonObject.put("受理备注", jsonField(ownerBusiness.getReason(Reason.ReasonType.RECEIVE).getReason()));
        }
        if(markCard.getCardInfo() !=null && markCard.getCardInfo().getMemo()!=null){
            jsonObject.put("缮证备注", jsonField(markCard.getCardInfo().getMemo()));
        }
        return jsonObject;
    }

    public String extendsPrintNotice(BusinessHouse businessHouse, MakeCard markCard, OwnerBusiness ownerBusiness, String poolInfo) {
        try {
            return genPrintUrl(noticeJson(businessHouse, markCard, ownerBusiness, poolInfo).toString());
        } catch (JSONException e) {
            Logging.getLog(getClass()).error(e);
            return null;
        }
    }


    private JSONObject projectMortgageJson(MakeCard markCard, OwnerBusiness ownerBusiness) throws JSONException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Report", "在建工程抵押.fr3");
        if (ownerBusiness.getHouseBusinesses()!=null && ownerBusiness.getHouseBusinesses().size()>1){
            jsonObject.put("字", jsonField(ownerBusiness.getHouseBusinesses().iterator().next().getAfterBusinessHouse().getDistrictName()));
            Project project = houseEntityLoader.getEntityManager().find(Project.class, ownerBusiness.getHouseBusinesses().iterator().next().getAfterBusinessHouse().getProjectCode());
            if(project!=null){
                jsonObject.put("在建工程坐落", jsonField(project.getAddress()));
            }
        }
        if (ownerBusiness.getHouseBusinesses()!=null && ownerBusiness.getHouseBusinesses().size()==1){
            jsonObject.put("在建工程坐落", jsonField(ownerBusiness.getHouseBusinesses().iterator().next().getAfterBusinessHouse().getAddress()));
            jsonObject.put("产籍号",jsonField(ownerBusiness.getHouseBusinesses().iterator().next().getAfterBusinessHouse().getDisplayHouseCode()));
        }

        jsonObject.put("在建工程抵押证号", jsonField(markCard.getNumber()));
        jsonObject.put("抵押权人", jsonField(ownerBusiness.getMortgaegeRegiste().getFinancial().getName()));
        jsonObject.put("抵押人", jsonField(ownerBusiness.getMortgaegeRegiste().getBusinessHouseOwner().getPersonName()));

        jsonObject.put("抵押面积", jsonField(ownerBusiness.getMortgaegeRegiste().getMortgageArea()));
//        jsonObject.put("图号", jsonField(businessHouse.getMapNumber()));
//        jsonObject.put("丘号", jsonField(businessHouse.getBlockNo()));
//        jsonObject.put("幢号", jsonField(businessHouse.getBuildNo()));
//        jsonObject.put("房号", jsonField(businessHouse.getHouseOrder()));

        jsonObject.put("债权数额", jsonField(ownerBusiness.getMortgaegeRegiste().getHighestMountMoney()));
        if(ownerBusiness.getRegTime()!=null){
            jsonObject.put("登记时间", jsonField(ownerBusiness.getRegTime().toString()));
        }
        jsonObject.put("抵押时间始", jsonField(ownerBusiness.getMortgaegeRegiste().getMortgageDueTimeS().toString()));

        //jsonObject.put("抵押时间止", jsonField(ownerBusiness.getMortgaegeRegiste().getMortgageTime().toString()));


        if (ownerBusiness.getMortgaegeRegiste().getTimeShowType().equals(TimeArea.TimeShowType.DATE_TIME)){

            jsonObject.put("抵押时间止",jsonField("至  "+ownerBusiness.getMortgaegeRegiste().getMortgageTime().toString()));
        }else{
            jsonObject.put("抵押时间止", jsonField(ownerBusiness.getMortgaegeRegiste().getTimeArea().getToSize().toString()+ownerBusiness.getMortgaegeRegiste().getTimeShowType().getLabel()));
        }

        //jsonObject.put("日期类型",jsonField(ownerBusiness.getMortgaegeRegiste().getTimeShowType().toString()));
        jsonObject.put("评估价格", jsonField(ownerBusiness.getEvaluate().getAssessmentPrice()));
        if(ownerBusiness.getReason(Reason.ReasonType.RECEIVE)!=null){
            jsonObject.put("受理备注", jsonField(ownerBusiness.getReason(Reason.ReasonType.RECEIVE).getReason()));
        }
        if(markCard.getCardInfo() !=null && markCard.getCardInfo().getMemo()!=null){
            jsonObject.put("缮证备注", jsonField(markCard.getCardInfo().getMemo()));
        }

        return jsonObject;
    }

    public String extendsPrintProjectMortgage(MakeCard markCard, OwnerBusiness ownerBusiness) {
        try {
            return genPrintUrl(projectMortgageJson(markCard, ownerBusiness).toString());
        } catch (JSONException e) {
            Logging.getLog(getClass()).error(e);
            return null;
        }
    }


    private JSONObject mortgageCardJson(BusinessHouse businessHouse,
                                        MakeCard markCard, OwnerBusiness ownerBusiness) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Report", "他项权证.fr3");
        jsonObject.put("字", jsonField(businessHouse.getDistrictName()));
        jsonObject.put("他项权证号", jsonField(markCard.getNumber()));
        jsonObject.put("房屋他项权利人", jsonField(ownerBusiness.getMortgaegeRegiste().getFinancial().getName()));
        jsonObject.put("房屋所有权人", jsonField(ownerBusiness.getSingleHoues().getAfterBusinessHouse().getBusinessHouseOwner().getPersonName()));
        jsonObject.put("所有权证证号", jsonField(ownerBusiness.getSingleHoues().getAfterBusinessHouse().getBusinessHouseOwner().getMakeCard().getNumber()));
        jsonObject.put("房屋坐落", jsonField(businessHouse.getAddress()));
        jsonObject.put("图号", jsonField(businessHouse.getMapNumber()));
        jsonObject.put("丘号", jsonField(businessHouse.getBlockNo()));
        jsonObject.put("幢号", jsonField(businessHouse.getBuildNo()));
        jsonObject.put("房号", jsonField(businessHouse.getHouseOrder()));
        jsonObject.put("他项权利种类", jsonField(DictionaryWord.instance().getWordValue(ownerBusiness.getMortgaegeRegiste().getInterestType())));
        jsonObject.put("债权数额", jsonField(ownerBusiness.getMortgaegeRegiste().getHighestMountMoney()));
        jsonObject.put("债权大写",jsonField(BigMoneyUtil.getBigMoney(ownerBusiness.getMortgaegeRegiste().getHighestMountMoney().doubleValue())));
        jsonObject.put("抵押面积", jsonField(ownerBusiness.getMortgaegeRegiste().getMortgageArea()));
        if(ownerBusiness.getRegTime()!=null){
            jsonObject.put("登记时间", jsonField(ownerBusiness.getRegTime().toString()));
        }


        jsonObject.put("产籍号",jsonField(businessHouse.getDisplayHouseCode()));
        jsonObject.put("房屋编号", jsonField(businessHouse.getHouseCode()));
        jsonObject.put("业务编号", jsonField(ownerBusiness.getId()));
        jsonObject.put("业务名称", jsonField(ownerBusiness.getDefineName()));
        jsonObject.put("抵押时间始", jsonField(ownerBusiness.getMortgaegeRegiste().getMortgageDueTimeS().toString()));
        //jsonObject.put("抵押时间止", jsonField(ownerBusiness.getMortgaegeRegiste().getMortgageTime().toString()));
        if (ownerBusiness.getMortgaegeRegiste().getTimeShowType().equals(TimeArea.TimeShowType.DATE_TIME)){

            jsonObject.put("抵押时间止",jsonField("至  "+ownerBusiness.getMortgaegeRegiste().getMortgageTime().toString()));
        }else{
            jsonObject.put("抵押时间止", jsonField(ownerBusiness.getMortgaegeRegiste().getTimeArea().getToSize().toString()+ownerBusiness.getMortgaegeRegiste().getTimeShowType().getLabel()));
        }

        if(ownerBusiness.getReason(Reason.ReasonType.RECEIVE)!=null){
            jsonObject.put("受理备注", jsonField(ownerBusiness.getReason(Reason.ReasonType.RECEIVE).getReason()));
        }
        if(markCard.getCardInfo() !=null && markCard.getCardInfo().getMemo()!=null){
            jsonObject.put("缮证备注", jsonField(markCard.getCardInfo().getMemo()));
        }
        return jsonObject;
    }


    public String extendsPrintMortgageCard(BusinessHouse businessHouse, MakeCard markCard, OwnerBusiness ownerBusiness) {
        try {
            return genPrintUrl(mortgageCardJson(businessHouse, markCard, ownerBusiness).toString());
        } catch (JSONException e) {
            Logging.getLog(getClass()).error(e);
            return null;
        }
    }


    private JSONObject ownerRsipJson(BusinessHouse businessHouse,
                                     MakeCard markCard, OwnerBusiness ownerBusiness, String poolInfo) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Report", "产权证.fr3");
        jsonObject.put("字", jsonField(businessHouse.getDistrictName()));
        jsonObject.put("产权证号", jsonField(markCard.getNumber()));
        jsonObject.put("房屋所有权人", jsonField(businessHouse.getBusinessHouseOwner().getPersonName()));
        if (businessHouse.getPoolType()!=null) {
            jsonObject.put("共有情况", jsonField(messages.get(businessHouse.getPoolType().name())));
        }
        jsonObject.put("房屋坐落", jsonField(businessHouse.getAddress()));
        jsonObject.put("图号", jsonField(businessHouse.getMapNumber()));
        jsonObject.put("丘号", jsonField(businessHouse.getBlockNo()));
        jsonObject.put("幢号", jsonField(businessHouse.getBuildNo()));
        jsonObject.put("房号", jsonField(businessHouse.getHouseOrder()));
        if(ownerBusiness.getRegTime()!=null){
            jsonObject.put("登记时间", jsonField(ownerBusiness.getRegTime().toString()));
        }
        if(businessHouse.getHouseType()!=null){
            jsonObject.put("房屋性质", jsonField(DictionaryWord.instance().getWordValue(businessHouse.getHouseType())));
        }



        jsonObject.put("规划用途", jsonField(DictionaryWord.instance().getWordValue(businessHouse.getUseType())));
        jsonObject.put("总层数", jsonField(businessHouse.getFloorCount()));
        jsonObject.put("建筑面积", jsonField(businessHouse.getHouseArea()));
        if (businessHouse.getUseArea()!=null && businessHouse.getUseArea().compareTo(BigDecimal.ZERO) > 0) {
            jsonObject.put("套内建筑面积", jsonField(businessHouse.getUseArea()));
        }
        if (businessHouse.getLandInfo() != null) {
            if (businessHouse.getLandInfo().getNumber()!=null){
                jsonObject.put("地号", jsonField(businessHouse.getLandInfo().getNumber()));
            }
            if (businessHouse.getLandInfo().getLandGetMode()!=null){
                jsonObject.put("土地获得方式", jsonField(DictionaryWord.instance().getWordValue(businessHouse.getLandInfo().getLandGetMode())));
            }

            if (businessHouse.getLandInfo().getBeginUseTime()!=null) {
                jsonObject.put("土地使用年始", jsonField(businessHouse.getLandInfo().getBeginUseTime().toString()));
            }
            if (businessHouse.getLandInfo().getEndUseTime()!=null) {
                jsonObject.put("土地使用年止", jsonField(businessHouse.getLandInfo().getEndUseTime().toString()));
            }
        }
        jsonObject.put("房屋编号", jsonField(businessHouse.getHouseCode()));
        jsonObject.put("业务编号", jsonField(ownerBusiness.getId()));
        if (businessHouse.getHouseRegInfo()!=null && businessHouse.getHouseRegInfo().getHouseProperty()!=null){
            jsonObject.put("产别", jsonField(DictionaryWord.instance().getWordValue(businessHouse.getHouseRegInfo().getHouseProperty())));
        }
        if (businessHouse.getHouseRegInfo()!=null && businessHouse.getHouseRegInfo().getHouseFrom()!=null){
            jsonObject.put("房屋来源", jsonField(DictionaryWord.instance().getWordValue(businessHouse.getHouseRegInfo().getHouseFrom())));
        }        jsonObject.put("结构", jsonField(DictionaryWord.instance().getWordValue(businessHouse.getStructure())));
        jsonObject.put("所在层", jsonField(businessHouse.getInFloorName()));
        jsonObject.put("共有信息", jsonField(poolInfo));
        jsonObject.put("产籍号",jsonField(businessHouse.getDisplayHouseCode()));
        if(ownerBusiness.getReason(Reason.ReasonType.RECEIVE)!=null){
            jsonObject.put("受理备注", jsonField(ownerBusiness.getReason(Reason.ReasonType.RECEIVE).getReason()));
        }
        if(markCard.getCardInfo() !=null && markCard.getCardInfo().getMemo()!=null){

            jsonObject.put("缮证备注", jsonField(markCard.getCardInfo().getMemo()));
        }

        return jsonObject;

    }

    public String extendsPrintOwnerRsip(BusinessHouse businessHouse, MakeCard markCard) {

            String str="";
            if (!businessHouse.getBusinessPools().isEmpty()){
                Integer poolType = RunParam.instance().getIntParamValue("PoolInfoPrint");
                if (poolType==1) {
                    str = "所有权人:" + businessHouse.getBusinessHouseOwner().getPersonName();
                    for (BusinessPool businessPool : businessHouse.getBusinessPools()) {
                        str=str+" 共有权人："+businessPool.getPersonName()+",身份证明号:"+businessPool.getCredentialsNumber();
                    }
                }
                if (poolType==2) {
                    String poolStr="";
                    for (BusinessPool businessPool : businessHouse.getBusinessPools()) {
                        poolStr = poolStr + businessPool.getPersonName()+"  ";
                    }
                    str = "共有权人: " + poolStr;
                }

            }


        try {
            return genPrintUrl(ownerRsipJson(businessHouse, markCard, businessHouse.getHouseBusinessForAfter().getOwnerBusiness(),str).toString());
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
