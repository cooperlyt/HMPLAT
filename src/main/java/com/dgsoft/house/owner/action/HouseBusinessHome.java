package com.dgsoft.house.owner.action;

import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.model.BusinessHouse;
import com.dgsoft.house.owner.model.HouseBusiness;
import org.jboss.seam.annotations.Name;

import java.util.Set;

/**
 * Created by cooper on 8/25/14.
 */
@Name("houseBusinessHome")
public class HouseBusinessHome extends OwnerEntityHome<HouseBusiness> {

    private static final String BUSINESS_START_PAGE = "/business/houseOwner/BizStartSubscribe.xhtml";


    private String selectHouseId;


    public String getSelectHouseId() {
        return selectHouseId;
    }

    public void setSelectHouseId(String selectHouseId) {
        this.selectHouseId = selectHouseId;
    }

    public String singleHouseSelectet() {

        return BUSINESS_START_PAGE;
    }


    public String mulitHouseSelect() {


        return BUSINESS_START_PAGE;
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
