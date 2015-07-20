package com.dgsoft.house.owner.business.create;

import com.dgsoft.common.system.business.BusinessDataFill;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.HouseBusiness;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

/**
 * Created by Administrator on 15-7-20.
 * 产权人提取,将beforehouse 产权人填充给 afterHouse
 */
@Name("businessHouseOwnerFill")
@Scope(ScopeType.STATELESS)
public class BusinessHouseOwnerFill implements BusinessDataFill {

    @In
    private OwnerBusinessHome ownerBusinessHome;


    @Override
    public void fillData() {

       for (HouseBusiness houseBusiness:ownerBusinessHome.getInstance().getHouseBusinesses()){
              houseBusiness.getAfterBusinessHouse().setBusinessHouseOwner(houseBusiness.getStartBusinessHouse().getBusinessHouseOwner());
       }

    }
}
