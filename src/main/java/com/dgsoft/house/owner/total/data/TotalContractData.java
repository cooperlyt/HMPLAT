package com.dgsoft.house.owner.total.data;

import java.math.BigDecimal;

/**
 * Created by wxy on 2015-11-18.
 * 备案合同统计（包括网签）
 */
public class TotalContractData {

    private String developerName;//开发商名称

    private String sectionName;//小区名称

    private BigDecimal sumPrice;//购房款

    private BigDecimal houseArea;//建筑面积

    private Long count;//套数


    public TotalContractData(String developerName,String sectionName,Long count,BigDecimal sumPrice,BigDecimal houseArea) {
        this.developerName = developerName;
        this.sectionName=sectionName;
        this.sumPrice = sumPrice;
        this.houseArea = houseArea;
        this.count = count;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getDeveloperName() {
        return developerName;
    }

    public void setDeveloperName(String developerName) {
        this.developerName = developerName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public BigDecimal getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(BigDecimal sumPrice) {
        this.sumPrice = sumPrice;
    }

    public BigDecimal getHouseArea() {
        return houseArea;
    }

    public void setHouseArea(BigDecimal houseArea) {
        this.houseArea = houseArea;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
