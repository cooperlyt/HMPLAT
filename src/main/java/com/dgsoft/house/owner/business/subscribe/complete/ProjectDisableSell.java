package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.house.HouseEntityLoader;
import com.dgsoft.house.model.Project;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessProject;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

@Name("projectDisableSell")
public class ProjectDisableSell implements TaskCompleteSubscribeComponent {

    @In
    private OwnerBusinessHome ownerBusinessHome;


    @In(create = true)
    private HouseEntityLoader houseEntityLoader;


    @Override
    public void valid() {

    }

    @Override
    public boolean isPass() {
        return true;
    }

    @Override
    public void complete() {
        BusinessProject bp = ownerBusinessHome.getInstance().getBusinessProject();
        if (bp != null){
            Project p = houseEntityLoader.getEntityManager().find(Project.class,bp.getProjectCode());
            if (p != null){
                p.setEnable(false);
                houseEntityLoader.getEntityManager().flush();
            }
        }
    }
}
