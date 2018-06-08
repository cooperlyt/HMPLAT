package com.dgsoft.house.owner.model;

import com.dgsoft.common.TimeArea;
import com.dgsoft.common.TimeAreaHelper;
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
public class LeaseHouse implements java.io.Serializable,TimeArea{

    public enum LeaseHouseStatus{
        // 已登记
        REGISTERED,
        // 已变更
        CHANGED,
        //已取消
        CANCEL
    }

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
    private String sellCompanyName;
    private String searchKey;
    private String display;



    private String leaseNo;




    private TimeArea.TimeShowType timeShowType;

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


    @Override
    @Enumerated(EnumType.STRING)
    @Column(name = "TIME_AREA_TYPE",nullable = false,length = 20)
    @NotNull
    public TimeArea.TimeShowType getTimeShowType() {
        return timeShowType;
    }

    public void setTimeShowType(TimeArea.TimeShowType timeShowType) {
        this.timeShowType = timeShowType;
    }

    @Override
    @Transient
    public Date getFromTime() {
        return getStartDate();
    }

    @Override
    @Transient
    public void setFromTime(Date date) {
        setStartDate(date);

    }

    @Override
    @Transient
    public Date getToTime() {
        return getEndDate();
    }

    @Override
    @Transient
    public void setToTime(Date date) {
        setEndDate(date);
    }


    @Transient
    public TimeAreaHelper getTimeArea(){
        return new TimeAreaHelper(this);
    }


    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "DISPLAY",nullable = false, columnDefinition = "LONGTEXT")
    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    @Column(name = "SEARCH_KEY",nullable = false, length = 1024)
    @Size(max = 1024)
    public String getSearchKey() {
        return searchKey;
    }


    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }



    @Column(name = "SELL_COMPANY_NAME", nullable = false, length = 100)
    @NotNull
    @Size(max = 100)
    public String getSellCompanyName() {
        return sellCompanyName;
    }

    public void setSellCompanyName(String sellCompanyName) {
        this.sellCompanyName = sellCompanyName;
    }

    @Column(name = "LEASE_NO", nullable = false, length = 20)
    @Size(max = 20)
    public String getLeaseNo() {
        return leaseNo;
    }

    public void setLeaseNo(String leaseNo) {
        this.leaseNo = leaseNo;
    }
}
