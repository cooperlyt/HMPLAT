package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.model.MoneyBackBusiness;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2020-05-08.
 */
@Name("cancelMoneyBackBusinessSubscribe")
public class CancelMoneyBackBusinessSubscribe extends BaseMoneyBackBusinessSubscribe {
    @Override
    protected MoneyBackBusiness.MoneyBackType getBackType() {
        return MoneyBackBusiness.MoneyBackType.CANCEL;
    }
}
