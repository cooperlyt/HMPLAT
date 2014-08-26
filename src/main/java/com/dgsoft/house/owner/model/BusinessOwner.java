package com.dgsoft.house.owner.model;
// Generated Aug 19, 2014 4:32:06 PM by Hibernate Tools 4.0.0

import com.dgsoft.common.system.PersonEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * BusinessOwner generated by hbm2java
 */
@Entity
@Table(name = "BUSINESS_OWNER", catalog = "HOUSE_OWNER_RECORD")
public class BusinessOwner implements java.io.Serializable,PersonEntity {

	private String id;
	private BusinessHouse businessHouse;
	private String personName;
	private CredentialsType credentialsType;
	private String credentialsNumber;
	private String phone;
	private String rootAddress;
	private String type;

	public BusinessOwner() {
	}


	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 32)
	@NotNull
	@Size(max = 32)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BUSINESS_ID", nullable = false)
	@NotNull
	public BusinessHouse getBusinessHouse() {
		return this.businessHouse;
	}

	public void setBusinessHouse(BusinessHouse businessHouse) {
		this.businessHouse = businessHouse;
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
	@Column(name = "ID_TYPE", nullable = false, length = 32)
	@NotNull
	@Size(max = 32)
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

	@Column(name = "TYPE", nullable = false, length = 10)
	@NotNull
	@Size(max = 10)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
