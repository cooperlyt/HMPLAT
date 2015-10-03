package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.common.system.PersonEntity;
import com.dgsoft.house.HouseEntityLoader;
import com.dgsoft.house.model.Developer;
import com.dgsoft.house.owner.model.BusinessHouseOwner;
import com.dgsoft.house.owner.model.HouseBusiness;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import java.util.UUID;

/**
 * Created by wxy on 2015-10-03.
 * 档案补录抵押信息 抵押权人，抵押人信息
 */
@Name("financialRecordSubscribe")
public class FinancialRecordSubscribe extends FinancialBaseSubscribe {


    @Override
    protected void addMortgage() {
        if(mortgaegeRegiste.getBusinessHouseOwner()==null){
            BusinessHouseOwner businessHouseOwner = new BusinessHouseOwner();
            businessHouseOwner.setOwnerBusiness(ownerBusinessHome.getInstance());
            ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().setBusinessHouseOwner(businessHouseOwner);
            mortgaegeRegiste.setBusinessHouseOwner(businessHouseOwner);
        }
    }
}
