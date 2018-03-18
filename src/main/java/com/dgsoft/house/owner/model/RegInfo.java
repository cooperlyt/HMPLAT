package com.dgsoft.house.owner.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by cooper on 17/03/2018.
 */
@Entity
@Table(name = "REG_INFO", catalog = "HOUSE_OWNER_RECORD")
public class RegInfo implements java.io.Serializable{

    private String id;
    private String data;
    private Date queryTime;
    private MoneyBusiness moneyBusiness;

    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    @GenericGenerator(name = "pkGenerator",
            strategy = "foreign",
            parameters = { @Parameter(name = "property", value = "regInfo") })
    @GeneratedValue(generator = "pkGenerator")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "DATA",nullable = false, columnDefinition = "LONGTEXT")
    @NotNull
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "QUERY_TIME",nullable = false)
    @NotNull
    public Date getQueryTime() {
        return queryTime;
    }

    public void setQueryTime(Date queryTime) {
        this.queryTime = queryTime;
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
