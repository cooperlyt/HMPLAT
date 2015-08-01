package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.common.system.business.BusinessInstance;
import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.HouseBusiness;
import com.dgsoft.house.owner.model.OwnerBusiness;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import java.util.Set;

/**
 * Created by cooper on 8/1/15.
 */
@Name("businessCompleteStatus")
public class BusinessCompleteStatus implements TaskCompleteSubscribeComponent {


    @In
    private OwnerBusinessHome ownerBusinessHome;

    @Override
    public void valid() {
    }

    @Override
    public boolean isPass() {
        return true;
    }

    @Override
    public void complete() {

        if(ownerBusinessHome.getInstance().getType().equals(BusinessInstance.BusinessType.NORMAL_BIZ)){
            if (ownerBusinessHome.getInstance().getSelectBusiness() != null){
                ownerBusinessHome.getInstance().getSelectBusiness().setStatus(BusinessInstance.BusinessStatus.COMPLETE_CANCEL);
            }
        }else{
            ownerBusinessHome.getInstance().getSelectBusiness().setStatus(BusinessInstance.BusinessStatus.CANCEL);
        }


        ownerBusinessHome.getInstance().setStatus(BusinessInstance.BusinessStatus.COMPLETE);
    }
}
