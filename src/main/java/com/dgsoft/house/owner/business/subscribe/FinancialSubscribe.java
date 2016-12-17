package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessHouse;
import com.dgsoft.house.owner.model.HouseBusiness;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2016-12-17.
 *
 */
@Name("financialSubscribe")
public class FinancialSubscribe extends FinancialBaseSubscribe {
    @Override
    protected void addMortgage() {
       for(HouseBusiness houseBusiness:ownerBusinessHome.getInstance().getHouseBusinesses()){
           houseBusiness.getAfterBusinessHouse().getMortgaegeRegistes().add(getMortgaegeRegiste());

       }

    }
}
