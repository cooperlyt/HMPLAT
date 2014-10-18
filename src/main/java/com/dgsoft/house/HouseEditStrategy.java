package com.dgsoft.house;

import com.dgsoft.house.model.House;

/**
 * Created by cooper on 10/18/14.
 */
public interface HouseEditStrategy {

    public static final String RUN_PARAM_NAME = "HouseEditStrategy";

    public boolean isCanEdit(House house);

}
