package com.dgsoft.house.model;

import com.dgsoft.house.HouseEntityHome;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by wxy on 2019-03-07.
 */
@Entity
@Table(name = "PERSONAL_CONTRACT_LOGON", catalog = "HOUSE_INFO")
public class PersonalContractLogOn implements java.io.Serializable {


    private String id;
    private String houseCode;
    private String password;
    private String userKey;
    private Date createTime;
    private String businessID;
    private Date dateTo;
    private boolean enable;

    public PersonalContractLogOn(){

    }

    public PersonalContractLogOn(String businessID, Boolean enable, Date createTime){
        this.businessID = businessID;
        this.enable = enable;
        this.createTime = createTime;

    }

    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "HOUSE_CODE", unique = true, nullable = false, length = 32)
    @NotNull
    public String getHouseCode() {
        return this.houseCode;
    }

    public void setHouseCode(String houseCode) {
        this.houseCode = houseCode;
    }

    @Column(name = "PASSWORD", nullable = false, length = 32)
    @Size(max = 32)
    @NotNull
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "USER_KEY", nullable = false, length = 32)
    @Size(max = 32)
    @NotNull
    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_TIME", nullable = false, length = 19)
    @NotNull
    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "BUSINESS_ID", nullable = false, length = 32)
    @Size(max = 32)
    @NotNull
    public String getBusinessID() {
        return businessID;
    }

    public void setBusinessID(String businessID) {
        this.businessID = businessID;
    }


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_TO", nullable = false, length = 19)
    @NotNull
    public Date getDateTo() {
        return this.dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }


    @Column(name = "ENABLE",nullable = false)
    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

}
