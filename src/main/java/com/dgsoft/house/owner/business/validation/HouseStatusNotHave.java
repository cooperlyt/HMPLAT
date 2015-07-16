package com.dgsoft.house.owner.business.validation;

import com.dgsoft.common.system.business.BusinessDataValid;
import com.dgsoft.common.system.business.TaskSubscribeComponent;
import com.dgsoft.house.owner.model.BusinessHouse;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.intercept.BypassInterceptors;

/**
 * Created by Administrator on 15-7-16.
 * 房屋必须没有房屋状态，没走过业务，新建房屋，网上签约
 */
@Name("houseStatusNotHave")
@Scope(ScopeType.STATELESS)
@BypassInterceptors
public class HouseStatusNotHave extends BusinessHouseValid {
    @Override
    public ValidResult valid(BusinessHouse businessHouse) {
       if (businessHouse.getHouseStates()==null){
           return new ValidResult(TaskSubscribeComponent.ValidResult.SUCCESS);
       }

        return new ValidResult("business_house_status_no_have", TaskSubscribeComponent.ValidResult.ERROR);
    }
}
