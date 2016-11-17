package com.dgsoft.house.owner.model;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by cooper on 10/6/15.
 */
@Entity
@Table(name = "PROJECT_MORTGAGE", catalog = "HOUSE_OWNER_RECORD")
public class ProjectMortgage {

    private String id;
    private String address;
    private String landNumber;
    private String developerCode;
    private String developerName;
    private MortgaegeRegiste mortgaegeRegiste;

    public ProjectMortgage() {
    }

    public ProjectMortgage(MortgaegeRegiste mortgaegeRegiste) {
        this.mortgaegeRegiste = mortgaegeRegiste;
    }

    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    @GenericGenerator(name = "pkGenerator",
            strategy = "foreign",
            parameters = { @org.hibernate.annotations.Parameter(name = "property", value = "mortgaegeRegiste") })
    @GeneratedValue(generator = "pkGenerator")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "ADDRESS", length = 512 )
    @Size(max = 512)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "LAND_NUMBER", length = 32)
    @Size(max = 32)
    public String getLandNumber() {
        return landNumber;
    }

    public void setLandNumber(String landNumber) {
        this.landNumber = landNumber;
    }

    @Column(name="DEVELOPER_CODE",length = 32,nullable = false)
    @NotNull
    @Size(max = 32)
    public String getDeveloperCode() {
        return developerCode;
    }

    public void setDeveloperCode(String developerCode) {
        this.developerCode = developerCode;
    }

    @Column(name="DEVELOPER_NAME",length = 100,nullable = false)
    @NotNull
    @Size(max = 100)
    public String getDeveloperName() {
        return developerName;
    }

    public void setDeveloperName(String developerName) {
        this.developerName = developerName;
    }

    @OneToOne(fetch = FetchType.LAZY,mappedBy = "projectMortgage")
    @PrimaryKeyJoinColumn
    public MortgaegeRegiste getMortgaegeRegiste() {
        return mortgaegeRegiste;
    }

    public void setMortgaegeRegiste(MortgaegeRegiste mortgaegeRegiste) {
        this.mortgaegeRegiste = mortgaegeRegiste;
    }
}
