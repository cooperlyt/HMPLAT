package com.dgsoft.house.model;
// Generated Jul 12, 2013 11:32:23 AM by Hibernate Tools 4.0.0

import com.dgsoft.common.system.PersonEntity;
import com.dgsoft.common.system.PowerPersonEntity;
import com.dgsoft.house.AttachCorpType;
import org.jboss.seam.log.Logging;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.*;

/**
 * AttachCorporation generated by hbm2java
 */
@Entity
@Table(name = "ATTACH_CORPORATION", catalog = "HOUSE_INFO")
public class AttachCorporation implements PersonEntity, java.io.Serializable {


	private String id;
	private Date recordDate;
	private String address;
	private String phone;
	private String ownerTel;
	private String ownerName;
	private String ownerCard;
	private String fax;
	private String email;
	private String memo;
	private String postCode;
	private boolean enable;
	private String licenseNumber;
	private String cerCode;

	private String companyCode;
	private String companyType;
	private BigDecimal registerMoney;
	private String level;
	private Date dateTo;

	private PowerPersonEntity.LegalType legalType;
	private PersonEntity.CredentialsType credentialsType;

	private AttachCorpType type;
	private Set<OrgAttachAction> orgAttachActions = new HashSet<OrgAttachAction>(0);
    private Set<AttachEmployee> attachEmployees = new HashSet<AttachEmployee>(0);
    private Developer developer;


    private MappingCorporation  mappingCorporation;

    private EvaluateCorporation evaluateCorporation;

    private Mcompany propertyCorporation;

	private Agencies agencies;




	public AttachCorporation(String id,AttachCorpType type,boolean enable, Date recordDate) {
		this.enable = enable;
		this.id = id;
		this.type = type;
		this.recordDate = recordDate;
		this.legalType = PowerPersonEntity.LegalType.LEGAL_OWNER;
	}

	public AttachCorporation() {
	}

