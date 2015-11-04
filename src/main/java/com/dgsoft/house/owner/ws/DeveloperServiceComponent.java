package com.dgsoft.house.owner.ws;

import com.dgsoft.common.system.DictionaryWord;
import com.dgsoft.common.system.NumberBuilder;
import com.dgsoft.common.system.PersonEntity;
import com.dgsoft.common.system.RunParam;
import com.dgsoft.common.system.business.BusinessInstance;
import com.dgsoft.developersale.DeveloperSaleService;
import com.dgsoft.developersale.LogonStatus;
import com.dgsoft.developersale.wsinterface.DESUtil;
import com.dgsoft.house.HouseStatus;
import com.dgsoft.house.PoolType;
import com.dgsoft.house.SaleType;
import com.dgsoft.house.action.BuildHome;
import com.dgsoft.house.model.*;
import com.dgsoft.house.owner.action.OwnerHouseHelper;
import com.dgsoft.house.owner.model.*;
import com.longmai.uitl.Base64;
import org.eclipse.emf.common.util.Pool;
import org.jboss.seam.Component;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.log.Logging;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.tuckey.web.filters.urlrewrite.Run;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

/**
 * Created by cooper on 9/9/15.
 */
@Name("developerServiceComponent")
public class DeveloperServiceComponent {

    @Transactional
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

                JSONObject attachCorpJsonObj = new JSONObject();
                attachCorpJsonObj.put("name", key.getAttachEmployee().getAttachCorporation().getDeveloper().getName());
                attachCorpJsonObj.put("address", key.getAttachEmployee().getAttachCorporation().getAddress());
                attachCorpJsonObj.put("postCode", key.getAttachEmployee().getAttachCorporation().getPostCode());
                attachCorpJsonObj.put("licenseNumber", key.getAttachEmployee().getAttachCorporation().getLicenseNumber());
                attachCorpJsonObj.put("cerCode", key.getAttachEmployee().getAttachCorporation().getCerCode());
                attachCorpJsonObj.put("ownerPerson", key.getAttachEmployee().getAttachCorporation().getOwnerName());
                attachCorpJsonObj.put("ownerTel",key.getAttachEmployee().getPhone());

                jsonObject.put("attachCorpInfo",attachCorpJsonObj);


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
                    cardJsonObj.put("developerName", card.getProjectSellInfo().getBusinessProject().getDeveloperName());

                    cardJsonObj.put("landCardType", DictionaryWord.instance().getWordValue(card.getProjectSellInfo().getLandCardType()));
                    cardJsonObj.put("landCardNumber", card.getProjectSellInfo().getLandCardNo());
                    cardJsonObj.put("landArea", card.getProjectSellInfo().getLandArea());
                    cardJsonObj.put("landUseType", DictionaryWord.instance().getWordValue(card.getProjectSellInfo().getLandUseType()));

                    cardJsonObj.put("landEndUseTime", card.getProjectSellInfo().getEndUseTime().getTime());
                    cardJsonObj.put("landGetMode", DictionaryWord.instance().getWordValue(card.getProjectSellInfo().getLandGetMode()));
                    cardJsonObj.put("landAddress", card.getProjectSellInfo().getLandAddress());
                    if (card.getProjectSellInfo().getBusinessProject().getOwnerBusiness().getMappingCorp() != null)
                        cardJsonObj.put("mappingCropName", card.getProjectSellInfo().getBusinessProject().getOwnerBusiness().getMappingCorp().getName());

                    cardJsonObj.put("createCardNumber", card.getProjectSellInfo().getCreateCardNumber());
                    cardJsonObj.put("createPrepareCardNumber", card.getProjectSellInfo().getCreatePrepareCardNumber());
                    cardJsonObj.put("name", card.getProjectSellInfo().getBusinessProject().getProjectName());

