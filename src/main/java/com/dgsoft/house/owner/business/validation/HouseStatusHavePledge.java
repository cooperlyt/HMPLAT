package com.dgsoft.house.owner.business.validation;

import com.dgsoft.common.system.business.TaskSubscribeComponent;
import com.dgsoft.house.HouseInfo;
import com.dgsoft.house.owner.model.BusinessHouse;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.intercept.BypassInterceptors;

/**
 * Created by cooper on 6/25/15.
 * 房屋有抵押状态
 */
@Name("houseStatusHavePledge")
@Scope(ScopeType.STATELESS)
@BypassInterceptors
public class HouseStatusHavePledge extends BusinessHouseValid {


    @Override
    public ValidResult valid(BusinessHouse businessHouse) {
        if (businessHouse.getHouseStates().contains(HouseInfo.HouseStatus.PLEDGE)){
            return new ValidResult(TaskSubscribeComponent.ValidResult.SUCCESS);
        }
        return new ValidResult("business_house_status_have_pledge", TaskSubscribeComponent.ValidResult.ERROR);
    }
}
