package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.CardInfo;
import com.dgsoft.house.owner.model.MakeCard;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2015-10-03.
 * 档案补录在建工程权证 缮证信息
 */
@Name("makeCardProjectMortgageRecordSubsrcibe")
public class MakeCardProjectMortgageRecordSubsrcibe extends OwnerEntityHome<MakeCard> {

    @In
    private OwnerBusinessHome ownerBusinessHome;

    @Override
    public MakeCard createInstance() {
        return new MakeCard(MakeCard.CardType.PROJECT_MORTGAGE);
    }

    @Override
    public void create() {
        super.create();
        for (MakeCard makeCard : ownerBusinessHome.getInstance().getMakeCards()) {
            if (makeCard.getType().equals(MakeCard.CardType.PROJECT_MORTGAGE)) {
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
