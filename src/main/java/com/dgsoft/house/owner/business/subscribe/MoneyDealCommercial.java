package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessMoney;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by Administrator on 15-7-25.
 * 商品房交易手续费
 */
@Name("moneyDealCommercial")
public class MoneyDealCommercial extends OwnerEntityHome<BusinessMoney>{
    @In
    private OwnerBusinessHome ownerBusinessHome;

    @Override
    public BusinessMoney createInstance(){
        return new BusinessMoney(BusinessMoney.MoneyType.DEAL_COMMERCIAL);
    }


    @Override
    public void create()
    {
        super.create();
        for(BusinessMoney businessMoney: ownerBusinessHome.getInstance().getBusinessMoneys()){
            if (businessMoney.getMoneyTypeId().equals(BusinessMoney.MoneyType.DEAL_COMMERCIAL)){
                setId(businessMoney.getId());
                return;
            }
        }
        getInstance().setOwnerBusiness(ownerBusinessHome.getInstance());
        ownerBusinessHome.getInstance().getBusinessMoneys().add(getInstance());

    }

}
