package com.dgsoft.house.owner.helper;

import com.dgsoft.common.system.DictionaryWord;
import com.dgsoft.developersale.SaleStatus;
import com.dgsoft.house.HouseEntityLoader;
import com.dgsoft.house.HouseStatus;
import com.dgsoft.house.action.BuildHome;
import com.dgsoft.house.action.HouseList;
import com.dgsoft.house.model.*;
import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.action.HouseRecordList;
import com.dgsoft.house.owner.model.BusinessBuild;
import com.dgsoft.house.owner.model.BusinessProject;
import com.dgsoft.house.owner.model.HouseRecord;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.web.RequestParameter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cooper on 12/30/15.
 */
@Name("buildSaleInfoProvider")
public class BuildSaleInfoProvider implements RestDataProvider {

    @RequestParameter
    private String buildId;

    @RequestParameter
    private String projectId;

    @RequestParameter
    private String cardId;

    @In(create = true)
    private HouseEntityLoader houseEntityLoader;

    @In(create = true)
    private OwnerEntityLoader ownerEntityLoader;

    @In(create = true)
    private DictionaryWord dictionary;

    private BusinessBuild businessBuild = null;

    private Map<String,House> houseMap;

    private Map<String,SaleStatus> houseStatusMap;


    @Override
    public JSONObject getJsonData() {



        if (buildId != null && !buildId.trim().equals("")) {
            try {
                businessBuild = ownerEntityLoader.getEntityManager().createQuery("select bb from BusinessBuild bb left join fetch bb.businessProject where bb.businessProject.ownerBusiness.status in ('COMPLETE','MODIFYING') and bb.buildCode = :buildCode", BusinessBuild.class)
                        .setParameter("buildCode", buildId).getSingleResult();
            } catch (NoResultException e) {
                return null;
            }
        } else {
            if (cardId != null && !cardId.trim().equals("")) {
                BusinessProject bp = ownerEntityLoader.getEntityManager().find(BusinessProject.class, cardId);
                if (bp != null) {
                    projectId = bp.getProjectCode();
                    if (!bp.getBusinessBuilds().isEmpty())
                        businessBuild = bp.getBusinessBuildList().get(0);
                }
            }

        }

        if (businessBuild != null && (projectId == null || projectId.trim().equals(""))) {
            projectId = businessBuild.getBusinessProject().getProjectCode();
        }

        if (projectId == null || projectId.trim().equals("")) {
            return null;
        }

        List<BusinessBuild> builds = new ArrayList<BusinessBuild>();
        String projectName = houseEntityLoader.getEntityManager().find(Project.class,projectId).getProjectName();
        List<BusinessProject> projects = ownerEntityLoader.getEntityManager().createQuery("select p from BusinessProject p where p.projectCode = :projectCode and p.ownerBusiness.status in ('COMPLETE','MODIFYING') order by p.ownerBusiness.regTime desc ", BusinessProject.class)
                .setParameter("projectCode", projectId).getResultList();
        for (BusinessProject p : projects) {
            builds.addAll(p.getBusinessBuildList());
        }
        if (businessBuild == null && !builds.isEmpty())
            businessBuild = builds.get(0);


        //all info get

        //put to json



        List<House> houseList = houseEntityLoader.getEntityManager().createQuery("select h from House h where h.build.id =:buildId and h.deleted = false",House.class)
                .setParameter("buildId",businessBuild.getBuildCode()).getResultList();
        if (houseList.isEmpty()){
            return null;
        }
        houseMap = new HashMap<String, House>(houseList.size());
        for (House h: houseList){
            houseMap.put(h.getId(),h);
        }



        List<String> lockedHouse = ownerEntityLoader.getEntityManager().createQuery("select lockedHouse.houseCode from LockedHouse lockedHouse where lockedHouse.houseCode in (:houseCodes)", String.class)
                .setParameter("houseCodes", houseMap.keySet()).getResultList();


        List<String> saledHouse = ownerEntityLoader.getEntityManager().createQuery("select distinct hb.houseCode from HouseContract hc left join hc.houseBusinesses hb where  hc.type in ('NOW_SELL', 'MAP_SELL') and  hc.ownerBusiness.status = 'RUNNING' and hb.houseCode in (:houseCodes)", String.class)
                .setParameter("houseCodes", houseMap.keySet()).getResultList();

        Map<String, HouseStatus> recordHouse = new HashMap<String, HouseStatus>();
        if (!houseList.isEmpty()){
           List<HouseRecord> houseRecordList = ownerEntityLoader.getEntityManager().createQuery("select hr from HouseRecord hr where hr.houseCode in (:houseIds)", HouseRecord.class)
                    .setParameter("houseIds",houseMap.keySet()).getResultList();
            for(HouseRecord hr: houseRecordList){

                recordHouse.put(hr.getHouseCode(),hr.getHouseStatus());
            }
        }

        houseStatusMap = new HashMap<String, SaleStatus>();

        for(House house: houseList){
            if (saledHouse.contains(house.getHouseCode())){
                houseStatusMap.put(house.getHouseCode(),SaleStatus.CONTRACT_SUBMIT);
            }else if (lockedHouse.contains(house.getHouseCode())){
                houseStatusMap.put(house.getHouseCode(),SaleStatus.NO_SALE);
            }else {
                HouseStatus houseStatus = recordHouse.get(house.getHouseCode());

                if (houseStatus == null){
                    houseStatusMap.put(house.getHouseCode(), SaleStatus.CAN_SALE);
                }else if (HouseStatus.COURT_CLOSE.equals(houseStatus)) {
                    houseStatusMap.put(house.getHouseCode(), SaleStatus.COURT_CLOSE);
                } else if (HouseStatus.CONTRACTS_RECORD.equals(houseStatus)) {
                    houseStatusMap.put(house.getHouseCode(),SaleStatus.CONTRACTS_RECORD);
                }else if (HouseStatus.PROJECT_PLEDGE.equals(houseStatus)){
                    houseStatusMap.put(house.getHouseCode(),SaleStatus.PROJECT_PLEDGE);
                } else if(HouseStatus.INIT_REG_CONFIRM.equals(houseStatus) || HouseStatus.INIT_REG.equals(houseStatus)){
                    houseStatusMap.put(house.getHouseCode(), SaleStatus.NOW_SALE);
                }else{
                    houseStatusMap.put(house.getHouseCode(),SaleStatus.HAVE_SALE);
                }
            }

        }




        List<BuildGridMap> gridMaps = houseEntityLoader.getEntityManager().createQuery("select buildGridMap from BuildGridMap buildGridMap where buildGridMap.build.id =:buildId order by buildGridMap.order", BuildGridMap.class)
                .setParameter("buildId", businessBuild.getBuildCode()).getResultList();


        JSONObject result = new JSONObject();
        try {

            JSONArray gridMapArray = new JSONArray();
            if (!gridMaps.isEmpty()) {
                for (BuildGridMap bgm : gridMaps) {
                    gridMapArray.put(getGridMapJson(bgm));
                }
            } else {
                gridMapArray.put(getAutoGridMapJson());
            }


            result.put("gridMaps", gridMapArray);
            JSONArray buildArray = new JSONArray();
            for(BusinessBuild build: builds){
                JSONObject buildObj = new JSONObject();
                buildObj.put("buildName",build.getBuildName());
                buildObj.put("id",build.getBuildCode());
                buildArray.put(buildObj);
            }
            result.put("builds",buildArray);
            result.put("projectName",projectName);
            result.put("buildName",businessBuild.getBuildName());
            return result;
        } catch (JSONException e) {
            return null;
        }

    }

