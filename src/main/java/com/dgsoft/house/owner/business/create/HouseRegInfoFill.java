package com.dgsoft.house.owner.business.create;


import com.dgsoft.common.system.business.BusinessDataFill;
import com.dgsoft.common.system.business.BusinessInstance;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.HouseBusiness;
import com.dgsoft.house.owner.model.HouseRegInfo;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Logging;

/**
 * Created by Administrator on 15-8-1.
 * 产别，产权来源 新建填充
 */
@Name("houseRegInfoFill")
public class HouseRegInfoFill implements BusinessDataFill {

    @In
    private OwnerBusinessHome ownerBusinessHome;

    @Override
    public void fillData() {

      if (!ownerBusinessHome.getInstance().getType().equals(BusinessInstance.BusinessType.MODIFY_BIZ)) {

          for (HouseBusiness houseBusiness : ownerBusinessHome.getInstance().getHouseBusinesses()) {
              HouseRegInfo houseRegInfo = houseBusiness.getStartBusinessHouse().getHouseRegInfo();
              if(houseRegInfo!=null) {
                  houseBusiness.getAfterBusinessHouse().setHouseRegInfo(new HouseRegInfo(houseRegInfo));


              }
          }
      }




    }
}
