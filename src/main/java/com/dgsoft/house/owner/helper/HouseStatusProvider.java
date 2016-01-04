package com.dgsoft.house.owner.helper;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.web.RequestParameter;
import org.json.JSONObject;

/**
 * Created by cooper on 1/1/16.
 */
@Name("houseStatusProvider")
public class HouseStatusProvider implements RestDataProvider{

    @RequestParameter
    private String cardNumber;

    @RequestParameter
    private String personNumber;

    @Override
    public JSONObject getJsonData() {
        return null;
    }
}
