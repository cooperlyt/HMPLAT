package com.dgsoft.house.model;
// Generated Jul 12, 2013 11:32:23 AM by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * SellEmployee generated by hbm2java
 */
@Entity
@Table(name = "SELL_EMPLOYEE", catalog = "HOUSE_INFO")
public class SellEmployee implements java.io.Serializable {

	private String id;
	private AttachEmployee attachEmployee;
	private HouseSellCompany houseSellCompany;
	private String memo;
	private Set<Oldhousecontract> oldhousecontracts = new HashSet<Oldhousecontract>(
			0);

	public SellEmployee() {
	}

	public SellEmployee(String id, AttachEmployee attachEmployee,
			HouseSellCompany houseSellCompany) {
		this.id = id;
		this.attachEmployee = attachEmployee;
		this.houseSellCompany = houseSellCompany;
	}
	public SellEmployee(String id, AttachEmployee attachEmployee,
			HouseSellCompany houseSellCompany, String memo,
			Set<Oldhousecontract> oldhousecontracts) {
		this.id = id;
		this.attachEmployee = attachEmployee;
		this.houseSellCompany = houseSellCompany;
		this.memo = memo;
		this.oldhousecontracts = oldhousecontracts;
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
	@JoinColumn(name = "ATTACH_EMLOPEE_ID", nullable = false)
	@NotNull
	public AttachEmployee getAttachEmployee() {
		return this.attachEmployee;
	}

	public void setAttachEmployee(AttachEmployee attachEmployee) {
		this.attachEmployee = attachEmployee;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "INTERMEDIARY", nullable = false)
	@NotNull
	public HouseSellCompany getHouseSellCompany() {
		return this.houseSellCompany;
	}

	public void setHouseSellCompany(HouseSellCompany houseSellCompany) {
		this.houseSellCompany = houseSellCompany;
	}

	@Column(name = "MEMO", length = 200)
	@Size(max = 200)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "sellEmployee")
	public Set<Oldhousecontract> getOldhousecontracts() {
		return this.oldhousecontracts;
	}

	public void setOldhousecontracts(Set<Oldhousecontract> oldhousecontracts) {
		this.oldhousecontracts = oldhousecontracts;
	}

}
