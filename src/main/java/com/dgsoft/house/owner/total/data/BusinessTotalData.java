package com.dgsoft.house.owner.total.data;

import java.math.BigDecimal;

/**
 * Created by wxy on 2015-11-14.
 * 业务数据统计
 */
public class BusinessTotalData {

    private String name;//业务名称

    private String bidId;//业务ID

    private BigDecimal sumPrice;//购房款

    private BigDecimal assessmentPrice;//评估价格

    private BigDecimal houseArea;//建筑面积

    private Long count;

    public BusinessTotalData() {
        sumPrice = BigDecimal.ZERO;
        assessmentPrice=BigDecimal.ZERO;
        houseArea=BigDecimal.ZERO;
        count = Long.valueOf(0);
    }

    public BusinessTotalData(String bidId,String name,Long count,BigDecimal sumPrice,BigDecimal assessmentPrice,
                             BigDecimal houseArea) {
        this.name = name;
        this.bidId = bidId;
        this.sumPrice = sumPrice;
        this.assessmentPrice = assessmentPrice;
        this.houseArea = houseArea;
        this.count = count;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public BigDecimal getHouseArea() {
        return houseArea;
    }

    public void setHouseArea(BigDecimal houseArea) {
        this.houseArea = houseArea;
    }

    public BigDecimal getAssessmentPrice() {
        return assessmentPrice;
    }

    public void setAssessmentPrice(BigDecimal assessmentPrice) {
        this.assessmentPrice = assessmentPrice;
    }

    public BigDecimal getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(BigDecimal sumPrice) {
        this.sumPrice = sumPrice;
    }

    public String getBidId() {
        return bidId;
    }

    public void setBidId(String bidId) {
        this.bidId = bidId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
