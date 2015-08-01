package com.dgsoft.house.owner.business.validation;

import com.dgsoft.common.system.business.BusinessDataValid;
import com.dgsoft.house.HouseInfo;
import com.dgsoft.house.owner.model.BusinessHouse;
import org.jboss.seam.annotations.Name;

/**
 * Created by Administrator on 15-8-1.
 * 初始登记启动必须有开发商信息
 */
@Name("houseHaveDeveloper")
public class HouseHaveDeveloper extends BusinessHouseValid {
    @Override
    public ValidResult valid(BusinessHouse businessHouse) {
        if (businessHouse.getDeveloperName()!=null && !businessHouse.getDeveloperName().equals("")){
            return new ValidResult(ValidResultLevel.SUCCESS);
        }
        return new ValidResult("business_house_not_have_Developer", ValidResultLevel.ERROR);
    }
}
