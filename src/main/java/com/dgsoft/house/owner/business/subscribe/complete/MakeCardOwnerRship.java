package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.common.system.NumberBuilder;
import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.common.system.business.TaskSubscribeComponent;
import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.action.OwnerNumberBuilder;
import com.dgsoft.house.owner.model.BusinessHouse;
import com.dgsoft.house.owner.model.HouseBusiness;
import com.dgsoft.house.owner.model.MakeCard;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Logging;
import sun.rmi.runtime.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 15-5-27.
 */
@Name("makeCardOwnerRship")
public class MakeCardOwnerRship implements TaskCompleteSubscribeComponent {

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
           if(m.getType().equals(MakeCard.CardType.OWNER_RSHIP)){
             return;
           }
        }
        SimpleDateFormat numberDateformat = new SimpleDateFormat("yyyyMMdd");
        String datePart = numberDateformat.format(new Date());
        String no= datePart+'-'+ Long.toString(numberBuilder.getNumber(MakeCard.CardType.OWNER_RSHIP.name()));


        MakeCard makeCard = new MakeCard(MakeCard.CardType.OWNER_RSHIP,no);

        if(ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getBusinessHouseOwner()!=null){
            ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getBusinessHouseOwner().setMakeCard(makeCard);

        }
        makeCard.setOwnerBusiness(ownerBusinessHome.getInstance());
        ownerBusinessHome.getInstance().getMakeCards().add(makeCard);



    }
}
