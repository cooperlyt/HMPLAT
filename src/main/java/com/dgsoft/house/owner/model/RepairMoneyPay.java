package com.dgsoft.house.owner.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
@Table(name = "REPAIR_MONEY_PAY", catalog = "HOUSE_OWNER_RECORD")
public class RepairMoneyPay implements java.io.Serializable {

    private String id;
    private String calcDetails;
    private BigDecimal mustMoney;
    private BigDecimal money;
    private String description;
    private HouseBusiness houseBusiness;

    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
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
}
