package com.dgsoft.house.owner.business.validation;

import com.dgsoft.common.system.business.BusinessDataValid;
import com.dgsoft.house.HouseInfo;
import com.dgsoft.house.HouseStatus;
import com.dgsoft.house.owner.model.BusinessHouse;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2015-08-29.
 * 房屋必须有已办产权或者商品房初始登记其中一个
 */
@Name("houseStatusHaveOwnerdOrInitReg")
public class HouseStatusHaveOwnerdOrInitReg extends BusinessHouseValid {
    @Override
    public ValidResult valid(BusinessHouse businessHouse) {
        if (businessHouse.getAllStatusList().contains(HouseStatus.OWNERED) || businessHouse.getAllStatusList().contains(HouseStatus.INIT_REG)){
            return new ValidResult(ValidResultLevel.SUCCESS);
        }
        return new ValidResult("business_house_status_have_OwnerdOrInitReg",ValidResultLevel.ERROR);
    }
}
