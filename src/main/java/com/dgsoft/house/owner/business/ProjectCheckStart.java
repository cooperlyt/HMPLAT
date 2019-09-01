package com.dgsoft.house.owner.business;

import com.dgsoft.common.BatchOperData;
import com.dgsoft.common.system.AuthenticationInfo;
import com.dgsoft.common.system.action.BusinessDefineHome;
import com.dgsoft.common.system.business.BusinessDataValid;
import com.dgsoft.house.HouseEntityLoader;
import com.dgsoft.house.action.ProjectHome;
import com.dgsoft.house.model.Build;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessBuild;
import com.dgsoft.house.owner.model.BusinessProject;
import com.dgsoft.house.owner.model.OwnerBusiness;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.log.Logging;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wxy on 2019-08-28.
 */
@Name("projectCheckStart")
@Scope(ScopeType.CONVERSATION)
public class ProjectCheckStart {

    @In(create = true)
    private ProjectHome projectHome;

    @In(create = true)
    private HouseEntityLoader houseEntityLoader;

    @In(create = true)
    private OwnerBusinessHome ownerBusinessHome;

    @In(create = true)
    private OwnerBusinessStart ownerBusinessStart;

    @In
    private AuthenticationInfo authInfo;

    @In(create = true)
    private FacesMessages facesMessages;

    @In
    private BusinessDefineHome businessDefineHome;

    private String selectBusinessId;

    public String getSelectBusinessId() {
        return selectBusinessId;
    }

    public void setSelectBusinessId(String selectBusinessId) {
        this.selectBusinessId = selectBusinessId;
    }

    private String selectProjectId;
    public String getSelectProjectId() {
        return selectProjectId;
    }

    public void setSelectProjectId(String selectProjectId) {
        this.selectProjectId = selectProjectId;
    }

    public String getSelectBuildId() {
        return selectBuildId;
    }

    public void setSelectBuildId(String selectBuildId) {
        this.selectBuildId = selectBuildId;
    }

    private String selectBuildId;


    public static class HouseBuild extends BatchOperData<BusinessBuild> {

        private List<BusinessDataValid.ValidResult> validInfoList;

        private BusinessDataValid.ValidResultLevel validLevel;

        public  HouseBuild(BusinessBuild data,List<BusinessDataValid.ValidResult> validInfo){
            super(data, false);
            this.validInfoList = validInfo;
            validLevel = BusinessDataValid.ValidResultLevel.SUCCESS;
            for(BusinessDataValid.ValidResult vr: validInfoList){
                if (vr.getResult().getPri() > validLevel.getPri()){
                    validLevel = vr.getResult();
                }
            }

        }
        public List<BusinessDataValid.ValidResult> getValidInfoList() {
            return validInfoList;
        }

        public BusinessDataValid.ValidResultLevel getValidLevel() {
            return validLevel;
        }

        public boolean isCanSelect(){
            return validLevel.getPri() <= BusinessDataValid.ValidResultLevel.WARN.getPri();
        }
    }

    private List<HouseBuild> builds = new ArrayList<HouseBuild>(0);

    private List<BusinessProject> projects = new ArrayList<BusinessProject>(0);

    public List<HouseBuild> getBuilds() {
        return builds;
    }

    public List<BusinessProject> getProjects() {
        return projects;
    }

    public void projectSelectedListener() {
        projects = ownerBusinessHome.getEntityManager().createQuery("select project from BusinessProject project where project.ownerBusiness.status = 'COMPLETE' and project.ownerBusiness.type <> 'CANCEL_BIZ' and project.projectCode =:projectCode", BusinessProject.class)
                .setParameter("projectCode", projectHome.getInstance().getProjectCode()).getResultList();

    }


    public BusinessProject getSelectBusinessProject() {
        return selectBusinessProject;
    }

    public void setSelectBusinessProject(BusinessProject selectBusinessProject) {
        this.selectBusinessProject = selectBusinessProject;
    }

    private BusinessProject selectBusinessProject;

    public List<HouseBuild> getBusinessModifyBuilds() {
        return businessModifyBuilds;
    }

    private List<HouseBuild> businessModifyBuilds = new ArrayList<HouseBuild>(0);

    public String findProjectCard() {


        selectBusinessProject = ownerBusinessHome.getEntityManager().find(BusinessProject.class,selectProjectId);
        businessModifyBuilds.clear();
        for (BusinessBuild businessBuild:selectBusinessProject.getBusinessBuildList()){

            List<BusinessDataValid.ValidResult> validResults = new ArrayList<BusinessDataValid.ValidResult>();
            for(BusinessDataValid valid: businessDefineHome.getCreateDataValidComponents()){
                try {
                    BusinessDataValid.ValidResult validResult = valid.valid(businessBuild);
                    if (validResult.getResult().getPri() > BusinessDataValid.ValidResultLevel.SUCCESS.getPri()) {
                        validResults.add(validResult);
                    }
                    if (validResult.getResult().equals(BusinessDataValid.ValidResultLevel.FATAL)){
                        throw new IllegalArgumentException(validResult.getMsgKey());
                    }
                    if (!validResult.getResult().equals(BusinessDataValid.ValidResultLevel.SUCCESS)){
                        facesMessages.addFromResourceBundle(validResult.getResult().getSeverity(),validResult.getMsgKey(),validResult.getParams());
                    }

                }catch (Exception e){
                    Logging.getLog(getClass()).error(e.getMessage(),e,"config error:" + valid.getClass().getSimpleName());
                    throw new IllegalArgumentException("config error:" + valid.getClass().getSimpleName());
                }
            }

            businessModifyBuilds.add(new HouseBuild(businessBuild,validResults));

        }
        return "modifyBuild";
    }

    public String businessSelected() {
        ownerBusinessHome.getInstance().setSelectBusiness(ownerBusinessHome.getEntityManager().find(OwnerBusiness.class, selectBusinessId));


        return ownerBusinessStart.dataSelected();
    }

}
