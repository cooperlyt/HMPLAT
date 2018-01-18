package com.dgsoft.house.owner.helper;

import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.model.BusinessHouse;
import com.dgsoft.house.owner.model.HouseContract;
import com.dgsoft.house.owner.model.PowerPerson;
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

            HouseContract houseContract = ownerEntityLoader.getEntityManager().
                    createQuery("select hc from OwnerBusiness  ob left join ob.houseContracts hc where hc.contractNumber = :cNumber", HouseContract.class)
                    .setParameter("cNumber", contractNumber).getSingleResult();
            BusinessHouse bh = houseContract.getBusinessHouse();
                for(PowerPerson pp: bh.getPowerPersons()){
                    if (personNumber.equals(pp.getCredentialsNumber())){
                        JSONObject result = new JSONObject();
                        try {
                            result.put("ownerName",bh.getMainOwner().getPersonName());
                            result.put("type",houseContract.getType().name());
                            result.put("buildName", bh.getBuildName());
                            result.put("address",bh.getAddress());
                            result.put("area",bh.getHouseArea().toString());
                            result.put("houseOrder",bh.getHouseOrder());
                            result.put("doorNumber",bh.getDoorNo());
                            result.put("inFloor",bh.getInFloorName());
                            result.put("unitName",bh.getHouseUnitName());
                            result.put("houseCode",bh.getHouseCode());
                            result.put("status",messages.get(houseContract.getOwnerBusiness().getStatus().name()));
                            result.put("businessId",houseContract.getOwnerBusiness().getId());
                            return result;
                        } catch (JSONException e) {
                            return null;
                        }
                    }
                }



        }catch (NoResultException e){
            return null;
        }
        return null;
    }
}
