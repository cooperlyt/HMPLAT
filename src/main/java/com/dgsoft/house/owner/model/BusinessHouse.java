package com.dgsoft.house.owner.model;
// Generated Oct 11, 2014 3:13:15 PM by Hibernate Tools 4.0.0

import cc.coopersoft.house.UseType;
import com.dgsoft.common.system.RunParam;
import com.dgsoft.house.*;
import com.dgsoft.house.owner.action.OwnerHouseHelper;
import org.hibernate.annotations.GenericGenerator;
import org.jboss.seam.log.Logging;

import java.math.BigDecimal;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * House generated by hbm2java
 */
@Entity
@Table(name = "HOUSE", catalog = "HOUSE_OWNER_RECORD")
public class BusinessHouse implements java.io.Serializable, HouseInfo {

    private String id;
    private String houseOrder;
    private String houseUnitName;
    private String inFloorName;
    private BigDecimal houseArea;
    private BigDecimal useArea;
    private BigDecimal commArea;
    private BigDecimal shineArea;
    private BigDecimal loftArea;
    private BigDecimal commParam;
    private HouseProperty houseType;
    private UseType useType;
    private String structure;
    private String knotSize;
    private String address;
    private String eastWall;
    private String westWall;
    private String southWall;
    private String northWall;
    private Date mapTime;
    private String direction;
    private String houseCode;
    private PoolType poolType;
    private PoolType oldPoolType;

    private boolean haveDownRoom;
    private String buildCode;
    private String mapNumber;
    private String blockNo;
    private String buildNo;
    private String streetCode;
    private String buildName;
    private String doorNo;
    private int upFloorCount;
    private int floorCount;
    private int downFloorCount;
    private String buildType;
    private String projectCode;
    private String projectName;
    private String completeDate;
    private String developerCode;
    private String developerName;
    private String sectionCode;
    private String sectionName;
    private String districtCode;
    private String districtName;
    //private Set<HouseBusiness> housesForAfterBusiness;

    private String designUseType;
    private String unitNumber;

    private LandInfo landInfo;
    private HouseRegInfo houseRegInfo;
    private String buildDevNumber;
    private Set<PowerPerson> powerPersons = new HashSet<PowerPerson>(0);
    private PowerPerson mainOwner;
    private Set<MortgaegeRegiste> mortgaegeRegistes = new HashSet<MortgaegeRegiste>(0);

    private HouseBusiness houseBusinessForAfter;
    //private HouseBusiness houseBusinessForStart;
    //private Set<HouseBusiness> houseBusinessesForAfter = new HashSet<HouseBusiness>(0);
    private Set<HouseRecord> houseRecords = new HashSet<HouseRecord>();
    //private Set<SaleInfo> saleInfos = new HashSet<SaleInfo>(0);
    private SaleInfo saleInfo;

    private Set<HouseContract> houseContracts = new HashSet<HouseContract>(0);


    public BusinessHouse() {
    }

