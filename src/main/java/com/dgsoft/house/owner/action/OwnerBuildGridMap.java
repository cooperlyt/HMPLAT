package com.dgsoft.house.owner.action;

import com.dgsoft.house.HouseEntityLoader;
import com.dgsoft.house.action.BuildHome;
import com.dgsoft.house.model.*;
import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.model.BusinessHouse;
import com.dgsoft.house.owner.model.HouseRecord;
import com.dgsoft.house.owner.model.OwnerBusiness;
import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;
import org.jboss.seam.log.Logging;

import javax.persistence.NoResultException;
import java.util.*;

/**
 * Created by cooper on 6/12/15.
 */

@Name("ownerBuildGridMap")
@Scope(ScopeType.CONVERSATION)
public class OwnerBuildGridMap {

    public BuildHome getBuildHome(){
       return (BuildHome)Component.getInstance(BuildHome.class,true);
    }

    @In(create = true)
    private OwnerEntityLoader ownerEntityLoader;

    @In(create = true)
    private HouseEntityLoader houseEntityLoader;

    private BuildGridMap curMap;

    private List<BuildGridMap> buildGridMaps;

    private BusinessHouse selectBizHouse;

    private boolean dataTableList;

    private boolean findByNumber;

    private String mapNumber;

    private String blockNumber;

    private String buildNumber;

    private List<BusinessHouse> resultBusinessHouse = new ArrayList<BusinessHouse>();

    public boolean isFindByNumber() {
        return findByNumber;
    }

    public void setFindByNumber(boolean findByNumber) {
        this.findByNumber = findByNumber;
    }

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



    private String houseOrder;


    public BusinessHouse getSelectBizHouse() {
        return selectBizHouse;
    }

    public void setSelectBizHouse(BusinessHouse selectBizHouse) {
        this.selectBizHouse = selectBizHouse;
    }

    public boolean isDataTableList() {
        return dataTableList;
    }

    public void setDataTableList(boolean dataTableList) {
        this.dataTableList = dataTableList;
    }


    public String getHouseOrder() {
        return houseOrder;
    }

    public void setHouseOrder(String houseOrder) {
        this.houseOrder = houseOrder;
    }



    public String getSelectBizHouseId(){
        if (selectBizHouse == null){
            return null;
        }
        return selectBizHouse.getHouseCode();
    }

