package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.action.OwnerHouseHelper;
import com.dgsoft.house.owner.model.HouseBusiness;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2018-06-07.
 * serchkey 凤城租赁房屋查询KEY生成器
 */
@Name("fcLeaseHouseOwnerKeyGen")
public class FcLeaseHouseOwnerKeyGen implements TaskCompleteSubscribeComponent {

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
            KeyGeneratorHelper key = new KeyGeneratorHelper();
            // 承租人
            if (ownerBusinessHome.getLesseePersion()!=null){
                key.addWord(ownerBusinessHome.getLesseePersion().getPersonName());
                key.addWord(ownerBusinessHome.getLesseePersion().getCredentialsNumber());;
            }
            // 出租人
            if (ownerBusinessHome.getLessorPersion()!=null){
                key.addWord(ownerBusinessHome.getLessorPersion().getPersonName());
                key.addWord(ownerBusinessHome.getLessorPersion().getCredentialsNumber());;
            }

            if(ownerBusinessHome.getCardNoByType("OWNER_RSHIP")!=null) {
                key.addWord(ownerBusinessHome.getCardNoByType("OWNER_RSHIP").getNumber());
            }

            if(!ownerBusinessHome.getInstance().getLeaseHouses().isEmpty() &&
                    ownerBusinessHome.getLeaseHouse().getLeaseNo()!=null &&
                    !ownerBusinessHome.getLeaseHouse().getLeaseNo().equals("")) {
                key.addWord(ownerBusinessHome.getLeaseHouse().getLeaseNo());
            }
            key.addWord(hb.getAfterBusinessHouse().getHouseCode());
            key.addWord(hb.getAfterBusinessHouse().getAddress());
            hb.setSearchKey(key.getKey());
        }

    }
}
