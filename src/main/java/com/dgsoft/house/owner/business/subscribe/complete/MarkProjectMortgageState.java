package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.HouseBusiness;
import com.dgsoft.house.owner.model.MakeCard;
import com.dgsoft.house.owner.model.OwnerBusiness;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2015-08-28.
 * 将以前的在建工程抵押证明作废
 */
@Name("markProjectMortgageState")
public class MarkProjectMortgageState implements TaskCompleteSubscribeComponent {

    @In
    private OwnerBusinessHome ownerBusinessHome;



    @Override
    public void valid() {

    }

    @Override
    public boolean isPass() {
        return false;
    }

    @Override
    public void complete() {
        for (HouseBusiness houseBusiness:ownerBusinessHome.getInstance().getHouseBusinesses()){
           for(MakeCard makeCard:houseBusiness.getStartBusinessHouse().getOtherPowerCards()){
               if (makeCard.equals(MakeCard.CardType.PROJECT_MORTGAGE)){
                   makeCard.setEnable(false);
               }
           }
        }
    }


}
