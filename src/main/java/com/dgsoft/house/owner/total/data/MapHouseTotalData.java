package com.dgsoft.house.owner.total.data;

import java.math.BigDecimal;

/**
 * Created by cooper on 11/25/15.
 */
public class MapHouseTotalData {

    private  String sectionName;

    private String sectionId;

    private BigDecimal homeArea;

    private BigDecimal otherArea;

    private Long homeCount;

    private Long otherCount;

    private Long buildCount;

    public MapHouseTotalData(String sectionName, String sectionId,
                             BigDecimal homeArea, BigDecimal otherArea,
                             Long homeCount, Long otherCount, Long buildCount) {
        this.sectionName = sectionName;
        this.sectionId = sectionId;
        this.homeArea = homeArea;
        this.otherArea = otherArea;
        this.homeCount = homeCount;
        this.otherCount = otherCount;
        this.buildCount = buildCount;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getSectionId() {
        return sectionId;
    }

    public BigDecimal getHomeArea() {
        if (homeArea == null)
            return BigDecimal.ZERO;
        return homeArea;
    }

    public BigDecimal getOtherArea() {
        if (otherArea == null)
            return BigDecimal.ZERO;
        return otherArea;
    }

    public Long getHomeCount() {
        return homeCount;
    }

    public Long getOtherCount() {
        return otherCount;
    }

    public Long getBuildCount() {
        return buildCount;
    }
}
