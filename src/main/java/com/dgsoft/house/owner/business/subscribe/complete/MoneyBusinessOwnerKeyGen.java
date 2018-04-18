package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.action.OwnerHouseHelper;
import com.dgsoft.house.owner.model.MoneyBusiness;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2018-04-18.
 */
@Name("moneyBusinessOwnerKeyGen")
public class MoneyBusinessOwnerKeyGen implements TaskCompleteSubscribeComponent {
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
        for(MoneyBusiness mb: ownerBusinessHome.getInstance().getMoneyBusinesses()){
            KeyGeneratorHelper key = OwnerHouseHelper.genHouseSearchKey(mb.getHouseContract().getHouseBusiness().getAfterBusinessHouse());
            if(mb.getHouseContract()!=null) {
                key.addWord(mb.getHouseContract().getContractNumber());
            }
            mb.setSearchKey(key.getKey());
        }

    }
}
