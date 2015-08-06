package com.dgsoft.house.owner.business.create;

import com.dgsoft.common.system.business.BusinessDataFill;
import com.dgsoft.common.system.business.BusinessInstance;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.HouseBusiness;
import com.dgsoft.house.owner.model.HouseRegInfo;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by cooper on 8/6/15.
 */
@Name("houseRegInfoInput")
public class HouseRegInfoInput implements BusinessDataFill {

    @In
    private OwnerBusinessHome ownerBusinessHome;


    @Override
    public void fillData() {
        if (ownerBusinessHome.getInstance().getType().equals(BusinessInstance.BusinessType.MODIFY_BIZ)){
            HouseRegInfo regInfo = ownerBusinessHome.getInstance().getSelectBusiness().getHouseRegInfo();
            if (regInfo != null) {
                for (HouseBusiness houseBusiness : ownerBusinessHome.getInstance().getHouseBusinesses()) {
                    houseBusiness.getAfterBusinessHouse().setHouseRegInfo(new HouseRegInfo(ownerBusinessHome.getInstance(),regInfo));
                }
            }
        }
    }
}
