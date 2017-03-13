package com.dgsoft.house.owner.total.data;

import com.dgsoft.house.SaleType;

import java.math.BigDecimal;

/**
 * Created by cooper on 11/03/2017.
 */
public class ContractTypeTotal extends HouseSaleTotalData {

    private SaleType saleType;

    public ContractTypeTotal(SaleType saleType, Long count, BigDecimal area, BigDecimal money) {
        super(count, area, money);
        this.saleType = saleType;
    }

    public SaleType getSaleType() {
        return saleType;
    }

    public void setSaleType(SaleType saleType) {
        this.saleType = saleType;
    }
}
