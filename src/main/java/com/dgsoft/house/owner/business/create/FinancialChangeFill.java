package com.dgsoft.house.owner.business.create;

import com.dgsoft.common.system.business.BusinessInstance;
import com.dgsoft.house.owner.model.*;
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
                BusinessPersion businessPersion = selectBiz.getMortgageObligor();
                ownerBusinessHome.getInstance().getBusinessPersions().add(new BusinessPersion(ownerBusinessHome.getInstance(),businessPersion));
                Evaluate evaluate = selectBiz.getEvaluate();
                ownerBusinessHome.getInstance().getEvaluates().add(new Evaluate(ownerBusinessHome.getInstance(),evaluate));


            }

        }

    }

}
