package com.dgsoft.house.owner.action;

import com.dgsoft.house.HouseEntityLoader;
import com.dgsoft.house.action.BuildHome;
import com.dgsoft.house.model.BuildGridMap;
import com.dgsoft.house.model.GridBlock;
import com.dgsoft.house.model.GridRow;
import com.dgsoft.house.model.House;
import com.dgsoft.house.owner.HouseInfoCompare;
import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.model.BusinessHouse;
import com.dgsoft.house.owner.model.HouseRecord;
import com.dgsoft.house.owner.model.OwnerBusiness;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Logging;

import javax.persistence.NoResultException;
import java.util.*;

/**
 * Created by cooper on 8/11/15.
 */
@Name("buildGridMapHouseSelect")
@Scope(ScopeType.CONVERSATION)
public class BuildGridMapHouseSelect {

    @In(required = false)
    private BuildHome buildHome;

    @In(create = true)
    private HouseEntityLoader houseEntityLoader;


    @In(create = true)
    private OwnerEntityLoader ownerEntityLoader;

    private BuildGridMap curMap;

    private List<BuildGridMap> buildGridMaps = new ArrayList<BuildGridMap>(0);

    private House selectBizHouse;

    private List<House> selectBizHouses = new ArrayList<House>();


    private String mapNumber;

    private String blockNumber;

    private String buildNumber;

    private String houseOrder;

    public String getMapNumber() {
        return mapNumber;
    }

    public void setMapNumber(String mapNumber) {
        this.mapNumber = mapNumber;
    }

    public String getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(String blockNumber) {
        this.blockNumber = blockNumber;
    }

    public String getBuildNumber() {
        return buildNumber;
    }

    public void setBuildNumber(String buildNumber) {
        this.buildNumber = buildNumber;
    }

    public House getSelectBizHouse() {
        return selectBizHouse;
    }

    public void setSelectBizHouse(House selectBizHouse) {
        this.selectBizHouse = selectBizHouse;
    }

    public List<House> getSelectBizHouses() {
        return selectBizHouses;
    }

    public void addToMulitSelect(){
        if (!selectBizHouses.contains(getSelectBizHouse())){
            selectBizHouses.add(getSelectBizHouse());
        }
    }

    public void removeMulitSelect(){
        selectBizHouses.remove(getSelectBizHouse());
    }

    public void clearMulitSelect(){
        selectBizHouses.clear();
    }

    public boolean isSelectInMulit(){
        return selectBizHouses.contains(getSelectBizHouse());
    }

    public boolean inMulitSelect(String houseCode){
        for(House house: getSelectBizHouses()){
            if (house.getHouseCode().equals(houseCode)){
                return true;
            }
        }
        return false;
    }


    public String getHouseOrder() {
        return houseOrder;
    }

    public void setHouseOrder(String houseOrder) {
        this.houseOrder = houseOrder;
    }

    public List<BuildGridMap> getBuildGridMaps() {
        return buildGridMaps;
    }

    public String getSelectBizHouseId(){
        if (selectBizHouse == null){
            return null;
        }
        return selectBizHouse.getHouseCode();
    }

    public void setSelectBizHouseId(String id){
        if ((id == null) || id.trim().equals("")){
            setSelectBizHouse(null);
        }

        for(House house:buildHome.getInstance().getHouses()){
            if (house.getHouseCode().equals(id)){
                setSelectBizHouse(house);
                return;
            }
        }


    }

    public void setId(String buildId){
        if ((buildId == null) || buildId.trim().equals("")){
            curMap = null;
        }
        for(BuildGridMap map: buildGridMaps){
            if (map.getId().equals(buildId)){
                curMap = map;
                return;
            }
        }
    }

    public String getId(){
        if (curMap == null){
            return null;
        }
        return curMap.getId();
    }

    public BuildGridMap getCurMap() {
        return curMap;
    }


    public void findBuildBySection(){

        mapNumber = buildHome.getInstance().getMapNumber();
        blockNumber = buildHome.getInstance().getBlockNo();
        buildNumber = buildHome.getInstance().getBuildNo();
        initBuildMap();
    }

    private boolean houseLocated;

    public boolean isHouseLocated() {
        return houseLocated;
    }

    public void setHouseLocated(boolean houseLocated) {
        this.houseLocated = houseLocated;
    }

    public void findHouseByNumber(){
        try {

            selectBizHouse = houseEntityLoader.getEntityManager().createQuery("select house from House house where house.build.mapNumber = :mapNumber and house.build.blockNo = :blockNumber and house.build.buildNo = :buildNumber and house.houseOrder = :houseOrder", House.class)
                            .setParameter("mapNumber", mapNumber).setParameter("blockNumber", blockNumber).setParameter("buildNumber", buildNumber).setParameter("houseOrder",houseOrder).getSingleResult();
            houseLocated = true;

        } catch (NoResultException e) {
            selectBizHouse = null;
            houseLocated = false;

        }
    }

    public void findBuildByNumber() {
        try {
            buildHome.setId(
                    houseEntityLoader.getEntityManager().createQuery("select build.id from Build build where build.mapNumber = :mapNumber and build.blockNo = :blockNumber and build.buildNo = :buildNumber", String.class)
                            .setParameter("mapNumber", mapNumber).setParameter("blockNumber", blockNumber).setParameter("buildNumber", buildNumber).getSingleResult());

            initBuildMap();
        } catch (NoResultException e) {
            buildHome.clearInstance();

        }
    }

