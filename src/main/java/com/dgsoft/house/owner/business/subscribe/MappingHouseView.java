package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.HouseEntityLoader;
import com.dgsoft.house.model.House;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by cooper on 1/21/16.
 */
@Name("mappingHouseView")
public class MappingHouseView {

    @In
    private OwnerBusinessHome ownerBusinessHome;

    @In(create = true)
    private HouseEntityLoader houseEntityLoader;

    private House house;

    public House getHouse(){
        if (house == null){
           house = houseEntityLoader.getEntityManager().find(House.class,ownerBusinessHome.getSingleHoues().getHouseCode());

        }
        return house;
    }



}
