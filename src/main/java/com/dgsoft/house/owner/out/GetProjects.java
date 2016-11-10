package com.dgsoft.house.owner.out;

import com.dgsoft.common.helper.JsonDataProvider;
import com.dgsoft.house.HouseEntityLoader;
import com.dgsoft.house.model.Project;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.log.Logging;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by cooper on 4/9/16.
 */

@Name("getProjects")
@Scope(ScopeType.STATELESS)
public class GetProjects implements JsonDataProvider.JsonDataProviderFunction{

    @RequestParameter
    private String developerId;

    @In(create = true)
    private HouseEntityLoader houseEntityLoader;

    @Override
    public String getJsonData() {
        List<Project> projects = houseEntityLoader.getEntityManager().createQuery("select project from Project project where project.developer.id = :developerId", Project.class)
                .setParameter("developerId",developerId).getResultList();

        JSONArray jsonArray = new JSONArray();
        for(Project project: projects){
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("id",project.getId());
                jsonObject.put("name",project.getName());
                jsonArray.put(jsonObject);
            } catch (JSONException e) {
                Logging.getLog(getClass()).debug(e.getMessage(),e);
                return "";
            }
        }
        return jsonArray.toString();
    }
}
