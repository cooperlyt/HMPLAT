package com.dgsoft.house.owner.total.data;

import cc.coopersoft.house.UseType;
import com.dgsoft.house.UseTypeWordAdapter;
import com.dgsoft.house.model.House;
import com.dgsoft.house.owner.model.BusinessHouse;

import java.math.BigDecimal;

/**
 * Created by wxy on 2017-01-15.
 */
public class InitToData {

    private Long count ;

    private BigDecimal houseArea;

    private UseType useType;

    public InitToData(){
        count=Long.valueOf(0);
        houseArea = BigDecimal.ZERO;


    }

    public InitToData(Long count,BigDecimal houseArea,UseType useType){
        this.count = count;
        this.houseArea=houseArea;
        this.useType=useType;
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

    public UseType getUseType() {
        return useType;
    }

    public void setUseType(UseType useType) {
        this.useType = useType;
    }


}
