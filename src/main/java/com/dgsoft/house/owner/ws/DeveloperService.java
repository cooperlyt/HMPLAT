package com.dgsoft.house.owner.ws;

import com.dgsoft.common.system.DictionaryWord;
import com.dgsoft.common.system.RunParam;
import com.dgsoft.common.system.business.BusinessInstance;
import com.dgsoft.developersale.LogonStatus;
import com.dgsoft.developersale.wsinterface.DESUtil;
import com.dgsoft.house.HouseStatus;
import com.dgsoft.house.action.BuildHome;
import com.dgsoft.house.model.*;
import com.dgsoft.house.owner.model.BusinessBuild;
import com.dgsoft.house.owner.model.HouseRecord;
import com.dgsoft.house.owner.model.ProjectCard;
import com.longmai.uitl.Base64;
import org.eclipse.birt.chart.computation.withaxes.Grid;
import org.jboss.seam.Component;
import org.jboss.seam.log.Logging;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

/**
 * Created by cooper on 9/4/15.
 */
@WebService
public class DeveloperService {

    @WebMethod
    public String logon(String userId, String password, String random) {

        JSONObject jsonObject = new JSONObject();

        EntityManager houseEntityManager = (EntityManager) Component.getInstance("houseEntityManager", true, true);

        //Map<String, String> messages = (Map<String, String>) Component.getInstance("messages", true, true);

        DeveloperLogonKey key = houseEntityManager.find(DeveloperLogonKey.class, userId);
        try {
            if (key == null) {
                jsonObject.put("logonStatus", LogonStatus.KEY_NOT_FOUND.name());
            } else if (!CheckHashValues(key.getPassword(), random, password)) {
                jsonObject.put("logonStatus", LogonStatus.PASSWORD_ERROR.name());
            } else if (!AttachCorporation.AttachCorpType.DEVELOPER.equals(key.getAttachEmployee().getAttachCorporation().getType())) {
                jsonObject.put("logonStatus", LogonStatus.TYPE_ERROR.name());
            } else if (!key.getAttachEmployee().isEnable()) {
                jsonObject.put("logonStatus", LogonStatus.EMP_DISABLE.name());
            } else if (!key.getAttachEmployee().getAttachCorporation().isEnable()) {
                jsonObject.put("logonStatus", LogonStatus.CORP_DISABLE.name());
            } else if (key.getAttachEmployee().getAttachCorporation().isOutTime()) {
                jsonObject.put("logonStatus", LogonStatus.CORP_OUT_TIME.name());
            } else {
                jsonObject.put("logonStatus", LogonStatus.LOGON.name());

                String rndData = "";
                int b;
                int a;
                SecureRandom r = new SecureRandom();
                for (int i = 0; i < 32; i++) {
                    a = r.nextInt(26);
                    b = (char) (a + 65);
                    rndData += new Character((char) b).toString();
                }

                jsonObject.put("sessionKey", rndData);

                jsonObject.put("employeeName", key.getAttachEmployee().getPersonName());
                jsonObject.put("corpName", key.getAttachEmployee().getAttachCorporation().getDeveloper().getName());

                jsonObject.put("orgName", RunParam.instance().getStringParamValue("SetupName"));

                JSONObject projectJsonObj = new JSONObject();

                projectJsonObj.put("developerName", key.getProject().getDeveloperName());
                projectJsonObj.put("developerCode", key.getProject().getDeveloperCode());

                projectJsonObj.put("projectName", key.getProject().getProjectName());
                projectJsonObj.put("projectCode", key.getProject().getProjectCode());

                projectJsonObj.put("districtName", key.getProject().getDistrictName());
                projectJsonObj.put("districtCode", key.getProject().getDistrictCode());


                projectJsonObj.put("sectionName", key.getProject().getSectionName());
                projectJsonObj.put("sectionCode", key.getProject().getSectionCode());

                EntityManager ownerEntityManager = (EntityManager) Component.getInstance("ownerEntityManager", true, true);

                JSONArray cardArray = new JSONArray();
                for (ProjectCard card : ownerEntityManager.createQuery("select projectCard from ProjectCard projectCard left join fetch projectCard.makeCard left join fetch projectCard.projectSellInfo sellInfo left join fetch sellInfo.businessProject project where project.projectCode = :projectCode and project.ownerBusiness.status in (:allowStatus)", ProjectCard.class)
                        .setParameter("projectCode", key.getProject().getProjectCode())
                        .setParameter("allowStatus", EnumSet.of(BusinessInstance.BusinessStatus.COMPLETE, BusinessInstance.BusinessStatus.MODIFYING))
                        .getResultList()) {

                    JSONObject cardJsonObj = new JSONObject();
                    cardJsonObj.put("cardType", card.getProjectSellInfo().getType().name());
                    cardJsonObj.put("cardNumber", card.getMakeCard().getNumber());
                    cardJsonObj.put("address", card.getProjectSellInfo().getBusinessProject().getAddress());


                    cardJsonObj.put("landCardType", DictionaryWord.instance().getWordValue(card.getProjectSellInfo().getLandCardType()));
                    cardJsonObj.put("landCardNumber", card.getProjectSellInfo().getLandCardNo());
                    cardJsonObj.put("landArea", card.getProjectSellInfo().getLandArea());
                    cardJsonObj.put("landUseType", DictionaryWord.instance().getWordValue(card.getProjectSellInfo().getLandUseType()));

                    cardJsonObj.put("landEndUseTime", card.getProjectSellInfo().getEndUseTime().getTime());
                    cardJsonObj.put("createCardNumber", card.getProjectSellInfo().getCreateCardNumber());
                    cardJsonObj.put("createPrepareCardNumber", card.getProjectSellInfo().getCreatePrepareCardNumber());
                    cardJsonObj.put("name", card.getProjectSellInfo().getBusinessProject().getProjectName());

                    JSONArray buildJsonArray = new JSONArray();
                    for (BusinessBuild build : card.getProjectSellInfo().getBusinessProject().getBusinessBuilds()) {
                        JSONObject buildJsonObj = new JSONObject();

                        buildJsonObj.put("buildName", build.getBuildName());
                        buildJsonObj.put("buildCode", build.getBuildCode());
                        buildJsonObj.put("mapNumber", build.getMapNumber());
                        buildJsonObj.put("blockNo", build.getBlockNo());
                        buildJsonObj.put("buildNo", build.getBlockNo());
                        buildJsonObj.put("completeYear", build.getCompleteYear());
                        buildJsonObj.put("streetCode", build.getSectionCode());
                        buildJsonObj.put("doorNo", build.getDoorNo());
                        buildJsonObj.put("unintCount", build.getUnintCount());
                        buildJsonObj.put("buildDevNumber", build.getBuildDevNumber());
                        buildJsonObj.put("buildType", DictionaryWord.instance().getWordValue(build.getBuildType()));
                        buildJsonObj.put("structure", DictionaryWord.instance().getWordValue(build.getStructure()));
                        buildJsonObj.put("upFloorCount", build.getUpFloorCount());
                        buildJsonObj.put("downFloorCount", build.getDownFloorCount());
                        buildJsonObj.put("mapTime", build.getMapTime().getTime());

                        buildJsonArray.put(buildJsonObj);
                    }

                    cardJsonObj.put("saleBuilds", buildJsonArray);
                    cardArray.put(cardJsonObj);
                }


                projectJsonObj.put("saleCards", cardArray);
                jsonObject.put("project", projectJsonObj);
                //  jsonObject.put("corp",)


                key.setSessionKey(rndData);
                houseEntityManager.flush();
            }
            try {
                return DESUtil.encrypt(jsonObject.toString(), key.getId());
            } catch (Exception e) {
                return null;
            }

        } catch (JSONException e) {
            Logging.getLog(getClass()).error(e.getMessage(), e);
            return null;
        } catch (NoSuchAlgorithmException e2) {
            Logging.getLog(getClass()).error(e2.getMessage(), e2);
            return null;
        }

    }

