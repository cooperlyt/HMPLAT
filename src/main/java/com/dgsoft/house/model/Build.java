package com.dgsoft.house.model;
// Generated Jul 12, 2013 11:32:23 AM by Hibernate Tools 4.0.0

import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.swing.tree.TreeNode;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Build generated by hbm2java
 */
@Entity
@Table(name = "BUILD", catalog = "HOUSE_INFO")
public class Build implements java.io.Serializable,TreeNode {

	private String id;
	private Integer version;
	private ProjectSellCard projectSellCard;
	private Project project;
	private LandInfo landInfo;
	private String mapNumber;
	private String blockNo;
	private String buildNo;
	private String Name;
	private String doorNo;
	private Integer unintCount;
	private Integer floorCount;
	private String address;
	private Integer houseCount;
	private BigDecimal area;
	private BigDecimal lng;
	private BigDecimal lat;
	private Integer zoom;
	private String buildType;
	private String structure;
	private String memo;
	private boolean inbiz;
	private String businessKey;
	private Integer homeCount;
	private BigDecimal homeArea;
	private Integer unhomeCount;
	private BigDecimal unhomeArea;
    private BigDecimal buildCost;
	private Integer shopCount;
	private BigDecimal shopArea;
	private Set<House> houses = new HashSet<House>(0);
	private Set<BuildGridMap> buildGridMaps = new HashSet<BuildGridMap>(0);

	public Build() {
	}

    public Build(Project project) {
        this.project = project;
    }

