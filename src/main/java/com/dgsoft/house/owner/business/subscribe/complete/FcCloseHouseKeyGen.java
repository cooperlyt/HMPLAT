package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.action.OwnerHouseHelper;
import com.dgsoft.house.owner.model.HouseBusiness;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2017-07-21.
 */
@Name("fcCloseHouseKeyGen")
public class FcCloseHouseKeyGen implements TaskCompleteSubscribeComponent {

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

        for(HouseBusiness hb: ownerBusinessHome.getInstance().getHouseBusinesses()){
            KeyGeneratorHelper key = OwnerHouseHelper.fcgenHouseSearchKey(hb.getAfterBusinessHouse());


            if (ownerBusinessHome.getApplyPersion()!=null){
                key.addWord(ownerBusinessHome.getApplyPersion().getPersonName());
            }

            if (ownerBusinessHome.getCloseHouse()!=null && ownerBusinessHome.getCloseHouse().getLegalDocuments()!=null){
                key.addWord(ownerBusinessHome.getCloseHouse().getLegalDocuments());
            }
            if (ownerBusinessHome.getCloseHouse()!=null && ownerBusinessHome.getCloseHouse().getExecutionNotice()!=null){
                key.addWord(ownerBusinessHome.getCloseHouse().getExecutionNotice());
            }

            if (ownerBusinessHome.getCloseHouse()!=null && ownerBusinessHome.getCloseHouse().getHouseCardNo()!=null){
                key.addWord(ownerBusinessHome.getCloseHouse().getHouseCardNo());
            }

            hb.setSearchKey(key.getKey());

        }

    }
}
