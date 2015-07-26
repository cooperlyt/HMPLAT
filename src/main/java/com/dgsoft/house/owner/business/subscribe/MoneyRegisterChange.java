package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessMoney;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by Administrator on 15-7-25.
 * 变更登记费
 */
@Name("moneyRegisterChange")
public class MoneyRegisterChange extends OwnerEntityHome<BusinessMoney> {

    @In
    private OwnerBusinessHome ownerBusinessHome;

    @Override
    public BusinessMoney createInstance(){
        return new BusinessMoney(BusinessMoney.MoneyType.REGISTER_CHANGE);
    }

    @Override
    public void create()
    {
        super.create();
        for(BusinessMoney businessMoney: ownerBusinessHome.getInstance().getBusinessMoneys()){
            if (businessMoney.getMoneyTypeId().equals(BusinessMoney.MoneyType.REGISTER_CHANGE)){
                setId(businessMoney.getId());
                return;
            }

        }
        getInstance().setOwnerBusiness(ownerBusinessHome.getInstance());
        ownerBusinessHome.getInstance().getBusinessMoneys().add(getInstance());

    }
}
