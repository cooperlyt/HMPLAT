package com.dgsoft.house.owner.helper;

import com.dgsoft.common.helper.JsonDataProvider;
import com.dgsoft.common.system.AuthenticationInfo;
import com.dgsoft.common.system.DictionaryWord;
import com.dgsoft.house.owner.model.*;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Logging;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.faces.context.FacesContext;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * Created by cooper on 10/9/15.
 *
 */
@Name("extendsDataCreator")
@AutoCreate
public class ExtendsDataCreator {
    @In
    private Map<String,String> messages;

    @In
    private JsonDataProvider jsonDataProvider;

    @In
    private AuthenticationInfo authInfo;

    @In
    private FacesContext facesContext;

    public final static String EXTENDS_PRINT_PROTOCOL = "ExtendsPrint://";


    public enum JsonFieldType {
        STRING(0),DOUBLE(1),INTEGER(2);

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



    private JSONObject projectMortgageJson(MakeCard markCard,OwnerBusiness ownerBusiness)throws JSONException{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Report", "在建工程抵押.fr3");
        jsonObject.put("字",jsonField(""));
        jsonObject.put("在建工程抵押证号",jsonField(markCard.getNumber()));
        jsonObject.put("抵押权人",jsonField(ownerBusiness.getMortgaegeRegiste().getFinancial().getName()));
        jsonObject.put("抵押人",jsonField(ownerBusiness.getMortgaegeRegiste().getBusinessHouseOwner().getPersonName()));
        jsonObject.put("在建工程坐落", jsonField(""));
        jsonObject.put("抵押面积", jsonField(ownerBusiness.getMortgaegeRegiste().getMortgageArea()));
//        jsonObject.put("图号", jsonField(businessHouse.getMapNumber()));
//        jsonObject.put("丘号", jsonField(businessHouse.getBlockNo()));
//        jsonObject.put("幢号", jsonField(businessHouse.getBuildNo()));
//        jsonObject.put("房号", jsonField(businessHouse.getHouseOrder()));
        jsonObject.put("债权数额", jsonField(ownerBusiness.getMortgaegeRegiste().getHighestMountMoney()));
        jsonObject.put("登记时间", jsonField(ownerBusiness.getRegTime().toString()));
        jsonObject.put("抵押时间始", jsonField(ownerBusiness.getMortgaegeRegiste().getMortgageDueTimeS().toString()));
        jsonObject.put("抵押时间止", jsonField(ownerBusiness.getMortgaegeRegiste().getMortgageTime().toString()));
        jsonObject.put("评估价格", jsonField(ownerBusiness.getEvaluate().getAssessmentPrice()));
        jsonObject.put("受理备注", jsonField(ownerBusiness.getReason(Reason.ReasonType.RECEIVE).getReason()));
        jsonObject.put("缮证备注", jsonField(markCard.getCardInfo().getMemo()));


        return jsonObject;
    }

    public String extendsPrintProjectMortgage(MakeCard markCard,OwnerBusiness ownerBusiness){

        try {
            Long putId = jsonDataProvider.putData(projectMortgageJson( markCard, ownerBusiness).toString());
            return EXTENDS_PRINT_PROTOCOL + URLEncoder.encode(facesContext.getExternalContext().getRequestContextPath() + "/JsonDataProvider.seam?id=" + putId,"UTF-8");
        } catch (JSONException e) {
            Logging.getLog(getClass()).error(e);
            return null;
        } catch (UnsupportedEncodingException e) {
            Logging.getLog(getClass()).error(e);
            return null;
        }
    }


