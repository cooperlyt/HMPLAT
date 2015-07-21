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
 * 初始登记已经完成，无法起到
 */
@Name("houseStatusInitRegNotHaveInitReg")
@Scope(ScopeType.STATELESS)
@BypassInterceptors
public class HouseStatusInitRegNotHaveInitReg extends BusinessHouseValid {

    @Override
    public BusinessDataValid.ValidResult valid(BusinessHouse businessHouse) {
        if (businessHouse.getHouseStates().contains(HouseInfo.HouseStatus.INIT_REG)){
            return new BusinessDataValid.ValidResult("business_house_InitReg_status_No_have_InitReg", TaskSubscribeComponent.ValidResult.ERROR);
        }
        return new BusinessDataValid.ValidResult(TaskSubscribeComponent.ValidResult.SUCCESS);

    }


}