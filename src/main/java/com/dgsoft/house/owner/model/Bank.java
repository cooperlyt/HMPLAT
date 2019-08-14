package com.dgsoft.house.owner.model;

import org.jboss.seam.annotations.Name;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by wxy on 2019-08-14.
 */
@Entity
@Table(name = "BANK",catalog = "HOUSE_OWNER_RECORD")
public class Bank implements Serializable {


    private String bank;
    private String BankName;

    public Bank(){

    }

    @Id
    @Column(name = "BANK", unique = true, nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    public String getBank() {
        return bank;
    }
    public void setBank(String bank) {
        this.bank = bank;
    }

    @Column(name = "BANK_NAME",nullable = false, length = 100)
    @NotNull
    @Size(max = 100)
    public String getBankName() {
        return BankName;
    }
    public void setBankName(String bankName) {
        BankName = bankName;
    }





}
