package com.dgsoft.house.owner.helper;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.web.RequestParameter;
import org.json.JSONObject;

/**
 * Created by cooper on 12/28/15.
 */
@Name("projectSaleInfoProvider")
public class ProjectSaleInfoProvider implements RestDataProvider {


    @RequestParameter
    private String id;

    @Override
    public JSONObject getJsonData() {
        return null;
    }




}
