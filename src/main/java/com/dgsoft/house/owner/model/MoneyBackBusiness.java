package com.dgsoft.house.owner.model;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by wxy on 2019-08-12.
 */
@Entity
@Table(name = "MONEY_BACK_BUSINESS",catalog = "HOUSE_OWNER_RECORD")
public class MoneyBackBusiness implements Serializable{


   // 撤消备案退款CANCEL,未备案退款ABORT,冲正退款DELETE
    public enum MoneyBackType{
        CANCEL,ABORT,DELETE
    }

    private String id;
    private MoneyBackType backType;
    private String contract;
    private BigDecimal backMoney;
    private OwnerBusiness ownerBusiness;

    public MoneyBackBusiness(){

    }


   @Id
   @Column(name = "ID",unique = true,nullable = false,length = 32)
   @NotNull
   @Size(max = 32)
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE",nullable = false,length = 8)
    @NotNull
    public MoneyBackType getBackType() {
        return backType;
    }
    public void setBackType(MoneyBackType backType) {
        this.backType = backType;
    }

    @Column(name = "CONTRACT",nullable = true,length = 50)
    @Size(max = 50)
    public String getContract() {
        return contract;
    }
    public void setContract(String contract) {
        this.contract = contract;
    }

    @Column(name = "MONEY",nullable = false,scale = 4)
    @NotNull
    public BigDecimal getBackMoney() {
        return backMoney;
    }
    public void setBackMoney(BigDecimal backMoney) {
        this.backMoney = backMoney;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BUSINESS",nullable = false)
    @NotNull
    public OwnerBusiness getOwnerBusiness() {
        return ownerBusiness;
    }
    public void setOwnerBusiness(OwnerBusiness ownerBusiness) {
        this.ownerBusiness = ownerBusiness;
    }


}
