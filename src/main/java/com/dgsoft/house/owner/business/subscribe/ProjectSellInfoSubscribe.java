package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.common.Entry;
import com.dgsoft.common.SetLinkList;
import com.dgsoft.house.SaleType;
import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.ProjectLandEndTime;
import com.dgsoft.house.owner.model.ProjectSellInfo;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;
import org.jboss.seam.log.Logging;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wxy on 2015-09-07.
 * 预售许可证信息
 */
@Name("projectSellInfoSubscribe")
public class ProjectSellInfoSubscribe extends OwnerEntityHome<ProjectSellInfo> {

    @In
    private OwnerBusinessHome ownerBusinessHome;


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

        }else{
            if(isType){
                getInstance().setType(SaleType.MAP_SELL);
            }else {
                getInstance().setType(SaleType.NOW_SELL);
            }
            getInstance().setBusinessProject(ownerBusinessHome.getInstance().getBusinessProject());
            ownerBusinessHome.getInstance().getBusinessProject().setProjectSellInfo(getInstance());
        }

        projectLandEndTimes = new SetLinkList<ProjectLandEndTime>(getInstance().getProjectLandEndTimes());

    }









}
