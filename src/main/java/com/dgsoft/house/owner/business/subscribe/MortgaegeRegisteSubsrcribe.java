package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.HouseBusinessHome;
import com.dgsoft.house.owner.model.MortgaegeRegiste;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-9-1
 * Time: 上午10:15
 * To change this template use File | Settings | File Templates.
 */
@Name("mortgaegeRegisteSubsrcribe")
public class MortgaegeRegisteSubsrcribe extends OwnerEntityHome<MortgaegeRegiste> {

    @In
    private HouseBusinessHome houseBusinessHome;

    @Override
    public void create(){
        super.create();
        if(!houseBusinessHome.getInstance().getMortgaegeRegistes().isEmpty()){
           setId(houseBusinessHome.getInstance().getMortgaegeRegistes().iterator().next().getId());
        }else {
           getInstance().setHouseBusiness(houseBusinessHome.getInstance());
           houseBusinessHome.getInstance().getMortgaegeRegistes().add(getInstance());
        }

    }


}