    @Transient
    public void modifyFormMapHouse(HouseInfo houseInfo){
        this.houseOrder = houseInfo.getHouseOrder();
        this.houseUnitName = houseInfo.getHouseUnitName();
        this.inFloorName = houseInfo.getInFloorName();
        this.houseArea = houseInfo.getHouseArea();
        this.useArea = houseInfo.getUseArea();
        this.commArea = houseInfo.getCommArea();
        this.shineArea = houseInfo.getShineArea();
        this.loftArea = houseInfo.getLoftArea();
        this.commParam = houseInfo.getCommParam();

        this.houseType = houseInfo.getHouseType();
        this.useType = houseInfo.getUseType();
        this.designUseType = houseInfo.getDesignUseType();
        this.unitNumber = houseInfo.getUnitNumber();
        this.structure = houseInfo.getStructure();
        this.knotSize = houseInfo.getKnotSize();
        this.address = houseInfo.getAddress();
        this.eastWall = houseInfo.getEastWall();
        this.westWall = houseInfo.getWestWall();
        this.southWall = houseInfo.getSouthWall();
        this.northWall = houseInfo.getNorthWall();
        this.mapTime = houseInfo.getMapTime();
        this.direction = houseInfo.getDirection();

        this.houseCode = houseInfo.getHouseCode();
        this.haveDownRoom = houseInfo.isHaveDownRoom();
        this.buildCode = houseInfo.getBuildCode();
        this.mapNumber = houseInfo.getMapNumber();
        this.blockNo = houseInfo.getBlockNo();
        this.buildNo = houseInfo.getBuildNo();
        this.streetCode = houseInfo.getStreetCode();
        this.buildName = houseInfo.getBuildName();
        this.doorNo = houseInfo.getDoorNo();
        this.upFloorCount = houseInfo.getUpFloorCount();
        this.floorCount = houseInfo.getFloorCount();
        this.downFloorCount = houseInfo.getDownFloorCount();
        this.buildType = houseInfo.getBuildType();
        this.projectCode = houseInfo.getProjectCode();
        this.projectName = houseInfo.getProjectName();
        this.completeDate = houseInfo.getCompleteYear();
        this.buildDevNumber = houseInfo.getBuildDevNumber();
        this.developerCode = houseInfo.getDeveloperCode();
        this.developerName = houseInfo.getDeveloperName();
        this.sectionCode = houseInfo.getSectionCode();
        this.sectionName = houseInfo.getSectionName();
        this.districtCode = houseInfo.getDistrictCode();
        this.districtName = houseInfo.getDistrictName();
        this.unitNumber = houseInfo.getUnitNumber();
        this.designUseType = houseInfo.getDesignUseType();
    }



    public BusinessHouse(HouseInfo houseInfo) {
        modifyFormMapHouse(houseInfo);
    }

    public BusinessHouse(BusinessHouse houseInfo) {

        this((HouseInfo)houseInfo);
        if (houseInfo.getHouseRegInfo() != null){
            this.houseRegInfo = new HouseRegInfo(houseInfo.getHouseRegInfo());
        }

        if (houseInfo.getLandInfo() != null){
            this.landInfo = new LandInfo(houseInfo.getLandInfo());
        }

        this.oldPoolType = houseInfo.getPoolType();

    }

    @Column(name = "DESIGN_USE_TYPE", nullable = false, length = 512)
    @NotNull
    @Size(max = 512)
    public String getDesignUseType() {
        return designUseType;
    }

    public void setDesignUseType(String designUseType) {
        this.designUseType = designUseType;
    }

