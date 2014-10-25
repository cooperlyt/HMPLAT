package com.dgsoft.house;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by cooper on 10/11/14.
 */
public interface HouseInfo extends BuildInfo {

    //不可售，可售，已办产权,签约，备案，商品房预告登记，
    // 房屋转移预告登记，商品房预告抵押，屋屋转移预告抵押,
    // 抵押,在建工程抵押,异议,声明作废，查封,灭籍
    public enum HouseStatus{
        CANTSALE,SALEING,OWNERED,CONTRACTS,CONTRACTS_RECORD,SALE_REGISTER,
        DIVERT_REGISTER,SALE_MORTGAGE_REGISTER,DIVERT_MORTGAGE_REGISTER,
        PLEDGE,PROJECT_PLEDGE,DIFFICULTY,DECLARE_CANCEL,COURT_CLOSE,DESTORY;
    }

    public enum InitRegStatus{
        NOT_INIT_REG,INIT_REG_CONFIRM,INIT_REG;
    }

    public class StatusComparator implements Comparator<HouseStatus>{

        private static StatusComparator instance;

        public static StatusComparator getInstance(){
            if (instance == null){
                instance = new StatusComparator();
            }
            return instance;
        }

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

    public InitRegStatus getInitRegStatus();

    public boolean isHaveDownRoom();

    public HouseStatus getMasterStatus();

    public List<HouseStatus> getAllStatusList();
}
