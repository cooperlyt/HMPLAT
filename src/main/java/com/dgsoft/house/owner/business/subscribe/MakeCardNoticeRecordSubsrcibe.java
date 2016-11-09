package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.CardInfo;
import com.dgsoft.house.owner.model.MakeCard;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2015-10-03.
 * 预告登记证档案补录信息
 */
@Name("makeCardNoticeRecordSubsrcibe")
public class MakeCardNoticeRecordSubsrcibe  extends OwnerEntityHome<MakeCard> {

    @In
    private OwnerBusinessHome ownerBusinessHome;

    @Override
    public MakeCard createInstance() {
        return new MakeCard(MakeCard.CardType.NOTICE);
    }

    @Override
    public void create() {
        super.create();
        for (MakeCard makeCard : ownerBusinessHome.getInstance().getMakeCards()) {
            if (makeCard.getType().equals(MakeCard.CardType.NOTICE)) {
                setId(makeCard.getId());
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
