package com.dgsoft.house.owner.model;
// Generated Aug 19, 2014 4:32:06 PM by Hibernate Tools 4.0.0

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Financial generated by hbm2java
 */
@Entity
@Table(name = "FINANCIAL", catalog = "HOUSE_OWNER_RECORD")
public class Financial implements java.io.Serializable {


	private String id;
	private OwnerBusiness ownerBusiness;
	private String type;
	private String name;
	private String code;

	public Financial() {
	}

	public Financial(String id, OwnerBusiness ownerBusiness, String type,
			String name, String code) {
		this.id = id;
		this.ownerBusiness = ownerBusiness;
		this.type = type;
		this.name = name;
		this.code = code;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BUSINESS_ID", nullable = false)
	@NotNull
	public OwnerBusiness getOwnerBusiness() {
		return this.ownerBusiness;
	}

	public void setOwnerBusiness(OwnerBusiness ownerBusiness) {
		this.ownerBusiness = ownerBusiness;
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

	@Column(name = "NAME", nullable = false, length = 120)
	@NotNull
	@Size(max = 120)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "CODE", nullable = false, length = 32)
	@NotNull
	@Size(max = 32)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