	public Build(String id, Project project, String mapNumber, String blockNo,
			String buildNo, String Name, boolean inbiz) {
		this.id = id;
		this.project = project;
		this.mapNumber = mapNumber;
		this.blockNo = blockNo;
		this.buildNo = buildNo;
		this.Name = Name;
		this.inbiz = inbiz;
	}


	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 32)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@NotNull
	@Size(max = 32)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Version
	@Column(name = "VERSION")
	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CARD_ID")
	public ProjectSellCard getProjectSellCard() {
		return this.projectSellCard;
	}

	public void setProjectSellCard(ProjectSellCard projectSellCard) {
		this.projectSellCard = projectSellCard;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT_ID", nullable = false)
	@NotNull
	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LAND_ID")
	public LandInfo getLandInfo() {
		return this.landInfo;
	}

	public void setLandInfo(LandInfo landInfo) {
		this.landInfo = landInfo;
	}

	@Column(name = "MAP_NUMBER", nullable = false, length = 50)
	@NotNull
	@Size(max = 50)
	public String getMapNumber() {
		return this.mapNumber;
	}

	public void setMapNumber(String mapNumber) {
		this.mapNumber = mapNumber;
	}

	@Column(name = "BLOCK_NO", nullable = false, length = 50)
	@NotNull
	@Size(max = 50)
	public String getBlockNo() {
		return this.blockNo;
	}

	public void setBlockNo(String blockNo) {
		this.blockNo = blockNo;
	}

	@Column(name = "BUILD_NO", nullable = false, length = 50)
	@NotNull
	@Size(max = 50)
	public String getBuildNo() {
		return this.buildNo;
	}

	public void setBuildNo(String buildNo) {
		this.buildNo = buildNo;
	}

	@Column(name = "NAME", nullable = false, length = 100)
	@NotNull
	@Size(max = 100)
	public String getName() {
		return this.Name;
	}

	public void setName(String Name) {
		this.Name = Name;
	}

	@Column(name = "DOOR_NO", length = 10)
	@Size(max = 10)
	public String getDoorNo() {
		return this.doorNo;
	}

	public void setDoorNo(String doorNo) {
		this.doorNo = doorNo;
	}

	@Column(name = "UNINT_COUNT")
	public Integer getUnintCount() {
		return this.unintCount;
	}

	public void setUnintCount(Integer unintCount) {
		this.unintCount = unintCount;
	}

	@Column(name = "FLOOR_COUNT")
	public Integer getFloorCount() {
		return this.floorCount;
	}

	public void setFloorCount(Integer floorCount) {
		this.floorCount = floorCount;
	}

	@Column(name = "ADDRESS", length = 50)
	@Size(max = 50)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String buildAddress) {
		this.address = buildAddress;
	}

	@Column(name = "HOUSE_COUNT")
	public Integer getHouseCount() {
		return this.houseCount;
	}

	public void setHouseCount(Integer houseCount) {
		this.houseCount = houseCount;
	}

	@Column(name = "AREA", precision = 18, scale = 3)
	public BigDecimal getArea() {
		return this.area;
	}

	public void setArea(BigDecimal area) {
		this.area = area;
	}

	@Column(name = "LNG", precision = 18, scale = 14)
	public BigDecimal getLng() {
		return this.lng;
	}

	public void setLng(BigDecimal lng) {
		this.lng = lng;
	}

	@Column(name = "LAT", precision = 18, scale = 14)
	public BigDecimal getLat() {
		return this.lat;
	}

	public void setLat(BigDecimal lat) {
		this.lat = lat;
	}

	@Column(name = "ZOOM")
	public Integer getZoom() {
		return this.zoom;
	}

	public void setZoom(Integer zoom) {
		this.zoom = zoom;
	}

	@Column(name = "BUILD_TYPE", length = 32)
	@Size(max = 32)
	public String getBuildType() {
		return this.buildType;
	}

	public void setBuildType(String buildType) {
		this.buildType = buildType;
	}

	@Column(name = "STRUCTURE", length = 32)
	@Size(max = 32)
	public String getStructure() {
		return this.structure;
	}

	public void setStructure(String structure) {
		this.structure = structure;
	}

	@Column(name = "MEMO", length = 200)
	@Size(max = 200)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Column(name = "INBIZ", nullable = false)
	public boolean isInbiz() {
		return this.inbiz;
	}

	public void setInbiz(boolean inbiz) {
		this.inbiz = inbiz;
	}

	@Column(name = "BUSINESS_KEY", length = 32)
	@Size(max = 32)
	public String getBusinessKey() {
		return this.businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	@Column(name = "HOME_COUNT")
	public Integer getHomeCount() {
		return this.homeCount;
	}

	public void setHomeCount(Integer homeCount) {
		this.homeCount = homeCount;
	}

	@Column(name = "HOME_AREA", precision = 18, scale = 3)
	public BigDecimal getHomeArea() {
		return this.homeArea;
	}

	public void setHomeArea(BigDecimal homeArea) {
		this.homeArea = homeArea;
	}

	@Column(name = "UNHOME_COUNT")
	public Integer getUnhomeCount() {
		return this.unhomeCount;
	}

	public void setUnhomeCount(Integer unhomeCount) {
		this.unhomeCount = unhomeCount;
	}

	@Column(name = "UNHOME_AREA", precision = 18, scale = 3)
	public BigDecimal getUnhomeArea() {
		return this.unhomeArea;
	}

	public void setUnhomeArea(BigDecimal unhomeArea) {
		this.unhomeArea = unhomeArea;
	}

	@Column(name = "SHOP_COUNT")
	public Integer getShopCount() {
		return this.shopCount;
	}

	public void setShopCount(Integer shopCount) {
		this.shopCount = shopCount;
	}

	@Column(name = "SHOP_AREA", precision = 18, scale = 3)
	public BigDecimal getShopArea() {
		return this.shopArea;
	}

	public void setShopArea(BigDecimal shopArea) {
		this.shopArea = shopArea;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "build")
	public Set<House> getHouses() {
		return this.houses;
	}

	public void setHouses(Set<House> houses) {
		this.houses = houses;
	}


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "build")
    public Set<BuildGridMap> getBuildGridMaps() {
        return buildGridMaps;
    }

    public void setBuildGridMaps(Set<BuildGridMap> buildGridMaps) {
        this.buildGridMaps = buildGridMaps;
    }


    @Column(name = "BUILD_COST", precision = 18, scale = 3)
    public BigDecimal getBuildCost() {
        return buildCost;
    }

    public void setBuildCost(BigDecimal buildCost) {
        this.buildCost = buildCost;
    }

    @Override
    @Transient
    public TreeNode getChildAt(int childIndex) {
        return null;
    }

    @Override
    @Transient
    public int getChildCount() {
        return 0;
    }

    @Override
    @Transient
    public TreeNode getParent() {
        return getProject();
    }

    @Override
    @Transient
    public int getIndex(TreeNode node) {
        return 0;
    }

    @Override
    @Transient
    public boolean getAllowsChildren() {
        return false;
    }

    @Override
    @Transient
    public boolean isLeaf() {
        return true;
    }

    @Override
    @Transient
    public Enumeration children() {
        return new Enumeration() {
            @Override
            public boolean hasMoreElements() {
                return false;
            }

            @Override
            public Object nextElement() {
                return null;
            }
        };
    }

}
