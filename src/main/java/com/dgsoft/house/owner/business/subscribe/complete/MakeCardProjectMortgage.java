package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.common.system.business.TaskSubscribeComponent;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.action.OwnerNumberBuilder;
import com.dgsoft.house.owner.model.MakeCard;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by Administrator on 15-5-28.
 */
@Name("makeCardProjectMortgage")
public class MakeCardProjectMortgage implements TaskCompleteSubscribeComponent {
    @In
    private OwnerBusinessHome ownerBusinessHome;


    @In(create = true)
    private OwnerNumberBuilder ownerNumberBuilder;

    private MakeCard makeCard;


    public MakeCard getMakeCard() {
        return makeCard;
    }

    public void setMakeCard(MakeCard makeCard) {
        this.makeCard = makeCard;
    }





    @Override
    public TaskSubscribeComponent.ValidResult valid() {
        if(ownerBusinessHome.getNowFinancial()==null){
            return TaskSubscribeComponent.ValidResult.ERROR;

        }
        return TaskSubscribeComponent.ValidResult.SUCCESS;
    }

    @Override
    public void complete() {
        if (!ownerBusinessHome.getInstance().getMakeCards().isEmpty()){
            for (MakeCard m:ownerBusinessHome.getInstance().getMakeCards()){
                if(m.getType().equals(MakeCard.CardType.PROJECT_MORTGAGE)){
                    this.makeCard = m;
                    return;
                }
            }

        }else{
            makeCard = new MakeCard(MakeCard.CardType.PROJECT_MORTGAGE,false,ownerNumberBuilder.useDayNumber(MakeCard.CardType.PROJECT_MORTGAGE.name()));
        }
        makeCard.setOwnerBusiness(ownerBusinessHome.getInstance());
        ownerBusinessHome.getInstance().getMakeCards().add(makeCard);
    }
}