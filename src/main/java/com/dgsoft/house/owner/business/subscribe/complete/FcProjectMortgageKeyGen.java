package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.action.OwnerHouseHelper;
import com.dgsoft.house.owner.model.HouseBusiness;
import com.dgsoft.house.owner.model.HouseContract;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2017-07-21.
 */
@Name("fcProjectMortgageKeyGen")
public class FcProjectMortgageKeyGen implements TaskCompleteSubscribeComponent {

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

            if(ownerBusinessHome.getInstance().getMortgaegeRegiste()!=null && ownerBusinessHome.getInstance().getMortgaegeRegiste().getFinancial()!=null){
                key.addWord(ownerBusinessHome.getInstance().getMortgaegeRegiste().getFinancial().getName());
            }

            if(ownerBusinessHome.getInstance().getMortgaegeRegiste()!=null && ownerBusinessHome.getInstance().getMortgaegeRegiste().getOldFinancial()!=null){
                key.addWord(ownerBusinessHome.getInstance().getMortgaegeRegiste().getOldFinancial().getName());
            }

            if(ownerBusinessHome.getCardNoByType("MORTGAGE_CONTRACT")!=null) {
                key.addWord(ownerBusinessHome.getCardNoByType("MORTGAGE_CONTRACT").getNumber());
            }

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
