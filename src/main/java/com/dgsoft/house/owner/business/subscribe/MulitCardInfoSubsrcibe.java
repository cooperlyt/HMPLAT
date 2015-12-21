package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.common.system.AuthenticationInfo;
import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.CardInfo;
import com.dgsoft.house.owner.model.HouseBusiness;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Logging;

import java.util.Date;

/**
 * Created by wxy on 2015-12-21.
 * 多房屋缮证信息,多房屋初始使用，只能是多房屋初始登记使用
 */
@Name("mulitCardInfoSubsrcibe")
public class MulitCardInfoSubsrcibe {

    @In
    private OwnerBusinessHome ownerBusinessHome;

    @In
    private AuthenticationInfo authInfo;





    @Create
    public void create() {

        for (HouseBusiness houseBusiness : ownerBusinessHome.getInstance().getHouseBusinesses()) {
            Logging.getLog(getClass()).debug("aaaaaaaaa");
            if (houseBusiness.getAfterBusinessHouse().getBusinessHouseOwner() != null
                && houseBusiness.getAfterBusinessHouse().getBusinessHouseOwner().getMakeCard() != null
                && houseBusiness.getAfterBusinessHouse().getBusinessHouseOwner().getMakeCard().getCardInfo() == null){
                    CardInfo cardInfo = new CardInfo();
                    cardInfo.setPrintTime(new Date());
                    Logging.getLog(getClass()).debug("123123123123");
                    cardInfo.setMakeEmpCode(authInfo.getLoginEmployee().getId());
                    cardInfo.setMakeEmpName(authInfo.getLoginEmployee().getPersonName());
                    houseBusiness.getAfterBusinessHouse().getBusinessHouseOwner().getMakeCard().setCardInfo(cardInfo);
                    cardInfo.setMakeCard(houseBusiness.getAfterBusinessHouse().getBusinessHouseOwner().getMakeCard());
            }
        }
    }

}
