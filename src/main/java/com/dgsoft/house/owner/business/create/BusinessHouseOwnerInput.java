package com.dgsoft.house.owner.business.create;

import com.dgsoft.common.system.business.BusinessDataFill;
import com.dgsoft.common.system.business.BusinessInstance;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessHouseOwner;
import com.dgsoft.house.owner.model.HouseBusiness;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2015-09-12.
 * 填充一个可编辑的产权人
 * 可编辑的 new一个填充，不可编辑直接连接
 */
@Name("businessHouseOwnerInput")
public class BusinessHouseOwnerInput implements BusinessDataFill {

    @In
    private OwnerBusinessHome ownerBusinessHome;

    @Override
    public void fillData() {
        if (!ownerBusinessHome.getInstance().getType().equals(BusinessInstance.BusinessType.MODIFY_BIZ)){
            for (HouseBusiness houseBusiness : ownerBusinessHome.getInstance().getHouseBusinesses()) {
                if (houseBusiness.getStartBusinessHouse().getBusinessHouseOwner()!= null) {
                    houseBusiness.getAfterBusinessHouse().setBusinessHouseOwner(new BusinessHouseOwner(ownerBusinessHome.getInstance(), houseBusiness.getStartBusinessHouse().getBusinessHouseOwner()));
                }
            }
        }

    }
}
