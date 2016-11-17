package com.dgsoft.house.owner.business.create;

import com.dgsoft.common.system.business.BusinessDataFill;
import com.dgsoft.common.system.business.BusinessInstance;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.HouseBusiness;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Logging;

/**
 * Created by wxy on 2015-10-02.
 * 撤销备案填充备案信息
 */
@Name("contractOwnerFill")
public class ContractOwnerFill implements BusinessDataFill {

    @In
    private OwnerBusinessHome ownerBusinessHome;


    @Override
    public void fillData() {
        if (!ownerBusinessHome.getInstance().getType().equals(BusinessInstance.BusinessType.MODIFY_BIZ)) {
            for (HouseBusiness houseBusiness : ownerBusinessHome.getInstance().getHouseBusinesses()) {
                if (ownerBusinessHome.getInstance().getSelectBusiness()!=null){
                   //houseBusiness.getAfterBusinessHouse().setContractOwner(ownerBusinessHome.getInstance().getSelectBusiness().getSingleHoues().getAfterBusinessHouse().getContractOwner());
                }

            }

        }
    }
}
