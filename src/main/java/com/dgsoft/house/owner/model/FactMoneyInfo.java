package com.dgsoft.house.owner.model;

import org.hibernate.annotations.GenericGenerator;
import org.jboss.seam.annotations.Name;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by Administrator on 15-7-28.
 * 缴费信息表
 *
 */
@Entity
@Table(name = "FACT_MONEYINFO",catalog = "HOUSE_OWNER_RECORD")
public class FactMoneyInfo implements java.io.Serializable {

    private String id;
    private Date factTime;
    private String paymentNo;
    private OwnerBusiness ownerBusiness;



    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "PAYMENT_NO",length = 25,nullable = true)
    @Size(max = 25)
    @NotNull
    public String getPaymentNo() {
        return paymentNo;
    }

    public void setPaymentNo(String paymentNo) {
        this.paymentNo = paymentNo;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FACT_TIME",nullable = true,length = 19)
    @NotNull
    public Date getFactTime() {
        return factTime;
    }

    public void setFactTime(Date factTime) {
        this.factTime = factTime;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BUSINESS",nullable = false)
    @NotNull
    public OwnerBusiness getOwnerBusiness() {
        return this.ownerBusiness;
    }

    public void setOwnerBusiness(OwnerBusiness ownerBusiness) {
        this.ownerBusiness = ownerBusiness;
    }
}