                    JSONArray buildJsonArray = new JSONArray();
                    for (BusinessBuild build : card.getProjectSellInfo().getBusinessProject().getBusinessBuildList()) {
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
            Logging.getLog(getClass()).debug("logon 1");
            try {
                return DESUtil.encrypt(jsonObject.toString(), key.getId());
            } catch (Exception e) {

                Logging.getLog(getClass()).error("encrpt error", e);
                throw new IllegalArgumentException(e.getMessage(),e);
            }

        } catch (JSONException e) {
            Logging.getLog(getClass()).error("json exception", e);
            throw new IllegalArgumentException(e.getMessage(),e);
        } catch (NoSuchAlgorithmException e2) {
            Logging.getLog(getClass()).error(e2.getMessage(), e2);
            throw new IllegalArgumentException(e2.getMessage(),e2);
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

    @Transactional
    public String applyContractNumber(String userId, int count , String typeName){
        EntityManager houseEntityManager = (EntityManager) Component.getInstance("houseEntityManager", true, true);
        DeveloperLogonKey key = houseEntityManager.find(DeveloperLogonKey.class, userId);
        if (key == null){
            throw new IllegalArgumentException("user fail");
        }
       // int genCount = RunParam.instance().getIntParamValue("ContractNOCount");
        //if (genCount <= 0){
        //    throw new IllegalArgumentException("ContractNOCount param is zero");
        //}

        SaleType type = SaleType.valueOf(typeName);

        EntityManager ownerEntityManager = (EntityManager) Component.getInstance("ownerEntityManager", true, true);


        //List<ContractNumber> contractNumbers = ownerEntityManager.createQuery("select n from ContractNumber n where n.type = :contractType and n.status = 'FREE'", ContractNumber.class)
        //        .setParameter("contractType", type).setMaxResults(count).getResultList();
        //if (contractNumbers.size() < count){
        String setupCode = RunParam.instance().getStringParamValue("SetupCode");
        Long max;
            try {
                max = ownerEntityManager.createQuery("select max(n.number) from ContractNumber n where n.type =:contractType", Long.class)
                        .setParameter("contractType", type).getSingleResult();
                if (max == null){
                    max = new Long(0);
                }
            }catch (NoResultException e){
                max = new Long(0);
            }

        max++;
        JSONArray jsonArray = new JSONArray();
            for(int i = 0 ; i < count ; i++){
                long number =  max + i ;
                ContractNumber contractNumber =  new ContractNumber(type, number, type.getNumberPrefx() + setupCode + "-" + number, ContractNumber.ContractNumberStatus.OUT, new Date());
                contractNumber.setApplyTime(new Date());
                ownerEntityManager.persist(contractNumber);
                jsonArray.put(contractNumber.getContractNumber());
            }
        //}


        try {
            String result = DESUtil.encrypt(jsonArray.toString(), key.getSessionKey());
            ownerEntityManager.flush();
            return result;
        } catch (Exception e) {
            Logging.getLog(getClass()).error(e.getMessage(), e);
            throw new IllegalArgumentException(e.getMessage(),e);
        }


    }

    @Transactional
    public String getHouseInfoBySale(String userId, String houseCode){
        EntityManager houseEntityManager = (EntityManager) Component.getInstance("houseEntityManager", true, true);
        DeveloperLogonKey key = houseEntityManager.find(DeveloperLogonKey.class, userId);
        if (key == null){
            throw new IllegalArgumentException("user fail");
        }
        House house = houseEntityManager.find(House.class, houseCode);
        if (house == null || house.isDeleted()){
            throw new IllegalArgumentException("house not exists:" + houseCode);
        }
        EntityManager ownerEntityManager = (EntityManager) Component.getInstance("ownerEntityManager", true, true);
        HouseRecord houseRecord = ownerEntityManager.find(HouseRecord.class, houseCode);

        boolean locked = ownerEntityManager.createQuery("select count(l.id) from LockedHouse l where l.houseCode = :houseCode", Long.class)
        .setParameter("houseCode",houseCode).getSingleResult().compareTo(new Long(0)) > 0;

        boolean sale = ownerEntityManager.createQuery("select count(c.id) from ContractOwner c where c.houseCode = :houseCode and c.ownerBusiness.type = 'RUNNING'", Long.class)
                .setParameter("houseCode", houseCode).getSingleResult().compareTo(new Long(0)) > 0;

        boolean inBiz = ownerEntityManager.createQuery("select count(b.id) from HouseBusiness b where b.houseCode = :houseCode and b.ownerBusiness.type = 'RUNNING'", Long.class)
                .setParameter("houseCode",houseCode).getSingleResult().compareTo(new Long(0)) > 0;
        HouseStatus houseStatus = null;

        if (houseRecord != null){
            houseStatus = houseRecord.getHouseStatus();
           //   may be sub status
        }

        try {
            JSONObject houseJsonObj = getHouseJsonObj(house, houseStatus,locked, sale,inBiz);
            houseJsonObj.put("pledge", searchHousePledgeInfo(houseCode));
            houseJsonObj.put("buildCode",house.getBuildCode());
            try {
                return DESUtil.encrypt(houseJsonObj.toString(),key.getSessionKey());
            } catch (Exception e) {
                Logging.getLog(getClass()).error(e.getMessage(), e);
                throw new IllegalArgumentException(e.getMessage(),e);
            }
        } catch (JSONException e) {
            Logging.getLog(getClass()).error(e.getMessage(), e);
            throw new IllegalArgumentException(e.getMessage(),e);
        }
    }

    private JSONArray searchHousePledgeInfo(String houseCode) throws JSONException {

      try {
            JSONArray jsonArray = new JSONArray();
            for(MortgaegeRegiste mortgaegeRegiste: OwnerHouseHelper.instance().getMortgaeges(houseCode)){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("type", mortgaegeRegiste.getOwnerBusiness().getDefineName());

                jsonObject.put("ownerPersonName",mortgaegeRegiste.getBusinessHouseOwner().getPersonName());

                jsonObject.put("pledgePersonName", mortgaegeRegiste.getFinancial().getName());

                jsonObject.put("pledgeCorpName", mortgaegeRegiste.getOrgName());

                jsonObject.put("beginTime", mortgaegeRegiste.getMortgageDueTimeS().getTime());

                jsonObject.put("endTime",mortgaegeRegiste.getMortgageTime());

                jsonArray.put(jsonObject);
            }
            return jsonArray;

        }catch (NoResultException e) {
            return new JSONArray();
        }

    }

    @Transactional
    public String getBuildGridMap(String buildCode) {
        EntityManager houseEntityManager = (EntityManager) Component.getInstance("houseEntityManager", true, true);
        Map<String, House> houseMap = new HashMap<String, House>();
        for (House house : houseEntityManager.createQuery("select House from House house where house.build.id = :buildId and deleted = false", House.class)
                .setParameter("buildId", buildCode).getResultList()) {
            houseMap.put(house.getHouseCode(), house);
        }
        if (houseMap.isEmpty()){
            return null;
        }

        EntityManager ownerEntityManager = (EntityManager) Component.getInstance("ownerEntityManager", true, true);
        List<HouseRecord> recordHouse = ownerEntityManager.createQuery("select houseRecord from HouseRecord houseRecord left join fetch houseRecord.businessHouse where houseRecord.houseCode in (:houseCodes)", HouseRecord.class)
                .setParameter("houseCodes", houseMap.keySet()).getResultList();

        Map<String,HouseStatus> houseStatusMap = new HashMap<String, HouseStatus>();

        List<String> lockedHouse = ownerEntityManager.createQuery("select lockedHouse.houseCode from LockedHouse lockedHouse where lockedHouse.houseCode in (:houseCodes)",String.class)
                .setParameter("houseCodes", houseMap.keySet()).getResultList();

        for (HouseRecord houseRecord: recordHouse){
            if (houseRecord.getHouseStatus() != null){
                houseStatusMap.put(houseRecord.getHouseCode(),houseRecord.getHouseStatus());
            }
        }

        List<String> contractHouseIds = ownerEntityManager.createQuery("select contractOwner.houseCode from ContractOwner contractOwner where contractOwner.ownerBusiness.status = 'RUNNING' and contractOwner.houseCode in (:houseCodes)", String.class)
                .setParameter("houseCodes", houseMap.keySet()).getResultList();



        try {
            JSONArray buildGridMapJsonArray = new JSONArray();

            for (BuildGridMap gridMap : houseEntityManager.createQuery("select buildGridMap from BuildGridMap buildGridMap where buildGridMap.build.id =:buildId order by buildGridMap.order", BuildGridMap.class)
                    .setParameter("buildId", buildCode).getResultList()) {


                buildGridMapJsonArray.put(getBuildMapJsonObject(houseMap, houseStatusMap, contractHouseIds,lockedHouse, gridMap));
            }

            if (!houseMap.isEmpty()){

                buildGridMapJsonArray.put(getBuildMapJsonObject(houseMap, houseStatusMap, contractHouseIds, lockedHouse, BuildHome.genIdleHouseGridMap(houseMap.values())));


            }

            return buildGridMapJsonArray.toString();

        } catch (JSONException e) {
            Logging.getLog(getClass()).debug(e.getMessage(), e);
            return null;
        }

    }

    private JSONObject getBuildMapJsonObject(Map<String, House> houseMap, Map<String, HouseStatus> houseStatusMap, List<String> contractHouseIds, List<String> lockedHouses, BuildGridMap gridMap) throws JSONException {
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
            for (GridBlock block : gridRow.getGridBlockList()) {
                bolckJsonArray.put(genBlockJsonObj(block, houseMap,houseStatusMap,contractHouseIds,lockedHouses));
            }
            rowJsonObj.put("blocks", bolckJsonArray);
            rowJsonArray.put(rowJsonObj);
        }
        result.put("rows",rowJsonArray);
        return result;
    }

