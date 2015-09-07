package com.dgsoft.house.owner.business.validation;

import com.dgsoft.common.system.business.TaskSubscribeComponent;
import com.dgsoft.house.HouseInfo;
import com.dgsoft.house.HouseStatus;
import com.dgsoft.house.owner.model.BusinessHouse;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.intercept.BypassInterceptors;

/**
 * Created by cooper on 6/25/15.
 * 不能有查封状态
 */
@Name("houseStatusNotHaveCourtClose")
@Scope(ScopeType.STATELESS)
@BypassInterceptors
public class HouseStatusNotHaveCourtClose extends BusinessHouseValid{
    @Override
    public ValidResult valid(BusinessHouse businessHouse) {
        if (businessHouse.getAllStatusList().contains(HouseStatus.COURT_CLOSE)){
            return new ValidResult("business_house_status_not_have_court_close",ValidResultLevel.ERROR);
        }
        return new ValidResult(ValidResultLevel.SUCCESS);
    }
}
