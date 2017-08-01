package com.dgsoft.house.owner.business.create;

import com.dgsoft.common.system.business.BusinessInstance;
import com.dgsoft.house.owner.model.*;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Logging;

/**
 * Created by wxy on 2015-08-30.
 * 注销提取到OLD
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
                if ( selectBiz.getMortgaegeRegiste()!=null && selectBiz.getMortgaegeRegiste().getFinancial()!=null) {
                    ownerBusinessHome.getInstance().setMortgaegeRegiste(new MortgaegeRegiste(ownerBusinessHome.getInstance(), selectBiz.getMortgaegeRegiste(), selectBiz.getMortgaegeRegiste().getFinancial()));
                }
                if (selectBiz.getMortgageObligor()!=null){
                    BusinessPersion businessPersion = selectBiz.getMortgageObligor();
                    ownerBusinessHome.getInstance().getBusinessPersions().add(new BusinessPersion(ownerBusinessHome.getInstance(),businessPersion));
                }
//                if(selectBiz.getEvaluate()!=null) {
//                    Evaluate evaluate = selectBiz.getEvaluate();
//                    ownerBusinessHome.getInstance().getEvaluates().add(new Evaluate(ownerBusinessHome.getInstance(),evaluate));
//                }
            }

        }

    }

}
