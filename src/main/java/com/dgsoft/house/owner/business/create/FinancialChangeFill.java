package com.dgsoft.house.owner.business.create;

import com.dgsoft.common.system.business.BusinessInstance;
import com.dgsoft.house.owner.model.BusinessPersion;
import com.dgsoft.house.owner.model.HouseBusiness;
import com.dgsoft.house.owner.model.MortgaegeRegiste;
import com.dgsoft.house.owner.model.OwnerBusiness;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Logging;

/**
 * Created by wxy on 2015-08-30.
 * 抵押变更提取抵押权人
 */
@Name("financialChangeFill")
@Scope(ScopeType.STATELESS)
public class FinancialChangeFill extends  FinancialModerfyFill {

    @Override
    public void fillData(){
        if (ownerBusinessHome.getInstance().getType().equals(BusinessInstance.BusinessType.MODIFY_BIZ)){
            super.fillData();

        }else{
            OwnerBusiness selectBiz=ownerBusinessHome.getInstance().getSelectBusiness();
            if(selectBiz !=null) {
                ownerBusinessHome.getInstance().setMortgaegeRegiste(new MortgaegeRegiste(ownerBusinessHome.getInstance(), selectBiz.getMortgaegeRegiste(), selectBiz.getMortgaegeRegiste().getFinancial()));
                for(HouseBusiness houseBusiness:ownerBusinessHome.getInstance().getHouseBusinesses()){
                    houseBusiness.getAfterBusinessHouse().getMortgaegeRegistes().add(ownerBusinessHome.getInstance().getMortgaegeRegiste());
                }
                BusinessPersion businessPersion = selectBiz.getMortgageObligor();
//                Logging.getLog(getClass()).debug(businessPersion.getPersonName());
//                Logging.getLog(getClass()).debug(businessPersion.getCredentialsType());
//                Logging.getLog(getClass()).debug(businessPersion.getCredentialsNumber());
//                Logging.getLog(getClass()).debug(businessPersion.getType());
                ownerBusinessHome.getInstance().getBusinessPersions().add(new BusinessPersion(ownerBusinessHome.getInstance(),businessPersion));
                ownerBusinessHome.getInstance().getMortgageObligor();
//                Logging.getLog(getClass()).debug(ownerBusinessHome.getInstance().getMortgageObligor().getPersonName());
//                Logging.getLog(getClass()).debug(ownerBusinessHome.getInstance().getMortgageObligor().getType());
//                Logging.getLog(getClass()).debug(ownerBusinessHome.getInstance().getMortgageObligor().getOwnerBusiness().getId());
               // ownerBusinessHome.getInstance().getMappingCorps().add(selectBiz.getMappingCorp());
            }

        }

    }

}
