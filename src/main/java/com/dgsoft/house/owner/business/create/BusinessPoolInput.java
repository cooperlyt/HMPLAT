package com.dgsoft.house.owner.business.create;

import com.dgsoft.common.system.business.BusinessDataFill;
import com.dgsoft.common.system.business.BusinessInstance;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessPool;
import com.dgsoft.house.owner.model.HouseBusiness;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2015-09-12.
 * 新建公共有权人填充，生成新的共有权证，可编辑
 * 可编辑的 new一个填充，不可编辑直接连接
 */
@Name("businessPoolInput")
public class BusinessPoolInput implements BusinessDataFill {

    @In
    private OwnerBusinessHome ownerBusinessHome;



    @Override
    public void fillData() {
        if (!ownerBusinessHome.getInstance().getType().equals(BusinessInstance.BusinessType.MODIFY_BIZ)) {
            for (HouseBusiness houseBusiness : ownerBusinessHome.getInstance().getHouseBusinesses()) {
                houseBusiness.getAfterBusinessHouse().setPoolType(houseBusiness.getStartBusinessHouse().getPoolType());
                if(!houseBusiness.getStartBusinessHouse().getBusinessPools().isEmpty()) {
                    for (BusinessPool businessPool : houseBusiness.getStartBusinessHouse().getBusinessPools()) {
                        BusinessPool newBusinessPool =  new BusinessPool(ownerBusinessHome.getInstance(),businessPool);

                        houseBusiness.getAfterBusinessHouse().getBusinessPools().add(newBusinessPool);
                    }
                }
            }
        }

    }
}
