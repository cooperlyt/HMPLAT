package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.action.OwnerHouseHelper;
import com.dgsoft.house.owner.model.HouseBusiness;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2017-07-21.
 * 东港
 *
 */
@Name("noteMortgageKeyGen")
public class NoteMortgageKeyGen implements TaskCompleteSubscribeComponent {

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

//            if(ownerBusinessHome.getCardNoByType("NOTICE")!=null) {
//                key.addWord(ownerBusinessHome.getCardNoByType("NOTICE").getNumber());
//            }
//
//            if(ownerBusinessHome.getCardNoByType("NOTICE_MORTGAGE")!=null) {
//                key.addWord(ownerBusinessHome.getCardNoByType("NOTICE_MORTGAGE").getNumber());
//            }
//
            if(ownerBusinessHome.getInstance().getMortgaegeRegiste()!=null && ownerBusinessHome.getInstance().getMortgaegeRegiste().getFinancial()!=null){
                key.addWord(ownerBusinessHome.getInstance().getMortgaegeRegiste().getFinancial().getName());
            }

            if(ownerBusinessHome.getInstance().getMortgaegeRegiste()!=null && ownerBusinessHome.getInstance().getMortgaegeRegiste().getOldFinancial()!=null){
                key.addWord(ownerBusinessHome.getInstance().getMortgaegeRegiste().getOldFinancial().getName());
            }

            hb.setSearchKey(key.getKey());
        }

    }
}