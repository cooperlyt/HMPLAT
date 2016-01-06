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
        if (money == null){
            return BigDecimal.ZERO;
        }
        return money;
    }

    public Long getCount() {
        if (count == null){
            return Long.valueOf(0);
        }
        return count;
    }

    public BigDecimal getArea() {
        if (area == null){
            return BigDecimal.ZERO;
        }
        return area;
    }
}
