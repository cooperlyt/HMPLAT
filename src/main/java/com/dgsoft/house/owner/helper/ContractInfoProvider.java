package com.dgsoft.house.owner.helper;

import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.model.ContractOwner;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.web.RequestParameter;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.NoResultException;
import java.util.Map;

/**
 * Created by cooper on 1/1/16.
 */
@Name("contractInfoProvider")
public class ContractInfoProvider implements RestDataProvider {

    @RequestParameter
    private String contractNumber;

    @RequestParameter
    private String personNumber;

    @In(create = true)
    private OwnerEntityLoader ownerEntityLoader;

    @In(create = true)
    private Map<String,String> messages;

    @Override
    public JSONObject getJsonData() {
        if (contractNumber == null || contractNumber.trim().equals("") || personNumber == null || personNumber.trim().equals("")){
            return null;
        }

        try {
            ContractOwner contractOwner = ownerEntityLoader.getEntityManager().createQuery("select co from ContractOwner co left join fetch co.ownerBusiness where co.credentialsNumber = :credentialsNumber and (co.contractNumber =:cNumber)",ContractOwner.class)
                    .setParameter("credentialsNumber", personNumber)
                    .setParameter("cNumber", contractNumber).getSingleResult();
            JSONObject result = new JSONObject();
            try {
                result.put("ownerName",contractOwner.getPersonName());
                result.put("type",contractOwner.getType());
                result.put("buildName", contractOwner.getBusinessHouse().getBuildName());
                result.put("address",contractOwner.getBusinessHouse().getAddress());
                result.put("area",contractOwner.getBusinessHouse().getHouseArea().toString());
                result.put("houseOrder",contractOwner.getBusinessHouse().getHouseOrder());
                result.put("doorNumber",contractOwner.getBusinessHouse().getDoorNo());
                result.put("inFloor",contractOwner.getBusinessHouse().getInFloorName());
                result.put("unitName",contractOwner.getBusinessHouse().getHouseUnitName());
                result.put("houseCode",contractOwner.getHouseCode());
                result.put("status",messages.get(contractOwner.getOwnerBusiness().getStatus().name()));
                result.put("businessId",contractOwner.getOwnerBusiness().getId());
                return result;
            } catch (JSONException e) {
                return null;
            }

        }catch (NoResultException e){
            return null;
        }
    }
}
