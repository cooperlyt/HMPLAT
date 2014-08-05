package com.dgsoft.house.action;

import com.dgsoft.house.HouseEntityHome;
import com.dgsoft.house.model.Build;
import org.jboss.seam.annotations.Name;

/**
 * Created by cooper on 7/29/14.
 */
@Name("buildHome")
public class BuildHome extends HouseEntityHome<Build> {

    public boolean isHaveHouse(){
        return !getInstance().getHouses().isEmpty();
    }

}
