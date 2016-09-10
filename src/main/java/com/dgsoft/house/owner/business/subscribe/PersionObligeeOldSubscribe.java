package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.model.BusinessPersion;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2016-09-10.
 * 原抵押权代理人
 */
@Name("persionObligeeOldSubscribe")
public class PersionObligeeOldSubscribe extends BaseBusinessPersionSubscribe {
    @Override
    protected BusinessPersion.PersionType getType() {
        return BusinessPersion.PersionType.MORTGAGE_OBLIGEE_OLD;
    }
}
