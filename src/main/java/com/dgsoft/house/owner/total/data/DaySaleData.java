package com.dgsoft.house.owner.total.data;

import java.math.BigDecimal;

/**
 * Created by cooper on 11/25/15.
 */
public class DaySaleData {

    private String businessId;

    private String houseCode;

    private String address;

    private BigDecimal area;

    private BigDecimal money;

    private String sectionName;

    public DaySaleData(String businessId, String houseCode, String address, BigDecimal area, BigDecimal money,String sectionName) {
        this.businessId = businessId;
        this.houseCode = houseCode;
        this.address = address;
        this.area = area;
        this.money = money;
        this.sectionName = sectionName;
    }

    public String getBusinessId() {
        return businessId;
    }

    public String getHouseCode() {
        return houseCode;
    }

    public BigDecimal getArea() {
        return area;
    }

    public String getAddress() {
        return address;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public String getSectionName() {
        return sectionName;
    }
}
