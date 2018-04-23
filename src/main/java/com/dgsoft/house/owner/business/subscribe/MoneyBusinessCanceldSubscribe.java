package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.common.system.business.BusinessInstance;
import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.MoneyBusiness;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2018-04-23.
 * 资金监管 撤回 取消，交易没成功，
 */
@Name("moneyBusinessCanceldSubscribe")
public class MoneyBusinessCanceldSubscribe extends OwnerEntityHome<MoneyBusiness> {

    @In
    private OwnerBusinessHome ownerBusinessHome;

    @Override
    public MoneyBusiness createInstance(){
        if (ownerBusinessHome.getInstance().getMoneyBusinesses().isEmpty()) {
            MoneyBusiness moneyBusiness = new MoneyBusiness(ownerBusinessHome.getInstance(), ownerBusinessHome.getInstance().getSelectBusiness().getMoneyBusiness(), 2);
            moneyBusiness.setStatus(MoneyBusiness.MoneyBusinessStatus.CANCEL);
            ownerBusinessHome.getInstance().getMoneyBusinesses().add(moneyBusiness);
            for (MoneyBusiness omb : ownerBusinessHome.getInstance().getSelectBusiness().getMoneyBusinesses()) {
                omb.setStatus(MoneyBusiness.MoneyBusinessStatus.CHANGED);
            }
            return moneyBusiness;
        }else{
            return ownerBusinessHome.getInstance().getMoneyBusiness();

        }

    }

}
