package com.dgsoft.house.owner.model;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "MONEY_SAFE", catalog = "HOUSE_OWNER_RECORD")
public class MoneySafe implements java.io.Serializable {


    private String id;
    private String bank;
    private String accountName;
    private String createBankName;
    private String cardNumber;
    private String cardName;
    private BusinessProject businessProject;


    public MoneySafe() {
    }

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    public BusinessProject getBusinessProject() {
        return this.businessProject;
    }

    public void setBusinessProject(BusinessProject businessProject) {
        this.businessProject = businessProject;
    }


    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    @GenericGenerator(name = "pkGenerator",
            strategy = "foreign",
            parameters = { @org.hibernate.annotations.Parameter(name = "property", value = "businessProject") })
    @GeneratedValue(generator = "pkGenerator")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "BANK", length = 32, nullable = false)
    @NotNull
    @Size(max = 32)
    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    @Column(name = "ACCOUNT_NAME", length = 128 , nullable = false)
    @NotNull
    @Size(max = 128)
    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    @Column(name = "CREATE_BANK", length = 512, nullable = false)
    @NotNull
    @Size(max = 512)
    public String getCreateBankName() {
        return createBankName;
    }

    public void setCreateBankName(String createBankName) {
        this.createBankName = createBankName;
    }

    @Column(name = "CARD_NUMBER",  length = 128, nullable = false)
    @NotNull
    @Size(max = 128)
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Column(name = "CARD_NAME", length = 50, nullable = false)
    @NotNull
    @Size(max = 50)
    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }
}
