package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.model.BusinessHouseOwner;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2016-02-25.
 * 预告抵押补录用，添加预告登记人，他项权人，预告人
 */
@Name("financialNoitceSubscribe")
public class FinancialNoitceSubscribe extends FinancialBaseSubscribe{
    @Override
    protected void addMortgage() {
        if(mortgaegeRegiste.getBusinessHouseOwner()==null){
            BusinessHouseOwner businessHouseOwner = new BusinessHouseOwner();
            businessHouseOwner.setOwnerBusiness(ownerBusinessHome.getInstance());
            ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().setBusinessHouseOwner(businessHouseOwner);
            ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().setNoticeOwner(businessHouseOwner);
            mortgaegeRegiste.setBusinessHouseOwner(businessHouseOwner);
        }

    }
}
