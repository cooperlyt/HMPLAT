package com.dgsoft.house.owner.business.validation;

import com.dgsoft.house.HouseStatus;
import com.dgsoft.house.owner.model.BusinessHouse;
import com.dgsoft.house.owner.model.MakeCard;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.jboss.seam.log.Logging;

/**
 * Created by wxy on 2016-08-29.
 * 启动判断房屋是否有产权人，产权人是否有权证 替代抵押业务已办产权状态判断
 */
@Name("houseNotHaveOwner")
@Scope(ScopeType.STATELESS)
@BypassInterceptors
public class HouseNotHaveOwner extends BusinessHouseValid {

    @Override
    public ValidResult valid(BusinessHouse businessHouse) {
        if (businessHouse.getBusinessHouseOwner()!=null && businessHouse.getBusinessHouseOwner().getMakeCard()!=null
                && businessHouse.getBusinessHouseOwner().getMakeCard().getType().equals(MakeCard.CardType.OWNER_RSHIP)){
            return new ValidResult(ValidResultLevel.SUCCESS);
        }
        return new ValidResult("House_NotHave_Owner_Card",ValidResultLevel.ERROR);
    }

}
