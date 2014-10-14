package com.dgsoft.house.owner.model;
// Generated Oct 11, 2014 3:13:15 PM by Hibernate Tools 4.0.0


import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * LandInfo generated by hbm2java
 */
@Entity
@Table(name = "LAND_INFO"
        , catalog = "HOUSE_OWNER_RECORD"
)
public class LandInfo implements java.io.Serializable {

    public enum LandInfoType{
        NOW_LAND_INFO,NEW_LAND_INFO;
    }

    private String id;
    private HouseBusiness houseBusiness;
    private String landCardNo;
    private String number;
    private String landProperty;
    private Date beginUseTime;
    private Date endUseTime;
    private BigDecimal landArea;
    private String landGetMode;
    private LandInfoType type;
    //private Set<HouseRecord> houseRecords = new HashSet<HouseRecord>(0);

    public LandInfo() {
    }


    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BUSINESS")
    public HouseBusiness getHouseBusiness() {
        return this.houseBusiness;
    }

    public void setHouseBusiness(HouseBusiness houseBusiness) {
        this.houseBusiness = houseBusiness;
    }

    @Column(name = "LAND_CARD_NO", length = 50)
    @Size(max = 50)
    public String getLandCardNo() {
        return this.landCardNo;
    }

    public void setLandCardNo(String landCardNo) {
        this.landCardNo = landCardNo;
    }


    @Column(name = "NUMBER", nullable = false, length = 50)
    @NotNull
    @Size(max = 50)
    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


    @Column(name = "LAND_PROPERTY", length = 32)
    @Size(max = 32)
    public String getLandProperty() {
        return this.landProperty;
    }

    public void setLandProperty(String landProperty) {
        this.landProperty = landProperty;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "BEGIN_USE_TIME", nullable = false, length = 19)
    @NotNull
    public Date getBeginUseTime() {
        return this.beginUseTime;
    }

    public void setBeginUseTime(Date beginUseTime) {
        this.beginUseTime = beginUseTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "END_USE_TIME", nullable = false, length = 19)
    @NotNull
    public Date getEndUseTime() {
        return this.endUseTime;
    }

    public void setEndUseTime(Date endUseTime) {
        this.endUseTime = endUseTime;
    }


    @Column(name = "LAND_AREA", precision = 18, scale = 3)
    public BigDecimal getLandArea() {
        return this.landArea;
    }

    public void setLandArea(BigDecimal landArea) {
        this.landArea = landArea;
    }


    @Column(name = "LAND_GET_MODE", nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    public String getLandGetMode() {
        return this.landGetMode;
    }

    public void setLandGetMode(String landGetMode) {
        this.landGetMode = landGetMode;
    }


    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE", nullable = false, length = 20)
    @NotNull
    public LandInfoType getType() {
        return this.type;
    }

    public void setType(LandInfoType type) {
        this.type = type;
    }


}

