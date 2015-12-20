package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessHouse;
import com.dgsoft.house.owner.model.HouseBusiness;
import com.dgsoft.house.owner.model.HouseRegInfo;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2015-12-19.
 * 多房屋相同相同产别，产权来源，商品房初始登记
 */
@Name("mulitHouseRegInfoSubscribe")
public class MulitHouseRegInfoSubscribe extends OwnerEntityHome<HouseRegInfo> {

    @In
    private OwnerBusinessHome ownerBusinessHome;

    @Override
    public void create(){
        super.create();
        for (HouseBusiness houseBusiness:ownerBusinessHome.getInstance().getHouseBusinesses()){
            if (houseBusiness.getAfterBusinessHouse().getHouseRegInfo()!=null){
                setId(houseBusiness.getAfterBusinessHouse().getHouseRegInfo().getId());
            }else{
                houseBusiness.getAfterBusinessHouse().setHouseRegInfo(getInstance());
            }

        }


    }


}
