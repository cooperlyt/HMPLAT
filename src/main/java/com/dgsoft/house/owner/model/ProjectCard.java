package com.dgsoft.house.owner.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by cooper on 8/1/15.
 */
@Entity
@Table(name = "PROJECT_CARD", catalog = "HOUSE_OWNER_RECORD")
public class ProjectCard implements java.io.Serializable{

    private String id;
    private String yearNumber;
    private String orderNumber;
    private Date printTime;
    private String memo;
    private MakeCard makeCard;
    private ProjectSellInfo projectSellInfo;

    public ProjectCard() {
    }

    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    @GenericGenerator(name = "pkGenerator",
            strategy = "foreign",
            parameters = { @org.hibernate.annotations.Parameter(name = "property", value = "makeCard") })
    @GeneratedValue(generator = "pkGenerator")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "YEAR_NUMBER", length = 20)
    @Size(max = 20)
    public String getYearNumber() {
        return this.yearNumber;
    }

    public void setYearNumber(String yearNumber) {
        this.yearNumber = yearNumber;
    }

    @Column(name = "ORDER_NUMBER", length = 20)
    @Size(max = 20)
    public String getOrderNumber() {
        return this.orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PRINT_TIME", nullable = false, length = 19)
    @NotNull
    public Date getPrintTime() {
        return this.printTime;
    }

    public void setPrintTime(Date printTime) {
        this.printTime = printTime;
    }

    @Column(name = "MEMO", length = 200)
    @Size(max = 200)
    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    public MakeCard getMakeCard() {
        return makeCard;
    }

    public void setMakeCard(MakeCard makeCard) {
        this.makeCard = makeCard;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROJECT",nullable = false)
    @NotNull
    public ProjectSellInfo getProjectSellInfo() {
        return projectSellInfo;
    }

    public void setProjectSellInfo(ProjectSellInfo projectSellInfo) {
        this.projectSellInfo = projectSellInfo;
    }
}
