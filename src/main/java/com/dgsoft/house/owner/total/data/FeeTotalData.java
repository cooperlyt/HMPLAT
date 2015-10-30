package com.dgsoft.house.owner.total.data;

import java.math.BigDecimal;

/**
 * Created by cooper on 10/30/15.
 */
public class FeeTotalData {

    private String itemId;

    private String defineId;

    private BigDecimal factMoney;

    private Long count;

    public FeeTotalData() {
        factMoney = BigDecimal.ZERO;
        count = Long.valueOf(0);
    }

    public FeeTotalData(String defineId, BigDecimal factMoney, Long count,String itemId) {
        this.defineId = defineId;
        this.factMoney = factMoney;
        this.count = count;
        this.itemId = itemId;
    }

    public String getDefineId() {
        return defineId;
    }

    public BigDecimal getFactMoney() {
        return factMoney;
    }

    public Long getCount() {
        return count;
    }

    public String getItemId() {
        return itemId;
    }


    public void put(FeeTotalData other){
        this.factMoney = this.factMoney.add(other.factMoney);
        this.count = this.count + other.count;
    }



}
