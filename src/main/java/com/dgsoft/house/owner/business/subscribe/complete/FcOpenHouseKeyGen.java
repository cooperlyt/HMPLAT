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
@Name("fcOpenHouseKeyGen")
public class FcOpenHouseKeyGen implements TaskCompleteSubscribeComponent {

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

            if (ownerBusinessHome.getApplyPersion()!=null && ownerBusinessHome.getApplyPersion()!=null){
                key.addWord(ownerBusinessHome.getApplyPersion().getCredentialsNumber());
            }


            if (ownerBusinessHome.getCloseHouseCancel()!=null && ownerBusinessHome.getCloseHouseCancel().getLegalDocuments()!=null){
                key.addWord(ownerBusinessHome.getCloseHouseCancel().getLegalDocuments());
            }
            if (ownerBusinessHome.getCloseHouseCancel()!=null && ownerBusinessHome.getCloseHouseCancel().getExecutionNotice()!=null){
                key.addWord(ownerBusinessHome.getCloseHouseCancel().getExecutionNotice());
            }

            if (ownerBusinessHome.getCloseHouseCancel()!=null && ownerBusinessHome.getCloseHouseCancel().getHouseCardNo()!=null){
                key.addWord(ownerBusinessHome.getCloseHouseCancel().getHouseCardNo());
            }


            hb.setSearchKey(key.getKey());
        }

    }
}
