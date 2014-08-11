package com.dgsoft.house.action;

import com.dgsoft.common.GBT;
import com.dgsoft.common.system.NumberBuilder;
import com.dgsoft.common.system.RunParam;
import com.dgsoft.common.system.model.NumberPool;
import com.dgsoft.common.system.model.SystemParam;
import com.dgsoft.house.HouseEntityHome;
import com.dgsoft.house.model.Build;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by cooper on 7/29/14.
 */
@Name("buildHome")
public class BuildHome extends HouseEntityHome<Build> {

    public boolean isHaveHouse() {
        return !getInstance().getHouses().isEmpty();
    }

    private String getBuildCode() {
        if (isManaged()) {
            return getInstance().getId().substring(0,21);
        } else {
            throw new IllegalArgumentException("build not manager!");
        }
    }


    public String genHouseOrder(int order) {
        String result = GBT.getJDJT246(getBuildCode(), order);
        getInstance().setNextHouseOrder(order + 1);
        return result;
    }


}
