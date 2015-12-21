package com.dgsoft.house.owner.helper;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.web.RequestParameter;
import org.json.JSONObject;

/**
 * Created by cooper on 12/20/15.
 */
@Name("salePrjectDataProvider")
public class SaleProjectDataProvider implements RestDataProvider{

    @RequestParameter
    private String searchKey;


    @RequestParameter
    private int firstResult;


    @RequestParameter
    private String order;

    @Override
    public JSONObject getJsonData() {
        return null;
    }
}
