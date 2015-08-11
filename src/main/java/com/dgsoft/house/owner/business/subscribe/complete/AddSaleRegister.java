package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.house.HouseInfo;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.AddHouseStatus;
import com.dgsoft.house.owner.model.HouseBusiness;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import java.util.Date;

/**
 * Created by Administrator on 15-7-29.
 * 添加预购商品房预告状态
 */
@Name("addSaleRegister")
public class AddSaleRegister implements TaskCompleteSubscribeComponent {


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
        for (HouseBusiness houseBusiness:ownerBusinessHome.getInstance().getHouseBusinesses()){
           // houseBusiness.getAfterBusinessHouse().addStatus(HouseInfo.HouseStatus.SALE_REGISTER);
            houseBusiness.getAddHouseStatuses().add(new AddHouseStatus(HouseInfo.HouseStatus.SALE_REGISTER, houseBusiness));

        }
    }
}
