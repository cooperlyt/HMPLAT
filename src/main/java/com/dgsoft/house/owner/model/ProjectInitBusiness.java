package com.dgsoft.house.owner.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by wxy on 2019-09-07.
 */
@Entity
@Table(name = "PROJECT_INIT_BUSINESS",catalog = "HOUSE_OWNER_RECORD")
public class ProjectInitBusiness implements Serializable{


    private String id;
    private BusinessProject businessProject;
    private OwnerBusiness ownerBusiness;
    private String projectInitBusiness;
    private String account;
    private String searchKey;
    private String display;

    public ProjectInitBusiness(){

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROJECT",nullable = false)
    @NotNull
    public BusinessProject getBusinessProject() {
        return businessProject;
    }

    public void setBusinessProject(BusinessProject businessProject) {
        this.businessProject = businessProject;
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

    @Column(name = "COMPLETE_RECORD_NUMBER", nullable = true, length = 128)
    @Size(max = 128)
    public String getProjectInitBusiness() {
        return projectInitBusiness;
    }

    public void setProjectInitBusiness(String projectInitBusiness) {
        this.projectInitBusiness = projectInitBusiness;
    }
    @Column(name = "ACCOUNT", nullable = true, length = 128)
    @Size(max = 128)
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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
