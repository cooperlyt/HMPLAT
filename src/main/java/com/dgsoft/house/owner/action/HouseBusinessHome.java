package com.dgsoft.house.owner.action;

import com.dgsoft.common.system.action.BusinessDefineHome;
import com.dgsoft.house.HouseEntityLoader;
import com.dgsoft.house.model.House;
import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.model.BusinessHouse;
import com.dgsoft.house.owner.model.HouseBusiness;
import com.dgsoft.house.owner.model.OwnerBusiness;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Logging;

import java.util.Date;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Created by cooper on 8/25/14.
 */
@Name("houseBusinessHome")
public class HouseBusinessHome extends OwnerEntityHome<HouseBusiness> {

    @Override
    public HouseBusiness createInstance(){
        return new HouseBusiness(OwnerBusiness.BusinessSource.BIZ_CAREATE,new Date(),
                OwnerBusiness.BusinessStatus.RUNNING,new Date(),new Date());
    }



    public BusinessHouse getSingleHoues() {

        Set<BusinessHouse> businessHouses = getInstance().getBusinessHouses();
        if (businessHouses.size() > 1) {
            throw new IllegalArgumentException("HouseBusiness count > 1");
        } else if (businessHouses.size() == 1) {
            return businessHouses.iterator().next();
        } else
            return null;

    }

}
