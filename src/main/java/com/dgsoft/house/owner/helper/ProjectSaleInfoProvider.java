package com.dgsoft.house.owner.helper;

import com.dgsoft.common.system.DictionaryWord;
import com.dgsoft.common.system.RunParam;
import com.dgsoft.house.HouseEntityLoader;
import com.dgsoft.house.model.Project;
import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.model.BusinessBuild;
import com.dgsoft.house.owner.model.BusinessProject;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.web.RequestParameter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by cooper on 12/28/15.
 */
@Name("projectSaleInfoProvider")
public class ProjectSaleInfoProvider implements RestDataProvider {

    @In(create = true)
    private OwnerEntityLoader ownerEntityLoader;

    @In(create = true)
    private HouseEntityLoader houseEntityLoader;

    @In(create = true)
    private DictionaryWord dictionary;

    @RequestParameter
    private String id;

    @Override
    public JSONObject getJsonData() {

        List<BusinessProject> projects = ownerEntityLoader.getEntityManager().createQuery("select p from BusinessProject p left join fetch p.projectSellInfo ps left join fetch ps.projectCard pd left join fetch pd.makeCard left join fetch p.ownerBusiness where p.projectCode = :projectCode and p.ownerBusiness.status in ('COMPLETE','MODIFYING') order by p.ownerBusiness.regTime desc ", BusinessProject.class)
                .setParameter("projectCode", id).getResultList();


        JSONObject result = new JSONObject();
        String projectName = null;
        Project mapProject = houseEntityLoader.getEntityManager().find(Project.class,id);
        if (mapProject != null){
            projectName = mapProject.getName();
        }
        JSONArray projectArray = new JSONArray();
        try {
            for (BusinessProject project : projects) {
                JSONObject projectObj = new JSONObject();
                if (projectName == null){
                    projectName = project.getProjectName();
                }
                projectObj.put("regTime",project.getOwnerBusiness().getRegTime().getTime());
                projectObj.put("cardNumber",project.getProjectSellInfo().getProjectCard().getMakeCard().getNumber());
                projectObj.put("cardId",project.getId());
                projectObj.put("name", project.getName());
                projectObj.put("orgName", RunParam.instance().getStringParamValue("SetupName"));
                projectObj.put("developerName",project.getDeveloperName());
                projectObj.put("developerCode",project.getDeveloperCode());
                projectObj.put("houseCount",project.getProjectSellInfo().getHouseCount());
                projectObj.put("buildCount",project.getProjectSellInfo().getBuildCount());
                projectObj.put("useType",project.getProjectSellInfo().getUseType());
                projectObj.put("type",project.getProjectSellInfo().getType().name());
                projectObj.put("landCardNumber",project.getProjectSellInfo().getLandCardNo());
                projectObj.put("landNumber",project.getProjectSellInfo().getNumber());
                projectObj.put("landProperty",project.getProjectSellInfo().getLandProperty());
                projectObj.put("landBeginTime",project.getProjectSellInfo().getBeginUseTime().getTime());
                projectObj.put("landEndTime",project.getProjectSellInfo().getEndUseTime().getTime());
                projectObj.put("landArea",project.getProjectSellInfo().getLandArea().toString());
                projectObj.put("landCardType",dictionary.getWordValue(project.getProjectSellInfo().getLandCardType()));
                projectObj.put("landGetMode",dictionary.getWordValue(project.getProjectSellInfo().getLandGetMode()));
                projectObj.put("landUseType",project.getProjectSellInfo().getLandUseType());
                projectObj.put("landAddress",project.getProjectSellInfo().getLandAddress());
                projectObj.put("createCardCode",project.getProjectSellInfo().getCreateCardNumber());
                projectObj.put("createPrepareCardCode",project.getProjectSellInfo().getCreatePrepareCardNumber());
                JSONArray buildArray = new JSONArray();
                for (BusinessBuild build: project.getBusinessBuildList()){
                    JSONObject buildObj = new JSONObject();
                    buildObj.put("id",build.getBuildCode());
                    buildObj.put("name",build.getBuildName());
                    buildArray.put(buildObj);
                }
                projectObj.put("builds",buildArray);
                projectArray.put(projectObj);
            }
            result.put("projects",projectArray);
            result.put("name",projectName);

            return result;

        } catch (JSONException e) {
            return null;
        }
    }


}
