package com.dgsoft.house.owner.business.create;

import com.dgsoft.common.system.business.BusinessDataFill;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessHouse;
import com.dgsoft.house.owner.model.HouseBusiness;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Logging;

/**
 * Created by wxy on 2015-11-22.
 * 提取预告人，商品房初始登记的开发商产权人，房屋所有权转移预告登记的上一手产权人
 */
@Name("noticeAndCommercialFill")
public class NoticeAndCommercialFill implements BusinessDataFill {

    @In
    private OwnerBusinessHome ownerBusinessHome;

    @Override
    public void fillData() {


        for (HouseBusiness houseBusiness:ownerBusinessHome.getInstance().getHouseBusinesses()){

            if (houseBusiness.getStartBusinessHouse().getOldOwner()!=null){
                houseBusiness.getAfterBusinessHouse().setOldOwner(houseBusiness.getStartBusinessHouse().getOldOwner());
            }

            if(houseBusiness.getStartBusinessHouse().getNoticeOwner()!=null){
                houseBusiness.getAfterBusinessHouse().setNoticeOwner(houseBusiness.getStartBusinessHouse().getNoticeOwner());
            }

        }


    }
}
