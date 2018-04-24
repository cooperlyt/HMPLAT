package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.MoneyBusiness;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2018-04-23.
 * 交易完成后解除资金监管
 */
@Name("moneyBusinessRemovedSubscribe")
public class MoneyBusinessRemovedSubscribe extends OwnerEntityHome<MoneyBusiness> {
    @In
    private OwnerBusinessHome ownerBusinessHome;

    @Override
    public MoneyBusiness createInstance(){
        if (ownerBusinessHome.getInstance().getMoneyBusinesses().isEmpty()) {
            MoneyBusiness moneyBusiness = new MoneyBusiness(ownerBusinessHome.getInstance(), ownerBusinessHome.getInstance().getSelectBusiness().getMoneyBusiness(), 1);
           // moneyBusiness.setStatus(MoneyBusiness.MoneyBusinessStatus.REGISTERED);
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
