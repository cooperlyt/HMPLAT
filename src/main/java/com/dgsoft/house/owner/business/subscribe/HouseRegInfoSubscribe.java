package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.HouseBusiness;
import com.dgsoft.house.owner.model.HouseRegInfo;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Logging;

/**
 * Created by Administrator on 15-7-23.
 */
@Name("houseRegInfoSubscribe")
public class HouseRegInfoSubscribe extends OwnerEntityHome<HouseRegInfo> {


    @In
    private OwnerBusinessHome ownerBusinessHome;


    @Override
    public void create(){

        super.create();
        if (ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getHouseRegInfo()!=null){
            if (ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getHouseRegInfo().getId()==null){
                setInstance(ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getHouseRegInfo());

            }else {
                setId(ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getHouseRegInfo().getId());
            }

           // setId(ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getHouseRegInfo().getId());

        }else{
            //clearInstance();
            ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().setHouseRegInfo(getInstance());

        }




    }
}
