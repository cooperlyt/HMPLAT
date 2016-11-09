package com.dgsoft.common.helper;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.web.RequestParameter;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by cooper on 10/9/15.
 */
@Name("queueJsonDataProvider")
@Scope(ScopeType.APPLICATION)
@AutoCreate
public class QueueJsonDataProvider implements JsonDataProvider.JsonDataProviderFunction {

    private final static int QUEUE_SIZE = 100;

    @RequestParameter
    private Long id;

    private Long index = new Long(0);

    private Map<Long,String> dataPool = new LinkedHashMap(){
        protected boolean removeEldestEntry(Map.Entry eldest) {
            return size() > QUEUE_SIZE;
        }
    };

    public Long putData(String data){
        index ++;
        dataPool.put(index,data);
        return index;
    }

    @Override
    public String getJsonData() {
        String jsonStr = dataPool.get(id);
        dataPool.remove(id);
        return jsonStr;
    }
}
