package com.dgsoft.house.owner.out.data;

import java.math.BigDecimal;

/**
 * Created by cooper on 4/9/16.
 */
public class BuildInfoData {

    private String mapNumber;

    private String blockNumber;

    private String buildNumber;

    private String buildName;

    private String structure;

    private BigDecimal houseArea;

    private Long houseCount;

    private Integer upFloorCount;

    private Integer downFloorCount;

    public BuildInfoData(String mapNumber, String blockNumber, String buildNumber, String buildName, String structure, BigDecimal houseArea, Long houseCount, Integer upFloorCount, Integer downFloorCount) {
        this.mapNumber = mapNumber;
        this.blockNumber = blockNumber;
        this.buildNumber = buildNumber;
        this.buildName = buildName;
        this.structure = structure;
        this.houseArea = houseArea;
        this.houseCount = houseCount;
        this.upFloorCount = upFloorCount;
        this.downFloorCount = downFloorCount;
    }

    public String getMapNumber() {
        return mapNumber;
    }

    public String getBlockNumber() {
        return blockNumber;
    }

    public String getBuildNumber() {
        return buildNumber;
    }

    public String getBuildName() {
        return buildName;
    }

    public String getStructure() {
        return structure;
    }

    public BigDecimal getHouseArea() {
        return houseArea;
    }

    public Long getHouseCount() {
        return houseCount;
    }

    public Integer getUpFloorCount() {
        return upFloorCount;
    }

    public Integer getDownFloorCount() {
        return downFloorCount;
    }
}
