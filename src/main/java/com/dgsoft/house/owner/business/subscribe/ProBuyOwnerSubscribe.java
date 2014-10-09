package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.model.BusinessHouseOwner;
import org.jboss.seam.annotations.Name;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-10-9
 * Time: 下午4:15
 * To change this template use File | Settings | File Templates.
 */
@Name("proBuyOwnerSubscribe")
public class ProBuyOwnerSubscribe extends BaseHouseOwnerSubscribe {
    @Override
    protected BusinessHouseOwner.HouseOwnerType getType() {
        return BusinessHouseOwner.HouseOwnerType.PRO_BUY_OWNER;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
