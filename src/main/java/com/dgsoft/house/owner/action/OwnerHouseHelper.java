package com.dgsoft.house.owner.action;

import com.dgsoft.house.HouseInfo;
import com.dgsoft.house.HouseStatus;
import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.model.AddHouseStatus;
import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Logging;

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


    public List<HouseStatus> getHouseAllStatus(String houseCode) {
        List<HouseStatus> result = new ArrayList<HouseStatus>();


         for(AddHouseStatus status: ownerEntityLoader.getEntityManager().createQuery("select addHouseStatus from AddHouseStatus  addHouseStatus where addHouseStatus.houseBusiness.houseCode = :houseCode and (addHouseStatus.houseBusiness.ownerBusiness.status = 'COMPLETE' or addHouseStatus.houseBusiness.ownerBusiness.status = 'MODIFYING' or addHouseStatus.houseBusiness.ownerBusiness.status = 'COMPLETE_CANCEL' or (addHouseStatus.houseBusiness.ownerBusiness.status = 'RUNNING' and addHouseStatus.houseBusiness.ownerBusiness.recorded = true)) order by remove", AddHouseStatus.class).setParameter("houseCode",houseCode).getResultList()){
            if(status.isRemove()){

                if (!result.remove(status.getStatus())){
                    Logging.getLog(getClass()).warn("calc all house status remove fail.");
                }
            }else if (status.getStatus().isAllowRepeat() || !result.contains(status.getStatus())){
                result.add(status.getStatus());
            }
         }


        Collections.sort(result, new HouseStatus.StatusComparator());
        return result;
    }

    public HouseStatus getMasterStatus(String houseCode){
        List<HouseStatus> statuses = getHouseAllStatus(houseCode);
        if (statuses.isEmpty()){
           return null;
        }else
            return statuses.get(0);
    }


    public static OwnerHouseHelper instance() {
        return (OwnerHouseHelper) Component.getInstance(OwnerHouseHelper.class, true);
    }



}
