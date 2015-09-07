package com.dgsoft.house.owner.ws;

import com.dgsoft.common.system.DictionaryWord;
import com.dgsoft.common.system.RunParam;
import com.dgsoft.common.system.business.BusinessInstance;
import com.dgsoft.house.model.AttachCorporation;
import com.dgsoft.house.model.DeveloperLogonKey;
import com.dgsoft.house.owner.model.BusinessBuild;
import com.dgsoft.house.owner.model.ProjectCard;
import com.longmai.uitl.Base64;
import org.jboss.seam.Component;
import org.jboss.seam.log.Logging;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Map;

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
                jsonObject.put("logonStatus", "KEY_NOT_FOUND");
            }else if(!CheckHashValues(key.getPassword(), random, password)){
                jsonObject.put("logonStatus", "PASSWORD_ERROR");
            }else if (!AttachCorporation.AttachCorpType.DEVELOPER.equals(key.getAttachEmployee().getAttachCorporation().getType())){
                jsonObject.put("logonStatus", "TYPE_ERROR");
            }else if (!key.getAttachEmployee().isEnable()) {
                jsonObject.put("logonStatus", "EMP_DISABLE");
            }else if (!key.getAttachEmployee().getAttachCorporation().isEnable()) {
                jsonObject.put("logonStatus", "CORP_DISABLE");
            }else if (key.getAttachEmployee().getAttachCorporation().isOutTime()) {
                jsonObject.put("logonStatus", "CORP_OUT_TIME");
            }else{
                jsonObject.put("logonStatus", "LOGON");

                jsonObject.put("employeeName", key.getAttachEmployee().getPersonName());
                jsonObject.put("corpName", key.getAttachEmployee().getAttachCorporation().getDeveloper().getName());

                jsonObject.put("orgName", RunParam.instance().getStringParamValue("SetupName"));

                JSONObject projectJsonObj =  new JSONObject();

                projectJsonObj.put("developerName",key.getProject().getDeveloperName());
                projectJsonObj.put("developerCode",key.getProject().getDeveloperCode());

                projectJsonObj.put("projectName",key.getProject().getProjectName());
                projectJsonObj.put("projectCode",key.getProject().getProjectCode());

                projectJsonObj.put("districtName",key.getProject().getDistrictName());
                projectJsonObj.put("districtCode",key.getProject().getDistrictCode());


                projectJsonObj.put("sectionName",key.getProject().getSectionName());
                projectJsonObj.put("sectionCode", key.getProject().getSectionCode());

                EntityManager ownerEntityManager = (EntityManager)Component.getInstance("ownerEntityManager", true, true);

                JSONArray cardArray = new JSONArray();
                for(ProjectCard card: ownerEntityManager.createQuery("select projectCard from ProjectCard projectCard left join fetch projectCard.makeCard left join fetch projectCard.projectSellInfo sellInfo left join fetch sellInfo.businessProject project where project.projectCode = :projectCode and project.ownerBusiness.status in (:allowStatus)", ProjectCard.class)
                        .setParameter("projectCode",key.getProject().getProjectCode())
                        .setParameter("allowStatus",EnumSet.of(BusinessInstance.BusinessStatus.COMPLETE, BusinessInstance.BusinessStatus.MODIFYING))
                        .getResultList()){

                    JSONObject cardJsonObj = new JSONObject();
                    cardJsonObj.put("cardType", card.getProjectSellInfo().getType().name());
                    cardJsonObj.put("cardNumber", card.getMakeCard().getNumber());
                    cardJsonObj.put("address", card.getProjectSellInfo().getBusinessProject().getAddress());


                    cardJsonObj.put("landCardType", DictionaryWord.instance().getWordValue(card.getProjectSellInfo().getLandCardType()));
                    cardJsonObj.put("landCardNumber", card.getProjectSellInfo().getLandCardNo());
                    cardJsonObj.put("landArea", card.getProjectSellInfo().getLandArea());
                    cardJsonObj.put("landUseType",DictionaryWord.instance().getWordValue(card.getProjectSellInfo().getLandUseType()));

                    cardJsonObj.put("landEndUseTime",card.getProjectSellInfo().getEndUseTime().getTime());
                    cardJsonObj.put("createCardNumber",card.getProjectSellInfo().getCreateCardNumber());
                    cardJsonObj.put("createPrepareCardNumber", card.getProjectSellInfo().getCreatePrepareCardNumber());
                    cardJsonObj.put("name", card.getProjectSellInfo().getBusinessProject().getProjectName());

                    JSONArray buildJsonArray = new JSONArray();
                    for(BusinessBuild build: card.getProjectSellInfo().getBusinessProject().getBusinessBuilds()){
                        JSONObject buildJsonObj = new JSONObject();

                        buildJsonObj.put("buildName",build.getBuildName());
                        buildJsonObj.put("buildCode", build.getBuildCode());
                        buildJsonObj.put("mapNumber", build.getMapNumber());
                        buildJsonObj.put("blockNo", build.getBlockNo());
                        buildJsonObj.put("buildNo",build.getBlockNo());
                        buildJsonObj.put("completeYear", build.getCompleteYear());
                        buildJsonObj.put("streetCode", build.getSectionCode());
                        buildJsonObj.put("doorNo", build.getDoorNo());
                        buildJsonObj.put("unintCount", build.getUnintCount());
                        buildJsonObj.put("buildDevNumber", build.getBuildDevNumber());
                        buildJsonObj.put("buildType", DictionaryWord.instance().getWordValue(build.getBuildType()));
                        buildJsonObj.put("structure", DictionaryWord.instance().getWordValue(build.getStructure()));
                        buildJsonObj.put("upFloorCount", build.getUpFloorCount());
                        buildJsonObj.put("downFloorCount", build.getDownFloorCount());
                        buildJsonObj.put("mapTime",build.getMapTime().getTime());

                        buildJsonArray.put(buildJsonObj);
                    }

                    cardJsonObj.put("saleBuilds",buildJsonArray);
                    cardArray.put(cardJsonObj);
                }


                projectJsonObj.put("saleCards", cardArray);
                jsonObject.put("project", projectJsonObj);
                    //  jsonObject.put("corp",)


            }
            return jsonObject.toString();

        } catch (JSONException e) {
            Logging.getLog(getClass()).error(e.getMessage(), e);
            return null;
        } catch (NoSuchAlgorithmException e2) {
            Logging.getLog(getClass()).error(e2.getMessage(),e2);
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

}
