package com.dgsoft.house.owner.helper;

import com.dgsoft.common.helper.JsonDataProvider;
import com.dgsoft.common.system.AuthenticationInfo;
import com.dgsoft.house.owner.model.BusinessMoney;
import com.dgsoft.house.owner.model.FactMoneyInfo;
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

/**
 * Created by cooper on 10/9/15.
 */
@Name("extendsDataCreator")
@AutoCreate
public class ExtendsDataCreator {

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
