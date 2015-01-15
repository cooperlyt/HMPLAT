package com.dgsoft.house.owner.business;

import com.dgsoft.common.system.action.BusinessDefineHome;
import com.dgsoft.common.system.business.TaskOperComponent;
import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.OwnerBusiness;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jbpm.taskmgmt.exe.TaskInstance;

/**
 * Created by cooper on 1/14/15.
 */
@Name("houseOwnerTaskOper")
public class HouseOwnerTaskOper implements TaskOperComponent {


    @In(create = true)
    private OwnerEntityLoader ownerEntityLoader;

    @In(create = true)
    private OwnerBusinessHome ownerBusinessHome;


    @Override
    public String beginTask(TaskInstance taskInstance) {
        OwnerBusiness ob = ownerEntityLoader.getEntityManager().find(OwnerBusiness.class, taskInstance.getProcessInstance().getKey());

        ownerBusinessHome.setId(ob.getId());

        return "/business/houseOwner/TaskOperInfo.xhtml";
    }
}
