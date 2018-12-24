package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.action.OwnerHouseHelper;
import com.dgsoft.house.owner.model.HouseBusiness;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2018-12-24.
 */
@Name("fcLzyjKeyGen")
public class FcLzyjKeyGen implements TaskCompleteSubscribeComponent {
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
            KeyGeneratorHelper key = OwnerHouseHelper.fcgenProjectSearchKey(hb.getAfterBusinessHouse());

            if (ownerBusinessHome.getApplyPersion()!=null && ownerBusinessHome.getApplyPersion().getPersonName()!=null){
                key.addWord(ownerBusinessHome.getApplyPersion().getPersonName());
                if (ownerBusinessHome.getApplyPersion().getCredentialsNumber()!=null){
                    key.addWord(ownerBusinessHome.getApplyPersion().getCredentialsNumber());
                }
            }


            hb.setSearchKey(key.getKey());
        }


    }


}
