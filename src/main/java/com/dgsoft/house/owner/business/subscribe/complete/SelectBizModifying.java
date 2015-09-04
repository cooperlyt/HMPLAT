package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.common.system.business.BusinessInstance;
import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2015-09-03.
 * 将选中业务改成业务中，不允许同一个Selectbiz 被俩个业务选中同时选中
 */
@Name("selectBizModifying")
public class SelectBizModifying implements TaskCompleteSubscribeComponent {

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
        if (ownerBusinessHome.getInstance().getSelectBusiness()!=null){
            ownerBusinessHome.getInstance().getSelectBusiness().setStatus(BusinessInstance.BusinessStatus.MODIFYING);
        }

    }
}
