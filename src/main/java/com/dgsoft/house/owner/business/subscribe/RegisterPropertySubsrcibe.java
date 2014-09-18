package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.RegisterProperty;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-9-18
 * Time: 下午2:43
 * To change this template use File | Settings | File Templates.
 */
@Name("registerPropertySubsrcibe")
public class RegisterPropertySubsrcibe extends OwnerEntityHome<RegisterProperty> {
    @In
    private OwnerBusinessHome ownerBusinessHome;


    @Override
    public void create(){
        super.create();
        if(!ownerBusinessHome.getInstance().getRegisterPropertys().isEmpty()){
            setId(ownerBusinessHome.getInstance().getMortgaegeRegistes().iterator().next().getId());
        }else {
            getInstance().setOwnerBusiness(ownerBusinessHome.getInstance());
            ownerBusinessHome.getInstance().getRegisterPropertys().add(getInstance());
        }

    }


}
