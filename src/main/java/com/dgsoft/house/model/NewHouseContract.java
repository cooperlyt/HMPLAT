package com.dgsoft.house.model;
// Generated Jul 12, 2013 11:32:23 AM by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * NewHouseContract generated by hbm2java
 */
@Entity
@Table(name = "NEW_HOUSE_CONTRACT", catalog = "HOUSE_INFO")
public class NewHouseContract implements java.io.Serializable {

	private String id;
	private int version;
	private Demployee demployee;
	private HouseContract houseContract;

	public NewHouseContract() {
	}

	public NewHouseContract(String id, Demployee demployee,
			HouseContract houseContract) {
		this.id = id;
		this.demployee = demployee;
		this.houseContract = houseContract;
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

	@Version
	@Column(name = "VERSION", nullable = false)
	public int getVersion() {
		return this.version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DEMPLOYEE", nullable = false)
	@NotNull
	public Demployee getDemployee() {
		return this.demployee;
	}

	public void setDemployee(Demployee demployee) {
		this.demployee = demployee;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CONTRACT_ID", nullable = false)
	@NotNull
	public HouseContract getHouseContract() {
		return this.houseContract;
	}

	public void setHouseContract(HouseContract houseContract) {
		this.houseContract = houseContract;
	}

}
