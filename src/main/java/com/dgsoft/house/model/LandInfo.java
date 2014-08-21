package com.dgsoft.house.model;

import com.dgsoft.house.owner.model.BusinessProject;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by cooper on 8/19/14.
 */
@Entity
@Table(name = "LAND_INFO", catalog = "HOUSE_INFO")
public class LandInfo implements java.io.Serializable{

    private String id;
    private String landCardNo;
    private String number;
    private String landProperty;
    private Date beginUseTime;
    private Date endUseTime;
    private BigDecimal area;
    private String landGetMode;
    private BusinessProject businessProject;
    private House house;

    public LandInfo() {
    }

    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "LAND_CARD_NO", length = 50)
    @Size(max = 50)
    public String getLandCardNo() {
        return this.landCardNo;
    }

    public void setLandCardNo(String landCardNo) {
        this.landCardNo = landCardNo;
    }

    @Column(name = "NUMBER", length = 50)
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

    @Column(name = "AREA", precision = 18, scale = 3)
    public BigDecimal getArea() {
        return this.area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    @Column(name = "LAND_GET_MODE", length = 32)
    @Size(max = 32)
    public String getLandGetMode() {
        return this.landGetMode;
    }

    public void setLandGetMode(String landGetMode) {
        this.landGetMode = landGetMode;
    }

}
