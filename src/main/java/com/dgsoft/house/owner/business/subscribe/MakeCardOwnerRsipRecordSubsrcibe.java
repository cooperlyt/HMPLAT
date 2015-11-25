package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.CardInfo;
import com.dgsoft.house.owner.model.HouseBusiness;
import com.dgsoft.house.owner.model.MakeCard;
import com.dgsoft.house.owner.model.Reason;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2015-09-18.
 * 档案补录所有权证 缮证信息
 */
@Name("makeCardOwnerRsipRecordSubsrcibe")
public class MakeCardOwnerRsipRecordSubsrcibe extends OwnerEntityHome<MakeCard> {

    @In
    private OwnerBusinessHome ownerBusinessHome;

    @Override
    public MakeCard createInstance() {
        return new MakeCard(MakeCard.CardType.OWNER_RSHIP);
    }

    @Override
    public void create() {
        super.create();
        for (MakeCard makeCard : ownerBusinessHome.getInstance().getMakeCards()) {
            if (makeCard.getType().equals(MakeCard.CardType.OWNER_RSHIP)) {
                setId(ownerBusinessHome.getInstance().getMakeCards().iterator().next().getId());
                return;
            }
        }
        getInstance().setOwnerBusiness(ownerBusinessHome.getInstance());
        ownerBusinessHome.getInstance().getMakeCards().add(getInstance());
        getInstance().setEnable(true);
        if(ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getBusinessHouseOwner()!=null){
            ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getBusinessHouseOwner().setMakeCard(getInstance());

        }

        CardInfo cardInfo = new CardInfo();
        getInstance().setCardInfo(cardInfo);
        cardInfo.setMakeCard(getInstance());





    }

}
