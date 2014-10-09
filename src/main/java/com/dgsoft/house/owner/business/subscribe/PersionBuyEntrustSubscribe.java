package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.model.BusinessPersion;
import org.jboss.seam.annotations.Name;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-10-9
 * Time: 下午2:44
 * To change this template use File | Settings | File Templates.
 */
@Name("persionBuyEntrustSubscribe")
public class PersionBuyEntrustSubscribe extends BaseBusinessPersionSubscribe {
    @Override
    protected BusinessPersion.PersionType getType() {
        return BusinessPersion.PersionType.BUY_ENTRUST;
    }
}
