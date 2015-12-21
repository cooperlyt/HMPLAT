package com.dgsoft.house.owner.total.data;

import java.math.BigDecimal;

/**
 * Created by cooper on 12/20/15.
 */
public class HouseSaleTotalData {

    private Long count;

    private BigDecimal area;

    private BigDecimal money;

    public HouseSaleTotalData(Long count, BigDecimal area) {
        this.count = count;
        this.area = area;
    }

    public HouseSaleTotalData(Long count, BigDecimal area, BigDecimal money) {
        this.count = count;
        this.area = area;
        this.money = money;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public Long getCount() {
        return count;
    }

    public BigDecimal getArea() {
        return area;
    }
}
