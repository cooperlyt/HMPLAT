package com.dgsoft.house.owner.business.create;

import com.dgsoft.common.system.PersonEntity;
import com.dgsoft.common.system.business.BusinessDataFill;
import com.dgsoft.common.system.business.BusinessInstance;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessHouseOwner;
import com.dgsoft.house.owner.model.BusinessPool;
import com.dgsoft.house.owner.model.HouseBusiness;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by Administrator on 15-8-1.
 * 开发商转化成产权人
 */
@Name("developerChangeOwner")
public class DeveloperChangeOwner implements BusinessDataFill {

    @In
    private OwnerBusinessHome ownerBusinessHome;



    @Override
    public void fillData() {

        if (!ownerBusinessHome.getInstance().getType().equals(BusinessInstance.BusinessType.MODIFY_BIZ)) {
            for (HouseBusiness houseBusiness : ownerBusinessHome.getInstance().getHouseBusinesses()) {

                BusinessHouseOwner businessHouseOwner = new BusinessHouseOwner();
                businessHouseOwner.setPersonName(houseBusiness.getStartBusinessHouse().getDeveloperName());
                businessHouseOwner.setCredentialsType(PersonEntity.CredentialsType.COMPANY_CODE);
                if (houseBusiness.getStartBusinessHouse().getDeveloperCode() != null
                        && !houseBusiness.getStartBusinessHouse().getDeveloperCode().equals("")) {
                    businessHouseOwner.setCredentialsNumber(houseBusiness.getStartBusinessHouse().getDeveloperCode());
                }
                businessHouseOwner.setOwnerBusiness(ownerBusinessHome.getInstance());
                houseBusiness.getAfterBusinessHouse().setBusinessHouseOwner(businessHouseOwner);
                houseBusiness.getAfterBusinessHouse().setOldOwner(businessHouseOwner);
            }
        }
    }
}
