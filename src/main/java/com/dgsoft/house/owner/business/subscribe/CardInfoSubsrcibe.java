package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.common.system.AuthenticationInfo;
import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.CardInfo;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by Administrator on 15-5-29.
 */
@Name("cardInfoSubsrcibe")
public class CardInfoSubsrcibe extends OwnerEntityHome<CardInfo> {

    @In
    private OwnerBusinessHome ownerBusinessHome;

    @In
    private AuthenticationInfo authInfo;
    @Override
    public void create(){
        super.create();
        if(!ownerBusinessHome.getInstance().getMakeCards().isEmpty() &&
                ownerBusinessHome.getInstance().getMakeCards().iterator().next().getCardInfo()!=null){
            setId(ownerBusinessHome.getInstance().getMakeCards().iterator().next().getCardInfo().getId());
        }else{

               getInstance().setMakeEmpCode(authInfo.getLoginEmployee().getId());
               getInstance().setMakeEmpName(authInfo.getLoginEmployee().getPersonName());
               getInstance().setBusinessHouseOwner(ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getBusinessHouseOwner());
               ownerBusinessHome.getInstance().getMakeCards().iterator().next().setCardInfo(getInstance());

               getInstance().setMakeCard(ownerBusinessHome.getInstance().getMakeCards().iterator().next());




        }
    }


}
