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
 * Created by Administrator on 15-7-18.
 * 不能有声明作废
 */
@Name("houseStatusNotHaveDeclareCancel")
@Scope(ScopeType.STATELESS)
@BypassInterceptors
public class HouseStatusNotHaveDeclareCancel extends BusinessHouseValid {

    @Override
    public ValidResult valid(BusinessHouse businessHouse) {
        if (businessHouse.getAllStatusList().contains(HouseInfo.HouseStatus.DECLARE_CANCEL)){
            return new ValidResult("business_house_status_no_have_DeclareCancel", ValidResultLevel.ERROR);
        }
        return new ValidResult(ValidResultLevel.SUCCESS);
    }
}
