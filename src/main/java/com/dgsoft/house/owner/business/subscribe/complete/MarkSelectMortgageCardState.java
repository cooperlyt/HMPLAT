package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.HouseBusiness;
import com.dgsoft.house.owner.model.MakeCard;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Logging;

/**
 * Created by wxy on 2015-09-01.
 * 作废所选中业务的他项权利证
 */
@Name("markSelectMortgageCardState")
public class MarkSelectMortgageCardState implements TaskCompleteSubscribeComponent {

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
              //  Logging.getLog(getClass()).debug("ownerBusinessHome.getInstance().getSelectBusiness().getId---:"+ownerBusinessHome.getInstance().getSelectBusiness().getId());
                for (MakeCard makeCard:houseBusiness.getAfterBusinessHouse().getOtherPowerCards()){
               //     Logging.getLog(getClass()).debug("makeCard.getNumber():"+makeCard.getNumber());
                    if (makeCard.getType().equals(MakeCard.CardType.MORTGAGE_CARD)){
                        makeCard.setEnable(false);
                    }
                }
            }
        }



    }
}
