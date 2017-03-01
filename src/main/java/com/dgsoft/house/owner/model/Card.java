package com.dgsoft.house.owner.model;
// Generated Aug 19, 2014 4:32:06 PM by Hibernate Tools 4.0.0

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Card generated by hbm2java
 */
@Entity
@Table(name = "CARD", catalog = "HOUSE_OWNER_RECORD")
public class Card implements java.io.Serializable {
    //预告登记证明号，所有权证号，抵押权证号，在建工程抵押登记证明号，预售许可证证号，                                 抵押预告登记证明号
    public enum CardType{NOTICE,OWNER_RSHIP,MORTGAGE,PROJECT_MORTGAGE,SALE_LICENSE,MERCHANDISE_CONTRAC,NOTICE_MORTGAGE,OTHER_CARD};
	private String id;
	private OwnerBusiness ownerBusiness;
	private CardType type;
	private String number;
    private String code;
    private String memo;



	public Card() {
	}

    public Card(CardType type) {
        this.type = type;
    }

    public Card(OwnerBusiness ownerBusiness, Card card) {
        this.ownerBusiness = ownerBusiness;
        this.type = card.getType();
        this.number = card.getNumber();
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

	@Column(name = "TYPE", nullable = false, length = 20)
	@NotNull
    @Enumerated(EnumType.STRING)
    public CardType getType() {
		return this.type;
	}

	public void setType(CardType type) {
		this.type = type;
	}

	@Column(name = "NUMBER", nullable = false, length = 100)
	@NotNull
	@Size(max = 100)
	public String getNumber() {
		return this.number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

    @Column(name = "MEMO", nullable = true, length = 200)
    @Size(max = 200)
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Column(name = "CODE", nullable = true, length = 30)
    @Size(max = 30)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
