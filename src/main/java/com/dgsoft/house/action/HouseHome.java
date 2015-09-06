package com.dgsoft.house.action;

import com.dgsoft.house.HouseEntityHome;
import com.dgsoft.house.HouseInfo;
import com.dgsoft.house.HouseStatus;
import com.dgsoft.house.model.Build;
import com.dgsoft.house.model.House;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.Name;

/**
 * Created by cooper on 8/14/14.
 */
@Name("houseHome")
public class HouseHome extends HouseEntityHome<House> {

    @Factory("houseStatus")
    public HouseStatus[] getStatuses(){
        return HouseStatus.values();
    }
}
