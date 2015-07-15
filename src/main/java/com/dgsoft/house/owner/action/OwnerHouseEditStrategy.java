package com.dgsoft.house.owner.action;

import com.dgsoft.house.HouseEditStrategy;
import com.dgsoft.house.model.House;
import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.model.LockedHouse;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

/**
 * Created by cooper on 10/18/14.
 */

@Name("ownerHouseEditStrategy")
@Scope(ScopeType.STATELESS)
public class OwnerHouseEditStrategy implements HouseEditStrategy {

    @In(create = true)
    private OwnerEntityLoader ownerEntityLoader;

    @Override
    public boolean isCanEdit(House house) {

        if (ownerEntityLoader.getEntityManager().
                createQuery("select count(biz.id) from HouseBusiness biz where biz.ownerBusiness.status != 'ABORT' and biz.ownerBusiness.status != 'CANCEL' and biz.startBusinessHouse.houseCode = :houseCode", Long.class)
                .setParameter("houseCode", house.getId()).getSingleResult().compareTo(new Long(0)) > 0 ){
            return false;
        }


        if (ownerEntityLoader.getEntityManager().find(LockedHouse.class,house.getId()) != null){
            return false;
        }

        return true;
   }

    @Override
    public boolean isCanEditArea(House house) {
        //初始登记 确权 
        return false;
    }


}
