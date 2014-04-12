package com.dgsoft.common.system.model;
// Generated Apr 28, 2013 11:02:59 AM by Hibernate Tools 4.0.0

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Person generated by hbm2java
 */
@Entity
@Table(name = "PERSON")
public class Person implements java.io.Serializable {

    public enum Sex {
        MALE,FEMALE;
    }

    public enum CredentialsType {
        ID_CRAR,SOLDIER_CARD,PASSPORT,OTHER;

    }

	private String id;
	private String name;
	private CredentialsType credentialsType;
	private String credentialsNumber;
	private String credentialsOrgan;
	private Sex sex;
	private String ethnic;
	private Date dateOfBirth;
	private String address;
	private String nowAddress;
	private String nationality;
	private String phone;
	private String city;
    private boolean foreign;

	public Person() {
	}

    public Person(String ethnic,String nationality){
        this.ethnic = ethnic;
        this.nationality = nationality;
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
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "CREDENTIALS_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
	public CredentialsType getCredentialsType() {
		return this.credentialsType;
	}

	public void setCredentialsType(CredentialsType credentialsType) {
		this.credentialsType = credentialsType;
	}

	@Column(name = "CREDENTIALS_NUMBER", nullable = false, length = 100)
	@NotNull
	@Size(max = 100)
	public String getCredentialsNumber() {
		return this.credentialsNumber;
	}

	public void setCredentialsNumber(String credentialsNumber) {
		this.credentialsNumber = credentialsNumber;
	}

	@Column(name = "CREDENTIALS_ORGAN", length = 100)
	@Size(max = 100)
	public String getCredentialsOrgan() {
		return this.credentialsOrgan;
	}

	public void setCredentialsOrgan(String credentialsOrgan) {
		this.credentialsOrgan = credentialsOrgan;
	}

	@Column(name = "SEX")
    @Enumerated(EnumType.STRING)
	public Sex getSex() {
		return this.sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	@Column(name = "ETHNIC", length = 20)
    @Size(max = 20)
	public String getEthnic() {
		return this.ethnic;
	}

	public void setEthnic(String ethnic) {
		this.ethnic = ethnic;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_OF_BIRTH", length = 19,columnDefinition = "DATETIME")
	public Date getDateOfBirth() {
		return this.dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	@Column(name = "ADDRESS", length = 200)
	@Size(max = 200)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "NOW_ADDRESS", length = 200)
	@Size(max = 200)
	public String getNowAddress() {
		return this.nowAddress;
	}

	public void setNowAddress(String nowAddress) {
		this.nowAddress = nowAddress;
	}

	@Column(name = "NATIONALITY", length = 100)
	@Size(max = 100)
	public String getNationality() {
		return this.nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	@Column(name = "PHONE", length = 50)
	@Size(max = 50)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "CITY", length = 50)
	@Size(max = 50)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

    @Column(name= "_FOREIGN", nullable = false)
    public boolean isForeign() {
        return foreign;
    }

    public void setForeign(boolean foreign) {
        this.foreign = foreign;
    }
}
