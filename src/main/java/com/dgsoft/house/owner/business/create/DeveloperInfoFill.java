package com.dgsoft.house.owner.business.create;

import com.dgsoft.common.system.business.BusinessDataFill;
import com.dgsoft.common.system.business.BusinessInstance;
import com.dgsoft.house.HouseEntityLoader;
import com.dgsoft.house.model.House;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.HouseBusiness;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;

/**
 * Created by wxy on 2015-09-04.
 * 空间库的开发商信息，启动时填充到档案库房屋表中
 *
 */
@Name("developerInfoFill")
public class DeveloperInfoFill implements BusinessDataFill {

    @In
    private OwnerBusinessHome ownerBusinessHome;


    @In(create = true)
    private HouseEntityLoader houseEntityLoader;

    @In
    private FacesMessages facesMessages;


    @Override
    public void fillData() {
        boolean can=true;
        if (!ownerBusinessHome.getInstance().getType().equals(BusinessInstance.BusinessType.MODIFY_BIZ)) {
            for (HouseBusiness houseBusiness : ownerBusinessHome.getInstance().getHouseBusinesses()) {
                ownerBusinessHome.getInstance().getBusinessProject().getDeveloperCode();
                House house = houseEntityLoader.getEntityManager().find(House.class, houseBusiness.getHouseCode());
                if (house != null && house.getDeveloperName()!=null){
                    houseBusiness.getAfterBusinessHouse().setDeveloperCode(house.getDeveloperCode());
                    houseBusiness.getAfterBusinessHouse().setDeveloperName(house.getDeveloperName());
                  if(can) {
                      facesMessages.addFromResourceBundle(StatusMessage.Severity.WARN, "Developer_info_fill");
                  }
                  can=false;
                }
            }
        }
    }
}