    public void findHouseBySection(){
        if (buildHome.isIdDefined()){
            for(House house: buildHome.getInstance().getHouses()){
                if (house.getHouseOrder().equals(houseOrder)){
                    houseLocated = true;
                    selectBizHouse = house;
                    return;
                }
            }
            houseLocated = false;
            selectBizHouse = null;
            return;
        }
        throw new IllegalArgumentException("buildHouse is not Defined");
    }


    private void findHouseByOrder(List<House> houses){

        buildHome.clearInstance();
        buildGridMaps = new ArrayList<BuildGridMap>(0);

    }


    private House getHouseByCode(String houseCode){
        for(House house: buildHome.getInstance().getHouses()){
            if (house.getHouseCode().equals(houseCode)){
                return house;
            }
        }
        return null;
    }

    public void initBuildMap(){



        buildGridMaps = new ArrayList<BuildGridMap>(buildHome.getInstance().getBuildGridMaps());
        Collections.sort(buildGridMaps, new Comparator<BuildGridMap>() {
            @Override
            public int compare(BuildGridMap o1, BuildGridMap o2) {
                return (new Integer(o1.getOrder())).compareTo(o2.getOrder());
            }
        });



        Map<String,House> houseMap = new HashMap<String, House>();
        for (House house: buildHome.getInstance().getHouses()){
            houseMap.put(house.getHouseCode(),house);
        }

        List<HouseRecord> houseRecords = ownerEntityLoader.getEntityManager().createQuery("select houseRecord from HouseRecord houseRecord left join fetch houseRecord.businessHouse businessHouse left join fetch businessHouse.businessHouseOwner where houseRecord.houseCode in (:houseCodes)", HouseRecord.class)
                .setParameter("houseCodes", houseMap.keySet())
                .getResultList();


        Map<String,HouseRecord> businessHouseMap = new HashMap<String, HouseRecord>();
        for (HouseRecord house: houseRecords){
            businessHouseMap.put(house.getHouseCode(),house);
        }

        List<String> lockedHouseCode;
        if (!houseMap.isEmpty()){
            lockedHouseCode = ownerEntityLoader.getEntityManager().createQuery("select lockedHouse.houseCode from LockedHouse lockedHouse where lockedHouse.houseCode in (:houseCodes)", String.class)
                    .setParameter("houseCodes", houseMap.keySet()).getResultList();
            List<String> inBusinessHouseCode = ownerEntityLoader.getEntityManager().createQuery("select houseBusiness.houseCode from HouseBusiness houseBusiness where (houseBusiness.ownerBusiness.status in (:runingStatus)) and houseBusiness.startBusinessHouse.houseCode in (:houseCodes)")
                    .setParameter("houseCodes", houseMap.keySet()).setParameter("runingStatus", OwnerBusiness.BusinessStatus.runningStatus()).getResultList();
            lockedHouseCode.addAll(inBusinessHouseCode);
        }else{
            lockedHouseCode = new ArrayList<String>(0);
        }





        for(BuildGridMap map: buildGridMaps){
            for(GridRow row: map.getGridRows()){
                for(GridBlock block: row.getGridBlocks()){
                    House house = null;
                    if ((block.getHouseCode() != null) && !block.getHouseCode().trim().equals(""))
                        house = houseMap.get(block.getHouseCode());
                    if (house != null){
                        block.setHouse(house);
                        block.setLocked(lockedHouseCode.contains(house.getHouseCode()));
                        HouseRecord houseRecord =  businessHouseMap.get(house.getHouseCode());
                        if (houseRecord != null) {
                            if (houseRecord.getBusinessHouse().getBusinessHouseOwner()!=null)
                                block.setOwnerName(houseRecord.getBusinessHouse().getBusinessHouseOwner().getPersonName());
                            block.setHouseStatus(houseRecord.getHouseStatus());
                        }
                        houseMap.remove(house.getHouseCode());
                    }
                }
            }
        }

        if (! houseMap.isEmpty()){
            List<House> idleHouses = new ArrayList<House>(houseMap.size());

            for (House house: houseMap.values()){
                House businessHouse = houseMap.get(house.getHouseCode());
                idleHouses.add(businessHouse);
            }

            BuildGridMap idleMap = BuildHome.genIdleHouseGridMap(idleHouses);
            for (GridRow gridRow: idleMap.getGridRows()){
                for(GridBlock block: gridRow.getGridBlocks()){
                    if (block.getHouse()!= null){
                        block.setLocked(lockedHouseCode.contains(block.getHouse().getHouseCode()));
                        HouseRecord houseRecord =  businessHouseMap.get(block.getHouseCode());
                        if (houseRecord != null) {
                            if (houseRecord.getBusinessHouse().getBusinessHouseOwner() != null)
                                block.setOwnerName(houseRecord.getBusinessHouse().getBusinessHouseOwner().getPersonName());
                            block.setHouseStatus(houseRecord.getHouseStatus());
                        }
                    }
                }
            }
            idleMap.setId("idleHouse");
            buildGridMaps.add(idleMap);

        }

        if (!buildGridMaps.isEmpty()){
            curMap = buildGridMaps.get(0);
        }
    }
}
