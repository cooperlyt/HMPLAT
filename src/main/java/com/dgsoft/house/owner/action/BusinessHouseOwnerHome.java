package com.dgsoft.house.owner.action;

import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.model.BusinessHouseOwner;
import org.jboss.seam.annotations.Name;

/**
 * Created by cooper on 8/27/14.
 */
@Name("businessHouseOwnerHome")
public class BusinessHouseOwnerHome extends OwnerEntityHome<BusinessHouseOwner> {



    private HouseOwnerEntityHelper houseOwnerEntityHelper;

    @Override
    public void create() {
        super.create();
        houseOwnerEntityHelper = new HouseOwnerEntityHelper(this);
    }


    public HouseOwnerEntityHelper getHouseOwnerEntityHelper() {
        return houseOwnerEntityHelper;
    }
}
