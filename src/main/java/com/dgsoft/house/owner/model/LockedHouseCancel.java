package com.dgsoft.house.owner.model;

import cc.coopersoft.house.LockType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by wxy on 2019-07-12.
 * 解除预警记录
 */
@Entity
@Table(name = "LOCKED_HOUSE_CANCEL"
        , catalog = "HOUSE_OWNER_RECORD"
)
public class LockedHouseCancel {

    private String id;
    private String houseCode;
    private String empCode;
    private String empName;
    private Date cancelDate;
    private String description;

    private String lEmpCode;



    private String lEmpName;

    private LockType type;


    public LockedHouseCancel (){

    }

    public LockedHouseCancel (String houseCode,String empCode,String empName,Date cancelDate,String description,LockType type,String lEmpCode,String lEmpName){

        this.houseCode = houseCode;
        this.empCode = empCode;
        this.empName = empName;
        this.cancelDate = cancelDate;
        this.description = description;
        this.type = type;
        this.lEmpCode = lEmpCode;
        this.lEmpName = lEmpName;

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
    public String getHouseCode() {
        return houseCode;
    }

    public void setHouseCode(String houseCode) {
        this.houseCode = houseCode;
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
    @Column(name = "CANCEL_TIME", nullable = false, length = 19)
    public Date getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(Date cancelDate) {
        this.cancelDate = cancelDate;
    }

    @Column(name = "DESCRIPTION" , nullable = true, length = 500)
    @Size(max = 500)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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


    @Column(name = "LEMP_NAME" , nullable = true, length = 50)
    @Size(max = 50)
    public String getlEmpName() {
        return lEmpName;
    }

    public void setlEmpName(String lEmpName) {
        this.lEmpName = lEmpName;
    }


    @Column(name = "LEMP_CODE" , nullable = true, length = 32)
    @Size(max = 32)
    public String getlEmpCode() {
        return lEmpCode;
    }

    public void setlEmpCode(String lEmpCode) {
        this.lEmpCode = lEmpCode;
    }
}
