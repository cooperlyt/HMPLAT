package com.dgsoft.house.owner.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by cooper on 17/03/2018.
 */
@Entity
@Table(name = "MONEY_PAY_INFO", catalog = "HOUSE_OWNER_RECORD")
public class MoneyPayInfo implements java.io.Serializable{

    private String id;
    private String bankName;
    private String cardNumber;
    private String cardName;
    private MoneyBusiness moneyBusiness;

    public MoneyPayInfo(){

    }
    public MoneyPayInfo(MoneyBusiness moneyBusiness){
        this.moneyBusiness = moneyBusiness;

    }
    public MoneyPayInfo(MoneyPayInfo moneyPayInfo,MoneyBusiness moneyBusiness){
        this.bankName = moneyPayInfo.getBankName();
        this.cardName = moneyPayInfo.getCardName();
        this.cardNumber = moneyPayInfo.getCardNumber();
        this.moneyBusiness = moneyBusiness;

    }

    public MoneyPayInfo(String bankName,String cardNumber,String cardName,MoneyBusiness moneyBusiness){
        this.bankName=bankName;
        this.cardNumber=cardNumber;
        this.cardName=cardName;
        this.moneyBusiness=moneyBusiness;

    }

    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    @GenericGenerator(name = "pkGenerator",
            strategy = "foreign",
            parameters = { @Parameter(name = "property", value = "moneyBusiness") })
    @GeneratedValue(generator = "pkGenerator")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "BANK_NAME",nullable = false, length = 512)
    @Size(max = 512)
    @NotNull
    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    @Column(name = "CARD_NUMBER",nullable = false, length = 32)
    @Size(max = 32)
    @NotNull
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Column(name = "CARD_NAME",nullable = false, length = 50)
    @Size(max = 50)
    @NotNull
    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    public MoneyBusiness getMoneyBusiness() {
        return moneyBusiness;
    }

    public void setMoneyBusiness(MoneyBusiness moneyBusiness) {
        this.moneyBusiness = moneyBusiness;
    }
}
