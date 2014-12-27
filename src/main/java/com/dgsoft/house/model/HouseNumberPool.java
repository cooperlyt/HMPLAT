package com.dgsoft.house.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by cooper on 12/27/14.
 */
@Entity
@Table(name = "NUMBER_POOL", catalog = "HOUSE_INFO")
public class HouseNumberPool {
    public enum NumberType {
        DAY_NUMBER,FLOW_NUMBER;
    }


    private String id;
    private long number;
    private Integer version;
    private NumberType numberType;
    private Date useTime;

    public HouseNumberPool() {
    }

    public HouseNumberPool(String id, long number, NumberType numberType, Date useTime) {
        this.id = id;
        this.number = number;
        this.numberType = numberType;
        this.useTime = useTime;
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

    @Column(name = "NUMBER",nullable = false)
    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    @Version
    @Column(name = "VERSION")
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE",nullable = false,length = 20)
    @NotNull
    public NumberType getNumberType() {
        return numberType;
    }

    public void setNumberType(NumberType numberType) {
        this.numberType = numberType;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="USE_TIME",nullable = false)
    @NotNull
    public Date getUseTime() {
        return useTime;
    }

    public void setUseTime(Date useTime) {
        this.useTime = useTime;
    }
}
