package com.dgsoft.house.owner.business.validation;

import com.dgsoft.common.system.business.BusinessDataValid;
import com.dgsoft.house.owner.model.BusinessHouse;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.jboss.seam.log.Logging;

/**
 * Created by wxy on 2016-06-02
 * 判断房屋性质 是否能启动备案，房改，动迁
 */
@Name("houseTypeNotHave")
@Scope(ScopeType.STATELESS)
@BypassInterceptors
public class HouseTypeNotHave extends BusinessHouseValid {
    @Override
    public ValidResult valid(BusinessHouse businessHouse) {
        Logging.getLog(getClass()).debug("businessHouse.getHouseType()");
        if(businessHouse.getHouseType()==null || businessHouse.getHouseType().equals("")){

            return new ValidResult(ValidResultLevel.SUCCESS);
        }

        return new ValidResult("business_house_Type_Have",ValidResultLevel.ERROR);
    }
}
