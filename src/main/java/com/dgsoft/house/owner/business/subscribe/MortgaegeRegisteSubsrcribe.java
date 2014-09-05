package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
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
    private OwnerBusinessHome ownerBusinessHome;

    @Override
    public void create(){
        super.create();
        if(!ownerBusinessHome.getInstance().getMortgaegeRegistes().isEmpty()){
           setId(ownerBusinessHome.getInstance().getMortgaegeRegistes().iterator().next().getId());
        }else {
           getInstance().setOwnerBusiness(ownerBusinessHome.getInstance());
           ownerBusinessHome.getInstance().getMortgaegeRegistes().add(getInstance());
        }

    }


}
