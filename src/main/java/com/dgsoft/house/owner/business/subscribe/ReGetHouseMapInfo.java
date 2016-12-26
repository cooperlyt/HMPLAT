package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.model.House;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.HouseBusiness;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import javax.persistence.EntityManager;

/**
 * Created by cooper on 4/25/16.
 */
@Name("reGetHouseMapInfo")
public class ReGetHouseMapInfo {

    @In
    private OwnerBusinessHome ownerBusinessHome;

    @In(create = true)
    private EntityManager houseEntityManager;

    public void doReGet(){
        for(HouseBusiness houseBusiness: ownerBusinessHome.getInstance().getHouseBusinesses()){
            House house = houseEntityManager.find(House.class,houseBusiness.getHouseCode());
            if (house != null){
//                houseBusiness.getAfterBusinessHouse().setAddress(house.getAddress());
//                houseBusiness.getAfterBusinessHouse().setFloorCount(house.getBuild().getFloorCount());
//                houseBusiness.getAfterBusinessHouse().setUpFloorCount(house.getBuild().getUpFloorCount());
//                houseBusiness.getAfterBusinessHouse().setDownFloorCount(house.getBuild().getDownFloorCount());
//                houseBusiness.getAfterBusinessHouse().setHouseArea(house.getHouseArea());
//                houseBusiness.getAfterBusinessHouse().setInFloorName(house.getInFloorName());
//                houseBusiness.getAfterBusinessHouse().setStructure(house.getStructure());
//                houseBusiness.getAfterBusinessHouse().setHouseType(house.getHouseType());
//                //houseBusiness.getAfterBusinessHouse().setHouseOrder(house.getHouseOrder());
//                houseBusiness.getAfterBusinessHouse().setMapNumber(house.getBuild().getMapNumber());
//                houseBusiness.getAfterBusinessHouse().setBlockNo(house.getBuild().getBlockNo());
//                houseBusiness.getAfterBusinessHouse().setBuildNo(house.getBuildNo());
//                houseBusiness.getAfterBusinessHouse().setHouseUnitName(house.getHouseUnitName());
//                houseBusiness.getAfterBusinessHouse().setDoorNo(house.getDoorNo());
//                houseBusiness.getAfterBusinessHouse().setBuildType(house.getBuildType());
//                houseBusiness.getAfterBusinessHouse().setDeveloperName(house.getDeveloperName());
//                houseBusiness.getAfterBusinessHouse().setDeveloperCode(house.getDeveloperCode());
//                houseBusiness.getAfterBusinessHouse().setSectionCode(house.getSectionCode());
//                houseBusiness.getAfterBusinessHouse().setSectionName(house.getSectionName());
//                houseBusiness.getAfterBusinessHouse().setUseType(house.getUseType());

                houseBusiness.getAfterBusinessHouse().setHouseArea(house.getHouseArea());
                houseBusiness.getAfterBusinessHouse().setUseArea(house.getUseArea());
                houseBusiness.getAfterBusinessHouse().setCommArea(house.getCommArea());

                houseBusiness.getAfterBusinessHouse().setAddress(house.getAddress());
                houseBusiness.getAfterBusinessHouse().setInFloorName(house.getInFloorName());
                houseBusiness.getAfterBusinessHouse().setStructure(house.getStructure());
                houseBusiness.getAfterBusinessHouse().setHouseType(house.getHouseType());
                houseBusiness.getAfterBusinessHouse().setHouseOrder(house.getHouseOrder());

                houseBusiness.getAfterBusinessHouse().setBuildNo(house.getBuildNo());
                houseBusiness.getAfterBusinessHouse().setBlockNo(house.getBlockNo());
                houseBusiness.getAfterBusinessHouse().setMapNumber(house.getMapNumber());

                houseBusiness.getAfterBusinessHouse().setHouseUnitName(house.getHouseUnitName());
                houseBusiness.getAfterBusinessHouse().setDoorNo(house.getHouseUnitName());
                houseBusiness.getAfterBusinessHouse().setBuildType(house.getBuildType());
                houseBusiness.getAfterBusinessHouse().setDeveloperName(house.getDeveloperName());
                houseBusiness.getAfterBusinessHouse().setDeveloperCode(house.getDeveloperCode());
                houseBusiness.getAfterBusinessHouse().setSectionName(house.getSectionName());
                houseBusiness.getAfterBusinessHouse().setSectionCode(house.getSectionCode());
                houseBusiness.getAfterBusinessHouse().setUseType(house.getUseType());
                houseBusiness.getAfterBusinessHouse().setBuildName(house.getBuildName());
                houseBusiness.getAfterBusinessHouse().setDesignUseType(house.getDesignUseType());

                houseBusiness.getAfterBusinessHouse().setDesignUseType(house.getDesignUseType());
                if (house.getBuild()!=null) {
                    houseBusiness.getAfterBusinessHouse().setFloorCount(house.getBuild().getFloorCount());
                    houseBusiness.getAfterBusinessHouse().setUpFloorCount(house.getBuild().getUpFloorCount());
                    houseBusiness.getAfterBusinessHouse().setDownFloorCount(house.getBuild().getDownFloorCount());
                }
            }
        }
    }

}
