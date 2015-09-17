package com.dgsoft.house.owner.model;

import com.dgsoft.house.SaleType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by cooper on 9/16/15.
 */
@Entity
@Table(name = "CONTRACT_NUMBER", catalog = "HOUSE_OWNER_RECORD")
public class ContractNumber implements java.io.Serializable{

    public enum ContractNumberStatus {
        FREE,OUT,USED
    }

    private String contractNumber;
    private ContractNumberStatus status;
    private long version;
    private SaleType type;
    private Date applyTime;
    private Date createTime;
    private long number;

    private HouseContract houseContract;

    public ContractNumber() {
    }

    public ContractNumber(SaleType type, long number, String contractNumber, ContractNumberStatus status, Date createTime) {
        this.number = number;
        this.createTime = createTime;
        this.type = type;
        this.status = status;
        this.contractNumber = contractNumber;
    }

    @Id
    @Column(name = "CONTRACT_NUMBER", unique = true, nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", length = 20, nullable = false)
    @NotNull
    public ContractNumberStatus getStatus() {
        return status;
    }

    public void setStatus(ContractNumberStatus status) {
        this.status = status;
    }

    @Version
    @Column(name = "VERSION",nullable = false)
    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "CONTRACT", nullable = true)
    public HouseContract getHouseContract() {
        return houseContract;
    }

    public void setHouseContract(HouseContract houseContract) {
        this.houseContract = houseContract;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE",length = 32, nullable = false)
    @NotNull
    public SaleType getType() {
        return type;
    }

    public void setType(SaleType type) {
        this.type = type;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "APPLY_TIME", nullable = true)
    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_TIME", nullable = false)
    @NotNull
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "NUMBER", nullable = false)
    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }
}
