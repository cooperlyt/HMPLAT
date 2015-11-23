package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.model.BusinessHouseOwner;
import com.dgsoft.house.owner.model.HouseBusiness;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2015-11-22.
 *将商品房初始登记，和房屋所有权转移登记的上一收产权人特殊记录以便在商品房交易，办完转移预告登记，办理二手房交易显示打印用
 */
@Name("businessCommercialOwnerSubscribe")
public class BusinessCommercialOwnerSubscribe  extends BusinessHouseOwnerSubscribe {

    @Override
    public Class getEntityClass(){
        return BusinessHouseOwner.class;
    }
    @Override
    public void create(){
        super.create();
        for(HouseBusiness houseBusiness:ownerBusinessHome.getInstance().getHouseBusinesses()){
            houseBusiness.getAfterBusinessHouse().setOldOwner(getInstance());
        }
    }
}
