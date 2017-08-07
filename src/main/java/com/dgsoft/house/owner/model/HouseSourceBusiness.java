package com.dgsoft.house.owner.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by cooper on 05/08/2017.
 */
@Entity
@Table(name = "HOUSE_SOURCE", catalog = "HOUSE_OWNER_RECORD")
public class HouseSourceBusiness implements java.io.Serializable {

    private String id;
    private String message;
    private String data;
    private String sourceId;
    private boolean checked;

    private String searchKey;
    private String display;

    private String attrId;
    private String attrName;

    private OwnerBusiness ownerBusiness;
    private BusinessHouse house;

    public HouseSourceBusiness() {
    }

    public HouseSourceBusiness(OwnerBusiness ownerBusiness,
                               String data, String sourceId, boolean checked,
                               String attrId, String attrName) {
        this.ownerBusiness = ownerBusiness;
        this.data = data;
        this.sourceId = sourceId;
        this.checked = checked;
        this.attrId = attrId;
        this.attrName = attrName;
    }

    @Column(name = "CHECKED",nullable = false)
    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
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

    @Column(name = "MESSAGES",length = 200)
    @Size(max = 200)
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "SOURCE_DATA",nullable = false, columnDefinition = "LONGTEXT")
    @NotNull
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Column(name = "SOURCE_ID",nullable = false,length = 200)
    @Size(max = 200)
    @NotNull
    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "BUSINESS",nullable = false)
    @NotNull
    public OwnerBusiness getOwnerBusiness() {
        return ownerBusiness;
    }

    public void setOwnerBusiness(OwnerBusiness ownerBusiness) {
        this.ownerBusiness = ownerBusiness;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "HOUSE",nullable = false)
    @NotNull
    public BusinessHouse getHouse() {
        return house;
    }

    public void setHouse(BusinessHouse house) {
        this.house = house;
    }

    @Column(name = "SEARCH_KEY",nullable = false, length = 1024)
    @Size(max = 1024)
    @NotNull
    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "DISPLAY",nullable = false, columnDefinition = "LONGTEXT")
    @NotNull
    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    @Column(name="ATTR_ID",length = 32,nullable = false)
    @Size(max = 32)
    @NotNull
    public String getAttrId() {
        return attrId;
    }

    public void setAttrId(String attrId) {
        this.attrId = attrId;
    }

    @Column(name = "ATTR_NAME", length = 100, nullable = false)
    @Size(max = 100)
    @NotNull
    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }
}
