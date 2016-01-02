package com.dgsoft.house.owner.helper;

import com.dgsoft.house.HouseStatus;
import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.action.OwnerHouseHelper;
import com.dgsoft.house.owner.model.HouseRecord;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.web.RequestParameter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.NoResultException;

/**
 * Created by cooper on 1/1/16.
 */
@Name("houseStatusProvider")
public class HouseStatusProvider implements RestDataProvider{

    @RequestParameter
    private String cardNumber;

    @RequestParameter
    private String personNumber;

    @In(create = true)
    private OwnerEntityLoader ownerEntityLoader;

    @Override
    public JSONObject getJsonData() {
        if (cardNumber == null || cardNumber.trim().equals("") || personNumber == null || personNumber.trim().equals("")){
            return null;
        }

        try {
            HouseRecord hr = ownerEntityLoader.getEntityManager().createQuery("select hr from HouseRecord hr left join fetch hr.businessHouse where hr.businessHouse.businessHouseOwner.credentialsNumber = :credentialsNumber and hr.businessHouse.businessHouseOwner.makeCard.number = :cardNumber", HouseRecord.class)
                    .setParameter("credentialsNumber", personNumber)
                    .setParameter("cardNumber", cardNumber).getSingleResult();

            JSONObject resultObj = new JSONObject();

            JSONArray jsonArray = new JSONArray();
            for(HouseStatus status: OwnerHouseHelper.instance().getHouseAllStatus(hr.getHouseCode())){
                jsonArray.put(status.name());
            }
            try {
                resultObj.put("status",jsonArray);
                resultObj.put("buildName",hr.getBusinessHouse().getBuildName());
                resultObj.put("address",hr.getBusinessHouse().getAddress());
                resultObj.put("area",hr.getBusinessHouse().getHouseArea().toString());
                resultObj.put("houseOrder",hr.getBusinessHouse().getHouseOrder());
                resultObj.put("doorNumber",hr.getBusinessHouse().getDoorNo());
                resultObj.put("inFloor",hr.getBusinessHouse().getInFloorName());
                resultObj.put("unitName",hr.getBusinessHouse().getHouseUnitName());
                resultObj.put("houseCode",hr.getHouseCode());

                return resultObj;
            } catch (JSONException e) {
                return null;
            }


        } catch (NoResultException e){
            return null;
        }

    }



}
