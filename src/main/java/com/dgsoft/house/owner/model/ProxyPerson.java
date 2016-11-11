package com.dgsoft.house.owner.model;

import cc.coopersoft.house.ProxyType;
import com.dgsoft.common.system.PersonEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by cooper on 10/11/2016.
 */
@Entity
@Table(name = "PROXY_PERSON", catalog = "HOUSE_OWNER_RECORD")
public class ProxyPerson implements PersonEntity, java.io.Serializable {

    private String id;

    private ProxyType proxyType;

    private String personName;
    private PersonEntity.CredentialsType credentialsType;
    private String credentialsNumber;

    private String phone;

    public ProxyPerson() {
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

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE", nullable = false, length = 16)
    @NotNull
    public ProxyType getProxyType() {
        return proxyType;
    }

    public void setProxyType(ProxyType proxyType) {
        this.proxyType = proxyType;
    }

    @Override
    @Column(name = "NAME", nullable = false, length = 50)
    public String getPersonName() {
        return personName;
    }

    @Override
    public void setPersonName(String personName) {
        this.personName = personName;
    }

    @Override
    @Enumerated(EnumType.STRING)
    @Column(name = "ID_TYPE",nullable = false, length = 32)
    @NotNull
    public CredentialsType getCredentialsType() {
        return credentialsType;
    }

    @Override
    public void setCredentialsType(CredentialsType credentialsType) {
        this.credentialsType = credentialsType;
    }

    @Override
    @Column(name = "ID_NO", nullable = false, length = 100)
    @NotNull
    @Size(max = 100)
    public String getCredentialsNumber() {
        return credentialsNumber;
    }

    @Override
    public void setCredentialsNumber(String credentialsNumber) {
        this.credentialsNumber = credentialsNumber;
    }

    @Column(name = "PHONE", length = 15, nullable = false)
    @NotNull
    @Size(max = 15)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
