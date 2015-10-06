package com.dgsoft.house.owner.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
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
    private OwnerBusiness ownerBusiness;

    public ProjectMortgage() {
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BUSINESS",nullable = false)
    @NotNull
    public OwnerBusiness getOwnerBusiness() {
        return ownerBusiness;
    }

    public void setOwnerBusiness(OwnerBusiness ownerBusiness) {
        this.ownerBusiness = ownerBusiness;
    }
}
