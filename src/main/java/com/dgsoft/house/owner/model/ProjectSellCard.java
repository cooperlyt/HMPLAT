package com.dgsoft.house.owner.model;
// Generated Oct 11, 2014 3:13:15 PM by Hibernate Tools 4.0.0

import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * ProjectSellCard generated by hbm2java
 */
@Entity
@Table(name = "PROJECT_SELL_CARD", catalog = "HOUSE_OWNER_RECORD")
public class ProjectSellCard implements java.io.Serializable {

	private String id;
	private BusinessProject businessProject;
	private Integer houseCount;
	private Integer buildCount;
	private BigDecimal area;
	private Boolean prepareSell;
	private String useType;
	private String sellObject;
	private String yearNumber;
	private String orderNumber;
	private Date printTime;
	private String memo;
	private String type;
	private Set<ProjectRecord> projectRecords = new HashSet<ProjectRecord>(0);

    private String landCardNo;
    private String number;
    private String landProperty;
    private Date beginUseTime;
    private Date endUseTime;
    private BigDecimal landArea;
    private String landGetMode;
    private MakeCard makeCard;


	public ProjectSellCard() {
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
	@JoinColumn(name = "PROJECT", nullable = false)
	@NotNull
	public BusinessProject getBusinessProject() {
		return this.businessProject;
	}

	public void setBusinessProject(BusinessProject businessProject) {
		this.businessProject = businessProject;
	}

	@Column(name = "HOUSE_COUNT")
	public Integer getHouseCount() {
		return this.houseCount;
	}

	public void setHouseCount(Integer houseCount) {
		this.houseCount = houseCount;
	}

	@Column(name = "BUILD_COUNT")
	public Integer getBuildCount() {
		return this.buildCount;
	}

	public void setBuildCount(Integer buildCount) {
		this.buildCount = buildCount;
	}

	@Column(name = "AREA", precision = 18, scale = 3)
	public BigDecimal getArea() {
		return this.area;
	}

	public void setArea(BigDecimal area) {
		this.area = area;
	}

	@Column(name = "PREPARE_SELL")
	public Boolean getPrepareSell() {
		return this.prepareSell;
	}

	public void setPrepareSell(Boolean prepareSell) {
		this.prepareSell = prepareSell;
	}

	@Column(name = "USE_TYPE", length = 32)
	@Size(max = 32)
	public String getUseType() {
		return this.useType;
	}

	public void setUseType(String useType) {
		this.useType = useType;
	}

	@Column(name = "SELL_OBJECT", length = 50)
	@Size(max = 50)
	public String getSellObject() {
		return this.sellObject;
	}

	public void setSellObject(String sellObject) {
		this.sellObject = sellObject;
	}

	@Column(name = "YEAR_NUMBER", length = 20)
	@Size(max = 20)
	public String getYearNumber() {
		return this.yearNumber;
	}

	public void setYearNumber(String yearNumber) {
		this.yearNumber = yearNumber;
	}

	@Column(name = "ORDER_NUMBER", length = 20)
	@Size(max = 20)
	public String getOrderNumber() {
		return this.orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "PRINT_TIME", nullable = false, length = 19)
	@NotNull
	public Date getPrintTime() {
		return this.printTime;
	}

	public void setPrintTime(Date printTime) {
		this.printTime = printTime;
	}

	@Column(name = "MEMO", length = 200)
	@Size(max = 200)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Column(name = "TYPE", nullable = false, length = 20)
	@NotNull
	@Size(max = 20)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "projectSellCard")
	public Set<ProjectRecord> getProjectRecords() {
		return this.projectRecords;
	}

	public void setProjectRecords(Set<ProjectRecord> projectRecords) {
		this.projectRecords = projectRecords;
	}


    @Column(name = "LAND_CARD_NO", length = 50)
    @Size(max = 50)
    public String getLandCardNo() {
        return this.landCardNo;
    }

    public void setLandCardNo(String landCardNo) {
        this.landCardNo = landCardNo;
    }


    @Column(name = "NUMBER", nullable = false, length = 50)
    @NotNull
    @Size(max = 50)
    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


    @Column(name = "LAND_PROPERTY", length = 32)
    @Size(max = 32)
    public String getLandProperty() {
        return this.landProperty;
    }

    public void setLandProperty(String landProperty) {
        this.landProperty = landProperty;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "BEGIN_USE_TIME", nullable = false, length = 19)
    @NotNull
    public Date getBeginUseTime() {
        return this.beginUseTime;
    }

    public void setBeginUseTime(Date beginUseTime) {
        this.beginUseTime = beginUseTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "END_USE_TIME", nullable = false, length = 19)
    @NotNull
    public Date getEndUseTime() {
        return this.endUseTime;
    }

    public void setEndUseTime(Date endUseTime) {
        this.endUseTime = endUseTime;
    }


    @Column(name = "LAND_AREA", precision = 18, scale = 3)
    public BigDecimal getLandArea() {
        return this.landArea;
    }

    public void setLandArea(BigDecimal landArea) {
        this.landArea = landArea;
    }


    @Column(name = "LAND_GET_MODE", nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    public String getLandGetMode() {
        return this.landGetMode;
    }

    public void setLandGetMode(String landGetMode) {
        this.landGetMode = landGetMode;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CARD",nullable = false)
    @NotNull
    public MakeCard getMakeCard() {
        return makeCard;
    }

    public void setMakeCard(MakeCard makeCard) {
        this.makeCard = makeCard;
    }
}
