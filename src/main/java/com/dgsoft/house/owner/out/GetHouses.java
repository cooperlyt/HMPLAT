package com.dgsoft.house.owner.out;

import com.dgsoft.common.helper.JsonDataProvider;
import com.dgsoft.house.HouseEntityLoader;
import com.dgsoft.house.model.GridBlock;
import com.dgsoft.house.model.House;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.log.Logging;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

/**
 * Created by cooper on 4/9/16.
 */

@Name("getHouses")
@Scope(ScopeType.STATELESS)
public class GetHouses implements JsonDataProvider.JsonDataProviderFunction {

    @RequestParameter
    private String buildId;

    @In(create = true)
    private HouseEntityLoader houseEntityLoader;


    @Override
    public String getJsonData() {

        List<House> result = houseEntityLoader.getEntityManager().createQuery("select house from House house where house.deleted = false and house.build.id = :buildId", House.class)
                .setParameter("buildId",buildId)
                .getResultList();

        Set<String> ids = new HashSet<String>();

        for(House house: result){
            ids.add(house.getId());
        }

        List<GridBlock> gridBlocks = houseEntityLoader.getEntityManager().createQuery("select block from GridBlock block where block.houseCode in (:houseCode)", GridBlock.class)
                .setParameter("houseCode",new ArrayList<String>(ids)).getResultList();

        Map<String,GridBlock> gridBlockMap = new HashMap<String, GridBlock>();
        for(GridBlock gridBlock: gridBlocks){
            gridBlockMap.put(gridBlock.getHouseCode(),gridBlock);
        }

        JSONArray jsonArray = new JSONArray();
        for(House house: result){
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("mapNumber",house.getMapNumber());
                jsonObject.put("blockNumber",house.getBlockNo());
                jsonObject.put("buildNumber",house.getBuildNo());
                jsonObject.put("houseOrder",house.getHouseOrder());
                jsonObject.put("houseArea",house.getHouseArea().doubleValue());
                jsonObject.put("useArea",house.getUseArea().doubleValue());
                jsonObject.put("commParam",house.getCommParam().doubleValue());
                GridBlock gridBlock = gridBlockMap.get(house.getId());
                jsonObject.put("row",(gridBlock == null) ? 0 : gridBlock.getGridRow().getFloorIndex());

                jsonObject.put("col",(gridBlock == null) ? 0 : gridBlock.getOrder());

                jsonArray.put(jsonObject);
            } catch (JSONException e) {
                Logging.getLog(getClass()).error(e.getMessage(),e);
                return "";
            }
        }

        return jsonArray.toString();
    }
}
