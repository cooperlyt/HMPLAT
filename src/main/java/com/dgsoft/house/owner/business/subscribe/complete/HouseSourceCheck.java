package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.house.owner.action.NotifySend;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.HouseSourceBusiness;
import com.dgsoft.house.owner.model.TaskOper;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.core.Events;

import java.util.ArrayList;
import java.util.List;

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


    @Override
    public void valid() {

    }

    @Override
    public boolean isPass() {
        return true;
    }

    @Override
    public void complete() {

        boolean checked = TaskOper.OperType.CHECK_ACCEPT.name().equals(transitionType);
        List<NotifySend.Messages> messagesList = new ArrayList<NotifySend.Messages>();
        for(HouseSourceBusiness hsb: ownerBusinessHome.getInstance().getHouseSourceBusinesses()){
            hsb.setMessage(transitionComments);
            hsb.setChecked(checked);
            messagesList.add(new NotifySend.Messages(hsb.getHouse().getHouseCode(),
                    checked? NotifySend.MessageType.COMPLETE: NotifySend.MessageType.ABORT,
                    ownerBusinessHome.getInstance().getDefineId(),ownerBusinessHome.getInstance().getId(),
                    transitionComments));
        }



        Events.instance().raiseAsynchronousEvent("cc.copersoft.houseStatusChangeEvent",messagesList);
    }
}
