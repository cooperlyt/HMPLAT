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

            if(hb.getHouseContract()!=null) {
                    key.addWord(hb.getHouseContract().getContractNumber());

            }


            if ((hb.getHouseContract()  == null) && (ownerBusinessHome.getInstance().getSelectBusiness() != null) &&
                    (ownerBusinessHome.getInstance().getSelectBusiness().getSingleHoues().getHouseContract()!=null) &&
                    (ownerBusinessHome.getInstance().getSelectBusiness().getHouseBusinesses().size() > 0)){
                key.addWord(ownerBusinessHome.getInstance().getSelectBusiness().getSingleHoues().getHouseContract().getContractNumber());
            }

            hb.setSearchKey(key.getKey());
        }

    }
}
