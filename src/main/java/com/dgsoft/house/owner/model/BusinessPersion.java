package com.dgsoft.house.owner.model;
// Generated Aug 19, 2014 4:32:06 PM by Hibernate Tools 4.0.0

import com.dgsoft.common.system.PersonEntity;
import org.hibernate.annotations.GenericGenerator;
import org.jboss.seam.log.Logging;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * BusinessPersion generated by hbm2java
 */
@Entity
@Table(name = "BUSINESS_PERSION", catalog = "HOUSE_OWNER_RECORD")
public class BusinessPersion implements java.io.Serializable,PersonEntity{


    public enum PersionType{
        OWNER_ENTRUST,BUY_ENTRUST,SELL_ENTRUST,PRE_SALE_ENTRUST,APPLY_PERSION,
        PRE_BUY_ENTRUST,TERRIBLE_RELATION,CORRECT,MORTGAGE,MORTGAGE_OBLIGEE,
        RECORD_OWNER,MORTGAGE_OBLIGOR,MORTGAGE_PROJECT,MORTGAGE_OBLIGEE_OLD;
    }


    private String id;
	private OwnerBusiness ownerBusiness;
	private String credentialsNumber;
	private CredentialsType credentialsType;
	private String personName;
	private PersionType type;
	private String phone;


	public BusinessPersion() {
	}

	public BusinessPersion(PersionType type) {
        this.type = type;
	}

    public BusinessPersion(OwnerBusiness ownerBusiness, BusinessPersion businessPersion) {
        Logging.getLog(getClass()).debug(ownerBusiness.getId());
        this.ownerBusiness = ownerBusiness;
        this.credentialsNumber = businessPersion.getCredentialsNumber();
        this.credentialsType = businessPersion.getCredentialsType();
        this.personName = businessPersion.getPersonName();
        this.type = businessPersion.getType();
        this.phone = businessPersion.getPhone();
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

    @Override
    @Enumerated(EnumType.STRING)
	@Column(name = "ID_TYPE", nullable = false, length = 32)
	@NotNull
    public CredentialsType getCredentialsType() {
        return this.credentialsType;
    }

    public void setCredentialsType(CredentialsType idType) {
        this.credentialsType = idType;
    }

    @Override
	@Column(name = "NAME", nullable = false, length = 50)
	@NotNull
	@Size(max = 50)
	public String getPersonName() {
		return this.personName;
	}

	public void setPersonName(String Name) {
		this.personName = Name;
	}


    @Enumerated(EnumType.STRING)
	@Column(name = "TYPE", nullable = false, length = 20)
	@NotNull
	public PersionType getType() {
		return this.type;
	}

	public void setType(PersionType type) {
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