    private JSONObject getGridMapJson(BuildGridMap bgm) throws JSONException {
        JSONObject result = new JSONObject();
        result.put("title",(bgm.getName() == null) ? "分户图" : bgm.getName());
        result.put("index",bgm.getOrder());

        JSONArray title = new JSONArray();
        int colCount = 0;
        for(HouseGridTitle t: bgm.getHouseGridTitleList()){
            JSONObject tjo = new JSONObject();
            tjo.put("title",t.getTitle());
            tjo.put("colsapn",t.getColspan());
            colCount += t.getColspan();
            title.put(tjo);
        }
        result.put("head",title);
        result.put("colCount",colCount);

        JSONArray row = new JSONArray();

        for(GridRow r: bgm.getGridRowList()){
            JSONObject ro = new JSONObject();
            ro.put("title",r.getTitle());
            JSONArray block = new JSONArray();
            for(GridBlock b: r.getGridBlockList()){
                JSONObject bo = new JSONObject();
                bo.put("colspan",b.getColspan());
                bo.put("rowSapn",b.getRowspan());

                if (b.getHouseCode() != null && !b.getHouseCode().trim().equals("")){
                    House house = houseMap.get(b.getHouseCode());
                    if (house != null) {
                        bo.put("title", house.getHouseOrder());
                        bo.put("status",houseStatusMap.get(b.getHouseCode()).name());
                        bo.put("area",house.getHouseArea().toString());
                        bo.put("useArea",house.getUseArea().toString());
                        bo.put("useType",dictionary.getWordValue(house.getDesignUseType()));
                        bo.put("commArea",house.getCommArea().toString());
                        bo.put("commParam",house.getCommParam().toString());
                        bo.put("houseProperty",house.getHouseType().name());
                        bo.put("knotSize",dictionary.getWordValue(house.getKnotSize()));
                        bo.put("shineArea",(house.getShineArea() == null) ? null : house.getShineArea().toString());
                        bo.put("loftArea",(house.getLoftArea() == null) ? null : house.getLoftArea().toString());
                        bo.put("eastWall",dictionary.getWordValue(house.getEastWall()));
                        bo.put("westWall",dictionary.getWordValue(house.getWestWall()));
                        bo.put("southWall",dictionary.getWordValue(house.getSouthWall()));
                        bo.put("northWall",dictionary.getWordValue(house.getNorthWall()));
                        bo.put("direction",dictionary.getWordValue(house.getDirection()));
                    }

                }


                block.put(bo);
            }
            ro.put("blocks",block);
            row.put(ro);
        }

        result.put("rows",row);

        return result;
    }

    private JSONObject getAutoGridMapJson() throws JSONException {
        return getGridMapJson(BuildHome.genIdleHouseGridMap(houseMap.values()));
    }


}
