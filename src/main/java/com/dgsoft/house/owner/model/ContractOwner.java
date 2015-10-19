package com.dgsoft.house.owner.model;

import com.dgsoft.common.system.PersonEntity;
import com.dgsoft.common.system.model.Person;
import com.dgsoft.house.SaleType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by cooper on 8/14/15.
 */
@Entity
@Table(name = "CONTRACT_OWNER", catalog = "HOUSE_OWNER_RECORD")
public class ContractOwner implements java.io.Serializable,PersonEntity {

    private String id;
    private String contractNumber;
    private String personName;
    private CredentialsType credentialsType;
    private String credentialsNumber;
    private String phone;
    private String rootAddress;
    private String address;
    private String legalPerson;

    private HouseContract houseContract;

    private SaleType type;
    private Date contractDate;
    private String houseCode;



    //由备案人得房屋没意义，因为有startHouse 和 afterHouse 可以使用 houseBusiness left join afterHouse left join ContractOwner
    //private Set<BusinessHouse> businessHouses = new HashSet<BusinessHouse>(0);
    private OwnerBusiness ownerBusiness;

    public ContractOwner() {
    }

    public ContractOwner(OwnerBusiness ownerBusiness, ContractOwner contractOwner ) {
        this.personName = contractOwner.getPersonName();
        this.credentialsType = contractOwner.getCredentialsType();
        this.credentialsNumber = contractOwner.getCredentialsNumber();
        this.phone = contractOwner.getPhone();
        this.rootAddress = contractOwner.getRootAddress();
        this.address = contractOwner.getAddress();
        this.legalPerson = contractOwner.getLegalPerson();
        this.ownerBusiness = ownerBusiness;
    }

    public ContractOwner(String personName, CredentialsType credentialsType, String credentialsNumber, String phone, String rootAddress, String address, String legalPerson, String contractCode, SaleType type, Date contractDate, String houseCode) {
        this.personName = personName;
        this.credentialsType = credentialsType;
        this.credentialsNumber = credentialsNumber;
        this.phone = phone;
        this.rootAddress = rootAddress;
        this.address = address;
        this.legalPerson = legalPerson;
        this.id = contractCode;
        this.type = type;
        this.contractDate = contractDate;
        this.houseCode = houseCode;
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

    @Column(name="CONTRACT_NUMBER", nullable = false, length = 50)
    @NotNull
    @Size(max = 50)
    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    @Override
    @Column(name = "NAME", nullable = false, length = 50)
    @NotNull
    @Size(max = 50)
    public String getPersonName() {
        return personName;
    }

    @Override
    public void setPersonName(String personName) {
        this.personName = personName;
    }

    @Override
    @Enumerated(EnumType.STRING)
    @Column(name = "ID_TYPE", nullable = false, length = 32)
    @NotNull
    public CredentialsType getCredentialsType() {
        return credentialsType;
    }

    @Override
    public void setCredentialsType(CredentialsType credentialsType) {
        this.credentialsType = credentialsType;
    }

    @Override
    @Column(name = "ID_NO", nullable = true, length = 100)
    @Size(max = 100)
    public String getCredentialsNumber() {
        return credentialsNumber;
    }

    @Override
    public void setCredentialsNumber(String credentialsNumber) {
        this.credentialsNumber = credentialsNumber;
    }

    @Column(name = "PHONE", nullable = true, length = 15)
    @Size(max = 15)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "ROOT_ADDRESS", length = 50)
    @Size(max = 50)
    public String getRootAddress() {
        return rootAddress;
    }

    public void setRootAddress(String rootAddress) {
        this.rootAddress = rootAddress;
    }

    @Column(name = "ADDRESS",length = 200)
    @Size(max = 200)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "LEGAL_PERSON", nullable = true, length = 50)
    @Size(max = 50)
    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BUSINESS",nullable = false)
    public OwnerBusiness getOwnerBusiness() {
        return ownerBusiness;
    }

    public void setOwnerBusiness(OwnerBusiness ownerBusiness) {
        this.ownerBusiness = ownerBusiness;
    }


    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    public HouseContract getHouseContract() {
        return houseContract;
    }

    public void setHouseContract(HouseContract houseContract) {
        this.houseContract = houseContract;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE", nullable = false, length = 32)
    @NotNull
    public SaleType getType() {
        return this.type;
    }

    public void setType(SaleType type) {
        this.type = type;
    }


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CONTRACT_DATE", nullable = false, length = 19)
    @NotNull
    public Date getContractDate() {
        return contractDate;
    }

    public void setContractDate(Date contractDate) {
        this.contractDate = contractDate;
    }

    @Column(name = "HOUSE_CODE", length = 32, nullable = false)
    @NotNull
    @Size(max = 32)
    public String getHouseCode() {
        return houseCode;
    }

    public void setHouseCode(String houseCode) {
        this.houseCode = houseCode;
    }


}
