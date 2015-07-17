package com.dgsoft.house.owner.business.validation;

import com.dgsoft.common.system.business.BusinessDataValid;
import com.dgsoft.common.system.business.TaskSubscribeComponent;
import com.dgsoft.house.HouseInfo;
import com.dgsoft.house.owner.model.BusinessHouse;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.intercept.BypassInterceptors;

/**
 * Created by Administrator on 15-7-17.
 * 不能有产权登记
 */
@Name("houseStatusNotHaveOwnerd")
@Scope(ScopeType.STATELESS)
@BypassInterceptors

public class HouseStatusNotHaveOwnerd extends BusinessHouseValid {
    @Override
    public ValidResult valid(BusinessHouse businessHouse) {
        if (businessHouse.getHouseStates().contains(HouseInfo.HouseStatus.OWNERED)){
            return new ValidResult("business_house_status_no_have_Ownerd", TaskSubscribeComponent.ValidResult.ERROR);
        }
        return new ValidResult(TaskSubscribeComponent.ValidResult.SUCCESS);

    }
}
