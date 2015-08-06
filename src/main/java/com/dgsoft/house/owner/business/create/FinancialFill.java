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

/**
 * Created by nistrator on 15-7-20.
 * 金融机构填充
 */
@Name("financialFill")
@Scope(ScopeType.STATELESS)

public class FinancialFill implements BusinessDataFill {
    @In
    private OwnerBusinessHome ownerBusinessHome;

    @Override
    public void fillData() {

        for(HouseBusiness houseBusiness:ownerBusinessHome.getInstance().getHouseBusinesses()){
            for(MortgaegeRegiste mortgaegeRegiste:houseBusiness.getStartBusinessHouse().getMortgaegeRegistes()){
                houseBusiness.getAfterBusinessHouse().getMortgaegeRegistes().add(mortgaegeRegiste);
            }
        }
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
