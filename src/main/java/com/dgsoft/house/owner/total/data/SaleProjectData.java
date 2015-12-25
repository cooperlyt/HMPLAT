package com.dgsoft.house.owner.total.data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by cooper on 12/22/15.
 */
public class SaleProjectData {

    private String id;

    private Long houseCount;

    private Long buildCount;

    private BigDecimal houseArea;

    private Date regDate;

    public SaleProjectData(String id, Long houseCount, Long buildCount, BigDecimal houseArea, Date regDate) {
        this.id = id;
        this.houseCount = houseCount;
        this.buildCount = buildCount;
        this.houseArea = houseArea;
        this.regDate = regDate;
    }


    public Date getRegDate() {
        return regDate;
    }

    public String getId() {
        return id;
    }

    public Long getHouseCount() {
        return houseCount;
    }

    public Long getBuildCount() {
        return buildCount;
    }

    public BigDecimal getHouseArea() {
        return houseArea;
    }
}
