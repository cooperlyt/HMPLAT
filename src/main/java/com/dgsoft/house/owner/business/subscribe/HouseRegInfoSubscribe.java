package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.HouseRegInfo;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

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
        if(!ownerBusinessHome.getInstance().getHouseRegInfos().isEmpty()){
            setId(ownerBusinessHome.getInstance().getHouseRegInfos().iterator().next());
        }else {
            getInstance().setOwnerBusiness(ownerBusinessHome.getInstance());
            ownerBusinessHome.getInstance().getHouseRegInfos().add(getInstance());
            //循环房屋添加HouseRegInfos关系
        }



    }
}
