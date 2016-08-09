package com.dgsoft.house.owner.business.create;

import com.dgsoft.common.system.business.BusinessDataFill;
import com.dgsoft.common.system.business.BusinessInstance;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessHouseOwner;
import com.dgsoft.house.owner.model.HouseBusiness;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2015-11-27.
 * 预告业务注销 提取预告人
 *
 */
@Name("noticeOwnerFill")
public class NoticeOwnerFill implements BusinessDataFill {
    @In
    private OwnerBusinessHome ownerBusinessHome;

    @Override
    public void fillData() {
        if (!ownerBusinessHome.getInstance().getType().equals(BusinessInstance.BusinessType.MODIFY_BIZ)){
            for (HouseBusiness houseBusiness : ownerBusinessHome.getInstance().getHouseBusinesses()) {
                if (houseBusiness.getStartBusinessHouse().getNoticeOwner()!= null) {
                    houseBusiness.getAfterBusinessHouse().setBusinessHouseOwner(new BusinessHouseOwner(ownerBusinessHome.getInstance(), houseBusiness.getStartBusinessHouse().getNoticeOwner()));
                }else if(houseBusiness.getStartBusinessHouse().getBusinessHouseOwner()!=null){//倒库没有导入NoticeOwner 提取上一手产权人
                    houseBusiness.getAfterBusinessHouse().setBusinessHouseOwner(new BusinessHouseOwner(ownerBusinessHome.getInstance(), houseBusiness.getStartBusinessHouse().getBusinessHouseOwner()));
                }


            }
        }

    }
}
