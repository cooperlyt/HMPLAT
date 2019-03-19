package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.common.system.business.BusinessInstance;
import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.business.CheckTaskOperation;
import com.dgsoft.house.owner.business.OwnerTaskHandle;
import com.dgsoft.house.owner.model.SubStatus;
import com.dgsoft.house.owner.model.TaskOper;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.log.Logging;

/**
 * Created by wxy on 2019-03-19.
 */
@Name("xfStatusComplete")
public class XfStatusComplete implements TaskCompleteSubscribeComponent {

    @In(required = false)
    private OwnerTaskHandle ownerTaskHandle;

    @In(required = false)
    private CheckTaskOperation checkTaskOperation;

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

        String transitionType;
        if (ownerTaskHandle !=null){
            transitionType = ownerTaskHandle.getTransitionType();
        }else if (checkTaskOperation != null){
            transitionType = checkTaskOperation.getTransitionType();
        }else{
            throw new IllegalStateException("XfStatusComplete ownerTaskHandle is error");
        }

            Logging.getLog(getClass()).debug("ownerTaskHandle--" + transitionType);
            if (TaskOper.OperType.CHECK_BACK.name().equals(transitionType) && ownerBusinessHome.getInstance().getSource().equals(BusinessInstance.BusinessSource.BIZ_OUTSIDE)) {
                ownerBusinessHome.getInstance().setStatus(BusinessInstance.BusinessStatus.CANCEL);
                for (SubStatus subStatus : ownerBusinessHome.getInstance().getSubStatuses()) {
                    subStatus.setStatus(BusinessInstance.BusinessStatus.CANCEL);
                }
            } else if (TaskOper.OperType.CHECK_ACCEPT.name().equals(transitionType)) {

                if (!ownerBusinessHome.getInstance().getType().equals(BusinessInstance.BusinessType.NORMAL_BIZ)) {
                    ownerBusinessHome.getInstance().getSelectBusiness().setStatus(BusinessInstance.BusinessStatus.CANCEL);
                    for (SubStatus subStatus : ownerBusinessHome.getInstance().getSelectBusiness().getSubStatuses()) {
                        subStatus.setStatus(BusinessInstance.BusinessStatus.CANCEL);
                    }
                }
                ownerBusinessHome.getInstance().setStatus(BusinessInstance.BusinessStatus.COMPLETE);
                for (SubStatus subStatus : ownerBusinessHome.getInstance().getSubStatuses()) {
                    subStatus.setStatus(BusinessInstance.BusinessStatus.COMPLETE);
                }
            }


    }
}
