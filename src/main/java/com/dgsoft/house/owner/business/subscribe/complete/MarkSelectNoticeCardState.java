package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.HouseBusiness;
import com.dgsoft.house.owner.model.MakeCard;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2015-09-11.
 */
@Name("markSelectNoticeCardState")
public class MarkSelectNoticeCardState implements TaskCompleteSubscribeComponent {

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
            for(HouseBusiness houseBusiness:ownerBusinessHome.getInstance().getSelectBusiness().getHouseBusinesses()){
                for (MakeCard makeCard:houseBusiness.getAfterBusinessHouse().getOtherPowerCards()){
                    if (makeCard.getType().equals(MakeCard.CardType.NOTICE)){
                        makeCard.setEnable(false);
                    }
                }
            }
        }

    }
}
