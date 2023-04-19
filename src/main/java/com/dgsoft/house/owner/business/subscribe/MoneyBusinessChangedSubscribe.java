package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.common.system.business.TaskSubscribeComponent;
import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.MoneyBusiness;
import com.dgsoft.house.owner.model.MoneyPayInfo;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2018-04-23.
 */
@Name("moneyBusinessChangedSubscribe")
public class MoneyBusinessChangedSubscribe extends OwnerEntityHome<MoneyBusiness> {

    @In
    private OwnerBusinessHome ownerBusinessHome;

    @Override
    public MoneyBusiness createInstance(){
        if (ownerBusinessHome.getInstance().getMoneyBusinesses().isEmpty()) {
            MoneyBusiness moneyBusiness = new MoneyBusiness(ownerBusinessHome.getInstance(), ownerBusinessHome.getInstance().getSelectBusiness().getMoneyBusiness(), 1);
            ownerBusinessHome.getInstance().getMoneyBusinesses().add(moneyBusiness);
            for (MoneyBusiness omb : ownerBusinessHome.getInstance().getSelectBusiness().getMoneyBusinesses()) {
                omb.setStatus(MoneyBusiness.MoneyBusinessStatus.CHANGED);
            }
            return  moneyBusiness;
        }else{
            return ownerBusinessHome.getInstance().getMoneyBusiness();
        }


    }

}