package com.dgsoft.house.owner.business.validation;

import com.dgsoft.common.system.business.BusinessDataValid;
import com.dgsoft.common.system.business.TaskSubscribeComponent;
import com.dgsoft.house.HouseInfo;
import com.dgsoft.house.HouseStatus;
import com.dgsoft.house.owner.model.BusinessHouse;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.intercept.BypassInterceptors;

/**
 * Created by Administrator on 15-7-16.
 * 房屋必须有查封状态，查封解除
 */
@Name("houseStatusHaveCourtClose")
@Scope(ScopeType.STATELESS)
@BypassInterceptors
public class HouseStatusHaveCourtClose extends BusinessHouseValid {
    @Override
    public ValidResult valid(BusinessHouse businessHouse) {
        if (businessHouse.getAllStatusList().contains(HouseStatus.COURT_CLOSE)){
            return new ValidResult(ValidResultLevel.SUCCESS);
        }
        return new ValidResult("business_house_status_have_CourtClose", ValidResultLevel.ERROR);
    }
}
