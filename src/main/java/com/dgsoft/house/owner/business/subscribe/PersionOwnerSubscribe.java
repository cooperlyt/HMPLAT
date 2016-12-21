package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.model.BusinessPersion;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2016-12-20.
 * 项目备案 多房屋代理人
 */
@Name("persionOwnerSubscribe")
public class PersionOwnerSubscribe extends BaseBusinessPersionSubscribe{
    @Override
    protected BusinessPersion.PersionType getType() {
        return BusinessPersion.PersionType.OWNER_ENTRUST;
    }
}
