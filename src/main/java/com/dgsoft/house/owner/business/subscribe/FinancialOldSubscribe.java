package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.model.Financial;
import com.dgsoft.house.owner.model.LandInfo;
import org.jboss.seam.annotations.Name;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-10-13
 * Time: 下午2:07
 * To change this template use File | Settings | File Templates.
 */
@Name("financialOldSubscribe")
public class FinancialOldSubscribe extends BaseFinancialSubscribe {
    @Override
    protected Financial.FinancialUseType getType() {
        return Financial.FinancialUseType.OLD;
    }
}
