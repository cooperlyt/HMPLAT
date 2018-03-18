package com.dgsoft.house.owner.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * Created by cooper on 17/03/2018.
 */
@Entity
@Table(name = "MONEY_BUSINESS", catalog = "HOUSE_OWNER_RECORD")
public class MoneyBusiness implements java.io.Serializable{

    public enum MoneyBusinessStatus{
        //等待资金存入
        CREATED,
        // 资金已存入
        PROTECTED,
        // 中止（资金未存入）
        ABORT,
        // 交易取消等待银行拨款
        CANCEL,
        // 已变更
        CHANGED,
        // 已登记（等待银行拨款)
        REGISTERED,
        //交易取消 拨款完成
        CANCEL_COMPLETE,
        //交易成功 拨款完成
        COMPLETE,
        // 交易取消 拨款失败
        CANCEL_PAY_FAIL,
        // 交易成功 拨款失败
        REGISTERED_PAY_FAIL
    }

    private String id;
    private MoneyBusinessStatus status;
    private String bank;
    private String bankName;
    private String accountNumber;
    private int ver;
    private int version;
    private BigDecimal money;

    private HouseContract houseContract;
    private RegInfo regInfo;
    private MoneyPayInfo moneyPayInfo;
    private OwnerBusiness ownerBusiness;


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

    @Enumerated
    @Column(name = "STATUS", nullable = false, length = 16)
    @NotNull
    public MoneyBusinessStatus getStatus() {
        return status;
    }

    public void setStatus(MoneyBusinessStatus status) {
        this.status = status;
    }

    @Column(name = "BANK",nullable = false,length = 32)
    @Size(max = 32)
    @NotNull
    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    @Column(name = "BANK_NAME", nullable = false, length = 128)
    @Size(max = 128)
    @NotNull
    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    @Column(name = "ACCOUNT_NUMBER", nullable = false, length = 32)
    @Size(max = 32)
    @NotNull
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Column(name = "VER",nullable = false)
    public int getVer() {
        return ver;
    }

    public void setVer(int ver) {
        this.ver = ver;
    }

    @Version
    @Column(name = "VERSION",nullable = false)
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Column(name = "MONEY",nullable = false)
    @NotNull
    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    @OneToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "CONTRACT",nullable = false)
    @NotNull
    public HouseContract getHouseContract() {
        return houseContract;
    }

    public void setHouseContract(HouseContract houseContract) {
        this.houseContract = houseContract;
    }

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL,orphanRemoval = true)
    @PrimaryKeyJoinColumn
    public RegInfo getRegInfo() {
        return regInfo;
    }

    public void setRegInfo(RegInfo regInfo) {
        this.regInfo = regInfo;
    }

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL,orphanRemoval = true)
    @PrimaryKeyJoinColumn
    public MoneyPayInfo getMoneyPayInfo() {
        return moneyPayInfo;
    }

    public void setMoneyPayInfo(MoneyPayInfo moneyPayInfo) {
        this.moneyPayInfo = moneyPayInfo;
    }

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "BUSINESS",nullable = false)
    @NotNull
    public OwnerBusiness getOwnerBusiness() {
        return ownerBusiness;
    }

    public void setOwnerBusiness(OwnerBusiness ownerBusiness) {
        this.ownerBusiness = ownerBusiness;
    }
}
