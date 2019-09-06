package com.dgsoft.house.owner.business;

import com.dgsoft.common.BatchOperData;
import com.dgsoft.common.system.AuthenticationInfo;
import com.dgsoft.common.system.action.BusinessDefineHome;
import com.dgsoft.common.system.business.BusinessDataValid;
import com.dgsoft.house.HouseEntityLoader;
import com.dgsoft.house.action.ProjectHome;
import com.dgsoft.house.model.Build;
import com.dgsoft.house.model.MoneyBank;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.*;
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



    public String getSelectBuildId() {
        return selectBuildId;
    }

    public void setSelectBuildId(String selectBuildId) {
        this.selectBuildId = selectBuildId;
    }

    private String selectBuildId;

    public BuildPoint getBuildPoint() {
        return buildPoint;
    }

    public void setBuildPoint(BuildPoint buildPoint) {
        this.buildPoint = buildPoint;
    }

    private BuildPoint buildPoint;


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
        projects.clear();
       List<MoneySafe> moneySafes = ownerBusinessHome.getEntityManager().createQuery("select moneySafe from MoneySafe moneySafe where moneySafe.businessProject.ownerBusiness.status = 'COMPLETE' and moneySafe.businessProject.ownerBusiness.type <> 'CANCEL_BIZ' and moneySafe.businessProject.projectCode =:projectCode", MoneySafe.class)
                .setParameter("projectCode", projectHome.getInstance().getProjectCode()).getResultList();

        for (MoneySafe moneySafe:moneySafes){
            projects.add(moneySafe.getBusinessProject());
        }


    }


    public BusinessBuild getSelectBusinessBuild() {
        return selectBusinessBuild;
    }

    public void setSelectBusinessBuild(BusinessBuild selectBusinessBuild) {
        this.selectBusinessBuild = selectBusinessBuild;
    }

    private BusinessBuild selectBusinessBuild;

    public List<HouseBuild> getBusinessModifyBuilds() {
        return businessModifyBuilds;
    }

    private List<HouseBuild> businessModifyBuilds = new ArrayList<HouseBuild>(0);

    public String findProjectCard() {
        buildPoint = null;
        selectBusinessBuild = ownerBusinessHome.getEntityManager().find(BusinessBuild.class,selectBuildId);
        businessModifyBuilds.clear();
            List<BusinessDataValid.ValidResult> validResults = new ArrayList<BusinessDataValid.ValidResult>();
            for(BusinessDataValid valid: businessDefineHome.getCreateDataValidComponents()){
                try {
                    BusinessDataValid.ValidResult validResult = valid.valid(selectBusinessBuild);
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
            businessModifyBuilds.add(new HouseBuild(selectBusinessBuild,validResults));
        return "modifyBuild";
    }

    public HouseBuild getSelectHouseBuild() {
        if (!businessModifyBuilds.isEmpty()){
            return businessModifyBuilds.get(0);
        }else {
            return null;
        }

    }

    private HouseBuild selectHouseBuild;


    public String businessSelected() {
        ownerBusinessHome.getInstance().setSelectBusiness(ownerBusinessHome.getEntityManager().find(OwnerBusiness.class, selectBusinessId));
        ProjectCheck projectCheck = new ProjectCheck();
        projectCheck.setBuildCode(selectBusinessBuild.getBuildCode());
        projectCheck.setPayPercent(buildPoint.getPercent());
        projectCheck.setPoint(buildPoint.getId());
        projectCheck.setPointName(buildPoint.getName());
        projectCheck.setOwnerBusiness(ownerBusinessHome.getInstance());
        ownerBusinessHome.getInstance().getProjectChecks().add(projectCheck);
        return ownerBusinessStart.dataSelected();
    }

}
