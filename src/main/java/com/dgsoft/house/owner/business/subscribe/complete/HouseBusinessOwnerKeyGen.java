package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.action.OwnerHouseHelper;
import com.dgsoft.house.owner.model.HouseBusiness;
import com.dgsoft.house.owner.model.HouseContract;
import com.dgsoft.house.owner.model.PowerPerson;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Logging;

/**
 * Created by cooper on 20/11/2016.
 * serchkey 房屋和现权力人查询KEY生成器
 */
@Name("houseBusinessOwnerKeyGen")
public class HouseBusinessOwnerKeyGen implements TaskCompleteSubscribeComponent {

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
            KeyGeneratorHelper key = OwnerHouseHelper.genHouseSearchKey(hb.getAfterBusinessHouse());

            if(hb.getAfterBusinessHouse().getHouseContracts()!=null) {
                for (HouseContract hc : hb.getAfterBusinessHouse().getHouseContracts()) {
                    key.addWord(hc.getContractNumber());
                }
            }
            if(hb.getStartBusinessHouse().getHouseContracts()!=null) {
                for (HouseContract hc : hb.getStartBusinessHouse().getHouseContracts()) {
                    key.addWord(hc.getContractNumber());
                }
            }
            hb.setSearchKey(key.getKey());
        }

    }
}
