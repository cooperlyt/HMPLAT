package com.dgsoft.house.owner.model;
// Generated Aug 19, 2014 4:32:06 PM by Hibernate Tools 4.0.0

import com.dgsoft.common.BigMoneyUtil;
import com.dgsoft.common.TimeArea;
import com.dgsoft.common.TimeAreaHelper;
import org.hibernate.annotations.GenericGenerator;
import org.jboss.seam.log.Logging;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * MortgaegeRegiste generated by hbm2java
 */
@Entity
@Table(name = "MORTGAEGE_REGISTE", catalog = "HOUSE_OWNER_RECORD")
public class MortgaegeRegiste implements java.io.Serializable ,TimeArea{

	private String id;
	private OwnerBusiness ownerBusiness;
	private BigDecimal highestMountMoney;
	private String warrantScope;
	private String interestType;
	private Date mortgageDueTimeS;
	private Date mortgageTime;
	private BigDecimal mortgageArea;
    private Financial oldFinancial;
    private Financial financial;
	private String orgName;
	private TimeArea.TimeShowType timeShowType;




	public MortgaegeRegiste() {
	}

    public MortgaegeRegiste(OwnerBusiness ownerBusiness) {
        this.ownerBusiness = ownerBusiness;
    }

    public MortgaegeRegiste(OwnerBusiness ownerBusiness, MortgaegeRegiste mortgaegeRegiste) {
        this.ownerBusiness = ownerBusiness;
        highestMountMoney = mortgaegeRegiste.getHighestMountMoney();
        warrantScope = mortgaegeRegiste.getWarrantScope();
        interestType = mortgaegeRegiste.getInterestType();
        mortgageDueTimeS = mortgaegeRegiste.getMortgageDueTimeS();
        mortgageTime = mortgaegeRegiste.getMortgageTime();
        mortgageArea = mortgaegeRegiste.getMortgageArea();
        oldFinancial = mortgaegeRegiste.getOldFinancial();
        financial = mortgaegeRegiste.getFinancial();

        if (mortgaegeRegiste.getOldFinancial() != null){
            oldFinancial = mortgaegeRegiste.getOldFinancial();
        }
        if (mortgaegeRegiste.getFinancial() != null){
            financial = new Financial(mortgaegeRegiste.getFinancial());
            financial.setMortgaegeForNew(this);
        }
        orgName = mortgaegeRegiste.getOrgName();

    }
    public MortgaegeRegiste(OwnerBusiness ownerBusiness, MortgaegeRegiste mortgaegeRegiste,Financial oldFinancial) {
        this.ownerBusiness = ownerBusiness;
        highestMountMoney = mortgaegeRegiste.getHighestMountMoney();
        warrantScope = mortgaegeRegiste.getWarrantScope();
        interestType = mortgaegeRegiste.getInterestType();
        mortgageDueTimeS = mortgaegeRegiste.getMortgageDueTimeS();
        mortgageTime = mortgaegeRegiste.getMortgageTime();
        mortgageArea = mortgaegeRegiste.getMortgageArea();
        this.oldFinancial = oldFinancial;
        financial = new Financial(mortgaegeRegiste.getFinancial());
        orgName = mortgaegeRegiste.getOrgName();
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

	@Column(name = "HIGHEST_MOUNT_MONEY", nullable = false, scale = 4)
	@NotNull
	public BigDecimal getHighestMountMoney() {
		return this.highestMountMoney;
	}

	public void setHighestMountMoney(BigDecimal highestMountMoney) {
		this.highestMountMoney = highestMountMoney;
	}

	@Column(name = "WARRANT_SCOPE", nullable = false, length = 100)
	@NotNull
	@Size(max = 100)
	public String getWarrantScope() {
		return this.warrantScope;
	}

	public void setWarrantScope(String warrantScope) {
		this.warrantScope = warrantScope;
	}

	@Column(name = "INTEREST_TYPE", nullable = false, length = 32)
	@NotNull
	@Size(max = 32)
	public String getInterestType() {
		return this.interestType;
	}

	public void setInterestType(String interestType) {
		this.interestType = interestType;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MORTGAGE_DUE_TIME_S", nullable = false, length = 19)
	@NotNull
	public Date getMortgageDueTimeS() {
		return this.mortgageDueTimeS;
	}

	public void setMortgageDueTimeS(Date mortgageDueTimeS) {
		this.mortgageDueTimeS = mortgageDueTimeS;
	}

    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MORTGAGE_TIME", nullable = false,length = 19)
	@NotNull
	public Date getMortgageTime() {
		return this.mortgageTime;
	}

	public void setMortgageTime(Date mortgageTime) {
		this.mortgageTime = mortgageTime;
	}

	@Column(name = "MORTGAGE_AREA", nullable = false, scale = 4)
	@NotNull
	public BigDecimal getMortgageArea() {
		return this.mortgageArea;
	}

	public void setMortgageArea(BigDecimal mortgageArea) {
		this.mortgageArea = mortgageArea;
	}

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OLD_FIN", nullable = true)
    public Financial getOldFinancial() {
        return oldFinancial;
    }

    public void setOldFinancial(Financial oldFinancial) {
        this.oldFinancial = oldFinancial;
    }

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "FIN", nullable = true)
    public Financial getFinancial() {
        return financial;
    }

    public void setFinancial(Financial financial) {
        this.financial = financial;
    }

	@Transient
    public String getBigHighestMountMoney(){
        return BigMoneyUtil.getBigMoney(getHighestMountMoney().doubleValue());
    }


	@Column(name = "ORG_NAME",nullable = false, length = 100)
	@NotNull
	@Size(max = 100)
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}


	@Override
	@Enumerated(EnumType.STRING)
	@Column(name = "TIME_AREA_TYPE",nullable = false,length = 20)
	@NotNull
	public TimeArea.TimeShowType getTimeShowType() {
		return timeShowType;
	}

	public void setTimeShowType(TimeArea.TimeShowType timeShowType) {
		this.timeShowType = timeShowType;
	}

	@Override
	@Transient
	public Date getFromTime() {
		return getMortgageDueTimeS();
	}

	@Override
	@Transient
	public void setFromTime(Date date) {
		setMortgageDueTimeS(date);

	}

	@Override
	@Transient
	public Date getToTime() {
		return getMortgageTime();
	}

	@Override
	@Transient
	public void setToTime(Date date) {
		setMortgageTime(date);
	}


	@Transient
	public TimeAreaHelper getTimeArea(){
		return new TimeAreaHelper(this);
	}

}
