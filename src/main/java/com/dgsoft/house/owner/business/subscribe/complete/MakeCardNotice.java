package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.common.system.business.TaskSubscribeComponent;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.action.OwnerNumberBuilder;
import com.dgsoft.house.owner.model.HouseBusiness;
import com.dgsoft.house.owner.model.MakeCard;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by Administrator on 15-5-28.
 */
@Name("makeCardNotice")
public class MakeCardNotice implements TaskCompleteSubscribeComponent {



    @In
    private OwnerBusinessHome ownerBusinessHome;


    @In(create = true)
    private OwnerNumberBuilder ownerNumberBuilder;


    @Override
    public void valid() {
    }

    @Override
    public boolean isPass() {
        return true;
    }

    @Override
    public void complete() {
        for (MakeCard m:ownerBusinessHome.getInstance().getMakeCards()){
            if(m.getType().equals(MakeCard.CardType.NOTICE)){
                return;
            }
        }

        MakeCard makeCard = new MakeCard(MakeCard.CardType.NOTICE,ownerNumberBuilder.useDayNumber(MakeCard.CardType.NOTICE.name()));
        makeCard.setOwnerBusiness(ownerBusinessHome.getInstance());
        ownerBusinessHome.getInstance().getMakeCards().add(makeCard);


        for (HouseBusiness houseBusiness:ownerBusinessHome.getInstance().getHouseBusinesses()){
            houseBusiness.getAfterBusinessHouse().getOtherPowerCards().add(makeCard);
            makeCard.getBusinessHouses().add(houseBusiness.getAfterBusinessHouse());
        }
    }
}
