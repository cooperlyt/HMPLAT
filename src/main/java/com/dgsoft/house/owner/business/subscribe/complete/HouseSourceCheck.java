package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.TaskOper;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.core.Events;

/**
 * Created by cooper on 07/08/2017.
 */
@Name("houseSourceCheck")
public class HouseSourceCheck implements TaskCompleteSubscribeComponent {

    @In
    private OwnerBusinessHome ownerBusinessHome;

    @In(scope = ScopeType.BUSINESS_PROCESS)
    private String transitionType;

    @In(scope = ScopeType.BUSINESS_PROCESS)
    private String transitionComments;

    @In
    private Events events;

    @Override
    public void valid() {

    }

    @Override
    public boolean isPass() {
        return true;
    }

    @Override
    public void complete() {

        ownerBusinessHome.getSingleHouseSource().setMessage(transitionComments);
        ownerBusinessHome.getSingleHouseSource().setChecked(TaskOper.OperType.CHECK_ACCEPT.name().equals(transitionType));

        events.raiseTransactionSuccessEvent("cc.coopersoft.house.HouseStateChanged", ownerBusinessHome.getSingleHouseSource().getHouse().getHouseCode());

            //TODO 异步事件 events
    }
}
