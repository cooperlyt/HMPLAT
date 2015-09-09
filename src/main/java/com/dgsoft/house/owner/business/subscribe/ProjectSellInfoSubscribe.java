package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.ProjectSellInfo;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2015-09-07.
 * 预售许可证信息
 */
@Name("projectSellInfoSubscribe")
public class ProjectSellInfoSubscribe extends OwnerEntityHome<ProjectSellInfo> {

    @In
    private OwnerBusinessHome ownerBusinessHome;



    @Override
    public void create(){
        super.create();
        if(ownerBusinessHome.getInstance().getBusinessProject().getProjectSellInfo()!=null){
            if (ownerBusinessHome.getInstance().getBusinessProject().getProjectSellInfo().getId()==null){
                setInstance(ownerBusinessHome.getInstance().getBusinessProject().getProjectSellInfo());
            }else{
                setId(ownerBusinessHome.getInstance().getBusinessProject().getProjectSellInfo().getId());
            }

        }else{
            getInstance().setBusinessProject(ownerBusinessHome.getInstance().getBusinessProject());
            ownerBusinessHome.getInstance().getBusinessProject().setProjectSellInfo(getInstance());

        }

    }









}
