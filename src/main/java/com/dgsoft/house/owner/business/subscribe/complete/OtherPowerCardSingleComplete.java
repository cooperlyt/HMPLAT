package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.common.system.business.TaskSubscribeComponent;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.Financial;
import com.dgsoft.house.owner.model.OtherPowerCard;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by Administrator on 15-6-4.
 */
@Name("otherPowerCardSingleComplete")
public class OtherPowerCardSingleComplete implements TaskCompleteSubscribeComponent {


    @In
    private OwnerBusinessHome ownerBusinessHome;



    private OtherPowerCard otherPowerCard;


    public Financial getFinancial() {
        return financial;
    }

    public void setFinancial(Financial financial) {
        this.financial = financial;
    }

    private Financial financial;



    public OtherPowerCard getOtherPowerCard() {
        return otherPowerCard;
    }

    public void setOtherPowerCard(OtherPowerCard otherPowerCard) {
        this.otherPowerCard = otherPowerCard;
    }

    @Override
    public TaskSubscribeComponent.ValidResult valid() {
        if (ownerBusinessHome.getInstance().getFinancials().isEmpty()
                && ownerBusinessHome.getInstance().getMakeCards().isEmpty()){

            return TaskSubscribeComponent.ValidResult.ERROR;
        }
        return TaskSubscribeComponent.ValidResult.SUCCESS;
    }

    @Override
    public void complete() {
        if(!ownerBusinessHome.getInstance().getMakeCards().iterator().next().getOtherPowerCards().isEmpty()){
            otherPowerCard = ownerBusinessHome.getInstance().getMakeCards().iterator().next().getOtherPowerCards().iterator().next();
        }else{
            financial = ownerBusinessHome.getInstance().getFinancials().iterator().next();
            otherPowerCard = new OtherPowerCard(financial.getName(),
                    financial.getCode(),financial.getPhone(),financial.getFinancialType(),financial.getCredentialsType());
        }
        otherPowerCard.setMakeCardByCard(ownerBusinessHome.getInstance().getMakeCards().iterator().next());
        ownerBusinessHome.getInstance().getMakeCards().iterator().next().getOtherPowerCards().add(otherPowerCard);

    }
}
