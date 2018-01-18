package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.action.OwnerHouseHelper;
import com.dgsoft.house.owner.model.HouseBusiness;
import com.dgsoft.house.owner.model.HouseContract;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2017-07-20.
 * serchkey 凤城房屋和现权力人查询KEY生成器
 */
@Name("fcHouseBusinessOwnerKeyGen")
public class FcHouseBusinessOwnerKeyGen implements TaskCompleteSubscribeComponent {

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
            if(ownerBusinessHome.getCardNoByType("OWNER_RSHIP")!=null) {
                key.addWord(ownerBusinessHome.getCardNoByType("OWNER_RSHIP").getNumber());
            }

            if(hb.getAfterBusinessHouse().getSaleContract()!=null) {
                key.addWord(hb.getAfterBusinessHouse().getSaleContract().getContractNumber());

                if (hb.getAfterBusinessHouse().getDeveloperName()!=null && !hb.getAfterBusinessHouse().getDeveloperName().equals("")){
                    key.addWord(hb.getAfterBusinessHouse().getDeveloperName());
                }
            }
            if(hb.getStartBusinessHouse().getSaleContract()!=null) {
                    key.addWord(hb.getStartBusinessHouse().getSaleContract().getContractNumber());

                if (hb.getAfterBusinessHouse().getDeveloperName()!=null && !hb.getAfterBusinessHouse().getDeveloperName().equals("")){
                    key.addWord(hb.getAfterBusinessHouse().getDeveloperName());
                }
            }


            hb.setSearchKey(key.getKey());
        }

    }
}
