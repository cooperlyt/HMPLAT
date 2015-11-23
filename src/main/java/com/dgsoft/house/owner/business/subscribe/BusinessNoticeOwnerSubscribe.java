package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.model.BusinessHouseOwner;
import com.dgsoft.house.owner.model.HouseBusiness;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Logging;

/**
 * Created by wxy on 2015-11-22.
 * 将预告人特殊记录，以后信息提取用
 */
@Name("businessNoticeOwnerSubscribe")
public class BusinessNoticeOwnerSubscribe extends BusinessHouseOwnerSubscribe {
    @Override
    public Class getEntityClass(){
        return BusinessHouseOwner.class;

    }

    @Override
    public void create(){
        super.create();
        for(HouseBusiness houseBusiness:ownerBusinessHome.getInstance().getHouseBusinesses()){
            houseBusiness.getAfterBusinessHouse().setNoticeOwner(getInstance());
        }

    }
}
