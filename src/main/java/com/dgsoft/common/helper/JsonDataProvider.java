package com.dgsoft.common.helper;

import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.web.RequestParameter;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;

/**
 * Created by cooper on 4/9/16.
 */
@Name("jsonDataProvider")
@Scope(ScopeType.STATELESS)
public class JsonDataProvider {

    @In
    private FacesContext facesContext;


    public interface JsonDataProviderFunction {

        String getJsonData();
    }


    @RequestParameter
    private String functionName;

    public void renderJson() throws IOException {
        ExternalContext externalContext = facesContext.getExternalContext();
        externalContext.setResponseContentType("application/json");
        externalContext.setResponseCharacterEncoding("UTF-8");
        if (functionName == null || functionName.equals("")){
            functionName = "queueJsonDataProvider";
        }

        JsonDataProviderFunction dataFunction = (JsonDataProviderFunction)Component.getInstance(functionName,true,true);
        String jsonStr = dataFunction.getJsonData();
        if (jsonStr == null){
            jsonStr = "";
        }
        externalContext.getResponseOutputWriter().write(jsonStr);
        facesContext.responseComplete();
    }

}