    private JSONObject mortgageCardJson(BusinessHouse businessHouse,
                                        MakeCard markCard,OwnerBusiness ownerBusiness)throws JSONException{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Report", "他项权证.fr3");
        jsonObject.put("字",jsonField(businessHouse.getDistrictName()));
        jsonObject.put("他项权证号",jsonField(markCard.getNumber()));
        jsonObject.put("房屋他项权利人",jsonField(ownerBusiness.getMortgaegeRegiste().getFinancial().getName()));
        jsonObject.put("房屋所有权人",jsonField(ownerBusiness.getSingleHoues().getAfterBusinessHouse().getBusinessHouseOwner().getPersonName()));
        jsonObject.put("所有权证证号",jsonField(ownerBusiness.getSingleHoues().getAfterBusinessHouse().getBusinessHouseOwner().getMakeCard().getNumber()));
        jsonObject.put("房屋坐落", jsonField(businessHouse.getAddress()));
        jsonObject.put("图号", jsonField(businessHouse.getMapNumber()));
        jsonObject.put("丘号", jsonField(businessHouse.getBlockNo()));
        jsonObject.put("幢号", jsonField(businessHouse.getBuildNo()));
        jsonObject.put("房号", jsonField(businessHouse.getHouseOrder()));
        jsonObject.put("他项权利种类", jsonField(DictionaryWord.instance().getWordValue(ownerBusiness.getMortgaegeRegiste().getInterestType())));
        jsonObject.put("债权数额", jsonField(ownerBusiness.getMortgaegeRegiste().getHighestMountMoney()));
        jsonObject.put("登记时间", jsonField(ownerBusiness.getRegTime().toString()));
        jsonObject.put("房屋编号", jsonField(businessHouse.getHouseCode()));
        jsonObject.put("业务编号", jsonField(ownerBusiness.getId()));
        jsonObject.put("业务名称", jsonField(ownerBusiness.getDefineName()));
        jsonObject.put("抵押时间始", jsonField(ownerBusiness.getMortgaegeRegiste().getMortgageDueTimeS().toString()));
        jsonObject.put("抵押时间止", jsonField(ownerBusiness.getMortgaegeRegiste().getMortgageTime().toString()));
        jsonObject.put("受理备注", jsonField(ownerBusiness.getReason(Reason.ReasonType.RECEIVE).getReason()));
        jsonObject.put("缮证备注", jsonField(markCard.getCardInfo().getMemo()));
        return jsonObject;
    }



    public String extendsPrintMortgageCard(BusinessHouse businessHouse,MakeCard markCard,OwnerBusiness ownerBusiness){

        try {
            Long putId = jsonDataProvider.putData(mortgageCardJson(businessHouse, markCard, ownerBusiness).toString());
            return EXTENDS_PRINT_PROTOCOL + URLEncoder.encode(facesContext.getExternalContext().getRequestContextPath() + "/JsonDataProvider.seam?id=" + putId,"UTF-8");
        } catch (JSONException e) {
            Logging.getLog(getClass()).error(e);
            return null;
        } catch (UnsupportedEncodingException e) {
            Logging.getLog(getClass()).error(e);
            return null;
        }
    }