    // private JSONObject get

    private boolean CheckHashValues(String Seed, String Random, String ClientDigest) throws NoSuchAlgorithmException {

        Logging.getLog(getClass()).debug("seed:" + Seed + ";random:" + Random + ";ClientDigest:" + ClientDigest);
        MessageDigest md = MessageDigest.getInstance("SHA1");
        String a = Random + Seed;
        byte[] serverDigest = md.digest(a.getBytes());
        byte[] clientDigest = Base64.decode(ClientDigest);

        return Arrays.equals(serverDigest, clientDigest);

    }

    public String getBuildGridMap(String buildCode) {
        EntityManager houseEntityManager = (EntityManager) Component.getInstance("houseEntityManager", true, true);
        Map<String, House> houseMap = new HashMap<String, House>();
        for (House house : houseEntityManager.createQuery("select House from House house where house.build.id = :buildId and deleted = false", House.class)
                .setParameter("buildId", buildCode).getResultList()) {
            houseMap.put(house.getHouseCode(), house);
        }

        EntityManager ownerEntityManager = (EntityManager) Component.getInstance("ownerEntityManager", true, true);
        List<HouseRecord> recordHouse = ownerEntityManager.createQuery("select houseRecord from HouseRecord houseRecord left join fetch houseRecord.businessHouse where houseRecord.houseCode in (:houseCodes)", HouseRecord.class)
                .setParameter("houseCodes", houseMap.keySet()).getResultList();

        Map<String,HouseStatus> houseStatusMap = new HashMap<String, HouseStatus>();

        for (HouseRecord houseRecord: recordHouse){
            if (houseRecord.getBusinessHouse().getMasterStatus() != null){
                houseStatusMap.put(houseRecord.getHouseCode(),houseRecord.getBusinessHouse().getMasterStatus());
            }
        }

        List<String> contractHouseIds = ownerEntityManager.createQuery("select contractOwner.houseCode from ContractOwner contractOwner where contractOwner.ownerBusiness.status = 'RUNNING' and contractOwner.houseCode in (:houseCodes)", String.class)
                .setParameter("houseCodes", houseMap.keySet()).getResultList();



        try {
            JSONArray buildGridMapJsonArray = new JSONArray();

            for (BuildGridMap gridMap : houseEntityManager.createQuery("select buildGridMap from BuildGridMap buildGridMap where buildGridMap.build.id =:buildId order by buildGridMap.order", BuildGridMap.class)
                    .setParameter("buildId", buildCode).getResultList()) {


                buildGridMapJsonArray.put(getBuildMapJsonObject(houseMap, houseStatusMap, contractHouseIds, gridMap));
            }

            if (!houseMap.isEmpty()){

                buildGridMapJsonArray.put(getBuildMapJsonObject(houseMap, houseStatusMap, contractHouseIds, BuildHome.genIdleHouseGridMap(houseMap.values())));


            }

            return buildGridMapJsonArray.toString();

        } catch (JSONException e) {
            Logging.getLog(getClass()).debug(e.getMessage(), e);
            return null;
        }

    }

