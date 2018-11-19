package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.common.SetLinkList;
import com.dgsoft.common.system.RunParam;
import com.dgsoft.house.HouseEntityLoader;
import com.dgsoft.house.SaleType;
import com.dgsoft.house.model.Developer;
import com.dgsoft.house.model.Project;
import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.ProjectLandEndTime;
import com.dgsoft.house.owner.model.ProjectSellInfo;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;

/**
 * Created by wxy on 2015-09-07.
 * 预售许可证信息
 */
@Name("projectSellInfoSubscribe")
public class ProjectSellInfoSubscribe extends OwnerEntityHome<ProjectSellInfo> {

    @In
    private OwnerBusinessHome ownerBusinessHome;

    @In(create = true)
    private HouseEntityLoader houseEntityLoader;


    @DataModel()
    private SetLinkList<ProjectLandEndTime> projectLandEndTimes;

    @DataModelSelection
    private ProjectLandEndTime selectLandEndTime;


    private boolean isType=true;

    public boolean isType() {
        return isType;
    }

    public void setType(boolean isType) {
        this.isType = isType;
    }

    public void addProjectLandEndTime(){
        projectLandEndTimes.add(new ProjectLandEndTime(getInstance()));
    }

    public void removeProjectLandEndTime(){
        projectLandEndTimes.remove(selectLandEndTime);
    }

    public boolean isEmptyLandEndTimes(){
        return projectLandEndTimes.isEmpty();
    }

    public void reloadDeveloperInfo(){
        Developer developer = houseEntityLoader.getEntityManager().find(Project.class, ownerBusinessHome.getInstance().getBusinessProject().getProjectCode()).getDeveloper();
        ownerBusinessHome.getInstance().getBusinessProject().setDeveloperName(developer.getName());
        ownerBusinessHome.getInstance().getBusinessProject().setDeveloperCode(developer.getId());
        if (developer.getAttachCorporation() != null){
            ownerBusinessHome.getInstance().getBusinessProject().setDeveloperAddress(developer.getAttachCorporation().getAddress());
            ownerBusinessHome.getInstance().getBusinessProject().setDeveloperLevel(developer.getAttachCorporation().getLevel());
            ownerBusinessHome.getInstance().getBusinessProject().setDeveloperProperty(developer.getAttachCorporation().getCompanyType());
        }
    }

    @Override
    public void create(){
        super.create();
        if(ownerBusinessHome.getInstance().getBusinessProject().getProjectSellInfo()!=null){
            if (ownerBusinessHome.getInstance().getBusinessProject().getProjectSellInfo().getId()==null){
                setInstance(ownerBusinessHome.getInstance().getBusinessProject().getProjectSellInfo());
            }else{
                setId(ownerBusinessHome.getInstance().getBusinessProject().getProjectSellInfo().getId());
            }

            if(isType){
                getInstance().setType(SaleType.MAP_SELL);
            }else {
                getInstance().setType(SaleType.NOW_SELL);
            }

            //发证机关
            getInstance().setGovName(RunParam.instance().getStringParamValue("SetupName"));

        }else{
            if(isType){
                getInstance().setType(SaleType.MAP_SELL);
            }else {
                getInstance().setType(SaleType.NOW_SELL);
            }
            //发证机关
            getInstance().setGovName(RunParam.instance().getStringParamValue("SetupName"));
            getInstance().setBusinessProject(ownerBusinessHome.getInstance().getBusinessProject());
            ownerBusinessHome.getInstance().getBusinessProject().setProjectSellInfo(getInstance());
        }

        projectLandEndTimes = new SetLinkList<ProjectLandEndTime>(getInstance().getProjectLandEndTimes());

    }

}
