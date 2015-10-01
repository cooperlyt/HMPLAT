package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.common.system.business.BusinessInstance;
import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.house.model.House;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessHouse;
import com.dgsoft.house.owner.model.HouseBusiness;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Logging;

/**
 * Created by wxy on 2015-10-01.
 */
@Name("selectBizProjectMortgageCompleteCancel")
public class SelectBizProjectMortgageCompleteCancel implements TaskCompleteSubscribeComponent {
    @In
    private OwnerBusinessHome ownerBusinessHome;

    @Override
    public void valid() {

    }

    @Override
    public boolean isPass() {
        return true;
    }

    @Override
    public void complete() {
        if (ownerBusinessHome.getInstance().getSelectBusiness() != null) {
            int selectBusinessHouse = 0, nowBusinessHouse = 0;
            if (!ownerBusinessHome.getInstance().getSelectBusiness().getHouseBusinesses().isEmpty()) {
                selectBusinessHouse = ownerBusinessHome.getInstance().getSelectBusiness().getHouseBusinesses().size();
            }

            if (!ownerBusinessHome.getInstance().getHouseBusinesses().isEmpty()) {
                nowBusinessHouse = ownerBusinessHome.getInstance().getHouseBusinesses().size();
            }


            if (nowBusinessHouse > 0 && nowBusinessHouse > 0 && nowBusinessHouse >= selectBusinessHouse) {
                ownerBusinessHome.getInstance().getSelectBusiness().setStatus(BusinessInstance.BusinessStatus.COMPLETE_CANCEL);
            } else {
                ownerBusinessHome.getInstance().getSelectBusiness().setStatus(BusinessInstance.BusinessStatus.COMPLETE);
            }


            if (nowBusinessHouse > 0) {
                for (HouseBusiness houseBusiness : ownerBusinessHome.getInstance().getHouseBusinesses()) {
                    for (HouseBusiness selecthouseBusiness : ownerBusinessHome.getInstance().getSelectBusiness().getHouseBusinesses()) {
                        if (selecthouseBusiness.getHouseCode().equals(houseBusiness.getHouseCode())) {
                            selecthouseBusiness.setCanceled(true);
                        }
                    }
                    houseBusiness.setCanceled(true);
                }
            }
        }
    }
}
