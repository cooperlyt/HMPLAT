package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessHouseOwner;
import com.dgsoft.house.owner.model.BusinessPersion;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-9-19
 * Time: 下午3:45
 * To change this template use File | Settings | File Templates.
 */
@Name("persionOwnerEntrustSubscribe")
public class PersionOwnerEntrustSubscribe extends BaseBusinessPersionSubscribe {


    @Override
    protected BusinessPersion.PersionType getType() {
        return BusinessPersion.PersionType.OWNER_ENTRUST;
    }
}
