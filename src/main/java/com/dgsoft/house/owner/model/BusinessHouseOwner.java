package com.dgsoft.house.owner.model;
// Generated Aug 19, 2014 4:32:06 PM by Hibernate Tools 4.0.0

import com.dgsoft.common.system.PersonEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * BusinessOwner generated by hbm2java
 */
@Entity
@Table(name = "BUSINESS_OWNER", catalog = "HOUSE_OWNER_RECORD")
public class BusinessHouseOwner implements java.io.Serializable, PersonEntity {


    private String id;
    private String personName;
    private CredentialsType credentialsType;
    private String credentialsNumber;
    private String phone;
    private String rootAddress;
    private String legalPerson;
    private Set<CardInfo> cardInfos = new HashSet<CardInfo>(0);

    public BusinessHouseOwner() {
    }

//    public BusinessHouseOwner(BusinessHouseOwner houseOwner) {
//        this.personName = houseOwner.getPersonName();
//        this.credentialsNumber = houseOwner.getCredentialsNumber();
//        this.credentialsType = houseOwner.getCredentialsType();
//        this.phone = houseOwner.getPhone();
//        this.rootAddress = houseOwner.getRootAddress();
//        this.makeCard = houseOwner.getMakeCard();
//    }

    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    @Column(name = "NAME", nullable = false, length = 50)
    @NotNull
    @Size(max = 50)
    public String getPersonName() {
        return this.personName;
    }

    public void setPersonName(String name) {
        this.personName = name;
    }

    @Override
    @Enumerated(EnumType.STRING)
    @Column(name = "ID_TYPE", nullable = false, length = 32)
    @NotNull
    public CredentialsType getCredentialsType() {
        return this.credentialsType;
    }

    public void setCredentialsType(CredentialsType idType) {
        this.credentialsType = idType;
    }

    @Override
    @Column(name = "ID_NO", nullable = false, length = 100)
    @NotNull
    @Size(max = 100)
    public String getCredentialsNumber() {
        return this.credentialsNumber;
    }

    public void setCredentialsNumber(String idNo) {
        this.credentialsNumber = idNo;
    }

    @Column(name = "PHONE", nullable = false, length = 15)
    @NotNull
    @Size(max = 15)
    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "ROOT_ADDRESS", length = 50)
    @Size(max = 50)
    public String getRootAddress() {
        return this.rootAddress;
    }

    public void setRootAddress(String rootAddress) {
        this.rootAddress = rootAddress;
    }



    @OneToMany(fetch = FetchType.LAZY, mappedBy = "businessHouseOwner")
    public Set<CardInfo> getCardInfos() {
        return cardInfos;
    }

    public void setCardInfos(Set<CardInfo> cardInfos) {
        this.cardInfos = cardInfos;
    }

    @Column(name = "LEGAL_PERSON", nullable = true, length = 50)
    @Size(max = 50)
    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }
}
