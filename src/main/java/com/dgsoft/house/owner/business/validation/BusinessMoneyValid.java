package com.dgsoft.house.owner.business.validation;

import com.dgsoft.common.system.business.BusinessDataValid;
import com.dgsoft.house.owner.model.MoneyBusiness;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2018-04-22.
 */

public abstract class BusinessMoneyValid implements BusinessDataValid {

    public abstract ValidResult valid(MoneyBusiness moneyBusiness);

    @Override
    public ValidResult valid(Object data) {
        if (data instanceof MoneyBusiness){
            return valid((MoneyBusiness)data);
        }
        return new ValidResult("config_error_data_not_MoneyBusiness", ValidResultLevel.FATAL);
    }
}
