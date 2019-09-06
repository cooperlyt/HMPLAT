package com.dgsoft.house.owner.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by wxy on 2019-08-26.
 */
@Entity
@Table(name = "PROJECT_CHECK",catalog = "HOUSE_OWNER_RECORD")
public class ProjectCheck implements Serializable {


    private String id;
    private String buildCode;
    private OwnerBusiness ownerBusiness;
    private String point;
    private String pointName;
    private int payPercent;
    private String searchKey;
    private String display;


    public ProjectCheck(){

    }

    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "BUILD_CODE", nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    public String getBuildCode() {
        return buildCode;
    }

    public void setBuildCode(String buildCode) {
        this.buildCode = buildCode;
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

    @Column(name = "POINT", nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    @Column(name = "POINT_NAME", nullable = false, length = 100)
    @NotNull
    @Size(max = 100)
    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }


    @Column(name = "PAY_PERCENT", nullable = false)
    public int getPayPercent() {
        return payPercent;
    }

    public void setPayPercent(int payPercent) {
        this.payPercent = payPercent;
    }

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "DISPLAY",nullable = true, columnDefinition = "LONGTEXT")
    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    @Column(name = "SEARCH_KEY",nullable = true, length = 1024)
    @Size(max = 1024)
    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

}
