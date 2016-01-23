package com.dgsoft.house.owner.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by cooper on 6/5/15.
 */
@Entity
@Table(name = "LOCKED_HOUSE"
        , catalog = "HOUSE_OWNER_RECORD",uniqueConstraints = {@UniqueConstraint(columnNames = {"HOUSE_CODE","TYPE"})}
)
public class LockedHouse {
        //不可售， 系统锁定(无论什么业务都不可以运行)， 预警
    public enum LockType{
        CANT_SALE,SYSTEM_LOCKED,HOUSE_LOCKED
    }

    private String id;
    private String houseCode;
    private LockType type;
    private String description;
    private String empCode;
    private String empName;
    private Date lockedTime;
    private String buildCode;

    public LockedHouse() {
    }

    public LockedHouse(String houseCode, LockType type, String empCode, String empName, Date lockedTime,String buildCode) {
        this.houseCode = houseCode;
        this.type = type;
        this.empCode = empCode;
        this.empName = empName;
        this.lockedTime = lockedTime;
        this.buildCode = buildCode;
    }


    public LockedHouse(String houseCode, LockType type, String empCode, String empName, Date lockedTime) {
        this.houseCode = houseCode;
        this.type = type;
        this.empCode = empCode;
        this.empName = empName;
        this.lockedTime = lockedTime;
    }

    public LockedHouse(String houseCode, LockType type, String description, String empCode, String empName, Date lockedTime) {
        this.houseCode = houseCode;
        this.type = type;
        this.description = description;
        this.empCode = empCode;
        this.empName = empName;
        this.lockedTime = lockedTime;
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


    @Column(name="HOUSE_CODE",nullable = false,length = 32)
    @NotNull
    @Size(max = 32)
    public String getHouseCode() {
        return houseCode;
    }

    public void setHouseCode(String houseCode) {
        this.houseCode = houseCode;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE" , nullable = false, length = 32)
    @NotNull
    public LockType getType() {
        return type;
    }

    public void setType(LockType type) {
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

    @Column(name="BUILD_CODE",length = 32)
    @Size(max = 32)
    public String getBuildCode() {
        return buildCode;
    }

    public void setBuildCode(String buildCode) {
        this.buildCode = buildCode;
    }
}
