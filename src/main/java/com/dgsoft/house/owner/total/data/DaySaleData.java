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

    private BigDecimal assessmentPrice;

    private String ownerAddress;

    public BigDecimal getAssessmentPrice() {
        return assessmentPrice;
    }

    public void setAssessmentPrice(BigDecimal assessmentPrice) {
        this.assessmentPrice = assessmentPrice;
    }

    public DaySaleData(String businessId, String houseCode, String address, BigDecimal area, BigDecimal money,String sectionName,BigDecimal assessmentPrice) {
        this.businessId = businessId;
        this.houseCode = houseCode;
        this.address = address;
        this.area = area;
        this.money = money;
        this.sectionName = sectionName;
        this.assessmentPrice=assessmentPrice;
    }

    public DaySaleData(String businessId, String houseCode, String address, BigDecimal area, BigDecimal money, String sectionName, BigDecimal assessmentPrice, String ownerAddress) {
        this.businessId = businessId;
        this.houseCode = houseCode;
        this.address = address;
        this.area = area;
        this.money = money;
        this.sectionName = sectionName;
        this.assessmentPrice = assessmentPrice;
        this.ownerAddress = ownerAddress;
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

    public String getOwnerAddress() {
        return ownerAddress;
    }
}
