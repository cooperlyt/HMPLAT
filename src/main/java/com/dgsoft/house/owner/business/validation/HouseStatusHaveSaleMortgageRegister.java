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
 * 房屋必须有商品房预告抵押
 */
@Name("houseStatusHaveSaleMortgageRegister")
@Scope(ScopeType.STATELESS)
@BypassInterceptors
public class HouseStatusHaveSaleMortgageRegister extends BusinessHouseValid{


    @Override
    public ValidResult valid(BusinessHouse businessHouse) {
        if (businessHouse.getHouseStates().contains(HouseInfo.HouseStatus.SALE_MORTGAGE_REGISTER)){
            return new ValidResult(ValidResultLevel.SUCCESS);
        }
        return new ValidResult("business_house_status_have_SaleMortgageRegister",ValidResultLevel.ERROR);
    }
}
