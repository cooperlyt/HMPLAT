package com.dgsoft.house.model;
// Generated Jul 12, 2013 11:32:23 AM by Hibernate Tools 4.0.0

import com.dgsoft.house.HouseInfo;
import org.jboss.seam.log.Logging;

import java.math.BigDecimal;
import java.util.*;
import javax.persistence.*;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * House generated by hbm2java
 */
@Entity
@Table(name = "HOUSE", catalog = "HOUSE_INFO", uniqueConstraints = @UniqueConstraint(columnNames = {
        "BUILDID", "HOUSE_ORDER"}))
public class House implements java.io.Serializable, HouseInfo {

    @Override
    @Transient
    public String getDeveloperName() {
        return getBuild().getDeveloperName();
    }

    @Override
    @Transient
    public String getDeveloperCode() {
        return getBuild().getDeveloperCode();
    }

    @Override
    @Transient
    public String getProjectName() {
        return getBuild().getProjectName();
    }

    @Override
    @Transient
    public String getProjectCode() {
        return getBuild().getProjectCode();
    }

    @Override
    @Transient
    public Date getCompleteDate() {
        return getBuild().getCompleteDate();
    }

    @Override
    @Transient
    public String getBuildSize() {
        return getBuild().getBuildSize();
    }

    @Override
    @Transient
    public String getDistrictName() {
        return getBuild().getDistrictName();
    }

    @Override
    @Transient
    public String getDistrictCode() {
        return getBuild().getDistrictCode();
    }

    @Override
    @Transient
    public String getSectionName() {
        return getBuild().getSectionName();
    }

    @Override
    @Transient
    public String getSectionCode() {
        return getBuild().getSectionCode();
    }

    public enum HouseDataSource {
        MAPPING, IMPORT, RECORD_ADD;
    }

    private String id;
    private Integer version;
    private Build build;
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
    private HouseStatus masterStatus;
    private String houseType;
    private String useType;
    private String structure;
    private String knotSize;
    private String address;
    private HouseDataSource dataSource;
    private String eastWall;
    private String westWall;
    private String southWall;
    private String northWall;
    private Date mapTime;
    private String direction;
    private InitRegStatus initRegStatus;

    private boolean haveDownRoom;
    private boolean payWXZJ;
    private String memo;
    private Set<HouseContract> houseContracts = new HashSet<HouseContract>(0);
    private Set<HouseState> houseStates = new HashSet<HouseState>(0);
    private Set<PoolOwner> poolOwners = new HashSet<PoolOwner>(0);
    private Date createTime;
    //private Set<GridBlock> gridBlock = new HashSet<GridBlock>(0);
    private HouseOwner houseOwner;
    private boolean lock;

    public House() {
    }


    public House(String id, Build build, GridBlock block) {
        this.id = id;
        this.build = build;
        this.houseArea = block.getArea();
        this.prepareArea = this.houseArea;
        this.useArea = block.getUseArea();
        this.commArea = block.getCommArea();
        this.commParam = block.getCommParam();
        this.shineArea = block.getShineArea();
        this.loftArea = block.getLoftArea();
        this.useType = block.getUseType();
        this.structure = block.getStructure();
        this.houseType = block.getHouseType();
        this.houseUnitName = block.getUnitName();
        this.inFloorName = block.getGridRow().getTitle();
        this.houseOrder = block.getHouseOrder();
        this.knotSize = block.getKnotSize();
        this.direction = block.getDirection();
        this.westWall = block.getWestWall();
        this.southWall = block.getSouthWall();
        this.northWall = block.getNorthWall();
        this.eastWall = block.getEastWall();
        this.masterStatus = HouseStatus.CANTSALE;
        this.haveDownRoom = block.isHaveDownRoom();
        this.initRegStatus = InitRegStatus.NOT_INIT_REG;
        lock = false;
        dataSource = HouseDataSource.MAPPING;

        if ((build.getAddress() != null) && !"".equals(build.getAddress())) {
            this.address = build.getAddress() + " " + block.getHouseOrder();
        } else {
            this.address = build.getName() + " " + block.getHouseOrder();
        }
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

    @Version
    @Column(name = "VERSION")
    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "BUILDID", nullable = false)
    @NotNull
    public Build getBuild() {
        return this.build;
    }

    public void setBuild(Build build) {
        this.build = build;
    }

