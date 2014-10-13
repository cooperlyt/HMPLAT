package com.dgsoft.house;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by cooper on 10/11/14.
 */
public interface HouseInfo extends BuildInfo {


    public String getHouseCode();

    public String getHouseOrder();

    public String getHouseUnitName();

    public String getInFloorName();

    public BigDecimal getHouseArea();

    public BigDecimal getPrepareArea();

    public BigDecimal getUseArea();

    public BigDecimal getCommArea();

    public BigDecimal getShineArea();

    public BigDecimal getLoftArea();

    public BigDecimal getCommParam();

    public int getHouseState();

    public String getHouseType();

    public String getUseType();

    public String getKnotSize();

    public String getAddress();

    public String getEastWall();

    public String getWestWall();

    public String getSouthWall();

    public String getNorthWall();

    public Date getMapTime();

    public String getDirection();

    public boolean isInitRegister();

    public boolean isFirmlyPower();

    public boolean isHaveDownRoom();
}
