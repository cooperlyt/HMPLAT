package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.common.system.NumberBuilder;
import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.MakeCard;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 15-5-28.
 */
@Name("makeCardNotice")
public class MakeCardNotice implements TaskCompleteSubscribeComponent {



    @In
    private OwnerBusinessHome ownerBusinessHome;


    @In(create = true)
    private NumberBuilder numberBuilder;



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
        SimpleDateFormat numberDateformat = new SimpleDateFormat("yyyyMMdd");
        String datePart = numberDateformat.format(new Date());
        String no= datePart+'-'+ Long.toString(numberBuilder.getNumber(MakeCard.CardType.NOTICE.name()));




        MakeCard makeCard = new MakeCard(MakeCard.CardType.NOTICE, no);

        if(ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getBusinessHouseOwner()!=null){
            ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getBusinessHouseOwner().setMakeCard(makeCard);

        }
        makeCard.setOwnerBusiness(ownerBusinessHome.getInstance());
        ownerBusinessHome.getInstance().getMakeCards().add(makeCard);



    }
}
