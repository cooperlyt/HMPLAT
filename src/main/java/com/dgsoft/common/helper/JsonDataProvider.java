package com.dgsoft.common.helper;

import com.dgsoft.common.system.AuthenticationInfo;
import com.dgsoft.house.owner.model.BusinessMoney;
import com.dgsoft.house.owner.model.FactMoneyInfo;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.log.Logging;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cooper on 10/9/15.
 */
@Name("jsonDataProvider")
@Scope(ScopeType.APPLICATION)
@AutoCreate
public class JsonDataProvider {

    @RequestParameter
    private Long id;

    @In
    private FacesContext facesContext;


    private Long index = new Long(0);

    private Map<Long,String> dataPool = new HashMap<Long, String>();

    public Long putData(String data){
        index ++;
        dataPool.put(index,data);
        return index;
    }


    public void renderJson() throws IOException {
        ExternalContext externalContext = facesContext.getExternalContext();
        externalContext.setResponseContentType("application/json");
        externalContext.setResponseCharacterEncoding("UTF-8");
        String jsonStr = dataPool.get(id);
        if (jsonStr == null){
            jsonStr = "";
        }
        externalContext.getResponseOutputWriter().write(jsonStr);
        facesContext.responseComplete();
        dataPool.remove(id);
    }







}