    private JSONObject getBuildMapJsonObject(Map<String, House> houseMap, Map<String, HouseStatus> houseStatusMap, List<String> contractHouseIds, BuildGridMap gridMap) throws JSONException {
        JSONObject result = new JSONObject();
        result.put("name", gridMap.getName());
        JSONArray titleJsonArray = new JSONArray();
        for (HouseGridTitle title : gridMap.getHouseGridTitleList()) {
            JSONObject titleJsonObject = new JSONObject();
            titleJsonObject.put("title", title.getTitle());
            titleJsonObject.put("colspan", title.getColspan());
            titleJsonArray.put(titleJsonObject);
        }
        result.put("titles", titleJsonArray);
        JSONArray rowJsonArray = new JSONArray();
        for (GridRow gridRow : gridMap.getGridRowList()) {
            JSONObject rowJsonObj = new JSONObject();
            rowJsonObj.put("title", gridRow.getTitle());
            JSONArray bolckJsonArray = new JSONArray();
            for (GridBlock block : gridRow.getGridBlocks()) {
                bolckJsonArray.put(genBlockJsonObj(block, houseMap,houseStatusMap,contractHouseIds));
            }
            rowJsonObj.put("blocks", bolckJsonArray);
            rowJsonArray.put(rowJsonObj);
        }
        return result;
    }