    private JSONObject getHouseJsonObj(House house, HouseStatus status, boolean locked , boolean saled, boolean inBiz) throws JSONException{
        JSONObject houseJsonObj = getHouseJsonObj(house,status,locked,saled);
        houseJsonObj.put("inBiz", inBiz);
        return houseJsonObj;
    }

    private JSONObject getHouseJsonObj(House house, HouseStatus status, boolean locked , boolean saled) throws JSONException {
        JSONObject houseJsonObj = new JSONObject();


        if (status != null){
            houseJsonObj.put("status", status.name());
        }

        houseJsonObj.put("locked",locked);
        houseJsonObj.put("saled",saled);

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
        return houseJsonObj;

    }

    private JSONObject genBlockJsonObj(GridBlock block,
                                       Map<String, House> houseMap,
                                       Map<String,HouseStatus> statusMap, List<String> contractHouses,List<String> lockedHouses) {
        JSONObject blockJsonObj = new JSONObject();
        try {
            blockJsonObj.put("colspan", block.getColspan());
            blockJsonObj.put("rowspan", block.getRowspan());


            if ( block.getHouseCode() != null && !block.getHouseCode().trim().equals("") ){
                House house = houseMap.get(block.getHouseCode());
                if (house != null){
                    HouseStatus status = statusMap.get(block.getHouseCode());

                    blockJsonObj.put("house",getHouseJsonObj(house,status,
                            lockedHouses.contains(block.getHouseCode()),
                            contractHouses.contains(block.getHouseCode())));
                }
                houseMap.remove(block.getHouseCode());
            }
        } catch (JSONException e) {
            Logging.getLog(getClass()).error(e.getMessage(), e);

        }

        return blockJsonObj;

    }



    public static DeveloperServiceComponent instance()
    {
        return (DeveloperServiceComponent) Component.getInstance("developerServiceComponent", true,true);
    }


}
