package com.dgsoft.house.model;

import com.dgsoft.common.OrderModel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by wxy on 2018-03-28.
 */
@Entity
@Table(name = "MONEY_BANK",catalog = "HOUSE_INFO")
public class MoneyBank implements java.io.Serializable,OrderModel {

    private String id;
    private String name;
    private String keyt;
    private String secretType;
    private int priority;
    private String accountNumber;

    public MoneyBank(){

    }

    public MoneyBank(String id,String name,String keyt,
                     String secretType,int priority,String accountNumber){
        this.id = id;
        this.name=name;
        this.keyt=keyt;
        this.secretType=secretType;
        this.priority=priority;
        this.accountNumber=accountNumber;
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

    @Column(name = "NAME", nullable = false, length = 50)
    @NotNull
    @Size(max = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "KEYT", nullable = false, length = 16)
    @NotNull
    @Size(max = 16)
    public String getKeyt() {
        return keyt;
    }

    public void setKeyt(String keyt) {
        this.keyt = keyt;
    }

    @Column(name = "SECRET_TYPE", nullable = false, length = 16)
    @NotNull
    @Size(max = 16)
    public String getSecretType() {
        return secretType;
    }

    public void setSecretType(String secretType) {
        this.secretType = secretType;
    }

    @Column(name = "PRI", nullable = false)
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }



    @Column(name = "ACCOUNT_NUMBER", nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }


}
