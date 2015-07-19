package com.dgsoft.house.owner.business.create;

import com.dgsoft.common.system.business.BusinessDataFill;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

/**
 * Created by cooper on 7/17/15.
 */
@Name("pledgeBizFill")
@Scope(ScopeType.STATELESS)
public class PledgeBizFill implements BusinessDataFill {

    @In
    private OwnerBusinessHome ownerBusinessHome;

    @Override
    public void fillData() {

        //ownerBusinessHome.getInstance().getSelectBusiness()
       // ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().setLandInfo(ownerBusinessHome.getSingleHoues().getStartBusinessHouse().getLandInfo());

    }

}
