package com.dgsoft.house.owner.model;
// Generated Oct 11, 2014 3:13:15 PM by Hibernate Tools 4.0.0

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * House generated by hbm2java
 */
@Entity
@Table(name = "HOUSE", catalog = "HOUSE_OWNER_RECORD")
public class BusinessHouse implements java.io.Serializable {

	private String id;
	private String houseOrder;
	private String houseUnitName;
	private String inFloorName;
	private BigDecimal houseArea;
	private BigDecimal prepareArea;
	private BigDecimal useArea;
	private BigDecimal commArea;
	private BigDecimal shineArea;
	private BigDecimal loftArea;
	private BigDecimal commParam;
	private int houseState;
	private String houseType;
	private String useType;
	private String structure;
	private String knotSize;
	private String address;
	private String eastWall;
	private String westWall;
	private String southWall;
	private String northWall;
	private Date mapTime;
	private String direction;
	private boolean initRegister;
	private boolean firmlyPower;
	private String houseCode;
	private String poolMemo;
	private String houseFrom;
	private String housePorperty;
	private Date haveDownRoom;
	private String buildCode;
	private String landBlockCode;
	private String mapNumber;
	private String blockNo;
	private String buildNo;
	private String streetCode;
	private String name;
	private String doorNo;
	private int upFloorCount;
	private int floorCount;
	private int downFloorCount;
	private String buildType;
	private String projectCode;
	private String projectName;
	private String buildSize;
	private Date completeDate;
	private String developerCode;
	private String developerName;
	private String sectionCode;
	private String sectionName;
	private String districtCode;
	private String districtName;
	private Set<HouseBusiness> housesForStartBusiness = new HashSet<HouseBusiness>(0);
	private Set<HouseState> houseStates = new HashSet<HouseState>(0);
	private Set<HouseBusiness> housesForAfterBusiness = new HashSet<HouseBusiness>(0);
	private Set<HouseRecord> houseRecords = new HashSet<HouseRecord>(0);

	public BusinessHouse() {
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

	@Column(name = "HOUSE_ORDER", nullable = false, length = 20)
	@NotNull
	@Size(max = 20)
	public String getHouseOrder() {
		return this.houseOrder;
	}

	public void setHouseOrder(String houseOrder) {
		this.houseOrder = houseOrder;
	}

	@Column(name = "HOUSE_UNIT_NAME", length = 20)
	@Size(max = 20)
	public String getHouseUnitName() {
		return this.houseUnitName;
	}

	public void setHouseUnitName(String houseUnitName) {
		this.houseUnitName = houseUnitName;
	}

	@Column(name = "IN_FLOOR_NAME", nullable = false, length = 50)
	@NotNull
	@Size(max = 50)
	public String getInFloorName() {
		return this.inFloorName;
	}

	public void setInFloorName(String inFloorName) {
		this.inFloorName = inFloorName;
	}

	@Column(name = "HOUSE_AREA", nullable = false, precision = 18, scale = 3)
	@NotNull
	public BigDecimal getHouseArea() {
		return this.houseArea;
	}

	public void setHouseArea(BigDecimal houseArea) {
		this.houseArea = houseArea;
	}

	@Column(name = "PREPARE_AREA", precision = 18, scale = 3)
	public BigDecimal getPrepareArea() {
		return this.prepareArea;
	}

	public void setPrepareArea(BigDecimal prepareArea) {
		this.prepareArea = prepareArea;
	}

	@Column(name = "USE_AREA", precision = 18, scale = 3)
	public BigDecimal getUseArea() {
		return this.useArea;
	}

	public void setUseArea(BigDecimal useArea) {
		this.useArea = useArea;
	}

	@Column(name = "COMM_AREA", precision = 18, scale = 3)
	public BigDecimal getCommArea() {
		return this.commArea;
	}

	public void setCommArea(BigDecimal commArea) {
		this.commArea = commArea;
	}

	@Column(name = "SHINE_AREA", precision = 18, scale = 10)
	public BigDecimal getShineArea() {
		return this.shineArea;
	}

	public void setShineArea(BigDecimal shineArea) {
		this.shineArea = shineArea;
	}

	@Column(name = "LOFT_AREA", precision = 18, scale = 3)
	public BigDecimal getLoftArea() {
		return this.loftArea;
	}

	public void setLoftArea(BigDecimal loftArea) {
		this.loftArea = loftArea;
	}

	@Column(name = "COMM_PARAM", precision = 18, scale = 3)
	public BigDecimal getCommParam() {
		return this.commParam;
	}

	public void setCommParam(BigDecimal commParam) {
		this.commParam = commParam;
	}

	@Column(name = "HOUSE_STATE", nullable = false)
	public int getHouseState() {
		return this.houseState;
	}

	public void setHouseState(int houseState) {
		this.houseState = houseState;
	}

	@Column(name = "HOUSE_TYPE", length = 32)
	@Size(max = 32)
	public String getHouseType() {
		return this.houseType;
	}

	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}

