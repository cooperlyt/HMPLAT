package com.dgsoft.house.owner.model;
// Generated Aug 19, 2014 4:32:06 PM by Hibernate Tools 4.0.0

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
 * BusinessPersion generated by hbm2java
 */
@Entity
@Table(name = "BUSINESS_PERSION", catalog = "HOUSE_OWNER_RECORD")
public class BusinessPersion implements java.io.Serializable {

	private String id;
	private OwnerBusiness ownerBusiness;
	private String idNo;
	private String idType;
	private String name;
	private String type;
	private String phone;

	public BusinessPersion() {
	}

	public BusinessPersion(String id, OwnerBusiness ownerBusiness, String idNo,
			String idType, String name, String type) {
		this.id = id;
		this.ownerBusiness = ownerBusiness;
		this.idNo = idNo;
		this.idType = idType;
		this.name = name;
		this.type = type;
	}
	public BusinessPersion(String id, OwnerBusiness ownerBusiness, String idNo,
			String idType, String name, String type, String phone) {
		this.id = id;
		this.ownerBusiness = ownerBusiness;
		this.idNo = idNo;
		this.idType = idType;
		this.name = name;
		this.type = type;
		this.phone = phone;
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
	public OwnerBusiness getOwnerBusiness() {
		return this.ownerBusiness;
	}

	public void setOwnerBusiness(OwnerBusiness ownerBusiness) {
		this.ownerBusiness = ownerBusiness;
	}

	@Column(name = "ID_NO", nullable = false, length = 100)
	@NotNull
	@Size(max = 100)
	public String getIdNo() {
		return this.idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	@Column(name = "ID_TYPE", nullable = false, length = 32)
	@NotNull
	@Size(max = 32)
	public String getIdType() {
		return this.idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
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

	@Column(name = "TYPE", nullable = false, length = 10)
	@NotNull
	@Size(max = 10)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "PHONE", length = 15)
	@Size(max = 15)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
