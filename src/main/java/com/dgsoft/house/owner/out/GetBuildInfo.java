package com.dgsoft.house.owner.out;

import com.dgsoft.common.helper.JsonDataProvider;
import com.dgsoft.common.system.DictionaryWord;
import com.dgsoft.house.HouseEntityLoader;
import com.dgsoft.house.owner.out.data.BuildInfoData;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.web.RequestParameter;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.NoResultException;

/**
 * Created by cooper on 4/9/16.
 */
@Name("getBuildInfo")
@Scope(ScopeType.STATELESS)
public class GetBuildInfo implements JsonDataProvider.JsonDataProviderFunction {

    @RequestParameter
    private String buildId;

    @In(create = true)
    private HouseEntityLoader houseEntityLoader;


    @Override
    public String getJsonData() {

        try {
            BuildInfoData data = houseEntityLoader.getEntityManager().createQuery("select new com.dgsoft.house.owner.out.data.BuildInfoData(build.mapNumber,build.blockNo,build.buildNo,build.name,build.structure,(select sum(house.houseArea) from House house where house.build.id = build.id), (select count(house.id) from House house where house.build.id = build.id) , build.upFloorCount,build.downFloorCount) from Build build where build.id = :buildId", BuildInfoData.class)
                    .setParameter("buildId", buildId).getSingleResult();

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("MapNumber",data.getMapNumber());
                jsonObject.put("BlockNumber", data.getBlockNumber());
                jsonObject.put("BuildNumber",data.getBuildNumber());
                jsonObject.put("BuildName",data.getBuildName());
                jsonObject.put("Structure", DictionaryWord.instance().getWordValue(data.getStructure()));
                jsonObject.put("")
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }catch (NoResultException e){

        }

        return null;
    }
}
