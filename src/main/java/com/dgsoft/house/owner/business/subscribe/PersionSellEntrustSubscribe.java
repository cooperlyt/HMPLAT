package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.model.BusinessPersion;
import org.jboss.seam.annotations.Name;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-10-9
 * Time: 下午3:04
 * To change this template use File | Settings | File Templates.
 */
@Name("persionSellEntrustSubscribe")
public class PersionSellEntrustSubscribe extends BaseBusinessPersionSubscribe {
    @Override
    protected BusinessPersion.PersionType getType() {
        return BusinessPersion.PersionType.SELL_ENTRUST;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
