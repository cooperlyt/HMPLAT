package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.CardInfo;
import com.dgsoft.house.owner.model.HouseBusiness;
import com.dgsoft.house.owner.model.MakeCard;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2015-09-25.
 * 档案补录他项权利证信息
 */
@Name("makeCardMortgageRecordSubsrcibe")
public class MakeCardMortgageRecordSubsrcibe extends OwnerEntityHome<MakeCard> {

    @In
    private OwnerBusinessHome ownerBusinessHome;

    @Override
    public MakeCard createInstance() {
        return new MakeCard(MakeCard.CardType.MORTGAGE_CARD);
    }





    @Override
    public void create() {
        super.create();
        for (MakeCard makeCard : ownerBusinessHome.getInstance().getMakeCards()) {
            if (makeCard.getType().equals(MakeCard.CardType.MORTGAGE_CARD)) {
                setId(ownerBusinessHome.getInstance().getMakeCards().iterator().next().getId());
                return;
            }
        }
        getInstance().setOwnerBusiness(ownerBusinessHome.getInstance());
        ownerBusinessHome.getInstance().getMakeCards().add(getInstance());
        getInstance().setEnable(true);
        if(ownerBusinessHome.getInstance().getMortgaegeRegiste()!=null && ownerBusinessHome.getInstance().getMortgaegeRegiste().getFinancial()!=null){
            ownerBusinessHome.getInstance().getMortgaegeRegiste().getFinancial().setMakeCard(getInstance());
            getInstance().setFinancial(ownerBusinessHome.getInstance().getMortgaegeRegiste().getFinancial());
        }

        CardInfo cardInfo = new CardInfo();
        getInstance().setCardInfo(cardInfo);
        cardInfo.setMakeCard(getInstance());





    }

}
