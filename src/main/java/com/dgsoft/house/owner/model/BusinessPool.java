package com.dgsoft.house.owner.model;
// Generated Aug 19, 2014 4:32:06 PM by Hibernate Tools 4.0.0

import com.dgsoft.common.system.PersonEntity;
import com.dgsoft.house.model.PoolOwner;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * BusinessPool generated by hbm2java
 */
@Entity
@Table(name = "BUSINESS_POOL", catalog = "HOUSE_OWNER_RECORD")
public class BusinessPool implements PersonEntity, java.io.Serializable {

    private String id;
    private String personName;
    private PersonEntity.CredentialsType credentialsType;
    private String credentialsNumber;
    private String relation;
    private BigDecimal poolArea;
    private String perc;
    private String phone;
    private MakeCard makeCard;
    private Date createTime;
    private String memo;

    private String legalPerson;
    private String rootAddress;


    public BusinessPool() {
    }

    public BusinessPool(Date createTime){
        this.createTime = createTime;
        this.credentialsType = CredentialsType.MASTER_ID;
    }

    public BusinessPool(PoolOwner pool){
        this.personName = pool.getPersonName();
        this.credentialsNumber = pool.getCredentialsNumber();
        this.credentialsType = pool.getCredentialsType();
        this.relation = pool.getRelation();
        this.poolArea = pool.getArea();
        this.perc = pool.getPerc();
        this.phone = pool.getPhone();
        this.memo = pool.getMemo();
        this.createTime = new Date();
    }

    public BusinessPool(BusinessPool pool){
        this.personName = pool.getPersonName();
        this.credentialsNumber = pool.getCredentialsNumber();
        this.credentialsType = pool.getCredentialsType();
        this.relation = pool.getRelation();
        this.poolArea = pool.getPoolArea();
        this.perc = pool.getPerc();
        this.phone = pool.getPhone();
        this.makeCard = pool.getMakeCard();
        this.memo = pool.getMemo();
        this.createTime = new Date();
    }


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

    @Column(name = "NAME", nullable = false, length = 50)
    @NotNull
    @Size(max = 50)
    @Override
    public String getPersonName() {
        return this.personName;
    }

    @Override
    public void setPersonName(String name) {
        this.personName = name;
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

    @Column(name = "RELATION", length = 32)
    @Size(max = 32)
    public String getRelation() {
        return this.relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    @Column(name = "POOL_AREA", scale = 4)
    public BigDecimal getPoolArea() {
        return this.poolArea;
    }

    public void setPoolArea(BigDecimal poolArea) {
        this.poolArea = poolArea;
    }

    @Column(name = "PERC", length = 10)
    @Size(max = 10)
    public String getPerc() {
        return this.perc;
    }

    public void setPerc(String perc) {
        this.perc = perc;
    }

    @Column(name = "PHONE", nullable = true, length = 15)
    @Size(max = 15)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "CARD", nullable = true)
    public MakeCard getMakeCard() {
        return this.makeCard;
    }

    public void setMakeCard(MakeCard makeCard) {
        this.makeCard = makeCard;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_TIME", nullable = false, length = 19)
    @NotNull
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name="MEMO", nullable = true, length = 200)
    @Size(max = 200)
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }


    @Column(name = "LEGAL_PERSON", nullable = true, length = 50)
    @Size(max = 50)
    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    @Column(name = "ROOT_ADDRESS", length = 50)
    @Size(max = 50)
    public String getRootAddress() {
        return this.rootAddress;
    }

    public void setRootAddress(String rootAddress) {
        this.rootAddress = rootAddress;
    }
}
