package com.dgsoft.house.owner.business.create;

import com.dgsoft.common.system.business.BusinessDataFill;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.HouseBusiness;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by Administrator on 15-8-1.
 * 产别，产权来源
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

    }
}