    public void setSelectBizHouseId(String id){
        if ((id == null) || id.trim().equals("")){
            selectBizHouse = null;
        }

            for (GridRow row: curMap.getGridRows()){
                for (GridBlock block: row.getGridBlocks()){

                    if ( (block.getHouse() != null) && block.getHouse().getHouseCode().equals(id)){
                        selectBizHouse = (BusinessHouse) block.getHouse();
                        return;
                    }
                }
            }

        Logging.getLog(getClass()).warn("houseCode not found in map");
        selectBizHouse = null;
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

    public List<BusinessHouse> getResultBusinessHouse() {
        return resultBusinessHouse;
    }

    public List<BuildGridMap> getBuildGridMaps() {
        return buildGridMaps;
    }

    private BusinessHouse getBusinessHouse(House house , List<HouseRecord> houseRecords){

        for(HouseRecord houseRecord: houseRecords){
            if (houseRecord.getHouseCode().equals(house.getId())){
                return houseRecord.getBusinessHouse();
            }
        }
        return new BusinessHouse(house);
    }


    public void findBuildBySection(){
        findByNumber = false;
        init();
    }

    public void findBuildByNumber() {
        try {
            getBuildHome().setId(
                    houseEntityLoader.getEntityManager().createQuery("select build.id from Build build where build.mapNumber = :mapNumber and build.blockNo = :blockNumber and build.buildNo = :buildNumber", String.class)
                            .setParameter("mapNumber", mapNumber).setParameter("blockNumber", blockNumber).setParameter("buildNumber", buildNumber).getSingleResult());
            findByNumber = true;
            init();
        } catch (NoResultException e) {
            getBuildHome().clearInstance();
            dataTableList = false;
        }
    }

    public void findHouseBySection(){
        findByNumber = false;

        try {

            House house = houseEntityLoader.getEntityManager().createQuery("select house from House house where house.build.id =:buildCode and house.houseOrder = :houseOrder",House.class)
                    .setParameter("buildCode", getBuildHome().getId()).setParameter("houseOrder", houseOrder).getSingleResult();
            //TODO map show : house.getGridBlockId()
            try {
                selectBizHouse = ownerEntityLoader.getEntityManager().createQuery("select houseRecord.businessHouse from HouseRecord houseRecord where houseRecord.businessHouse.houseCode = :houseCode", BusinessHouse.class)
                    .setParameter("houseCode", house.getHouseCode()).getSingleResult();

            } catch (NoResultException e2){
                selectBizHouse = new BusinessHouse(house);
            }

        } catch (NoResultException e1){
            selectBizHouse = null;
        }


    }

    public void findHouseByNumber(){
        findByNumber = true;
        dataTableList = true;
        resultBusinessHouse = ownerEntityLoader.getEntityManager().createQuery("select houseRecord.businessHouse from HouseRecord houseRecord where houseRecord.businessHouse.mapNumber = :mapNumber and houseRecord.businessHouse.blockNo = :blockNumber and houseRecord.businessHouse.buildNo = :buildNumber and houseRecord.businessHouse.houseOrder = :houseOrder", BusinessHouse.class)
                .setParameter("mapNumber",mapNumber)
                .setParameter("blockNumber",blockNumber)
                .setParameter("buildNumber",buildNumber)
                .setParameter("houseOrder",houseOrder).getResultList();
        if (resultBusinessHouse.size() == 1){
            selectBizHouse = resultBusinessHouse.get(0);
        }else{
            selectBizHouse = null;
        }
    }



    @Create
    public void init(){




        buildGridMaps = new ArrayList<BuildGridMap>(getBuildHome().getInstance().getBuildGridMaps());
        Collections.sort(buildGridMaps, new Comparator<BuildGridMap>() {
            @Override
            public int compare(BuildGridMap o1, BuildGridMap o2) {
                return (new Integer(o1.getOrder())).compareTo(o2.getOrder());
            }
        });



        Set<House> houses = getBuildHome().getInstance().getHouses();

        List<HouseRecord> houseRecords = ownerEntityLoader.getEntityManager().createQuery("select houseRecord from HouseRecord houseRecord where houseRecord.businessHouse.buildCode = :buildCode",HouseRecord.class)
                .setParameter("buildCode",getBuildHome().getInstance().getId()).getResultList();

        List<String> lockedHouseCode = ownerEntityLoader.getEntityManager().createQuery("select lockedHouse.houseCode from LockedHouse lockedHouse where lockedHouse.buildCode =:buildCode", String.class)
                .setParameter("buildCode", getBuildHome().getInstance().getId()).getResultList();

        List<String> inBusinessHouseCode = ownerEntityLoader.getEntityManager().createQuery("select houseBusiness.houseCode from HouseBusiness houseBusiness where (houseBusiness.ownerBusiness.status in (:runingStatus)) and houseBusiness.startBusinessHouse.buildCode =:buildCode")
                .setParameter("buildCode", getBuildHome().getInstance().getId()).setParameter("runingStatus", OwnerBusiness.BusinessStatus.runningStatus()).getResultList();

        lockedHouseCode.addAll(inBusinessHouseCode);
        for(BuildGridMap map: buildGridMaps){
            for(GridRow row: map.getGridRows()){
                for(GridBlock block: row.getGridBlocks()){

                    for(House house: houses){
                        if ((house.getGridBlockId() != null) && house.getGridBlockId().equals(block.getId())){

                            block.setLocked(lockedHouseCode.contains(house.getId()));
                            block.setHouse(getBusinessHouse(house,houseRecords));
                            houses.remove(house);
                            break;
                        }
                    }

                }
            }

        }

        if (! houses.isEmpty()){
            List<BusinessHouse> idleHouses = new ArrayList<BusinessHouse>(houses.size());

            for (House house: houses){
                idleHouses.add(getBusinessHouse(house,houseRecords));
            }

            BuildGridMap idleMap = BuildHome.genIdleHouseGridMap(idleHouses,lockedHouseCode);
            idleMap.setId("idleHouse");
            buildGridMaps.add(idleMap);

        }

        if (!buildGridMaps.isEmpty()){
            curMap = buildGridMaps.get(0);
            dataTableList = false;
        }else{
            dataTableList = true;
            fillDataByBuild();
        }

    }

    public void fillDataByBuild(){
        resultBusinessHouse = ownerEntityLoader.getEntityManager().createQuery("select houseRecord.businessHouse from HouseRecord houseRecord where houseRecord.businessHouse.buildCode = :buildCode", BusinessHouse.class)
                .setParameter("buildCode",getBuildHome().getInstance().getId()).getResultList();

    }



}
