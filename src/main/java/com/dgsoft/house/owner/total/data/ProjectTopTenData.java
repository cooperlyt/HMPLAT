package com.dgsoft.house.owner.total.data;

import java.math.BigDecimal;

/**
 * Created by cooper on 12/21/15.
 */

public class ProjectTopTenData {

    private String id;

    private Long count;

    private BigDecimal moneyOrArea;

    public ProjectTopTenData(String id, Long count) {
        this.id = id;
        this.count = count;
    }

    public ProjectTopTenData(String id, BigDecimal moneyOrArea) {
        this.id = id;
        this.moneyOrArea = moneyOrArea;
    }

    public String getId() {
        return id;
    }

    public Long getCount() {
        if (count == null){
            return Long.valueOf(0);
        }
        return count;
    }

    public BigDecimal getMoneyOrArea() {
        if (moneyOrArea == null){
            return BigDecimal.ZERO;
        }
        return moneyOrArea;
    }
}
