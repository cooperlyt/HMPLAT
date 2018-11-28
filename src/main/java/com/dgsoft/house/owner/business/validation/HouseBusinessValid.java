package com.dgsoft.house.owner.business.validation;

import com.dgsoft.common.system.business.BusinessDataValid;
import com.dgsoft.house.owner.model.BusinessHouse;
import com.dgsoft.house.owner.model.HouseBusiness;

/**
 * Created by wxy on 2018-11-27.
 * 在建工程抵押注销登记， HouseBusinessInBizValid
 */
public abstract class HouseBusinessValid implements BusinessDataValid {
    public abstract ValidResult valid(HouseBusiness houseBusiness);
    @Override
    public ValidResult valid(Object data) {
        if (data instanceof HouseBusiness) {
            return valid((HouseBusiness) data);
        }
        return new ValidResult("config_error_data_not_HouseBusiness", ValidResultLevel.FATAL);
    }
}
