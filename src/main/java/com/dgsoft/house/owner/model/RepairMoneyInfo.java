package com.dgsoft.house.owner.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by wxy on 2018-09-29.
 */

@Entity
@Table(name = "REPAIR_MONEY_INFO", catalog = "HOUSE_OWNER_RECORD")
public class RepairMoneyInfo implements java.io.Serializable {

    private String id;
    private BigDecimal publicArea;
    private BigDecimal privateArea;
    private BigDecimal publicRate;
    private BigDecimal privateRate;
    private RepairMoneyPay repairMoneyPay;

    public RepairMoneyInfo(){

    }

    public RepairMoneyInfo(RepairMoneyPay repairMoneyPay,BigDecimal publicArea,BigDecimal privateArea,BigDecimal publicRate,BigDecimal privateRate){
        this.publicArea=publicArea;
        this.privateArea=privateArea;
        this.publicRate=publicRate;
        this.privateRate=privateRate;
        this.repairMoneyPay=repairMoneyPay;
    }

    public RepairMoneyInfo(RepairMoneyPay repairMoneyPay,BigDecimal publicArea,BigDecimal publicRate){
        this.publicArea=publicArea;
        this.publicRate=publicRate;
        this.repairMoneyPay=repairMoneyPay;
    }


    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    @GenericGenerator(name = "pkGenerator",
            strategy = "foreign",
            parameters = { @Parameter(name = "property", value = "repairMoneyPay") })
    @GeneratedValue(generator = "pkGenerator")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Column(name = "PUBLIC_AREA", nullable = false, precision = 18, scale = 3)
    @NotNull
    public BigDecimal getPublicArea() {
        return publicArea;
    }

    public void setPublicArea(BigDecimal publicArea) {
        this.publicArea = publicArea;
    }

    @Column(name = "PRIVATE_AREA", nullable = false, precision = 18, scale = 3)
    public BigDecimal getPrivateArea() {
        return privateArea;
    }

    public void setPrivateArea(BigDecimal privateArea) {
        this.privateArea = privateArea;
    }

    @Column(name = "PUBLIC_RATE", nullable = false, precision = 18, scale = 2)
    @NotNull
    public BigDecimal getPublicRate() {
        return publicRate;
    }

    public void setPublicRate(BigDecimal publicRate) {
        this.publicRate = publicRate;
    }

    @Column(name = "PRIVATE_RATE", nullable = false, precision = 18, scale = 2)
    public BigDecimal getPrivateRate() {
        return privateRate;
    }

    public void setPrivateRate(BigDecimal privateRate) {
        this.privateRate = privateRate;
    }



    @OneToOne(fetch = FetchType.LAZY,optional = false)
    @PrimaryKeyJoinColumn
    @NotNull
    public RepairMoneyPay getRepairMoneyPay() {
        return repairMoneyPay;
    }

    public void setRepairMoneyPay(RepairMoneyPay repairMoneyPay) {
        this.repairMoneyPay = repairMoneyPay;
    }

}
