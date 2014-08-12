package com.dgsoft.house.model;
// Generated Jul 12, 2013 11:32:23 AM by Hibernate Tools 4.0.0

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * AttachCorporation generated by hbm2java
 */
@Entity
@Table(name = "ATTACH_CORPORATION", catalog = "HOUSE_INFO")
public class AttachCorporation implements java.io.Serializable {

	private String id;
	private Date recordDate;
	private String address;
	private String phone;
	private String ownerName;
	private String ownerCard;
	private String fax;
	private String email;
	private String memo;
	private String password;
	private String postCode;
	private boolean enable;
	private String licenseNumber;
	private String taxLicense;
	private String companyCode;
	private String companyType;
	private BigDecimal registerMoney;
	private String level;
	private Date dateTo;
	private String manager;
	private Set<OrgAttachAction> orgAttachActions = new HashSet<OrgAttachAction>(
			0);
    private Developer developer;

    private MappingCorporation  mappingCorporation;

    private EvaluateCorporation evaluateCorporation;



    private FinancialCorporation financialCorporation;

    public AttachCorporation() {
	}

    public AttachCorporation(boolean enable){
        this.enable = enable;
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RECORD_DATE", nullable = false, length = 19)
	@NotNull
	public Date getRecordDate() {
		return this.recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	@Column(name = "ADDRESS", length = 100)
	@Size(max = 100)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "PHONE", length = 200)
	@Size(max = 200)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "OWNER_NAME", length = 20)
	@Size(max = 20)
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

	@Column(name = "PASSWORD", length = 50)
	@Size(max = 50)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	@Column(name = "LICENSE_NUMBER", length = 100)
	@Size(max = 100)
	public String getLicenseNumber() {
		return this.licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	@Column(name = "TAX_LICENSE", length = 100)
	@Size(max = 100)
	public String getTaxLicense() {
		return this.taxLicense;
	}

	public void setTaxLicense(String taxLicense) {
		this.taxLicense = taxLicense;
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

	@Column(name = "MANAGER", length = 20)
	@Size(max = 20)
	public String getManager() {
		return this.manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

    @OneToOne(fetch = FetchType.LAZY,mappedBy = "attachCorporation")
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

    @OneToOne(fetch = FetchType.LAZY,mappedBy = "attachCorporation")
    public EvaluateCorporation getEvaluateCorporation() {
        return evaluateCorporation;
    }

    public void setEvaluateCorporation(EvaluateCorporation evaluateCorporation) {
        this.evaluateCorporation = evaluateCorporation;
    }

    @OneToOne(fetch = FetchType.LAZY,mappedBy = "attachCorporation")
    public FinancialCorporation getFinancialCorporation() {
        return financialCorporation;
    }

    public void setFinancialCorporation(FinancialCorporation financialCorporation) {
        this.financialCorporation = financialCorporation;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "attachCorporation")
	public Set<OrgAttachAction> getOrgAttachActions() {
		return this.orgAttachActions;
	}

	public void setOrgAttachActions(Set<OrgAttachAction> orgAttachActions) {
		this.orgAttachActions = orgAttachActions;
	}
}
