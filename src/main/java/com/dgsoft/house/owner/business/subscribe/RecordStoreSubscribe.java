package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.common.system.business.TaskSubscribeComponent;
import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.*;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.log.Logging;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 15-6-11.
 */
@Name("recordStoreSubscribe")
public class RecordStoreSubscribe implements TaskSubscribeComponent {

    @In
    private OwnerBusinessHome ownerBusinessHome;


    @In
    private FacesMessages facesMessages;








    @Override
    public void initSubscribe() {



       if(!ownerBusinessHome.getInstance().getHouseBusinesses().isEmpty()){
           for(HouseBusiness houseBusiness:ownerBusinessHome.getInstance().getHouseBusinesses()){

           }
       }
    }

    @Override
    public ValidResult validSubscribe() {
        if (ownerBusinessHome.getInstance().getHouseBusinesses().isEmpty()){
            Logging.getLog(getClass()).error("not have HouseBusiness");
            return ValidResult.ERROR;
        }

        return ValidResult.SUCCESS;
    }

    @Override
    public boolean saveSubscribe() {

        return false;
    }





}
