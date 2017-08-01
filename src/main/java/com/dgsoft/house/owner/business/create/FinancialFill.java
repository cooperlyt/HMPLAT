package com.dgsoft.house.owner.business.create;

import com.dgsoft.common.system.business.BusinessInstance;
import com.dgsoft.house.owner.model.BusinessPersion;
import com.dgsoft.house.owner.model.MortgaegeRegiste;
import com.dgsoft.house.owner.model.OwnerBusiness;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

/**
 * Created by wxy on 2017-08-01.
 * 变更提取
 */
@Name("financialFill")
@Scope(ScopeType.STATELESS)
public class FinancialFill extends  FinancialModerfyFill {
    @Override
    public void fillData() {
        if (ownerBusinessHome.getInstance().getType().equals(BusinessInstance.BusinessType.MODIFY_BIZ)) {
            super.fillData();

        } else {
            OwnerBusiness selectBiz = ownerBusinessHome.getInstance().getSelectBusiness();
            if (selectBiz != null) {
                if (selectBiz.getMortgaegeRegiste() != null && selectBiz.getMortgaegeRegiste().getFinancial() != null) {
                    ownerBusinessHome.getInstance().setMortgaegeRegiste(new MortgaegeRegiste(ownerBusinessHome.getInstance(), selectBiz.getMortgaegeRegiste()));
                }
                if (selectBiz.getMortgageObligor() != null) {
                    BusinessPersion businessPersion = selectBiz.getMortgageObligor();
                    ownerBusinessHome.getInstance().getBusinessPersions().add(new BusinessPersion(ownerBusinessHome.getInstance(), businessPersion));
                }
//                if(selectBiz.getEvaluate()!=null) {
//                    Evaluate evaluate = selectBiz.getEvaluate();
//                    ownerBusinessHome.getInstance().getEvaluates().add(new Evaluate(ownerBusinessHome.getInstance(),evaluate));
//                }
            }

        }
    }
}
