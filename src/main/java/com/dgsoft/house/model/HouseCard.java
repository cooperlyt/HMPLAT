package com.dgsoft.house.model;
// Generated Jul 12, 2013 11:32:23 AM by Hibernate Tools 4.0.0

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * HouseCard generated by hbm2java
 */
@Entity
@Table(name = "HOUSE_CARD", catalog = "HOUSE_INFO")
public class HouseCard implements java.io.Serializable {

	private HouseCardId id;
	private Card card;
	private House house;

	public HouseCard() {
	}

	public HouseCard(HouseCardId id, Card card, House house) {
		this.id = id;
		this.card = card;
		this.house = house;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "cardId", column = @Column(name = "CARD_ID", nullable = false, length = 32)),
			@AttributeOverride(name = "houseId", column = @Column(name = "HOUSE_ID", nullable = false, length = 32))})
	@NotNull
	public HouseCardId getId() {
		return this.id;
	}

	public void setId(HouseCardId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CARD_ID", nullable = false, insertable = false, updatable = false)
	@NotNull
	public Card getCard() {
		return this.card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "HOUSE_ID", nullable = false, insertable = false, updatable = false)
	@NotNull
	public House getHouse() {
		return this.house;
	}

	public void setHouse(House house) {
		this.house = house;
	}

}
