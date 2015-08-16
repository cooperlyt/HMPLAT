package com.dgsoft.house.owner.business;

import com.dgsoft.common.BatchOperData;
import com.dgsoft.common.Entry;
import com.dgsoft.common.system.AuthenticationInfo;
import com.dgsoft.house.HouseEntityLoader;
import com.dgsoft.house.HouseInfo;
import com.dgsoft.house.UseTypeWordAdapter;
import com.dgsoft.house.action.BuildHome;
import com.dgsoft.house.action.ProjectHome;
import com.dgsoft.house.model.*;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.*;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by cooper on 8/14/15.
 */

@Name("projectBusinessStart")
@Scope(ScopeType.CONVERSATION)
public class ProjectBusinessStart {


    @In
    private ProjectHome projectHome;

    @In(create = true)
    private HouseEntityLoader houseEntityLoader;

    @In
    private OwnerBusinessHome ownerBusinessHome;

    @In(create = true)
    private OwnerBusinessStart ownerBusinessStart;

    @In
    private AuthenticationInfo authInfo;

    private List<BatchOperData<Build>> builds = new ArrayList<BatchOperData<Build>>(0);

    private Map<Build,List<BuildGridMap>> buildGridMaps;

    public List<BatchOperData<Build>> getBuilds() {
        return builds;
    }

    private House selectHouse;

    public House getSelectHouse() {
        return selectHouse;
    }

    public String getSelectHouseCode(){
        if (selectHouse == null){
            return null;
        }
        return selectHouse.getHouseCode();
    }

    public void setSelectHouseCode(String code){
        if (code == null || code.trim().equals("")){
            selectHouse = null;
            return;
        }
        for(Build build: buildGridMaps.keySet()){
            for(House house: build.getHouses()){
                if (house.getHouseCode().equals(code)){
                    selectHouse = house;
                    return;
                }
            }
        }
        throw new IllegalArgumentException("invalid house code:" + code);
    }

    public void projectSelectedListener(){
        builds = new ArrayList<BatchOperData<Build>>(projectHome.getInstance().getBuilds().size());
        for(Build build: projectHome.getInstance().getBuildList()){
            builds.add(new BatchOperData<Build>(build,true));
        }
    }

