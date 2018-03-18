package com.dgsoft.house.owner.action;

import com.dgsoft.house.owner.model.HouseSourceBusiness;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.core.Events;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cooper on 10/03/2018.
 */
@Name("houseSourceBusinessCancel")
public class HouseSourceBusinessCancel {

    @In
    private OwnerBusinessHome ownerBusinessHome;

    public void notiify(String description){
        List<NotifySend.Messages> messagesList = new ArrayList<NotifySend.Messages>();
        for (HouseSourceBusiness hsb : ownerBusinessHome.getInstance().getHouseSourceBusinesses()){
            messagesList.add(new NotifySend.Messages(hsb.getHouse().getId(), NotifySend.MessageType.ABORT,
                    ownerBusinessHome.getInstance().getDefineId(),ownerBusinessHome.getInstance().getId(),
                    description));
        }

        Events.instance().raiseAsynchronousEvent("cc.copersoft.houseStatusChangeEven",messagesList);
    }

}
