package com.dgsoft.house.owner.total;

import com.dgsoft.common.system.model.BusinessDefine;

import java.math.BigDecimal;

/**
 * Created by cooper on 8/5/15.
 */
public class OwnerBusinessTotalData {

    private String defineId;

    private Long count;

    private BusinessDefine businessDefine;

    public OwnerBusinessTotalData(String defineId, Long count) {
        this.defineId = defineId;
        this.count = count;
    }

    public Long getCount() {
        return count;
    }

    public String getDefineId() {
        return defineId;
    }

    public BusinessDefine getBusinessDefine() {
        return businessDefine;
    }

    public void setBusinessDefine(BusinessDefine businessDefine) {
        this.businessDefine = businessDefine;
    }
}
