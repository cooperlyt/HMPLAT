package com.dgsoft.house.owner.model;

import com.dgsoft.common.system.PersonEntity;
import com.dgsoft.common.system.PowerPersonEntity;
import com.dgsoft.house.AttachCorpType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "ATTACH_CORPORATION", catalog = "HOUSE_OWNER_RECORD")
public class BusinessCorp implements java.io.Serializable, PersonEntity{

  private String id;
  private String address;
  private String phone;
  private String ownerTel;
  private String ownerName;
  private String ownerCard;
  private String fax;
  private String email;
  private String memo;
  private String postCode;
  private String licenseNumber;
  private String cerCode;

  private String companyCode;
  private String companyType;
  private BigDecimal registerMoney;
  private String level;
  private String name;


  private PowerPersonEntity.LegalType legalType;
  private PersonEntity.CredentialsType credentialsType;

  private AttachCorpType type;

  private OwnerBusiness ownerBusiness;

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

  @Column(name = "ADDRESS", nullable = false,length = 100)
  @Size(max = 100)
  @NotNull
  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  @Column(name = "PHONE",nullable = false,length = 200)
  @Size(max = 200)
  @NotNull
  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  @Column(name="OWNER_TEL", length = 100)
  @Size(max = 100)
  public String getOwnerTel() {
    return ownerTel;
  }

  public void setOwnerTel(String ownerTel) {
    this.ownerTel = ownerTel;
  }

  @Column(name = "OWNER_NAME",nullable = false, length = 20)
  @Size(max = 20)
  @NotNull
  public String getOwnerName() {
    return ownerName;
  }

  public void setOwnerName(String ownerName) {
    this.ownerName = ownerName;
  }

  @Column(name = "OWNER_CARD", length = 50)
  @Size(max = 50)
  public String getOwnerCard() {
    return ownerCard;
  }

  public void setOwnerCard(String ownerCard) {
    this.ownerCard = ownerCard;
  }

  @Column(name = "FAX", length = 50)
  @Size(max = 50)
  public String getFax() {
    return fax;
  }

  public void setFax(String fax) {
    this.fax = fax;
  }

  @Column(name = "EMAIL", length = 50)
  @Size(max = 50)
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Column(name = "MEMO", length = 200)
  @Size(max = 200)
  public String getMemo() {
    return memo;
  }

  public void setMemo(String memo) {
    this.memo = memo;
  }

  @Column(name = "POST_CODE", length = 50)
  @Size(max = 50)
  public String getPostCode() {
    return postCode;
  }

  public void setPostCode(String postCode) {
    this.postCode = postCode;
  }

  @Column(name = "LICENSE_NUMBER",nullable = false,length = 100)
  @Size(max = 100)
  @NotNull
  public String getLicenseNumber() {
    return licenseNumber;
  }

  public void setLicenseNumber(String licenseNumber) {
    this.licenseNumber = licenseNumber;
  }

  @Column(name = "COMPANY_CER_CODE", length = 100)
  @Size(max = 100)
  public String getCerCode() {
    return cerCode;
  }

  public void setCerCode(String cerCode) {
    this.cerCode = cerCode;
  }

  @Column(name = "COMPANY_CODE", length = 100)
  @Size(max = 100)
  public String getCompanyCode() {
    return companyCode;
  }

  public void setCompanyCode(String companyCode) {
    this.companyCode = companyCode;
  }

  @Column(name = "COMPANY_TYPE", length = 32)
  @Size(max = 32)
  public String getCompanyType() {
    return companyType;
  }


  public void setCompanyType(String companyType) {
    this.companyType = companyType;
  }

  @Column(name = "REGISTER_MONEY", precision = 18, scale = 3)
  public BigDecimal getRegisterMoney() {
    return registerMoney;
  }

  public void setRegisterMoney(BigDecimal registerMoney) {
    this.registerMoney = registerMoney;
  }

  @Column(name = "LEVEL", length = 32)
  @Size(max = 32)
  public String getLevel() {
    return level;
  }

  public void setLevel(String level) {
    this.level = level;
  }

  @Column(name = "NAME", length = 100,nullable = false)
  @Size(max = 32)
  @NotEmpty
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Enumerated(EnumType.STRING)
  @Column(name = "LEGAL_TYPE", nullable = false, length = 16)
  @NotNull
  public PowerPersonEntity.LegalType getLegalType() {
    return legalType;
  }

  public void setLegalType(PowerPersonEntity.LegalType legalType) {
    this.legalType = legalType;
  }

  @Enumerated(EnumType.STRING)
  @Column(name = "CREDENTIALS_TYPE",nullable = false, length = 16)
  @NotNull
  public PersonEntity.CredentialsType getCredentialsType() {
    return credentialsType;
  }

  public void setCredentialsType(PersonEntity.CredentialsType credentialsType) {
    this.credentialsType = credentialsType;
  }

  @Transient
  @Override
  public String getCredentialsNumber() {
    return getOwnerCard();
  }

  @Transient
  @Override
  public void setCredentialsNumber(String s) {
    this.setOwnerCard(s);
  }

  @Transient
  @Override
  public String getPersonName() {
    return getOwnerName();
  }

  @Transient
  @Override
  public void setPersonName(String s) {
    setOwnerName(s);
  }

  @Enumerated(EnumType.STRING)
  @Column(name = "TYPE",nullable = false, length = 20)
  @NotNull
  public AttachCorpType getType() {
    return type;
  }

  public void setType(AttachCorpType type) {
    this.type = type;
  }

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "BUSINESS_ID", nullable = false)
  @NotNull
  public OwnerBusiness getOwnerBusiness() {
    return ownerBusiness;
  }

  public void setOwnerBusiness(OwnerBusiness ownerBusiness) {
    this.ownerBusiness = ownerBusiness;
  }
}
