package com.dgsoft.house.owner.business.validation;

import com.dgsoft.common.system.business.BusinessDataValid;
import com.dgsoft.house.owner.model.BusinessHouse;
import com.dgsoft.house.owner.model.MoneyBusiness;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.intercept.BypassInterceptors;

/**
 * Created by wxy on 2018-04-22.
 * 资金监管的状态不能是COMPLETE 交易成功 拨款完成
 *
 */
@Name("moneyStatusNotHaveComplete")
@Scope(ScopeType.STATELESS)
@BypassInterceptors
public class MoneyStatusNotHaveComplete extends BusinessMoneyValid {

    @Override
    public ValidResult valid(MoneyBusiness moneyBusiness) {
        if (moneyBusiness.getStatus().equals(MoneyBusiness.MoneyBusinessStatus.COMPLETE)){
            return new ValidResult("business_money_status_not_have_complete",ValidResultLevel.ERROR);
        }
        return new ValidResult(ValidResultLevel.SUCCESS);
    }
}