    @Override
    @Transient
    public String getHouseCode() {
        return getId();
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

    @Column(name = "IN_FLOOR_NAME", length = 50, nullable = false)
    @Size(max = 50)
    @NotNull
    public String getInFloorName() {
        return this.inFloorName;
    }

    public void setInFloorName(String inFloorName) {
        this.inFloorName = inFloorName;
    }


    @Override
    @Enumerated(EnumType.STRING)
    @Column(name = "INIT_REG_STATUS",nullable = false,length = 20)
    @NotNull
    public InitRegStatus getInitRegStatus() {
        return initRegStatus;
    }

    public void setInitRegStatus(InitRegStatus initRegStatus) {
        this.initRegStatus = initRegStatus;
    }

    @Column(name = "HOUSE_AREA", nullable = false, precision = 18, scale = 3)
    @NotNull
    public BigDecimal getHouseArea() {
        return this.houseArea;
    }

    public void setHouseArea(BigDecimal houseArea) {
        this.houseArea = houseArea;
    }

    @Column(name = "HAVE_DOWN_ROOM", nullable = false)
    public boolean isHaveDownRoom() {
        return haveDownRoom;
    }

    public void setHaveDownRoom(boolean haveDownRoom) {
        this.haveDownRoom = haveDownRoom;
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

    @Override
    @Enumerated(EnumType.STRING)
    @Column(name = "HOUSE_STATUS", nullable = false, length = 32)
    @NotNull
    public HouseStatus getMasterStatus() {
        return masterStatus;
    }

    public void setMasterStatus(HouseStatus masterStatus) {
        this.masterStatus = masterStatus;
    }

    @Override
    @Transient
    public List<HouseStatus> getAllStatusList() {
        List<HouseStatus> result = new ArrayList<HouseStatus>(getHouseStates().size());
        for (HouseState state : getHouseStates()) {
            result.add(state.getState());
        }
        Collections.sort(result, new StatusComparator());
        return result;
    }

    @Column(name = "HOUSE_TYPE", length = 32)
    @Size(max = 32)
    public String getHouseType() {
        return this.houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    @Column(name = "USE_TYPE", length = 32, nullable = false)
    @Size(max = 32)
    @NotNull
    public String getUseType() {
        return this.useType;
    }

    public void setUseType(String useType) {
        this.useType = useType;
    }

    @Column(name = "_LOCK", nullable = false)
    public boolean isLock() {
        return lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }

    @Override
    @Transient
    public String getBuildName() {
        return getBuild().getBuildName();
    }

    @Override
    @Transient
    public String getBuildCode() {
        return getBuild().getBuildCode();
    }

    @Override
    @Transient
    public String getLandBlockCode() {
        return getBuild().getLandBlockCode();
    }

    @Override
    @Transient
    public String getStreetCode() {
        return getBuild().getStreetCode();
    }

    @Override
    @Transient
    public String getMapNumber() {
        return getBuild().getMapNumber();
    }

    @Override
    @Transient
    public String getBlockNo() {
        return getBuild().getBlockNo();
    }

    @Override
    @Transient
    public String getBuildNo() {
        return getBuild().getBuildNo();
    }

    @Override
    @Transient
    public String getDoorNo() {
        return getBuild().getDoorNo() + " " + getHouseOrder();
    }

    @Override
    @Transient
    public int getFloorCount() {
        return getBuild().getFloorCount();
    }

    @Override
    @Transient
    public int getUpFloorCount() {
        return getBuild().getUpFloorCount();
    }

    @Override
    @Transient
    public int getDownFloorCount() {
        return getBuild().getDownFloorCount();
    }

    @Override
    @Transient
    public String getBuildType() {
        return getBuild().getBuildType();
    }

    @Column(name = "STRUCTURE", length = 32, nullable = false)
    @Size(max = 32)
    @NotNull
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


    @Column(name = "ADDRESS", length = 200, nullable = false)
    @Size(max = 200)
    @NotNull
    public String getAddress() {
        return this.address;
    }

    public void setAddress(String houseStation) {
        this.address = houseStation;
    }


    @Enumerated(EnumType.STRING)
    @Column(name = "DATA_SOURCE", length = 32, nullable = false)
    @NotNull
    public HouseDataSource getDataSource() {
        return this.dataSource;
    }

    public void setDataSource(HouseDataSource dataSource) {
        this.dataSource = dataSource;
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

    @Column(name = "PAY_WXZJ",nullable = false)
    public boolean isPayWXZJ() {
        return payWXZJ;
    }

    public void setPayWXZJ(boolean payWXZJ) {
        this.payWXZJ = payWXZJ;
    }

    @Column(name = "MEMO", length = 200)
    @Size(max = 200)
    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "house")
    public Set<HouseContract> getHouseContracts() {
        return this.houseContracts;
    }

    public void setHouseContracts(Set<HouseContract> houseContracts) {
        this.houseContracts = houseContracts;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "house")
    public Set<HouseState> getHouseStates() {
        return this.houseStates;
    }

    public void setHouseStates(Set<HouseState> houseStates) {
        this.houseStates = houseStates;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "house")
    public Set<PoolOwner> getPoolOwners() {
        return this.poolOwners;
    }

    public void setPoolOwners(Set<PoolOwner> poolOwners) {
        this.poolOwners = poolOwners;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_TIME", nullable = true, length = 19)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "house", cascade = {CascadeType.DETACH, CascadeType.REMOVE})
//    public Set<GridBlock> getGridBlock() {
//        return gridBlock;
//    }
//
//    public void setGridBlock(Set<GridBlock> gridBlock) {
//        this.gridBlock = gridBlock;
//    }

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL,optional = true)
    @JoinColumn(name = "MASTER_OWNER",nullable = true)
    public HouseOwner getHouseOwner() {
        return houseOwner;
    }

    public void setHouseOwner(HouseOwner houseOwner) {
        this.houseOwner = houseOwner;
    }

    @Transient
    private Boolean orderValid;

    @Transient
    private Boolean detailsValid;

    @Transient
    public Boolean getOrderValid() {
        if (orderValid == null){
            isValidator();
        }
        return orderValid;
    }

    @Transient
    public Boolean getDetailsValid() {
        if (detailsValid == null){
            isValidator();
        }
        return detailsValid;
    }



    @Transient
    public boolean isValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<House>> constraintViolations = validator.validate(this);


        detailsValid = constraintViolations.size() <= 0;

        orderValid = true;

        if (this.getHouseOrder() != null) {
            int count = 0;
            for (House house : getBuild().getHouses()) {
                if (this.getHouseOrder().equals(house.getHouseOrder())) {
                    count++;
                    if (count >= 2){
                        orderValid = false;
                        break;
                    }

                }
            }
        }


        return orderValid && detailsValid;
    }
}
