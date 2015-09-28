package com.dgsoft.house.owner.business.create;

import com.dgsoft.common.system.PersonEntity;
import com.dgsoft.common.system.business.BusinessDataFill;
import com.dgsoft.common.system.business.BusinessInstance;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessHouseOwner;
import com.dgsoft.house.owner.model.ContractOwner;
import com.dgsoft.house.owner.model.HouseBusiness;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Logging;

/**
 * Created by wxy on 2015-09-28.
 * 备案人转成预告人
 */
@Name("contractChangeOwnerFill")
public class ContractChangeOwnerFill implements BusinessDataFill {

    @In
    private OwnerBusinessHome ownerBusinessHome;

    @Override
    public void fillData() {
        if (!ownerBusinessHome.getInstance().getType().equals(BusinessInstance.BusinessType.MODIFY_BIZ)) {
            for (HouseBusiness houseBusiness : ownerBusinessHome.getInstance().getHouseBusinesses()) {
                if(houseBusiness.getStartBusinessHouse().getContractOwner()!=null){

                    ContractOwner contractOwner = houseBusiness.getStartBusinessHouse().getContractOwner();
                    BusinessHouseOwner businessHouseOwner = new BusinessHouseOwner();
                    businessHouseOwner.setPersonName(contractOwner.getPersonName());
                    businessHouseOwner.setCredentialsType(contractOwner.getCredentialsType());
                    businessHouseOwner.setCredentialsNumber(contractOwner.getCredentialsNumber());
                    businessHouseOwner.setPhone(contractOwner.getPhone());
                    businessHouseOwner.setAddress(contractOwner.getAddress());
                    businessHouseOwner.setRootAddress(contractOwner.getRootAddress());
                    businessHouseOwner.setOwnerBusiness(ownerBusinessHome.getInstance());
                    houseBusiness.getAfterBusinessHouse().setBusinessHouseOwner(businessHouseOwner);
                }



            }
        }

    }
}
