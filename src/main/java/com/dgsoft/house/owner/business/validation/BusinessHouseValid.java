package com.dgsoft.house.owner.business.validation;

import com.dgsoft.common.system.business.BusinessDataValid;
import com.dgsoft.common.system.business.TaskSubscribeComponent;
import com.dgsoft.house.owner.model.BusinessHouse;

/**
 * Created by cooper on 6/25/15.
 */
public abstract class BusinessHouseValid implements BusinessDataValid {

    public abstract ValidResult valid(BusinessHouse businessHouse);

    @Override
    public ValidResult valid(Object data) {
        if (data instanceof BusinessHouse){
            valid((BusinessHouse)data);
        }
        return new ValidResult("config_error_data_not_house", TaskSubscribeComponent.ValidResult.FATAL);
    }


}
