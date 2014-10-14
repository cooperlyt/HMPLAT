package com.dgsoft.house.owner.action;

import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.model.HouseBusiness;
import com.dgsoft.house.owner.model.OwnerBusiness;
import org.jboss.seam.annotations.Name;

import java.util.Date;
import java.util.Set;

/**
 * Created by cooper on 8/25/14.
 */
@Name("ownerBusinessHome")
public class OwnerBusinessHome extends OwnerEntityHome<OwnerBusiness> {

    @Override
    public OwnerBusiness createInstance(){
        return new OwnerBusiness(OwnerBusiness.BusinessSource.BIZ_CAREATE,new Date(),
                OwnerBusiness.BusinessStatus.RUNNING,new Date(),new Date());
    }



    public HouseBusiness getSingleHoues() {

        Set<HouseBusiness> houseBusinesses = getInstance().getHouseBusinesses();
        if (houseBusinesses.size() > 1) {
            throw new IllegalArgumentException("HouseBusiness count > 1");
        } else if (houseBusinesses.size() == 1) {
            return houseBusinesses.iterator().next();
        } else
            return null;

    }


}
