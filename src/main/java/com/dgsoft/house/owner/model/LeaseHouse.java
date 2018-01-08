package com.dgsoft.house.owner.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by wxy on 2018-01-08.
 * 租赁房屋
 */
@Entity
@Table(name = "LEASE"
        , catalog = "HOUSE_OWNER_RECORD"
)
public class LeaseHouse implements java.io.Serializable{

    private String id;
    private OwnerBusiness ownerBusiness;
    private String name;
    private BigDecimal leaseArea;
    private BigDecimal selfRent;
    private BigDecimal evaluateRent;
    private BigDecimal sumRent;
    private Date startDate;
    private Date endDate;
    private int sumMonth;

    public LeaseHouse(){
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BUISINESS_ID", nullable = false)
    @NotNull
    public OwnerBusiness getOwnerBusiness() {
        return ownerBusiness;
    }

    public void setOwnerBusiness(OwnerBusiness ownerBusiness) {
        this.ownerBusiness = ownerBusiness;
    }

    @Column(name = "NAME", nullable = false, length = 100)
    @NotNull
    @Size(max = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Column(name = "LEASE_AREA", nullable = false, precision = 18, scale = 3)
    @NotNull
    public BigDecimal getLeaseArea() {
        return leaseArea;
    }

    public void setLeaseArea(BigDecimal leaseArea) {
        this.leaseArea = leaseArea;
    }

    @Column(name = "SELF_RENT", nullable = false, scale = 4)
    @NotNull
    public BigDecimal getSelfRent() {
        return selfRent;
    }

    public void setSelfRent(BigDecimal selfRent) {
        this.selfRent = selfRent;
    }

    @Column(name = "EVALUATE_RENT", nullable = false, scale = 4)
    @NotNull
    public BigDecimal getEvaluateRent() {
        return evaluateRent;
    }

    public void setEvaluateRent(BigDecimal evaluateRent) {
        this.evaluateRent = evaluateRent;
    }

    @Column(name = "SUM_RENT", nullable = false, scale = 4)
    @NotNull
    public BigDecimal getSumRent() {
        return sumRent;
    }
    public void setSumRent(BigDecimal sumRent) {
        this.sumRent = sumRent;
    }


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "START_DATE", nullable = false, length = 19)
    @NotNull
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "END_DATE", nullable = false, length = 19)
    @NotNull
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }


    @Column(name = "SUM_MONTH", nullable = false)
    public int getSumMonth() {
        return sumMonth;
    }

    public void setSumMonth(int sumMonth) {
        this.sumMonth = sumMonth;
    }

}
