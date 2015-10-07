package com.dgsoft.common.system.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by Administrator on 15-7-25.
 */
@Entity
@Table(name = "FEE_TIME_AREA",catalog = "DB_PLAT_SYSTEM")
public class FeeTimeArea implements java.io.Serializable {

    private String id;
    private Fee fee;
    private Date beginTime;
    private Date endTime;
    private String feeEl;
    private String detailsEl;
    private String description;
    private String forEachValues;
    private String forEachVar;

    public FeeTimeArea(){

    }


    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "FEE",nullable = false)
    public Fee getFee() {
        return fee;
    }

    public void setFee(Fee fee) {
        this.fee = fee;
    }


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "BEGIN_TIME", nullable = false, length = 19)
    @NotNull
    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "END_TIME", nullable = false, length = 19)
    @NotNull
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }


    @Column(name = "FEE_EL",nullable = false,length = 512)
    @NotNull
    @Size(max=500)
    public String getFeeEl() {
        return feeEl;
    }

    public void setFeeEl(String feeEl) {
        this.feeEl = feeEl;
    }

    @Column(name = "DETAILS_EL",nullable = true,length = 512)
    @Size(max=500)
    public String getDetailsEl() {
        return detailsEl;
    }

    public void setDetailsEl(String detailsEl) {
        this.detailsEl = detailsEl;
    }

    @Column(name = "FOR_EACH_VALUES",length = 512)
    @Size(max = 512)
    public String getForEachValues() {
        return forEachValues;
    }

    public void setForEachValues(String forEachValues) {
        this.forEachValues = forEachValues;
    }

    @Column(name="FOR_EACH_VAR", length = 32)
    @Size(max = 32)
    public String getForEachVar() {
        return forEachVar;
    }

    public void setForEachVar(String forEachVar) {
        this.forEachVar = forEachVar;
    }

    @Column(name = "DESCRIPTION", nullable = true, length = 400)
    @Size(max = 400)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



}