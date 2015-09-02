package com.dgsoft.house.owner.business.create;

import com.dgsoft.common.system.business.BusinessDataFill;
import com.dgsoft.common.system.business.BusinessInstance;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.HouseBusiness;
import com.dgsoft.house.owner.model.MortgaegeRegiste;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Logging;

/**
 * Created by nistrator on 15-7-20.
 * 金融机构填充 抵押登记更正用，每个抵押正常业务都配
 */
@Name("financialModerfyFill")
@Scope(ScopeType.STATELESS)

public class FinancialModerfyFill implements BusinessDataFill {
    @In
    protected OwnerBusinessHome ownerBusinessHome;

    @Override
    public void fillData() {

        if (ownerBusinessHome.getInstance().getType().equals(BusinessInstance.BusinessType.MODIFY_BIZ)){
            MortgaegeRegiste mr = ownerBusinessHome.getInstance().getSelectBusiness().getMortgaegeRegiste();
            if (mr != null){
                for(HouseBusiness houseBusiness: ownerBusinessHome.getInstance().getHouseBusinesses()){
                    houseBusiness.getAfterBusinessHouse().getMortgaegeRegistes().add(new MortgaegeRegiste(ownerBusinessHome.getInstance(),mr));
                }
            }
        }


    }
}
