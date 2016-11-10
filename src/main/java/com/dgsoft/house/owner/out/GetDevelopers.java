package com.dgsoft.house.owner.out;

import com.dgsoft.common.helper.JsonDataProvider;
import com.dgsoft.house.HouseEntityLoader;
import com.dgsoft.house.model.Developer;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Logging;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by cooper on 4/9/16.
 */

@Name("getDevelopers")
@Scope(ScopeType.STATELESS)
public class GetDevelopers implements JsonDataProvider.JsonDataProviderFunction{

    @In(create = true)
    private HouseEntityLoader houseEntityLoader;

    @Override
    public String getJsonData() {
        List<Developer> developers = houseEntityLoader.getEntityManager().createQuery("select developer from Developer developer where destroyed = false", Developer.class)
                .getResultList();
        JSONArray result = new JSONArray();
        for(Developer developer: developers){
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("id",developer.getId());
                jsonObject.put("name",developer.getName());
                result.put(jsonObject);
            } catch (JSONException e) {
                Logging.getLog(getClass()).debug(e.getMessage(),e);
                return "";
            }
        }

        return result.toString();
    }
}
