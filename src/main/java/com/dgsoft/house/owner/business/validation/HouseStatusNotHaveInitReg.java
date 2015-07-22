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
 * 房屋没有初始登记，无法办理商品房交易业务
 */
@Name("houseStatusNotHaveInitReg")
@Scope(ScopeType.STATELESS)
@BypassInterceptors
public class HouseStatusNotHaveInitReg extends BusinessHouseValid {

    @Override
    public ValidResult valid(BusinessHouse businessHouse) {
        if (businessHouse.getHouseStates().contains(HouseInfo.HouseStatus.INIT_REG)){
            return new ValidResult("business_house_status_No_have_InitReg",ValidResultLevel.ERROR);
        }
        return new ValidResult(ValidResultLevel.SUCCESS);

    }
}
