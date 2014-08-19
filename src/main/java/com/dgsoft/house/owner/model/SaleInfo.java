package com.dgsoft.house.owner.model;
// Generated Aug 19, 2014 4:32:06 PM by Hibernate Tools 4.0.0

import java.math.BigDecimal;
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
 * SaleInfo generated by hbm2java
 */
@Entity
@Table(name = "SALE_INFO", catalog = "HOUSE_OWNER_RECORD")
public class SaleInfo implements java.io.Serializable {

	private String id;
	private HouseBusiness houseBusiness;
	private String payType;
	private BigDecimal sumPrice;

	public SaleInfo() {
	}

	public SaleInfo(String id, HouseBusiness houseBusiness, BigDecimal sumPrice) {
		this.id = id;
		this.houseBusiness = houseBusiness;
		this.sumPrice = sumPrice;
	}
	public SaleInfo(String id, HouseBusiness houseBusiness, String payType,
			BigDecimal sumPrice) {
		this.id = id;
		this.houseBusiness = houseBusiness;
		this.payType = payType;
		this.sumPrice = sumPrice;
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
	public HouseBusiness getHouseBusiness() {
		return this.houseBusiness;
	}

	public void setHouseBusiness(HouseBusiness houseBusiness) {
		this.houseBusiness = houseBusiness;
	}

	@Column(name = "PAY_TYPE", length = 32)
	@Size(max = 32)
	public String getPayType() {
		return this.payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	@Column(name = "SUM_PRICE", nullable = false, scale = 4)
	@NotNull
	public BigDecimal getSumPrice() {
		return this.sumPrice;
	}

	public void setSumPrice(BigDecimal sumPrice) {
		this.sumPrice = sumPrice;
	}

}