    private JSONObject genBlockJsonObj(GridBlock block,
                                       Map<String, House> houseMap,
                                       Map<String,HouseStatus> statusMap, List<String> contractHouses) {
        JSONObject blockJsonObj = new JSONObject();
        try {
            blockJsonObj.put("colspan", block.getColspan());
            blockJsonObj.put("rowspan", block.getRowspan());


            if ( block.getHouseCode() != null && !block.getHouseCode().trim().equals("") ){
                House house = houseMap.get(block.getHouseCode());
                if (house != null){
                    if (contractHouses.contains(block.getHouseCode())){
                        blockJsonObj.put("status", "CONTRACTING");
                    }else {

                        HouseStatus status = statusMap.get(block.getHouseCode());
                        if (status != null) {
                            blockJsonObj.put("status", status.name());
                        }
                    }
                    JSONObject houseJsonObj = new JSONObject();


                    houseJsonObj.put("houseCode",house.getHouseCode());
                    houseJsonObj.put("displayHouseCode",house.getDisplayHouseCode());
                    houseJsonObj.put("houseOrder", house.getHouseOrder());
                    houseJsonObj.put("houseUnitName",house.getHouseUnitName());
                    houseJsonObj.put("inFloorName",house.getInFloorName());
                    if (house.getHouseArea() != null)
                    houseJsonObj.put("houseArea",house.getHouseArea().doubleValue());
                    if (house.getUseArea() != null)
                    houseJsonObj.put("useArea",house.getUseArea().doubleValue());
                    if (house.getCommArea() != null)
                    houseJsonObj.put("commArea",house.getCommArea().doubleValue());
                    if (house.getShineArea() != null)
                    houseJsonObj.put("shineArea",house.getShineArea().doubleValue());
                    if (house.getLoftArea() != null)
                    houseJsonObj.put("loftArea",house.getLoftArea().doubleValue());
                    if (house.getCommParam() != null)
                    houseJsonObj.put("commParam",house.getCommParam().doubleValue());
                    houseJsonObj.put("houseType", DictionaryWord.instance().getWordValue(house.getHouseType()));
                    houseJsonObj.put("houseType", DictionaryWord.instance().getWordValue(house.getHouseType()));
                    houseJsonObj.put("structure", DictionaryWord.instance().getWordValue(house.getStructure()));
                    houseJsonObj.put("knotSize", DictionaryWord.instance().getWordValue(house.getKnotSize()));
                    houseJsonObj.put("address", house.getAddress());
                    houseJsonObj.put("eastWall",DictionaryWord.instance().getWordValue(house.getEastWall()));
                    houseJsonObj.put("westWall", DictionaryWord.instance().getWordValue(house.getWestWall()));
                    houseJsonObj.put("southWall", DictionaryWord.instance().getWordValue(house.getSouthWall()));
                    houseJsonObj.put("northWall", DictionaryWord.instance().getWordValue(house.getNorthWall()));
                    houseJsonObj.put("direction", DictionaryWord.instance().getWordValue(house.getDirection()));

                    blockJsonObj.put("house",houseJsonObj);
                }
                houseMap.remove(block.getHouseCode());
            }
        } catch (JSONException e) {
            Logging.getLog(getClass()).error(e.getMessage(),e);

        }

        return blockJsonObj;

    }

    public String submitContract(String contract, String userId) {
        return null;
    }

//    public String searchContract(String houseCode){
//
//
//    }


}
