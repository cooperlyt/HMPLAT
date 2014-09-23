package com.dgsoft.house.owner.model;
// Generated Aug 19, 2014 4:32:06 PM by Hibernate Tools 4.0.0

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Reason generated by hbm2java
 */
@Entity
@Table(name = "REASON", catalog = "HOUSE_OWNER_RECORD")
public class Reason implements java.io.Serializable {

    public enum ReasonType{MODIFY_BEFOR_RENSON,MODIFY_AFTER_RENSON,CHANG_BEFOR_RESON,CHANG_AFTER_RESON,FILL_CHANGE};
	private String id;
	private OwnerBusiness ownerBusiness;
	private ReasonType type;
	private String reason;

	public Reason() {
	}

    public Reason(ReasonType type) {
        this.type = type;
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
	@JoinColumn(name = "BUISINESS_ID", nullable = false)
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
	public ReasonType getType() {
		return this.type;
	}

	public void setType(ReasonType type) {
		this.type = type;
	}

	@Column(name = "REASON", nullable = true, length = 200)
	@Size(max = 200)
	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
