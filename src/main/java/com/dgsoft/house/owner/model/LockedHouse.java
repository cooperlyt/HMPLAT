package com.dgsoft.house.owner.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by cooper on 6/5/15.
 */
@Entity
@Table(name = "LOCKED_HOUSE"
        , catalog = "HOUSE_OWNER_RECORD"
)
public class LockedHouse {

    private String houseCode;
    private String type;
    private String description;
    private String empCode;
    private String empName;
    private Date lockedTime;

    public LockedHouse() {
    }

    @Id
    @Column(name="HOUSE_CODE",nullable = false,length = 32)
    @NotNull
    @Size(max = 32)
    public String getHouseCode() {
        return houseCode;
    }

    public void setHouseCode(String houseCode) {
        this.houseCode = houseCode;
    }

    @Column(name = "TYPE" , nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "DESCRIPTION" , nullable = true, length = 500)
    @Size(max = 500)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "EMP_CODE" , nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    @Column(name = "EMP_NAME" , nullable = false, length = 50)
    @NotNull
    @Size(max = 50)
    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LOCKED_TIME", nullable = false, length = 19)
    public Date getLockedTime() {
        return lockedTime;
    }

    public void setLockedTime(Date lockedTime) {
        this.lockedTime = lockedTime;
    }
}
