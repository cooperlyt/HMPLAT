package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.common.system.NumberBuilder;
import com.dgsoft.common.system.RunParam;
import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.MakeCard;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wxy on 2015-09-09.
 * 预售许可证编号
 */
@Name("makeCardProjectRship")
public class MakeCardProjectRship implements TaskCompleteSubscribeComponent {
    @In
    private OwnerBusinessHome ownerBusinessHome;

    @Override
    public void valid() {

    }
    @In(create = true)
    private NumberBuilder numberBuilder;

    @Override
    public boolean isPass() {
        return true;
    }

    @Override
    public void complete() {

        for (MakeCard m:ownerBusinessHome.getInstance().getMakeCards()){
            if(m.getType().equals(MakeCard.CardType.PROJECT_RSHIP)){
                return;
            }
        }
        SimpleDateFormat numberDateformat = new SimpleDateFormat("yyyyMMdd");
        String datePart = numberDateformat.format(new Date());
        Integer typeCard = RunParam.instance().getIntParamValue("CreateCradNumberType");
        String no;
        if (typeCard==2){
            no= datePart + Long.toString(numberBuilder.getNumber(MakeCard.CardType.PROJECT_RSHIP.name()));

        }else {
            no= datePart+'-'+Long.toString(numberBuilder.getNumber(MakeCard.CardType.PROJECT_RSHIP.name()));

        }




        MakeCard makeCard = new MakeCard(MakeCard.CardType.PROJECT_RSHIP, no);
        makeCard.setOwnerBusiness(ownerBusinessHome.getInstance());
        ownerBusinessHome.getInstance().getMakeCards().add(makeCard);
    }
}
