package com.dgsoft.house.owner.model;

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

    public LockedHouseCancel (){

    }

    public LockedHouseCancel (String houseCode,String empCode,String empName,Date cancelDate,String description){

        this.houseCode = houseCode;
        this.empCode = empCode;
        this.empName = empName;
        this.cancelDate = cancelDate;
        this.description = description;

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


}
