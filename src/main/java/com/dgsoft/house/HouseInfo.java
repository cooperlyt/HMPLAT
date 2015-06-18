package com.dgsoft.house;

import com.dgsoft.common.DataFormat;

import javax.persistence.criteria.Order;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by cooper on 10/11/14.
 */
public interface HouseInfo extends BuildInfo {
    //-- 不可售 CANTSALE 签约 CONTRACTS
    // 备案，已办产权，商品房预告登记，
    // 房屋转移预告登记，商品房预告抵押，屋屋转移预告抵押,
    // 抵押,在建工程抵押,异议,声明作废，查封,灭籍
    // 确权,初始登记
    public enum HouseStatus{
        CONTRACTS_RECORD,OWNERED,SALE_REGISTER,
        DIVERT_REGISTER,SALE_MORTGAGE_REGISTER,DIVERT_MORTGAGE_REGISTER,
        PLEDGE,PROJECT_PLEDGE,DIFFICULTY,DECLARE_CANCEL,COURT_CLOSE,DESTROY,
        INIT_REG_CONFIRM,INIT_REG
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

    public class OrderComparator implements Comparator<String>{

        private static OrderComparator instance;

        public static OrderComparator getInstance(){
            if (instance == null){
                instance = new OrderComparator();
            }
            return instance;
        }

        @Override
        public int compare(String o1, String o2) {
            String number1 = o1.trim();
            String number2 = o2.trim();

            if (number1 == null){
                number1 = "";
            }
            if (number2 == null){
                number2 = "";
            }
            if (number1.equals(number2)){
                return 0;
            }
            if ((number1.equals("")) && (number2.equals(""))){
                return 0;
            }
            if (number2.equals("")){
                return 1;
            }
            if (number1.equals("")){
                return -1;
            }

            Integer i1 = DataFormat.strToInt(number1);
            Integer i2 = DataFormat.strToInt(number2);


            if ((i1 == null) && (i2 == null)){
                return 0;
            }
            if (i2 == null){
                return 1;
            }
            if (i1 == null){
                return -1;
            }

            return new Integer(i1).compareTo(i2);
        }
    }

    public String getHouseCode();

    public String getHouseOrder();

    public String getHouseUnitName();

    public String getInFloorName();

    public BigDecimal getHouseArea();

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


    public boolean isHaveDownRoom();





    public String getDisplayHouseCode();

    //public LockStatus getLockStatus();
}
