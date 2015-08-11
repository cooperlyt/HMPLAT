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
 * Created by Administrator on 15-7-16.
 * 房屋必须有备案登记业务，备案注销，商品房预告登记
 */
@Name("houseStatusHaveContactsRecord")
@Scope(ScopeType.STATELESS)
@BypassInterceptors
public class HouseStatusHaveContactsRecord extends BusinessHouseValid {
    //ValidResultLevel.
    @Override
    public ValidResult valid(BusinessHouse businessHouse) {
        if (businessHouse.getAllStatusList().contains(HouseInfo.HouseStatus.CONTRACTS_RECORD)){
            return new ValidResult(ValidResultLevel.SUCCESS);
        }
        return new ValidResult("business_house_status_have_ContactsRecord",ValidResultLevel.ERROR);
    }
}
