package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.common.system.action.BusinessDefineHome;
import com.dgsoft.common.system.business.BusinessInstance;
import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.SubStatus;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import java.util.Arrays;
import java.util.List;

/**
 * Created by wxy on 2015-09-02.
 * 将选中的业务，业务状态改成已解除
 */

@Name("selectBizCompleteCancel")
public class SelectBizCompleteCancel implements TaskCompleteSubscribeComponent {
    @In
    private OwnerBusinessHome ownerBusinessHome;

    @In
    private BusinessDefineHome businessDefineHome;

    @Override
    public void valid() {

    }

    @Override
    public boolean isPass() {
        return true;
    }

    @Override
    public void complete() {
        if (ownerBusinessHome.getInstance().getSelectBusiness()!=null){


            List<String> allowDefineIds = Arrays.asList(businessDefineHome.getInstance().getPickBusinessDefineId().split(","));

            if (allowDefineIds.contains(ownerBusinessHome.getInstance().getSelectBusiness().getDefineId())){
                ownerBusinessHome.getInstance().getSelectBusiness().setStatus(BusinessInstance.BusinessStatus.COMPLETE_CANCEL);
                for(SubStatus subStatus:ownerBusinessHome.getInstance().getSelectBusiness().getSubStatuses()){
                    subStatus.setStatus(BusinessInstance.BusinessStatus.COMPLETE_CANCEL);
                }
            }else{
                BusinessInstance.BusinessStatus masterStatus = BusinessInstance.BusinessStatus.COMPLETE_CANCEL;
                for(SubStatus subStatus:ownerBusinessHome.getInstance().getSelectBusiness().getSubStatuses()){
                    if (allowDefineIds.contains(subStatus.getDefineId())){
                        subStatus.setStatus(BusinessInstance.BusinessStatus.COMPLETE_CANCEL);
                    }else if (!BusinessInstance.BusinessStatus.COMPLETE_CANCEL.equals(subStatus.getStatus())){
                        masterStatus = BusinessInstance.BusinessStatus.COMPLETE;
                    }
                }
                ownerBusinessHome.getInstance().getSelectBusiness().setStatus(masterStatus);
            }


        }
    }
}
