package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.common.system.AuthenticationInfo;
import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.CardInfo;
import com.dgsoft.house.owner.model.MakeCard;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2016-04-23.
 * 他项权证附记
 */
@Name("cardInfoMortgageSubsrcibe")
public class CardInfoMortgageSubsrcibe extends OwnerEntityHome<CardInfo> {

    @In
    private OwnerBusinessHome ownerBusinessHome;

    @In
    private AuthenticationInfo authInfo;


    @Override
    public void create(){
        super.create();
        if (!ownerBusinessHome.getInstance().getMakeCards().isEmpty()){
            for (MakeCard makeCard:ownerBusinessHome.getInstance().getMakeCards()){
                if (makeCard.getType().equals(MakeCard.CardType.MORTGAGE_CARD)){
                    if (makeCard.getCardInfo()!=null){
                        setId(makeCard.getCardInfo().getId());
                    }else {
                        getInstance().setMakeEmpCode(authInfo.getLoginEmployee().getId());
                        getInstance().setMakeEmpName(authInfo.getLoginEmployee().getPersonName());
                        makeCard.setCardInfo(getInstance());
                        getInstance().setMakeCard(makeCard);
                    }

                }

            }
        }
    }
}
