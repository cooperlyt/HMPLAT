package com.dgsoft.house.owner.business.create;

import com.dgsoft.common.system.business.BusinessDataFill;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessHouse;
import com.dgsoft.house.owner.model.HouseBusiness;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2016-12-17.
 * 现共有情况提取
 */
@Name("poolTypeFill")
public class PoolTypeFill implements BusinessDataFill {
    @In
    private OwnerBusinessHome ownerBusinessHome;
    @Override
    public void fillData() {
        for (HouseBusiness houseBusiness:ownerBusinessHome.getInstance().getHouseBusinesses()){
            if (houseBusiness.getStartBusinessHouse().getPoolType()!=null){
                houseBusiness.getAfterBusinessHouse().setPoolType(houseBusiness.getStartBusinessHouse().getPoolType());
            }

        }

    }
}
