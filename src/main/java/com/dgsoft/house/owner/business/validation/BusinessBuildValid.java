package com.dgsoft.house.owner.business.validation;

import com.dgsoft.common.system.business.BusinessDataValid;
import com.dgsoft.house.owner.model.BusinessBuild;

/**
 * Created by wxy on 2019-09-03.
 * 档案库楼幢是否判断
 */
public abstract class BusinessBuildValid implements BusinessDataValid {
    public abstract ValidResult valid(BusinessBuild build);
    @Override
    public ValidResult valid(Object data) {
        if (data instanceof BusinessBuild) {
            return valid((BusinessBuild) data);
        }
        return new ValidResult("config_error_data_not_Build", ValidResultLevel.FATAL);
    }
}
