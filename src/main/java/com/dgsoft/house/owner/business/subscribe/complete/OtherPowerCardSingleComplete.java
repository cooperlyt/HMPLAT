package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.common.system.business.TaskSubscribeComponent;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.Financial;
import com.dgsoft.house.owner.model.MakeCard;
import com.dgsoft.house.owner.model.OtherPowerCard;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Logging;

import java.util.EnumSet;

/**
 * Created by Administrator on 15-6-4.
 */
@Name("otherPowerCardSingleComplete")
public class OtherPowerCardSingleComplete implements TaskCompleteSubscribeComponent {


    @In
    private OwnerBusinessHome ownerBusinessHome;



    @Override
    public TaskSubscribeComponent.ValidResult valid() {

        return TaskSubscribeComponent.ValidResult.SUCCESS;
    }

    @Override
    public void complete() {

        for(MakeCard makeCard: ownerBusinessHome.getMakeCardByType(EnumSet.of(MakeCard.CardType.MORTGAGE_CARD,MakeCard.CardType.NOTICE,MakeCard.CardType.PROJECT_MORTGAGE))){
           Financial financial = ownerBusinessHome.getInstance().getMortgaegeRegistes().iterator().next().getFinancial();

                OtherPowerCard otherPowerCard = makeCard.getCardInfo().getOtherPowerCard();
                if ( otherPowerCard == null) {
                    otherPowerCard = new OtherPowerCard(financial.getName(),
                            financial.getCode(), financial.getPhone(), financial.getFinancialType(), financial.getCredentialsType());
                } else {
                    otherPowerCard.setFinancialName(financial.getName());
                    otherPowerCard.setFinancialCode(financial.getCode());
                    otherPowerCard.setFinancialPhone(financial.getPhone());
                    otherPowerCard.setCredentialsType(financial.getCredentialsType());
                    otherPowerCard.setFinancialType(financial.getFinancialType());

                }
                otherPowerCard.setCardInfo(makeCard.getCardInfo());
                makeCard.getCardInfo().setOtherPowerCard(otherPowerCard);


        }

    }
}