    public boolean isSelectBuild(){
        if(projectHome.isIdDefined()){
            for(BatchOperData<Build> bb: builds){
                if (bb.isSelected()){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isSelectAll(){
        if (builds.isEmpty()){
            return false;
        }
        for(BatchOperData<Build> bb: builds){
            if (!bb.isSelected()){
                return false;
            }
        }
        return true;
    }

    public void setSelectAll(boolean selected){
        for(BatchOperData<Build> bb: builds){
            bb.setSelected(selected);
        }
    }

    public String buildComplete(){
        ownerBusinessHome.createInstance();
        ownerBusinessHome.getInstance().setBusinessProject(new BusinessProject(ownerBusinessHome.getInstance(), projectHome.getInstance()));

        for(BatchOperData<Build> build: builds){
            if (build.isSelected()){
                ownerBusinessHome.getInstance().getBusinessProject().getBusinessBuilds().add(
                        new BusinessBuild(ownerBusinessHome.getInstance().getBusinessProject(), build.getData()));
            }
        }
        initBuildGridMap();
        return "BUILD_COMPLETE";

    }


    public void initBuildGridMap(){
        buildGridMaps = new HashMap<Build, List<BuildGridMap>>();

        Set<String> buildIds = new HashSet<String>(ownerBusinessHome.getInstance().getBusinessProject().getBusinessBuilds().size());
        for(BusinessBuild build: ownerBusinessHome.getInstance().getBusinessProject().getBusinessBuilds()){
            buildIds.add(build.getBuildCode());
        }

        for(Build build: houseEntityLoader.getEntityManager().createQuery("select build from Build build where build.id in (:buildIds)", Build.class)
                .setParameter("buildIds", buildIds).getResultList()){

            Map<String,House> houseMap = new HashMap<String, House>();
            for (House house: build.getHouses()){
                houseMap.put(house.getHouseCode(),house);
            }

            List<String> lockedHouseCode;
            if (!houseMap.isEmpty()){

                lockedHouseCode = ownerBusinessHome.getEntityManager().createQuery("select lockedHouse.houseCode from LockedHouse lockedHouse where lockedHouse.type = 'CANT_SALE' and lockedHouse.houseCode in (:houseCodes)", String.class)
                        .setParameter("houseCodes", houseMap.keySet()).getResultList();
            }else{
                lockedHouseCode = new ArrayList<String>(0);
            }

            Map<String,HouseInfo.HouseStatus> houseStatusMap;

            if (!houseMap.isEmpty()){
                houseStatusMap = new HashMap<String, HouseInfo.HouseStatus>();
                for(HouseRecord hr: ownerBusinessHome.getEntityManager().createQuery("select houseRecord from HouseRecord  houseRecord left join fetch houseRecord.businessHouse where houseRecord.houseCode in (:houseCodes)", HouseRecord.class)
                        .setParameter("houseCodes",houseMap.keySet()).getResultList()){
                    if (hr.getBusinessHouse().getMasterStatus() != null)
                        houseStatusMap.put(hr.getHouseCode(),hr.getBusinessHouse().getMasterStatus());
                }
            }else{
                houseStatusMap = new HashMap<String, HouseInfo.HouseStatus>(0);
            }


            List<BuildGridMap> gridMaps = new ArrayList<BuildGridMap>(build.getBuildGridMaps());
            Collections.sort(gridMaps, new Comparator<BuildGridMap>() {
                @Override
                public int compare(BuildGridMap o1, BuildGridMap o2) {
                    return Integer.valueOf(o1.getOrder()).compareTo(o2.getOrder());
                }
            });

            for(BuildGridMap map: gridMaps){
                for(GridRow row: map.getGridRows()){
                    for(GridBlock block: row.getGridBlocks()){
                        House house = null;
                        if ((block.getHouseCode() != null) && !block.getHouseCode().trim().equals(""))
                            house = houseMap.get(block.getHouseCode());
                        if (house != null){
                            block.setHouse(house);
                            block.setHouseStatus(houseStatusMap.get(house.getHouseCode()));
                            block.setLocked(!lockedHouseCode.contains(house.getHouseCode()));
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
                            block.setLocked(!lockedHouseCode.contains(block.getHouse().getHouseCode()));
                            block.setHouseStatus(houseStatusMap.get(block.getHouse().getHouseCode()));
                        }
                    }
                }

                idleMap.setId("idleHouse");
                gridMaps.add(idleMap);

            }

            buildGridMaps.put(build, gridMaps);
        }

    }


    public List<Map.Entry<Build,List<BuildGridMap>>> getBuildGridMaps(){
        List<Map.Entry<Build,List<BuildGridMap>>> result = new ArrayList<Map.Entry<Build, List<BuildGridMap>>>(buildGridMaps.entrySet());
        Collections.sort(result, new Comparator<Map.Entry<Build, List<BuildGridMap>>>() {
            @Override
            public int compare(Map.Entry<Build, List<BuildGridMap>> o1, Map.Entry<Build, List<BuildGridMap>> o2) {
                return o1.getKey().getId().compareTo(o2.getKey().getId());


            }
        });
        return result;
    }

    private BuildGridMap curMap;

    public void setId(String mapId){
        if ((mapId == null) || mapId.trim().equals("")){
            curMap = null;
        }

        for(List<BuildGridMap> bGridMaps : buildGridMaps.values()){
            for(BuildGridMap gridMap: bGridMaps){
                if(gridMap.getId().equals(mapId)){
                    curMap = gridMap;
                    return;
                }
            }
        }
        throw  new IllegalArgumentException("map id not found");

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

    private Entry<Integer,Integer> getGridMapHouseCount(BuildGridMap gridMap){
        int totalCount = 0;
        int count = 0;
        for(GridRow row: gridMap.getGridRows()) {
            for (GridBlock block : row.getGridBlocks()) {
                if (block.getHouse() != null){
                    totalCount ++;
                    if (block.isLocked()){
                        count++;
                    }
                }
            }

        }
        return new Entry<Integer, Integer>(totalCount,count);
    }


    public Entry<Integer,Integer> getCountByBuild(String buildId){
        int totalCount = 0;
        int count = 0;
        for(Map.Entry<Build,List<BuildGridMap>> entry: buildGridMaps.entrySet()){
            if (entry.getKey().getId().equals(buildId)){
                for(BuildGridMap gridMap: entry.getValue()){
                    Entry<Integer,Integer> mapCount = getGridMapHouseCount(gridMap);
                    totalCount += mapCount.getKey();
                    count += mapCount.getValue();
                }
            }
        }
        return new Entry<Integer, Integer>(totalCount,count);


    }

    public Entry<Integer,Integer> getCountByMap(String mapId){
        for(Map.Entry<Build,List<BuildGridMap>> entry: buildGridMaps.entrySet()){
            for(BuildGridMap gridMap: entry.getValue()){
                if (gridMap.getId().equals(mapId)){
                    return getGridMapHouseCount(gridMap);
                }
            }
        }
        throw new IllegalArgumentException("invail gridMap id:" + mapId);
    }

    private BusinessBuild getBusinessBuild(String buildCode){
        for(BusinessBuild businessBuild: ownerBusinessHome.getInstance().getBusinessProject().getBusinessBuilds()){
            if(businessBuild.getBuildCode().equals(buildCode)){
                return businessBuild;
                //businessBuild.getProjectExceptHouses().add(new ProjectExceptHouse(houseCode,businessBuild));
            }
        }
        throw new IllegalArgumentException("invalid build code:" + buildCode);
    }

    public String dataComplete(){

        for(Map.Entry<Build,List<BuildGridMap>> entry: buildGridMaps.entrySet()){
            BusinessBuild businessBuild = getBusinessBuild(entry.getKey().getBuildCode());
            businessBuild.setHouseCount(0);
            businessBuild.setArea(BigDecimal.ZERO);
            businessBuild.setHomeCount(0);
            businessBuild.setHomeArea(BigDecimal.ZERO);
            businessBuild.setUnhomeCount(0);
            businessBuild.setUnhomeArea(BigDecimal.ZERO);
            businessBuild.setShopCount(0);
            businessBuild.setShopArea(BigDecimal.ZERO);

            Set<String> exceptHouseCode = new HashSet<String>();
            for(BuildGridMap gridMap: entry.getValue()){
                for(GridRow row: gridMap.getGridRows()){
                    for(GridBlock block: row.getGridBlocks()){

                        if (block.getHouse() != null) {
                            if (block.isLocked()){
                                UseTypeWordAdapter.UseType useType = UseTypeWordAdapter.instance().getUseType(block.getHouse().getUseType());
                                businessBuild.setHouseCount(businessBuild.getHomeCount() + 1);
                                businessBuild.setArea(businessBuild.getArea().add(block.getHouse().getHouseArea()));

                                if (useType.isDwelling()){
                                    businessBuild.setHomeCount(businessBuild.getHomeCount() + 1);
                                    businessBuild.setHomeArea(businessBuild.getHomeArea().add(block.getHouse().getHouseArea()));
                                }else if (useType.isShopHouse()){
                                    businessBuild.setShopCount(businessBuild.getShopCount() + 1);
                                    businessBuild.setShopArea(businessBuild.getShopArea().add(block.getHouse().getHouseArea()));
                                }else{
                                    businessBuild.setUnhomeCount(businessBuild.getUnhomeCount() + 1);
                                    businessBuild.setUnhomeArea(businessBuild.getUnhomeArea().add(block.getHouse().getHouseArea()));
                                }
                            } else {
                                businessBuild.getProjectExceptHouses().add(new ProjectExceptHouse(block.getHouseCode(), businessBuild));
                                exceptHouseCode.add(block.getHouseCode());
                            }
                        }

                    }
                }
            }

            Set<String> houseCodes = new HashSet<String>();
            for (House house: entry.getKey().getHouses()){
                houseCodes.add(house.getHouseCode());
            }

            if (!houseCodes.isEmpty()) {
                List<LockedHouse> lockedHouse = ownerBusinessHome.getEntityManager().createQuery("select lockedHouse from LockedHouse lockedHouse where lockedHouse.type = 'CANT_SALE' and lockedHouse.houseCode in (:houseCodes)", LockedHouse.class)
                        .setParameter("houseCodes", houseCodes).getResultList();

                for(LockedHouse lh : lockedHouse){
                    ownerBusinessHome.getEntityManager().remove(lh);
                }
                for(String houseCode: exceptHouseCode){
                    ownerBusinessHome.getEntityManager().persist(
                            new LockedHouse(houseCode,
                                    LockedHouse.LockType.CANT_SALE,
                                    authInfo.getLoginEmployee().getId(),
                                    authInfo.getLoginEmployee().getPersonName(),new Date()));
                }
            }

        }



        return ownerBusinessStart.dataSelected();

    }
}
