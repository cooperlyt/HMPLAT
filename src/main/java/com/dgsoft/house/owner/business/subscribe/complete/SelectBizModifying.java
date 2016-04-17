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

import static java.util.Arrays.asList;

/**
 * Created by wxy on 2015-09-03.
 * 将选中业务改成业务中，不允许同一个Selectbiz 被俩个业务选中同时选中
 */
@Name("selectBizModifying")
public class SelectBizModifying implements TaskCompleteSubscribeComponent {

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

            List<String> allDefineIds = Arrays.asList(businessDefineHome.getInstance().getPickBusinessDefineId().split(","));

            ownerBusinessHome.getInstance().getSelectBusiness().setStatus(BusinessInstance.BusinessStatus.MODIFYING);
            for(SubStatus subStatus: ownerBusinessHome.getInstance().getSelectBusiness().getSubStatuses()){
                if (allDefineIds.contains(subStatus))
                    subStatus.setStatus(BusinessInstance.BusinessStatus.MODIFYING);
            }
        }

    }
}
