package com.dgsoft.house;

/**
 * Created by cooper on 10/11/14.
 */
public interface BuildInfo extends ProjectInfo {

    public String getBuildName();

    public String getBuildCode();

    public String getLandBlockCode();

    public String getStreetCode();

    public String getMapNumber();

    public String getBlockNo();

    public String getBuildNo();

    public String getDoorNo();

    public int getFloorCount();

    public int getUpFloorCount();

    public int getDownFloorCount();

    public String getBuildType();

    public String getStructure();

}
