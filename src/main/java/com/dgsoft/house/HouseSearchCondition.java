package com.dgsoft.house;

import org.jboss.seam.annotations.Name;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-8-14
 * Time: 下午4:32
 * To change this template use File | Settings | File Templates.
 */
@Name("houseSearchCondition")
public class HouseSearchCondition {
    private String houseId;
    private String houseOrder;
    private String houseAddres;
    private String doorNo;

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getHouseOrder() {
        return houseOrder;
    }

    public void setHouseOrder(String houseOrder) {
        this.houseOrder = houseOrder;
    }

    public String getHouseAddres() {
        return houseAddres;
    }

    public void setHouseAddres(String houseAddres) {
        this.houseAddres = houseAddres;
    }

    public String getDoorNo() {
        return doorNo;
    }

    public void setDoorNo(String doorNo) {
        this.doorNo = doorNo;
    }
}
