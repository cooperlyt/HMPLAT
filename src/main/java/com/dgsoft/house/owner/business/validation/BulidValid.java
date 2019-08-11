package com.dgsoft.house.owner.business.validation;

import com.dgsoft.common.system.business.BusinessDataValid;
import com.dgsoft.house.model.Build;
import com.dgsoft.house.owner.model.HouseBusiness;

/**
 * Created by wxy on 2019-08-11.
 * 预售许可证空间楼幢业务中判断
 */
public abstract class BulidValid implements BusinessDataValid {
    public abstract ValidResult valid(Build build);
    @Override
    public ValidResult valid(Object data) {
        if (data instanceof Build) {
            return valid((Build) data);
        }
        return new ValidResult("config_error_data_not_Build", ValidResultLevel.FATAL);

    }
}
