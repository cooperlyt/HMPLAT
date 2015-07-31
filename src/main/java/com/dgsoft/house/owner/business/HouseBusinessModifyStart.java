package com.dgsoft.house.owner.business;

import com.dgsoft.common.system.action.BusinessDefineHome;
import com.dgsoft.common.system.business.BusinessInstance;
import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.OwnerBusiness;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.web.RequestParameter;

/**
 * Created by cooper on 7/31/15.
 */
@Name("houseBusinessModifyStart")
public class HouseBusinessModifyStart {


    @RequestParameter
    private String selectBusinessId;


    @In(create = true)
    private OwnerEntityLoader ownerEntityLoader;

    @In(create = true)
    private OwnerBusinessHome ownerBusinessHome;

    @In(create = true)
    private BusinessDefineHome businessDefineHome;



    public String startModify(){
        //TODO ROLE
        cloneData(ownerEntityLoader.getEntityManager().find(OwnerBusiness.class, selectBusinessId));


        return null;
    }

    private void cloneData(OwnerBusiness ownerBusiness){
        //TODO needFile
        ownerBusiness.setStatus(BusinessInstance.BusinessStatus.MODIFYING);

        businessDefineHome.setId(ownerBusiness.getDefineId());
        ownerBusinessHome.clearInstance();
        ownerBusinessHome.getInstance().setSelectBusiness(ownerBusiness);
        ownerBusinessHome.getInstance().setType(BusinessInstance.BusinessType.MODIFY_BIZ);



    }

}
