package com.dgsoft.house.owner.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by wxy on 2018-10-12.
 */
@Entity
@Table(name = "OLD_SELL_CONTRACT_INFO", catalog = "HOUSE_OWNER_RECORD")
public class OldSellContractInfo implements java.io.Serializable {

    private String id;
    private OwnerBusiness ownerBusiness;

    private String PooCards;
    private String LandGetMode;
    private String LandCardNo;
    private String LeaseMemo;
    private String SellType;
    private String HouseSellCompany;

    private Date EarnestDate;
    private BigDecimal EarnestMoney;
    private String PayType;

    private Date AllPayDate;
    private Date DebitDate;
    private BigDecimal DebitFirstMoney;
    private BigDecimal DebitOtherMoney;
    private String PayOther;

    private String DebitType;
    private String DebitOther;
    private String KeepingType;
    private String ContractSellCompany;
    private String BankName;
    private String BankNameNo;

    private String HouseOtherMemo;
    private Date PayHouseDate;

    private String BreachType;
    private String BreachOther;
    private String TaxPay;
    private String TaxOther;

    private String DisputeType;
    private String OtherMemo;
    private String Pages;
    private String Sheres;

    private String SellerSheres;
    private String BuyerSheres;

    public OldSellContractInfo(){

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
    @JoinColumn(name = "BUSINESS_ID", nullable = false)
    @NotNull
    public OwnerBusiness getOwnerBusiness() {
        return ownerBusiness;
    }

    public void setOwnerBusiness(OwnerBusiness ownerBusiness) {
        this.ownerBusiness = ownerBusiness;
    }


    @Column(name = "POO_CARDS", length = 200)
    @Size(max = 200)
    public String getPooCards() {
        return PooCards;
    }

    public void setPooCards(String pooCards) {
        PooCards = pooCards;
    }

    @Column(name = "LAND_GET_MODE", length = 32)
    @Size(max = 32)
    public String getLandGetMode() {
        return LandGetMode;
    }

    public void setLandGetMode(String landGetMode) {
        LandGetMode = landGetMode;
    }

    @Column(name = "LAND_CARD_NO", length = 50)
    @Size(max = 50)
    public String getLandCardNo() {
        return LandCardNo;
    }

    public void setLandCardNo(String landCardNo) {
        LandCardNo = landCardNo;
    }

    @Column(name = "LEASE_MEMO", length = 32)
    @Size(max = 32)
    public String getLeaseMemo() {
        return LeaseMemo;
    }

    public void setLeaseMemo(String leaseMemo) {
        LeaseMemo = leaseMemo;
    }

    @Column(name = "SALE_TYPE", length = 5)
    @Size(max = 5)
    public String getSellType() {
        return SellType;
    }

    public void setSellType(String sellType) {
        SellType = sellType;
    }


    @Column(name = "HOUSE_SELL_COMPANY", length = 100)
    @Size(max = 100)
    public String getHouseSellCompany() {
        return HouseSellCompany;
    }

    public void setHouseSellCompany(String houseSellCompany) {
        HouseSellCompany = houseSellCompany;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EARNEST_DATE", length = 19)
    public Date getEarnestDate() {
        return EarnestDate;
    }

    public void setEarnestDate(Date earnestDate) {
        EarnestDate = earnestDate;
    }

    @Column(name = "EARNEST_MONEY", scale = 3)
    public BigDecimal getEarnestMoney() {
        return EarnestMoney;
    }

    public void setEarnestMoney(BigDecimal earnestMoney) {
        EarnestMoney = earnestMoney;
    }

    @Column(name = "PAY_TYPE", length = 5)
    @Size(max = 5)
    public String getPayType() {
        return PayType;
    }

    public void setPayType(String payType) {
        PayType = payType;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ALL_PAY_DATE", length = 19)
    public Date getAllPayDate() {
        return AllPayDate;
    }

    public void setAllPayDate(Date allPayDate) {
        AllPayDate = allPayDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DEBIT_DATE", length = 19)
    public Date getDebitDate() {
        return DebitDate;
    }

    public void setDebitDate(Date debitDate) {
        DebitDate = debitDate;
    }

    @Column(name = "DEBIT_FIRST_MONEY", scale = 3)
    public BigDecimal getDebitFirstMoney() {
        return DebitFirstMoney;
    }

    public void setDebitFirstMoney(BigDecimal debitFirstMoney) {
        DebitFirstMoney = debitFirstMoney;
    }

    @Column(name = "DEBIT_OTHER_MONEY", scale = 3)
    public BigDecimal getDebitOtherMoney() {
        return DebitOtherMoney;
    }

    public void setDebitOtherMoney(BigDecimal debitOtherMoney) {
        DebitOtherMoney = debitOtherMoney;
    }

    @Column(name = "PAY_OTHER", length = 200)
    @Size(max = 200)
    public String getPayOther() {
        return PayOther;
    }

    public void setPayOther(String payOther) {
        PayOther = payOther;
    }

    @Column(name = "DEBIT_TYPE", length = 10)
    @Size(max = 10)
    public String getDebitType() {
        return DebitType;
    }

    public void setDebitType(String debitType) {
        DebitType = debitType;
    }
    @Column(name = "DEBIT_OTHER", length = 200)
    @Size(max = 200)
    public String getDebitOther() {
        return DebitOther;
    }

    public void setDebitOther(String debitOther) {
        DebitOther = debitOther;
    }

    @Column(name = "KEEPING_TYPE", length = 5)
    @Size(max = 5)
    public String getKeepingType() {
        return KeepingType;
    }

    public void setKeepingType(String keepingType) {
        KeepingType = keepingType;
    }

    @Column(name = "CONTRACT_SELL_COMPANY", length = 100)
    @Size(max = 100)
    public String getContractSellCompany() {
        return ContractSellCompany;
    }

    public void setContractSellCompany(String contractSellCompany) {
        ContractSellCompany = contractSellCompany;
    }


    @Column(name = "BANK_NAME", length = 100)
    @Size(max = 100)
    public String getBankName() {
        return BankName;
    }

    public void setBankName(String bankName) {
        BankName = bankName;
    }

    @Column(name = "BANK_NAME_NO", length = 100)
    @Size(max = 100)
    public String getBankNameNo() {
        return BankNameNo;
    }

    public void setBankNameNo(String bankNameNo) {
        BankNameNo = bankNameNo;
    }

    @Column(name = "HOUSE_OTHER_MEMO", length = 100)
    @Size(max = 100)
    public String getHouseOtherMemo() {
        return HouseOtherMemo;
    }

    public void setHouseOtherMemo(String houseOtherMemo) {
        HouseOtherMemo = houseOtherMemo;
    }


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PAY_HOUSE_DATE", length = 19)
    public Date getPayHouseDate() {
        return PayHouseDate;
    }

    public void setPayHouseDate(Date payHouseDate) {
        PayHouseDate = payHouseDate;
    }

    @Column(name = "BREACH_TYPE", length = 5)
    @Size(max = 5)
    public String getBreachType() {
        return BreachType;
    }

    public void setBreachType(String breachType) {
        BreachType = breachType;
    }

    @Column(name = "BREACH_OTTHER", length = 200)
    @Size(max = 200)
    public String getBreachOther() {
        return BreachOther;
    }

    public void setBreachOther(String breachOther) {
        BreachOther = breachOther;
    }


    @Column(name = "TAX_PAY", length = 10)
    @Size(max = 10)
    public String getTaxPay() {
        return TaxPay;
    }

    public void setTaxPay(String taxPay) {
        TaxPay = taxPay;
    }

    @Column(name = "TAX_OTHER", length = 200)
    @Size(max = 200)
    public String getTaxOther() {
        return TaxOther;
    }

    public void setTaxOther(String taxOther) {
        TaxOther = taxOther;
    }

    @Column(name = "DISPUTE_TYPE", length = 5)
    @Size(max = 5)
    public String getDisputeType() {
        return DisputeType;
    }
    public void setDisputeType(String disputeType) {
        DisputeType = disputeType;
    }

    @Column(name = "OTHER_MEMO", length = 200)
    @Size(max = 200)
    public String getOtherMemo() {
        return OtherMemo;
    }

    public void setOtherMemo(String otherMemo) {
        OtherMemo = otherMemo;
    }

    @Column(name = "PAGES", length = 5)
    @Size(max = 5)
    public String getPages() {
        return Pages;
    }

    public void setPages(String pages) {
        Pages = pages;
    }

    @Column(name = "SHERES", length = 5)
    @Size(max = 5)
    public String getSheres() {
        return Sheres;
    }

    public void setSheres(String sheres) {
        Sheres = sheres;
    }

    @Column(name = "SELLER_SHERES", length = 5)
    @Size(max = 5)
    public String getSellerSheres() {
        return SellerSheres;
    }

    public void setSellerSheres(String sellerSheres) {
        SellerSheres = sellerSheres;
    }

    @Column(name = "BUYER_SHERES", length = 5)
    @Size(max = 5)
    public String getBuyerSheres() {
        return BuyerSheres;
    }

    public void setBuyerSheres(String buyerSheres) {
        BuyerSheres = buyerSheres;
    }




}
