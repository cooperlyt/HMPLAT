package com.dgsoft.house.model;

import com.dgsoft.common.system.model.PersonId;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by cooper on 8/19/14.
 */
@Entity
@Table(name = "HOUSE_OWNER", catalog = "HOUSE_INFO")
public class HouseOwner implements java.io.Serializable{

    private String id;
    private PersonId.CredentialsType credentialsType;
    private String phone;
    private String rootAddress;
    private House house;

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


    @Enumerated(EnumType.STRING)
    @Column(name = "ID_TYPE", nullable = false, length = 32)
    @NotNull
    public PersonId.CredentialsType getCredentialsType() {
        return credentialsType;
    }

    public void setCredentialsType(PersonId.CredentialsType credentialsType) {
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
}