	@Column(name = "USE_TYPE", nullable = false, length = 32)
	@NotNull
	@Size(max = 32)
	public String getUseType() {
		return this.useType;
	}

	public void setUseType(String useType) {
		this.useType = useType;
	}

	@Column(name = "STRUCTURE", nullable = false, length = 32)
	@NotNull
	@Size(max = 32)
	public String getStructure() {
		return this.structure;
	}

	public void setStructure(String structure) {
		this.structure = structure;
	}

	@Column(name = "KNOT_SIZE", length = 32)
	@Size(max = 32)
	public String getKnotSize() {
		return this.knotSize;
	}

	public void setKnotSize(String knotSize) {
		this.knotSize = knotSize;
	}

	@Column(name = "ADDRESS", nullable = false, length = 200)
	@NotNull
	@Size(max = 200)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "EAST_WALL", length = 32)
	@Size(max = 32)
	public String getEastWall() {
		return this.eastWall;
	}

	public void setEastWall(String eastWall) {
		this.eastWall = eastWall;
	}

	@Column(name = "WEST_WALL", length = 32)
	@Size(max = 32)
	public String getWestWall() {
		return this.westWall;
	}

	public void setWestWall(String westWall) {
		this.westWall = westWall;
	}

	@Column(name = "SOUTH_WALL", length = 32)
	@Size(max = 32)
	public String getSouthWall() {
		return this.southWall;
	}

	public void setSouthWall(String southWall) {
		this.southWall = southWall;
	}

	@Column(name = "NORTH_WALL", length = 32)
	@Size(max = 32)
	public String getNorthWall() {
		return this.northWall;
	}

	public void setNorthWall(String northWall) {
		this.northWall = northWall;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MAP_TIME", nullable = false, length = 19)
	@NotNull
	public Date getMapTime() {
		return this.mapTime;
	}

	public void setMapTime(Date mapTime) {
		this.mapTime = mapTime;
	}

	@Column(name = "DIRECTION", length = 32)
	@Size(max = 32)
	public String getDirection() {
		return this.direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	@Column(name = "INIT_REGISTER", nullable = false)
	public boolean isInitRegister() {
		return this.initRegister;
	}

	public void setInitRegister(boolean initRegister) {
		this.initRegister = initRegister;
	}

	@Column(name = "FIRMLY_POWER", nullable = false)
	public boolean isFirmlyPower() {
		return this.firmlyPower;
	}

	public void setFirmlyPower(boolean firmlyPower) {
		this.firmlyPower = firmlyPower;
	}

	@Column(name = "HOUSE_CODE", nullable = false, length = 32)
	@NotNull
	@Size(max = 32)
	public String getHouseCode() {
		return this.houseCode;
	}

	public void setHouseCode(String houseCode) {
		this.houseCode = houseCode;
	}

	@Column(name = "POOL_MEMO", length = 32)
	@Size(max = 32)
	public String getPoolMemo() {
		return this.poolMemo;
	}

	public void setPoolMemo(String poolMemo) {
		this.poolMemo = poolMemo;
	}

	@Column(name = "HOUSE_FROM", length = 32)
	@Size(max = 32)
	public String getHouseFrom() {
		return this.houseFrom;
	}

	public void setHouseFrom(String houseFrom) {
		this.houseFrom = houseFrom;
	}

	@Column(name = "HOUSE_PORPERTY", length = 32)
	@Size(max = 32)
	public String getHousePorperty() {
		return this.housePorperty;
	}

	public void setHousePorperty(String housePorperty) {
		this.housePorperty = housePorperty;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "HAVE_DOWN_ROOM", nullable = false, length = 19)
	@NotNull
	public Date getHaveDownRoom() {
		return this.haveDownRoom;
	}

	public void setHaveDownRoom(Date haveDownRoom) {
		this.haveDownRoom = haveDownRoom;
	}

	@Column(name = "BUILD_CODE", nullable = false, length = 32)
	@NotNull
	@Size(max = 32)
	public String getBuildCode() {
		return this.buildCode;
	}

	public void setBuildCode(String buildCode) {
		this.buildCode = buildCode;
	}

	@Column(name = "LAND_BLOCK_CODE", length = 4)
	@Size(max = 4)
	public String getLandBlockCode() {
		return this.landBlockCode;
	}

	public void setLandBlockCode(String landBlockCode) {
		this.landBlockCode = landBlockCode;
	}

	@Column(name = "MAP_NUMBER", length = 4)
	@Size(max = 4)
	public String getMapNumber() {
		return this.mapNumber;
	}

	public void setMapNumber(String mapNumber) {
		this.mapNumber = mapNumber;
	}

	@Column(name = "BLOCK_NO", nullable = false, length = 10)
	@NotNull
	@Size(max = 10)
	public String getBlockNo() {
		return this.blockNo;
	}

	public void setBlockNo(String blockNo) {
		this.blockNo = blockNo;
	}

	@Column(name = "BUILD_NO", nullable = false, length = 4)
	@NotNull
	@Size(max = 4)
	public String getBuildNo() {
		return this.buildNo;
	}

	public void setBuildNo(String buildNo) {
		this.buildNo = buildNo;
	}

	@Column(name = "STREET_CODE", length = 4)
	@Size(max = 4)
	public String getStreetCode() {
		return this.streetCode;
	}

	public void setStreetCode(String streetCode) {
		this.streetCode = streetCode;
	}

	@Column(name = "NAME", nullable = false, length = 100)
	@NotNull
	@Size(max = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "DOOR_NO", length = 10)
	@Size(max = 10)
	public String getDoorNo() {
		return this.doorNo;
	}

	public void setDoorNo(String doorNo) {
		this.doorNo = doorNo;
	}

	@Column(name = "UP_FLOOR_COUNT", nullable = false)
	public int getUpFloorCount() {
		return this.upFloorCount;
	}

	public void setUpFloorCount(int upFloorCount) {
		this.upFloorCount = upFloorCount;
	}

	@Column(name = "FLOOR_COUNT", nullable = false)
	public int getFloorCount() {
		return this.floorCount;
	}

	public void setFloorCount(int floorCount) {
		this.floorCount = floorCount;
	}

	@Column(name = "DOWN_FLOOR_COUNT", nullable = false)
	public int getDownFloorCount() {
		return this.downFloorCount;
	}

	public void setDownFloorCount(int downFloorCount) {
		this.downFloorCount = downFloorCount;
	}

	@Column(name = "BUILD_TYPE", length = 32)
	@Size(max = 32)
	public String getBuildType() {
		return this.buildType;
	}

	public void setBuildType(String buildType) {
		this.buildType = buildType;
	}

	@Column(name = "PROJECT_CODE", nullable = false, length = 32)
	@NotNull
	@Size(max = 32)
	public String getProjectCode() {
		return this.projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	@Column(name = "PROJECT_NAME", nullable = false, length = 50)
	@NotNull
	@Size(max = 50)
	public String getProjectName() {
		return this.projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@Column(name = "BUILD_SIZE", length = 32)
	@Size(max = 32)
	public String getBuildSize() {
		return this.buildSize;
	}

	public void setBuildSize(String buildSize) {
		this.buildSize = buildSize;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "COMPLETE_DATE", length = 19)
	public Date getCompleteDate() {
		return this.completeDate;
	}

	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}

	@Column(name = "DEVELOPER_CODE", length = 32)
	@Size(max = 32)
	public String getDeveloperCode() {
		return this.developerCode;
	}

	public void setDeveloperCode(String developerCode) {
		this.developerCode = developerCode;
	}

	@Column(name = "DEVELOPER_NAME", length = 100)
	@Size(max = 100)
	public String getDeveloperName() {
		return this.developerName;
	}

	public void setDeveloperName(String developerName) {
		this.developerName = developerName;
	}

	@Column(name = "SECTION_CODE", nullable = false, length = 32)
	@NotNull
	@Size(max = 32)
	public String getSectionCode() {
		return this.sectionCode;
	}

	public void setSectionCode(String sectionCode) {
		this.sectionCode = sectionCode;
	}

	@Column(name = "SECTION_NAME", nullable = false, length = 50)
	@NotNull
	@Size(max = 50)
	public String getSectionName() {
		return this.sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	@Column(name = "DISTRICT_CODE", nullable = false, length = 32)
	@NotNull
	@Size(max = 32)
	public String getDistrictCode() {
		return this.districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}

	@Column(name = "DISTRICT_NAME", nullable = false, length = 100)
	@NotNull
	@Size(max = 100)
	public String getDistrictName() {
		return this.districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "startHouse")
	public Set<HouseBusiness> getHousesForStartBusiness() {
		return this.housesForStartBusiness;
	}

	public void setHousesForStartBusiness(
            Set<HouseBusiness> housesForStartBusiness) {
		this.housesForStartBusiness = housesForStartBusiness;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "house")
	public Set<HouseState> getHouseStates() {
		return this.houseStates;
	}

	public void setHouseStates(Set<HouseState> houseStates) {
		this.houseStates = houseStates;
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "afterHouse")
	public Set<HouseBusiness> getHousesForAfterBusiness() {
		return this.housesForAfterBusiness;
	}

	public void setHousesForAfterBusiness(
            Set<HouseBusiness> housesForAfterBusiness) {
		this.housesForAfterBusiness = housesForAfterBusiness;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "house")
	public Set<HouseRecord> getHouseRecords() {
		return this.houseRecords;
	}

	public void setHouseRecords(Set<HouseRecord> houseRecords) {
		this.houseRecords = houseRecords;
	}

}
