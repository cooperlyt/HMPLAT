package com.dgsoft.house.owner.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by cooper on 8/1/15.
 */
@Entity
@Table(name = "EXCEPT_HOUSE", catalog = "HOUSE_OWNER_RECORD",uniqueConstraints = @UniqueConstraint(columnNames = {
        "BUILD", "HOUSE_CODE"}))
public class ProjectExceptHouse {

    private String id;
    private String houseCode;
    private BusinessBuild businessBuild;

    public ProjectExceptHouse() {
    }

    public ProjectExceptHouse(String houseCode, BusinessBuild businessBuild) {
        this.houseCode = houseCode;
        this.businessBuild = businessBuild;
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

    @Column(name = "HOUSE_CODE",nullable = false,length = 32)
    @NotNull
    @Size(max = 32)
    public String getHouseCode() {
        return houseCode;
    }

    public void setHouseCode(String houseCode) {
        this.houseCode = houseCode;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BUILD",nullable = false)
    @NotNull
    public BusinessBuild getBusinessBuild() {
        return businessBuild;
    }

    public void setBusinessBuild(BusinessBuild businessBuild) {
        this.businessBuild = businessBuild;
    }
}