    @Column(name = "UNIT_NUMBER", length = 32)
    @Size(max = 32)
    public String getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "POOL_MEMO", nullable = true, length = 32)
    public PoolType getPoolType() {
        return this.poolType;
    }

    public void setPoolType(PoolType poolType) {
        this.poolType = poolType;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "OLD_POOL_MEMO", length = 32)
    public PoolType getOldPoolType() {
        return oldPoolType;
    }

    public void setOldPoolType(PoolType oldPoolType) {
        this.oldPoolType = oldPoolType;
    }

    @ManyToOne(fetch = FetchType.LAZY,optional = true,cascade = CascadeType.ALL)
    @JoinColumn(name = "REG_INFO",nullable = true)
    public HouseRegInfo getHouseRegInfo() {
        return houseRegInfo;
    }

    public void setHouseRegInfo(HouseRegInfo houseRegInfo) {
        this.houseRegInfo = houseRegInfo;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL , mappedBy = "businessHouse")
    public Set<HouseRecord> getHouseRecords() {
        return houseRecords;
    }

    public void setHouseRecords(Set<HouseRecord> houseRecords) {
        this.houseRecords = houseRecords;
    }

    @Override
    @Transient
    public String getDisplayHouseCode() {
        switch (RunParam.instance().getIntParamValue("HouseCodeDisplayModel")){
            case 2:
                return ((getMapNumber() == null) ? "" : (getMapNumber() + "-")) + getBlockNo() + "-" + getBuildNo() + ((getHouseOrder() == null || getHouseOrder().trim().equals("")) ? "" : "-") + getHouseOrder();

            case 3:
                return getDistrictCode() + "-" + getBlockNo() + "-" + getBuildNo() + ((getHouseOrder() == null || getHouseOrder().trim().equals("")) ? "" : "-") + getHouseOrder();

            case 4:

                return getBlockNo() + "-" + getBuildNo() + ((getHouseOrder() == null || getHouseOrder().trim().equals("")) ? "" : "-") + getHouseOrder();

            case 5:
                if ("2772".equals(getBuildType())){
                    return getBlockNo() + "-" + "0" + ((getHouseOrder() == null || getHouseOrder().trim().equals("")) ? "" : "-") + getHouseOrder();

                }else{
                    return getBlockNo() + "-" + getBuildNo() + ((getHouseOrder() == null || getHouseOrder().trim().equals("")) ? "" : "-") + getHouseOrder();

                }




        }
        return getHouseCode();
    }


    @Transient
    public String getDisplayHouseCodeByCh() {
        return ((getMapNumber() == null) ? "" : ("图: "+getMapNumber()  )) + "丘: "+ getBlockNo()+"幢: "  + getBuildNo() + ((getHouseOrder() == null || getHouseOrder().trim().equals("")) ? "" : "房号: ") + getHouseOrder();
    }


    @Enumerated(EnumType.STRING)
    @Column(name = "HOUSE_TYPE", length = 32)
    public HouseProperty getHouseType() {
        return this.houseType;
    }

    public void setHouseType(HouseProperty houseType) {
        this.houseType = houseType;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "USE_TYPE", nullable = false, length = 32)
    @NotNull
    public UseType getUseType() {
        return this.useType;
    }

    public void setUseType(UseType useType) {
        this.useType = useType;
    }

    @Override
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
    @Column(name = "MAP_TIME", nullable = true, length = 19)
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

    @Column(name = "HOUSE_CODE", nullable = false, length = 32)
    @NotNull
    public String getHouseCode() {
        return this.houseCode;
    }

    public void setHouseCode(String houseCode) {
        this.houseCode = houseCode;
    }


    @Override
    @Column(name = "HAVE_DOWN_ROOM", nullable = false)
    public boolean isHaveDownRoom() {
        return haveDownRoom;
    }

    public void setHaveDownRoom(boolean haveDownRoom) {
        this.haveDownRoom = haveDownRoom;
    }

    @Column(name = "BUILD_CODE", nullable = false, length = 32)
    @NotNull
    public String getBuildCode() {
        return this.buildCode;
    }

    public void setBuildCode(String buildCode) {
        this.buildCode = buildCode;
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
    public String getBlockNo() {
        return this.blockNo;
    }

    public void setBlockNo(String blockNo) {
        this.blockNo = blockNo;
    }

    @Column(name = "BUILD_NO", nullable = false, length = 24)
    @NotNull
    @Size(max = 24)
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

    @Override
    @Column(name = "BUILD_NAME", nullable = false, length = 100)
    @NotNull
    @Size(max = 100)
    public String getBuildName() {
        return this.buildName;
    }

    public void setBuildName(String buildName) {
        this.buildName = buildName;
    }

    @Column(name = "DOOR_NO", length = 50)
    @Size(max = 50)
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


    @Override
    @Column(name = "COMPLETE_DATE", length = 6)
    @Size(max = 6)
    public String getCompleteYear() {
        return this.completeDate;
    }

    public void setCompleteYear(String completeDate) {
        this.completeDate = completeDate;
    }

    @Column(name = "DEVELOPER_CODE", length = 32)
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

    @Override
    @Column(name="BUILD_DEVELOPER_NUMBER",nullable = true,length = 20)
    @Size(max = 20)
    public String getBuildDevNumber() {
        return buildDevNumber;
    }

    public void setBuildDevNumber(String buildDevNumber) {
        this.buildDevNumber = buildDevNumber;
    }

//    @Transient
//    public HouseBusiness getLasterHouseBusiness() {
//        if (getHousesForAfterBusiness().isEmpty()){
//            return null;
//        }else{
//            return getHousesForAfterBusiness().iterator().next();
//        }
//    }
//
//    @Transient
//    public void setLasterHouseBusiness(HouseBusiness lasterHouseBusiness) {
//        getHousesForAfterBusiness().clear();
//        if (lasterHouseBusiness != null){
//            getHousesForAfterBusiness().add(lasterHouseBusiness);
//        }
//    }


    @OneToOne(fetch = FetchType.LAZY, mappedBy = "afterBusinessHouse")
    public HouseBusiness getHouseBusinessForAfter() {
        return houseBusinessForAfter;
    }

    public void setHouseBusinessForAfter(HouseBusiness houseBusinessForAfter) {
        this.houseBusinessForAfter = houseBusinessForAfter;
    }

//   有中止的业务时 StartHouse 可能有多个 不可以@OneToOne
//    @OneToOne(fetch = FetchType.LAZY, mappedBy = "startBusinessHouse")
//    public HouseBusiness getHouseBusinessForStart() {
//        return houseBusinessForStart;
//    }
//
//    public void setHouseBusinessForStart(HouseBusiness houseBusinessForStart) {
//        this.houseBusinessForStart = houseBusinessForStart;
//    }

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "LAND_INFO",nullable = true)
    public LandInfo getLandInfo() {
        return landInfo;
    }

    public void setLandInfo(LandInfo landInfo) {
        this.landInfo = landInfo;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MAIN_OWNER",nullable = true)
    public PowerPerson getMainOwner() {
        return mainOwner;
    }

    public void setMainOwner(PowerPerson mainOwner) {
        this.mainOwner = mainOwner;
    }


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "HOUSE_OWNER", joinColumns = @JoinColumn(name = "HOUSE"), inverseJoinColumns = @JoinColumn(name = "POOL"))
    public Set<PowerPerson> getPowerPersons() {
        return powerPersons;
    }

    public void setPowerPersons(Set<PowerPerson> powerPersons) {
        this.powerPersons = powerPersons;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "HOUSE_MORTGAEGE", joinColumns = @JoinColumn(name = "HOUSE"), inverseJoinColumns = @JoinColumn(name = "MORTGAEGE"))
    public Set<MortgaegeRegiste> getMortgaegeRegistes() {
        return mortgaegeRegistes;
    }

    public void setMortgaegeRegistes(Set<MortgaegeRegiste> mortgaegeRegistes) {
        this.mortgaegeRegistes = mortgaegeRegistes;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "HOUSE_AND_CONTRACT",joinColumns=@JoinColumn(name="HOUSE"),inverseJoinColumns = @JoinColumn(name = "CONTRACT"))
    public Set<HouseContract> getHouseContracts() {
        return houseContracts;
    }

    public void setHouseContracts(Set<HouseContract> houseContracts) {
        this.houseContracts = houseContracts;
    }

    @Transient
    public HouseContract getSaleContract(){
        for(HouseContract hc: getHouseContracts()){
            if (SaleType.MAP_SELL.equals(hc.getType()) || SaleType.NOW_SELL.equals(hc.getType()) ||
                    SaleType.OLD_SELL.equals(hc.getType())){
                return hc;
            }
        }
        return null;
    }

    @Transient
    private List<HouseStatus> allStatusList;

    @Transient
    public List<HouseStatus> getAllStatusList() {
        if (allStatusList == null){
            allStatusList = OwnerHouseHelper.instance().getHouseAllStatus(getHouseCode());
        }

        return allStatusList;
    }

    @Transient
    public List<PowerPerson> getPowerPersonListByType(PowerPerson.PowerPersonType type, boolean old){
        List<PowerPerson> result = new ArrayList<PowerPerson>();
        for(PowerPerson pp: getPowerPersons()){
            if (type.equals(pp.getType()) && (old == pp.isOld())){
                result.add(pp);
            }
        }
        Collections.sort(result);
        return result;
    }


    @Transient
    public List<PowerPerson> getAllPowerPersonList(){
        List<PowerPerson> result = new ArrayList<PowerPerson>(getPowerPersons());
        Collections.sort(result);
        return result;
    }


    @Transient
    public List<PowerPerson> getAllNewPowerPersonList(){
        List<PowerPerson> result = new ArrayList<PowerPerson>();
        for(PowerPerson pp: getPowerPersons()){
            if (!pp.isOld()){
                result.add(pp);
            }
        }
        Collections.sort(result);
        return result;
    }

    //所有备案人
    @Transient
    public List<PowerPerson> getNewContractOwnerList(){
        return getPowerPersonListByType(PowerPerson.PowerPersonType.CONTRACT,false);
    }

    @Transient
    public List<PowerPerson> getOldContractOwnerList(){
        return getPowerPersonListByType(PowerPerson.PowerPersonType.CONTRACT,true);
    }

    @Transient
    public PowerPerson getMainPowerPerson(){
        List<PowerPerson> result = getAllNewPowerPersonList();
        if (result.isEmpty()){
            return null;
        }else{
            return result.get(0);
        }
    }

    @Transient
    private void calcMainOwner(){
        List<PowerPerson> result = new ArrayList<PowerPerson>();
        for(PowerPerson pp: getPowerPersons()){
            if (!pp.isOld()){
                result.add(pp);
            }
        }
        if (result.isEmpty()){
            setMainOwner(null);
        }else{
            setMainOwner(result.get(0));
        }
    }

    @Transient
    public void addPowerPerson(PowerPerson powerPerson){
        int pri = 0;
        for (PowerPerson pp : getPowerPersons()){
            if (pp.isOld() == powerPerson.isOld() && powerPerson.getType().equals(pp.getType()) && (pp.getPriority() >= pri)){
                pri = pp.getPriority() + 1;
            }
        }
        powerPerson.setPriority(pri);
        getPowerPersons().add(powerPerson);
        if (!powerPerson.isOld())
            calcMainOwner();
    }

    @Transient
    public void removePowerPerson(PowerPerson powerPerson){
        if (getPowerPersons().remove(powerPerson) && !powerPerson.isOld()){
            calcMainOwner();
        }
    }



    @Transient
    public PowerPerson getMainContractPerson(){
        List<PowerPerson> result = getPowerPersonListByType(PowerPerson.PowerPersonType.CONTRACT,false);
        if (result.isEmpty()){
            return null;
        }else{
            return result.get(0);
        }
    }

    @Transient
    public List<PowerPerson> getOldInitPersonList(){
        return getPowerPersonListByType(PowerPerson.PowerPersonType.INIT,true);
    }

    @Transient
    public List<PowerPerson> getOldMainOwnerPersonList(){
        return getPowerPersonListByType(PowerPerson.PowerPersonType.OWNER,true);
    }

    @Transient
    public List<PowerPerson> getMainOwnerPersonList(){
        return getPowerPersonListByType(PowerPerson.PowerPersonType.OWNER,false);
    }

    @Transient
    public PowerPerson getMainOwnerPerson(){
        List<PowerPerson> result = getPowerPersonListByType(PowerPerson.PowerPersonType.OWNER,false);
        if (result.isEmpty()){
            return null;
        }else{
            return result.get(0);
        }
    }

    @Transient
    public PowerPerson getOldMainOwnerPerson(){
        List<PowerPerson> result = getPowerPersonListByType(PowerPerson.PowerPersonType.OWNER,true);
        if (result.isEmpty()){
            return null;
        }else{
            return result.get(0);
        }
    }

    @Transient
    public PowerPerson getOldMainInitPerson(){
        List<PowerPerson> result = getPowerPersonListByType(PowerPerson.PowerPersonType.INIT,true);
        if (result.isEmpty()){
            return null;
        }else{
            return result.get(0);
        }
    }



    @OneToOne(fetch = FetchType.LAZY,mappedBy = "businessHouse", cascade = CascadeType.ALL)
    public SaleInfo getSaleInfo() {
        return saleInfo;
    }

    public void setSaleInfo(SaleInfo saleInfo) {
        this.saleInfo = saleInfo;
    }


}