    public AttachCorporation(boolean enable){
        this.enable = enable;
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RECORD_DATE", nullable = false, length = 19)
	@NotNull
	public Date getRecordDate() {
		return this.recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	@Column(name = "ADDRESS", nullable = false,length = 100)
	@Size(max = 100)
	@NotNull
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "PHONE",nullable = false,length = 200)
	@Size(max = 200)
	@NotNull
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "OWNER_NAME",nullable = false, length = 20)
	@Size(max = 20)
	@NotNull
	public String getOwnerName() {
		return this.ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	@Column(name = "OWNER_CARD", length = 50)
	@Size(max = 50)
	public String getOwnerCard() {
		return this.ownerCard;
	}

	public void setOwnerCard(String ownerCard) {
		this.ownerCard = ownerCard;
	}

	@Column(name = "FAX", length = 50)
	@Size(max = 50)
	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name = "EMAIL", length = 50)
	@Size(max = 50)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "MEMO", length = 200)
	@Size(max = 200)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Column(name = "POST_CODE", length = 50)
	@Size(max = 50)
	public String getPostCode() {
		return this.postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

    @Column(name = "ENABLE",nullable = false)
    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

	@Column(name = "LICENSE_NUMBER",nullable = false,length = 100)
	@Size(max = 100)
	@NotNull
	public String getLicenseNumber() {
		return this.licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	@Column(name = "COMPANY_CER_CODE", length = 100)
	@Size(max = 100)
	public String getCerCode() {
		return cerCode;
	}

	public void setCerCode(String cerCode) {
		this.cerCode = cerCode;
	}

    @Column(name="OWNER_TEL", length = 100)
    @Size(max = 100)
    public String getOwnerTel() {
        return ownerTel;
    }

    public void setOwnerTel(String ownerTel) {
        this.ownerTel = ownerTel;
    }


	@Column(name = "COMPANY_CODE", length = 100)
	@Size(max = 100)
	public String getCompanyCode() {
		return this.companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	@Column(name = "COMPANY_TYPE", length = 32)
	@Size(max = 32)
	public String getCompanyType() {
		return this.companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	@Column(name = "REGISTER_MONEY", precision = 18, scale = 3)
	public BigDecimal getRegisterMoney() {
		return this.registerMoney;
	}

	public void setRegisterMoney(BigDecimal registerMoney) {
		this.registerMoney = registerMoney;
	}

	@Column(name = "LEVEL", length = 32)
	@Size(max = 32)
	public String getLevel() {
		return this.level;
	}

	public void setLevel(String level) {
		this.level = level;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_TO", nullable = false, length = 19)
	@NotNull
	public Date getDateTo() {
		return this.dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}



    @OneToOne(fetch = FetchType.LAZY,mappedBy = "attachCorporation", cascade = CascadeType.ALL)
    public Developer getDeveloper() {
        return developer;
    }

    public void setDeveloper(Developer developer) {
        this.developer = developer;
    }

    @OneToOne(fetch = FetchType.LAZY,mappedBy = "attachCorporation")
    public MappingCorporation getMappingCorporation() {
        return mappingCorporation;
    }


    public void setMappingCorporation(MappingCorporation mappingCorporation) {
        this.mappingCorporation = mappingCorporation;
    }

    @OneToOne(fetch = FetchType.LAZY,mappedBy = "attachCorporation",cascade = CascadeType.ALL)
    public EvaluateCorporation getEvaluateCorporation() {
        return evaluateCorporation;
    }

    public void setEvaluateCorporation(EvaluateCorporation evaluateCorporation) {
        this.evaluateCorporation = evaluateCorporation;
    }

	@OneToOne(fetch = FetchType.LAZY,mappedBy = "attachCorporation", cascade = CascadeType.ALL)
	public Agencies getAgencies() {
		return agencies;
	}

	public void setAgencies(Agencies agencies) {
		this.agencies = agencies;
	}


	@OneToOne(fetch = FetchType.LAZY,mappedBy = "attachCorporation", cascade = CascadeType.ALL)
	public Mcompany getPropertyCorporation() {
		return propertyCorporation;
	}

	public void setPropertyCorporation(Mcompany propertyCorporation) {
		this.propertyCorporation = propertyCorporation;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "attachCorporation")
	public Set<OrgAttachAction> getOrgAttachActions() {
		return this.orgAttachActions;
	}

	public void setOrgAttachActions(Set<OrgAttachAction> orgAttachActions) {
		this.orgAttachActions = orgAttachActions;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "TYPE",nullable = false, length = 20)
	@NotNull
	public AttachCorpType getType() {
		return type;
	}

	public void setType(AttachCorpType type) {
		this.type = type;
	}

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "attachCorporation")
    public Set<AttachEmployee> getAttachEmployees() {
        return attachEmployees;
    }

    @Enumerated(EnumType.STRING)
	@Column(name = "LEGAL_TYPE", nullable = false, length = 16)
	@NotNull
	public PowerPersonEntity.LegalType getLegalType() {
		return legalType;
	}

	public void setLegalType(PowerPersonEntity.LegalType legalType) {
		this.legalType = legalType;
	}

	@Override
	@Enumerated(EnumType.STRING)
	@Column(name = "CREDENTIALS_TYPE",nullable = false, length = 16)
	@NotNull
	public PersonEntity.CredentialsType getCredentialsType() {
		return credentialsType;
	}

	public void setCredentialsType(PersonEntity.CredentialsType credentialsType) {
		this.credentialsType = credentialsType;
	}

	@Transient
	@Override
	public String getCredentialsNumber() {
		return getOwnerCard();
	}

	@Transient
	@Override
	public void setCredentialsNumber(String s) {
		Logging.getLog(this.getClass()).debug("set card number:" + s);
		this.setOwnerCard(s);
	}

	@Transient
	@Override
	public String getPersonName() {
		return getOwnerName();
	}

	@Transient
	@Override
	public void setPersonName(String s) {
		setOwnerName(s);
	}

	public void setAttachEmployees(Set<AttachEmployee> attachEmployees) {
        this.attachEmployees = attachEmployees;
    }

    @Transient
    public List<AttachEmployee> getAttachEmployeeList(){
        List<AttachEmployee> result = new ArrayList<AttachEmployee>(getAttachEmployees());
        Collections.sort(result, new Comparator<AttachEmployee>() {
            @Override
            public int compare(AttachEmployee o1, AttachEmployee o2) {
                return o1.getId().compareTo(o2.getId());
            }
        });
        return result;
    }

    @Transient
	public boolean isOutTime(){
		return getDateTo().compareTo(new Date()) < 0;
	}

	@Transient
	public String getName(){
		switch (type){

			case DEVELOPER:
				return getDeveloper().getName();
			case MCOMPANY:
				return getPropertyCorporation().getName();
			case EVALUATE:
				return getEvaluateCorporation().getName();
			case MAPPING:
				return getMappingCorporation().getName();
			case AGENCIES:
				return getAgencies().getName();

			default:
				throw new IllegalAccessError("unknow attr corp type");
		}
	}


}
