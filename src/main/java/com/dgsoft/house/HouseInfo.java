package com.dgsoft.house;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by cooper on 10/11/14.
 */
public interface HouseInfo extends BuildInfo {

    public enum HouseStatus{
        CANTSALE,SALEING,OWNERED;
    }

    public class StatusComparator implements Comparator<HouseStatus>{

        @Override
        public int compare(HouseStatus o1, HouseStatus o2) {
            //TODO compare
            return 0;
        }
    }

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

    public HouseStatus getMasterStatus();

    public List<HouseStatus> getAllStatusList();
}
