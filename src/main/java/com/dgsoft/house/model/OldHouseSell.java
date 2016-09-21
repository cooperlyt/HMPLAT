package com.dgsoft.house.model;

import com.dgsoft.common.system.PersonEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by cooper on 9/17/16.
 */
@Entity
@Table(name = "OLD_HOUSE_SELL", catalog = "HOUSE_INFO")
public class OldHouseSell implements java.io.Serializable,PersonEntity {

    private String id;
    private Date applyTime;
    private Date showTime;
    private Date endTime;
    private String checkBusinessId;
    private String sellBusinessId;
    private String tel;
    private String powerCardNumber;
    private BigDecimal price;
    private CredentialsType credentialsType;
    private String credentialsNumber;
    private String personName;

    private HouseSellCompany houseSellCompany;
    private Set<HouseSellCompany> houseSellCompanies = new HashSet<HouseSellCompany>(0);
    private HouseSellInfo houseSellInfo;


    public OldHouseSell() {
    }

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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "APPLY_TIME",nullable = false)
    @NotNull
    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SHOW_TIME",nullable = true)
    public Date getShowTime() {
        return showTime;
    }

    public void setShowTime(Date showTime) {
        this.showTime = showTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "END_TIME",nullable = false)
    @NotNull
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Column(name = "CHECK_BIZ_ID",nullable = false, length = 32)
    @Size(max = 32)
    @NotNull
    public String getCheckBusinessId() {
        return checkBusinessId;
    }

    public void setCheckBusinessId(String checkBusinessId) {
        this.checkBusinessId = checkBusinessId;
    }

    @Column(name = "SELL_BIZ_ID", length = 32)
    @Size(max = 32)
    public String getSellBusinessId() {
        return sellBusinessId;
    }

    public void setSellBusinessId(String sellBusinessId) {
        this.sellBusinessId = sellBusinessId;
    }


    @Column(name = "TEL", length = 16)
    @Size(max = 16)
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Column(name = "POWER_CARD_NUMBER",nullable = false, length = 50)
    @Size(max = 50)
    @NotNull
    public String getPowerCardNumber() {
        return powerCardNumber;
    }

    public void setPowerCardNumber(String powerCardNumber) {
        this.powerCardNumber = powerCardNumber;
    }

    @Column(name = "PRICE",nullable = false,precision = 18, scale = 3)
    @NotNull
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    @Enumerated(EnumType.STRING)
    @Column(name = "CREDENTIALS_TYPE",nullable = false,length = 32)
    @NotNull
    public CredentialsType getCredentialsType() {
        return credentialsType;
    }

    @Override
    public void setCredentialsType(CredentialsType credentialsType) {
        this.credentialsType = credentialsType;
    }

    @Override
    @Column(name = "CREDENTIALS_NUMBER",nullable = false, length = 100)
    @Size(max = 100)
    @NotNull
    public String getCredentialsNumber() {
        return credentialsNumber;
    }

    @Override
    public void setCredentialsNumber(String s) {
        this.credentialsNumber = s;
    }

    @Override
    @Column(name = "OWNER_NAME",nullable = false, length = 100)
    @Size(max = 100)
    @NotNull
    public String getPersonName() {
        return personName;
    }

    @Override
    public void setPersonName(String s) {
        this.personName = s;
    }


    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "MASTER_COMPANY",nullable = false)
    @NotNull
    public HouseSellCompany getHouseSellCompany() {
        return houseSellCompany;
    }

    public void setHouseSellCompany(HouseSellCompany houseSellCompany) {
        this.houseSellCompany = houseSellCompany;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "COMPANY_SELL_INFO",joinColumns=@JoinColumn(name="SELL_INFO"),inverseJoinColumns = @JoinColumn(name = "COMPANY"))
    public Set<HouseSellCompany> getHouseSellCompanies() {
        return houseSellCompanies;
    }

    public void setHouseSellCompanies(Set<HouseSellCompany> houseSellCompanies) {
        this.houseSellCompanies = houseSellCompanies;
    }

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL,optional = false)
    @JoinColumn(name = "HOUSE_SELL_INFO", nullable = false)
    @NotNull
    public HouseSellInfo getHouseSellInfo() {
        return houseSellInfo;
    }

    public void setHouseSellInfo(HouseSellInfo houseSellInfo) {
        this.houseSellInfo = houseSellInfo;
    }
}
