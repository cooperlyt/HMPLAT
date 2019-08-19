package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.action.OwnerHouseHelper;
import com.dgsoft.house.owner.model.MoneyBackBusiness;
import com.dgsoft.house.owner.model.MoneyBusiness;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2019-08-19.
 */
@Name("moneyBackKeyGen")
public class MoneyBackKeyGen implements TaskCompleteSubscribeComponent {

    @In
    private OwnerBusinessHome ownerBusinessHome;

    @Override
    public void valid() {

    }

    @Override
    public boolean isPass() {
        return true;
    }

    @Override
    public void complete() {

            if (!ownerBusinessHome.getInstance().getMoneyBackBusinesses().isEmpty()) {
                for (MoneyBackBusiness mb : ownerBusinessHome.getInstance().getMoneyBackBusinesses()) {
                    if (ownerBusinessHome.getInstance().getSelectBusiness()!=null) {
                        KeyGeneratorHelper key = OwnerHouseHelper.genHouseSearchKey(ownerBusinessHome.getInstance().getSelectBusiness().getSingleHoues().getAfterBusinessHouse());
                        key.addWord(mb.getContract());
                        key.addWord(mb.getId());
                        mb.setSearchKey(key.getKey());
                    }
                }
            }

     }


}
