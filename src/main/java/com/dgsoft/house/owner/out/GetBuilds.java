package com.dgsoft.house.owner.out;

import com.dgsoft.common.helper.JsonDataProvider;
import com.dgsoft.house.HouseEntityLoader;
import com.dgsoft.house.model.Build;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.log.Logging;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import sun.rmi.runtime.Log;

import java.util.List;

/**
 * Created by cooper on 4/9/16.
 */

@Name("getBuilds")
@Scope(ScopeType.STATELESS)
public class GetBuilds implements JsonDataProvider.JsonDataProviderFunction{

    @RequestParameter
    private String projectId;

    @In(create = true)
    private HouseEntityLoader houseEntityLoader;

    @Override
    public String getJsonData() {
        List<Build> builds = houseEntityLoader.getEntityManager().createQuery("select build from Build build where build.project.id = :projectId", Build.class)
                .setParameter("projectId",projectId).getResultList();

        JSONArray result = new JSONArray();

        for (Build build: builds){
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("id",build.getId());
                jsonObject.put("mapNumber",build.getMapNumber());
                jsonObject.put("blockNumber",build.getBlockNo());
                jsonObject.put("buildNumber",build.getBuildNo());
                jsonObject.put("buildName",build.getName());
                result.put(jsonObject);
            } catch (JSONException e) {
                Logging.getLog(getClass()).error(e.getMessage(),e);
                return "";
            }
        }
        return result.toString();

    }
}
