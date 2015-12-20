package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.common.system.NumberBuilder;
import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.HouseBusiness;
import com.dgsoft.house.owner.model.MakeCard;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wxy on 2015-12-20.
 * 生成多房屋产权证号，商品房初始登记多房屋启动
 */
@Name("mulitMakeCardOwnerRship")
public class MulitMakeCardOwnerRship implements TaskCompleteSubscribeComponent {

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

        for(HouseBusiness houseBusiness:ownerBusinessHome.getInstance().getHouseBusinesses()){

            SimpleDateFormat numberDateformat = new SimpleDateFormat("yyyyMMdd");
            String datePart = numberDateformat.format(new Date());
            String no= datePart+'-'+ Long.toString(numberBuilder.getNumber(MakeCard.CardType.OWNER_RSHIP.name()));


            MakeCard makeCard = new MakeCard(MakeCard.CardType.OWNER_RSHIP,no);

            if (houseBusiness.getAfterBusinessHouse().getBusinessHouseOwner()!=null){
                houseBusiness.getAfterBusinessHouse().getBusinessHouseOwner().setMakeCard(makeCard);
            }

            makeCard.setOwnerBusiness(ownerBusinessHome.getInstance());
            ownerBusinessHome.getInstance().getMakeCards().add(makeCard);


        }

    }
}
