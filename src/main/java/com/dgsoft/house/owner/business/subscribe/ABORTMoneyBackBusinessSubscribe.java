package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.model.MoneyBackBusiness;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2019-08-18.
 */
@Name("abortMoneyBackBusinessSubscribe")
public class ABORTMoneyBackBusinessSubscribe extends BaseMoneyBackBusinessSubscribe {
    @Override
    protected MoneyBackBusiness.MoneyBackType getBackType() {
        return MoneyBackBusiness.MoneyBackType.ABORT;
    }
}
