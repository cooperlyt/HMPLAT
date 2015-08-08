package com.dgsoft.house.owner.business.create;

import com.dgsoft.common.system.business.BusinessDataFill;
import com.dgsoft.common.system.business.BusinessInstance;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.HouseBusiness;
import com.dgsoft.house.owner.model.HouseRegInfo;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by Administrator on 15-8-1.
 * 产别，产权来源 每个可以走的业务都配
 */
@Name("houseRegInfoFill")
public class HouseRegInfoFill implements BusinessDataFill {

    @In
    private OwnerBusinessHome ownerBusinessHome;

    @Override
    public void fillData() {

       for (HouseBusiness houseBusiness:ownerBusinessHome.getInstance().getHouseBusinesses()){
            houseBusiness.getAfterBusinessHouse().setHouseRegInfo(houseBusiness.getStartBusinessHouse().getHouseRegInfo());
       }


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
