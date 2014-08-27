package com.dgsoft.house.owner.model;
// Generated Aug 19, 2014 4:32:06 PM by Hibernate Tools 4.0.0

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * HouseBusiness generated by hbm2java
 */
@Entity
@Table(name = "HOUSE_BUSINESS", catalog = "HOUSE_OWNER_RECORD")
@DiscriminatorValue("HOUSE")
public class HouseBusiness extends OwnerBusiness implements java.io.Serializable {


    private HouseBusiness selectBusiness;
    private Set<Evaluate> evaluates = new HashSet<Evaluate>(0);
    private Set<MortgaegeRegiste> mortgaegeRegistes = new HashSet<MortgaegeRegiste>(
            0);
    private Set<SaleInfo> saleInfos = new HashSet<SaleInfo>(0);
    private Set<HouseCloseCancel> houseCloseCancels = new HashSet<HouseCloseCancel>(
            0);
    private Set<Financial> financials = new HashSet<Financial>(0);
    private Set<BusinessHouse> businessHouses = new HashSet<BusinessHouse>(0);
    private Set<CloseHouse> closeHouses = new HashSet<CloseHouse>(0);
    private Set<BusinessHouseOwner> businessHouseOwners = new HashSet<BusinessHouseOwner>(0);
    private Set<BusinessPool> businessPools = new HashSet<BusinessPool>(0);

    public HouseBusiness() {
    }

    public HouseBusiness(BusinessSource source,
                         Date recordTime,
                         BusinessStatus status,
                         Date applyTime, Date createTime) {
        super(source, recordTime, status, applyTime, createTime);
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "houseBusiness")
    public Set<Evaluate> getEvaluates() {
        return this.evaluates;
    }

    public void setEvaluates(Set<Evaluate> evaluates) {
        this.evaluates = evaluates;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "houseBusiness")
    public Set<MortgaegeRegiste> getMortgaegeRegistes() {
        return this.mortgaegeRegistes;
    }

    public void setMortgaegeRegistes(Set<MortgaegeRegiste> mortgaegeRegistes) {
        this.mortgaegeRegistes = mortgaegeRegistes;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "houseBusiness")
    public Set<SaleInfo> getSaleInfos() {
        return this.saleInfos;
    }

    public void setSaleInfos(Set<SaleInfo> saleInfos) {
        this.saleInfos = saleInfos;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "houseBusiness")
    public Set<HouseCloseCancel> getHouseCloseCancels() {
        return this.houseCloseCancels;
    }

    public void setHouseCloseCancels(Set<HouseCloseCancel> houseCloseCancels) {
        this.houseCloseCancels = houseCloseCancels;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "houseBusiness")
    public Set<Financial> getFinancials() {
        return this.financials;
    }

    public void setFinancials(Set<Financial> financials) {
        this.financials = financials;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "houseBusiness")
    public Set<BusinessHouse> getBusinessHouses() {
        return this.businessHouses;
    }

    public void setBusinessHouses(Set<BusinessHouse> businessHouses) {
        this.businessHouses = businessHouses;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "houseBusiness")
    public Set<CloseHouse> getCloseHouses() {
        return this.closeHouses;
    }

    public void setCloseHouses(Set<CloseHouse> closeHouses) {
        this.closeHouses = closeHouses;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "houseBusiness")
    public Set<BusinessHouseOwner> getBusinessHouseOwners() {
        return businessHouseOwners;
    }

    public void setBusinessHouseOwners(Set<BusinessHouseOwner> businessHouseOwners) {
        this.businessHouseOwners = businessHouseOwners;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "houseBusiness")
    public Set<BusinessPool> getBusinessPools() {
        return businessPools;
    }

    public void setBusinessPools(Set<BusinessPool> businessPools) {
        this.businessPools = businessPools;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "SELECT_BUSINESS", nullable = true)
    public HouseBusiness getSelectBusiness() {
        return selectBusiness;
    }

    public void setSelectBusiness(HouseBusiness selectBusiness) {
        this.selectBusiness = selectBusiness;
    }
}
