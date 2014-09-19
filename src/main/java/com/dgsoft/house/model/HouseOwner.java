package com.dgsoft.house.model;

import com.dgsoft.common.system.PersonEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by cooper on 8/19/14.
 */
@Entity
@Table(name = "HOUSE_OWNER", catalog = "HOUSE_INFO")
public class HouseOwner implements PersonEntity,java.io.Serializable {

    private String id;
    private PersonEntity.CredentialsType credentialsType;
    private String credentialsNumber;
    private String phone;
    private String rootAddress;
    private House house;
    private String personName;
    private String memo;

    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 32)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    @NotNull
    @Size(max = 32)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    @Enumerated(EnumType.STRING)
    @Column(name = "ID_TYPE", nullable = false, length = 32)
    @NotNull
    public PersonEntity.CredentialsType getCredentialsType() {
        return credentialsType;
    }

    @Override
    public void setCredentialsType(PersonEntity.CredentialsType credentialsType) {
        this.credentialsType = credentialsType;
    }

    @Column(name = "PHONE", nullable = true, length = 15)
    @Size(max = 15)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "ROOT_ADDRESS", nullable = true, length = 50)
    @Size(max = 50)
    public String getRootAddress() {
        return rootAddress;
    }

    public void setRootAddress(String rootAddress) {
        this.rootAddress = rootAddress;
    }


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "HOUSE", nullable = true, unique = true)
    @NotNull
    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    @Override
    @Column(name = "NAME", nullable = false, length = 50)
    @NotNull
    @Size(max = 50)
    public String getPersonName() {
        return personName;
    }

    @Override
    public void setPersonName(String name) {
        this.personName = name;
    }

    @Override
    @Column(name = "ID_NO", nullable = false, length = 100)
    @NotNull
    @Size(max = 100)
    public String getCredentialsNumber() {
        return credentialsNumber;
    }

    @Override
    public void setCredentialsNumber(String cerdentialsNumber) {
        this.credentialsNumber = cerdentialsNumber;
    }

    @Column(name = "MEMO",nullable = true, length = 200)
    @Size(max = 200)
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
