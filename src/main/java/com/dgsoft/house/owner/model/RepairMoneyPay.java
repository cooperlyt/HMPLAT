package com.dgsoft.house.owner.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "REPAIR_MONEY_PAY", catalog = "HOUSE_OWNER_RECORD")
public class RepairMoneyPay implements java.io.Serializable {

    private String id;
    private String calcDetails;
    private BigDecimal mustMoney;
    private BigDecimal money;
    private String description;
    private HouseBusiness houseBusiness;
    private Date noticeTime;
    private String empCode;
    private String empName;



    private RepairMoneyInfo repairMoneyInfo;

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

    @Column(name = "CALC_DETAILS", length = 512)
    @Size(max = 512)
    public String getCalcDetails() {
        return calcDetails;
    }

    public void setCalcDetails(String calcDetails) {
        this.calcDetails = calcDetails;
    }

    @Column(name = "MUST_MONEY", nullable = false)

    public BigDecimal getMustMoney() {
        return mustMoney;
    }

    public void setMustMoney(BigDecimal mustMoney) {
        this.mustMoney = mustMoney;
    }

    @Column(name = "MONEY",nullable = false)

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    @Column(name="NOTICE_TIME",nullable = false)
    @NotNull
    public Date getNoticeTime() {
        return noticeTime;
    }

    public void setNoticeTime(Date noticeTime) {
        this.noticeTime = noticeTime;
    }

    @Column(name = "EMP_CODE" , length = 32, nullable = false)
    @Size(max = 32)
    @NotNull
    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    @Column(name = "EMP_NAME", length = 50, nullable = false)
    @Size(max = 50)
    @NotNull
    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    @Column(name = "DESCRIPTION", length = 512)
    @Size(max = 512)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="BUSINESS",nullable = false)
    public HouseBusiness getHouseBusiness() {
        return houseBusiness;
    }

    public void setHouseBusiness(HouseBusiness houseBusiness) {
        this.houseBusiness = houseBusiness;
    }

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    @PrimaryKeyJoinColumn
    public RepairMoneyInfo getRepairMoneyInfo() {
        return repairMoneyInfo;
    }

    public void setRepairMoneyInfo(RepairMoneyInfo repairMoneyInfo) {
        this.repairMoneyInfo = repairMoneyInfo;
    }
}
