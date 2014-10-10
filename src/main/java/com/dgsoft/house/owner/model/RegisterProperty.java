package com.dgsoft.house.owner.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-9-18
 * Time: 下午2:09
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "REGISTER_PROPERTY", catalog = "HOUSE_OWNER_RECORD")
public class RegisterProperty implements java.io.Serializable {

    private String id;
    private OwnerBusiness ownerBusiness;
    private String poolMemo;
    private String houseProperty;
    private String houseFrom;

    public RegisterProperty(){

    }

    public RegisterProperty(String id,OwnerBusiness ownerBusiness,String
            poolMemo,String houseProperty,String houseFrom){

        this.id = id;
        this.ownerBusiness = ownerBusiness;
        this.poolMemo =poolMemo;
        this.houseProperty = houseProperty;
        this.houseFrom = houseFrom;

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
    @JoinColumn(name = "BUSINESS_ID", nullable = false)
    @NotNull
    public OwnerBusiness getOwnerBusiness() {
        return ownerBusiness;
    }

    public void setOwnerBusiness(OwnerBusiness ownerBusiness) {
        this.ownerBusiness = ownerBusiness;
    }

    @Column(name = "POOL_MEMO", length = 32)
    @Size(max = 32)
    public String getPoolMemo() {
        return poolMemo;
    }

    public void setPoolMemo(String poolMemo) {
        this.poolMemo = poolMemo;
    }
    @Column(name = "HOUSE_PROPERTY", length = 32)
    @Size(max = 32)
    public String getHouseProperty() {
        return houseProperty;
    }

    public void setHouseProperty(String houseProperty) {
        this.houseProperty = houseProperty;
    }

    @Column(name = "HOUSE_FROM", length = 32)
    @Size(max = 32)
    public String getHouseFrom() {
        return houseFrom;
    }

    public void setHouseFrom(String houseFrom) {
        this.houseFrom = houseFrom;
    }


}
