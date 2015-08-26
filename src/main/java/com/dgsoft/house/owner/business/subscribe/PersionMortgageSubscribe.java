package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.model.BusinessPersion;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2015-08-26.
 * 抵押人代理人
 */
@Name("persionMortgageSubscribe")
public class PersionMortgageSubscribe extends BaseBusinessPersionSubscribe {
    @Override
    protected BusinessPersion.PersionType getType() {
        return BusinessPersion.PersionType.MORTGAGE;
    }
}
