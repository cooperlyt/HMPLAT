package com.dgsoft.house.owner.helper;

import org.jboss.seam.Component;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.web.RequestParameter;
import org.json.JSONObject;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;

/**
 * Created by cooper on 12/20/15.
 */
@Name("houseSaleRecordDataProvider")
public class HouseSaleRecordDataProvider {

    public enum DataType {
        TOTAL_INFO("newHouseRecordTotalDataProvider"), PROJECT_LIST("saleProjectDataProvider"),
        PROJECT_SALE_TOP_TEN("projectSaleTopTen"), PROJECT_INFO("projectSaleInfoProvider"),
        BUILD_INFO("buildSaleInfoProvider"),CONTRACT_SEARCH("contractInfoProvider"),
        HOUSE_STATUS_SEARCH("houseStatusProvider"),BUSINESS_SEARCH("businessInfoProvider");

        private String provider;

        public String getProvider() {
            return provider;
        }

        DataType(String provider) {
            this.provider = provider;
        }
    }

    @RequestParameter
    private String type;

    @In
    private FacesContext facesContext;


    public void renderJsonData() throws IOException {
        JSONObject data = ((RestDataProvider) Component.getInstance(DataType.valueOf(type).getProvider(), true)).getJsonData();

        ExternalContext externalContext = facesContext.getExternalContext();
        externalContext.setResponseContentType("application/json");
        externalContext.setResponseCharacterEncoding("UTF-8");
        if (data != null) {
            String jsonStr = data.toString();
            if (jsonStr == null) {
                jsonStr = "";
            }
            externalContext.getResponseOutputWriter().write(jsonStr);
        } else {
            externalContext.getResponseOutputWriter().write("{}");
        }
        facesContext.responseComplete();
    }


}
