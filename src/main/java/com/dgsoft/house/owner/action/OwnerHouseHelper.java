package com.dgsoft.house.owner.action;

import com.dgsoft.house.HouseInfo;
import com.dgsoft.house.owner.OwnerEntityLoader;
import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by cooper on 8/11/15.
 */

@Name("ownerHouseHelper")
@Scope(ScopeType.STATELESS)
public class OwnerHouseHelper {


    @In(create = true)
    private OwnerEntityLoader ownerEntityLoader;


    public List<HouseInfo.HouseStatus> getHouseAllStatus(String houseCode) {
        List<HouseInfo.HouseStatus> result = new ArrayList<HouseInfo.HouseStatus>();


         for(HouseInfo.HouseStatus status: ownerEntityLoader.getEntityManager().createQuery("select addHouseStatus.status from AddHouseStatus  addHouseStatus where addHouseStatus.houseBusiness.houseCode = :houseCode and (addHouseStatus.houseBusiness.ownerBusiness.status = 'COMPLETE' or (addHouseStatus.houseBusiness.ownerBusiness.status = 'RUNNING' and addHouseStatus.houseBusiness.ownerBusiness.recorded = true))", HouseInfo.HouseStatus.class).setParameter("houseCode",houseCode).getResultList()){
            if (status.isAllowRepeat() || !result.contains(status)){
                result.add(status);
            }
         }


        Collections.sort(result, new HouseInfo.StatusComparator());
        return result;
    }

    public HouseInfo.HouseStatus getMasterStatus(String houseCode){
        List<HouseInfo.HouseStatus> statuses = getHouseAllStatus(houseCode);
        if (statuses.isEmpty()){
           return null;
        }else
            return statuses.get(0);
    }


    public static OwnerHouseHelper instance() {
        return (OwnerHouseHelper) Component.getInstance(OwnerHouseHelper.class, true);
    }



}