    private JSONObject ownerRsipJson(BusinessHouse businessHouse,
                                 MakeCard markCard,OwnerBusiness ownerBusiness,String poolInfo) throws JSONException{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Report", "产权证.fr3");
        jsonObject.put("字",jsonField(businessHouse.getDistrictName()));
        jsonObject.put("产权证号",jsonField(markCard.getNumber()));
        jsonObject.put("房屋所有权人",jsonField(businessHouse.getBusinessHouseOwner().getPersonName()));
        jsonObject.put("共有情况", jsonField(messages.get(businessHouse.getPoolType().name())));
        jsonObject.put("房屋坐落", jsonField(businessHouse.getAddress()));
        jsonObject.put("图号", jsonField(businessHouse.getMapNumber()));
        jsonObject.put("丘号", jsonField(businessHouse.getBlockNo()));
        jsonObject.put("幢号", jsonField(businessHouse.getBuildNo()));
        jsonObject.put("房号", jsonField(businessHouse.getHouseOrder()));
        jsonObject.put("登记时间", jsonField(ownerBusiness.getRegTime().toString()));
        jsonObject.put("房屋性质", jsonField(DictionaryWord.instance().getWordValue(businessHouse.getHouseType())));
        jsonObject.put("规划用途", jsonField(DictionaryWord.instance().getWordValue(businessHouse.getUseType())));
        jsonObject.put("总层数", jsonField(businessHouse.getFloorCount()));
        jsonObject.put("建筑面积", jsonField(businessHouse.getHouseArea()));
        if (businessHouse.getUseArea().compareTo(BigDecimal.ZERO)>0) {
            jsonObject.put("套内建筑面积", jsonField(businessHouse.getUseArea()));
        }
        if (businessHouse.getLandInfo()!=null) {
            jsonObject.put("地号", jsonField(businessHouse.getLandInfo().getNumber()));
            jsonObject.put("土地获得方式", jsonField(DictionaryWord.instance().getWordValue(businessHouse.getLandInfo().getLandGetMode())));
            jsonObject.put("土地使用年始", jsonField(businessHouse.getLandInfo().getBeginUseTime().toString()));
            jsonObject.put("土地使用年止", jsonField(businessHouse.getLandInfo().getEndUseTime().toString()));
        }
        jsonObject.put("房屋编号", jsonField(businessHouse.getHouseCode()));
        jsonObject.put("业务编号", jsonField(ownerBusiness.getId()));
        jsonObject.put("产别", jsonField(DictionaryWord.instance().getWordValue(businessHouse.getHouseRegInfo().getHouseProperty())));
        jsonObject.put("房屋来源", jsonField(DictionaryWord.instance().getWordValue(businessHouse.getHouseRegInfo().getHouseFrom())));
        jsonObject.put("结构", jsonField(DictionaryWord.instance().getWordValue(businessHouse.getStructure())));
        jsonObject.put("所在层", jsonField(businessHouse.getInFloorName()));
        jsonObject.put("共有信息", jsonField(poolInfo));
        jsonObject.put("受理备注", jsonField(ownerBusiness.getReason(Reason.ReasonType.RECEIVE).getReason()));
        jsonObject.put("缮证备注", jsonField(markCard.getCardInfo().getMemo()));
        return jsonObject;

    }

    public String extendsPrintOwnerRsip(BusinessHouse businessHouse,MakeCard markCard,OwnerBusiness ownerBusiness,String poolInfo){

        try {
            Long putId = jsonDataProvider.putData(ownerRsipJson(businessHouse, markCard, ownerBusiness, poolInfo).toString());
            return EXTENDS_PRINT_PROTOCOL + URLEncoder.encode(facesContext.getExternalContext().getRequestContextPath() + "/JsonDataProvider.seam?id=" + putId,"UTF-8");
        } catch (JSONException e) {
            Logging.getLog(getClass()).error(e);
            return null;
        } catch (UnsupportedEncodingException e) {
            Logging.getLog(getClass()).error(e);
            return null;
        }
    }



    private JSONObject createFeeJson(String id, String payPerson, String orgName , FactMoneyInfo factMoneyInfo) throws JSONException {

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("Report", "辽宁省非税收入统一收据.fr3");
        jsonObject.put("缴款凭证号码", jsonField(id));
        jsonObject.put("缴款人",jsonField(payPerson));
        jsonObject.put("执收单位名称",jsonField(orgName));

        List<BusinessMoney> businessMoneyList = factMoneyInfo.getBusinessMoneyList();

        for (int i = 0 ; i< businessMoneyList.size() ; i++) {
            jsonObject.put("收费项目" + (i + 1),jsonField(businessMoneyList.get(i).getTypeName()));
            jsonObject.put("收费金额" + (i + 1),jsonField(businessMoneyList.get(i).getShouldMoney()));
        }

        jsonObject.put("收款人", jsonField(authInfo.getLoginEmployee().getPersonName()));

        return jsonObject;
    }

    public String extendsPrintFee(String id, String payPerson, String orgName , FactMoneyInfo factMoneyInfo){

        try {
            Long putId = jsonDataProvider.putData(createFeeJson(id,payPerson,orgName,factMoneyInfo).toString());
            return EXTENDS_PRINT_PROTOCOL + URLEncoder.encode(facesContext.getExternalContext().getRequestContextPath() + "/JsonDataProvider.seam?id=" + putId,"UTF-8");
        } catch (JSONException e) {
            Logging.getLog(getClass()).error(e);
            return null;
        } catch (UnsupportedEncodingException e) {
            Logging.getLog(getClass()).error(e);
            return null;
        }
    }
}
