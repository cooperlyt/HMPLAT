package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.HouseBusiness;
import com.dgsoft.house.owner.model.PowerPerson;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by cooper on 20/11/2016.
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



        KeyGeneratorHelper businessKey = new KeyGeneratorHelper(ownerBusinessHome.getInstance().getSearchKey());

        for(HouseBusiness hb: ownerBusinessHome.getInstance().getHouseBusinesses()){
            KeyGeneratorHelper key = new KeyGeneratorHelper(hb.getSearchKey());
            for (PowerPerson pp : hb.getAfterBusinessHouse().getPowerPersons()){
                if (!pp.isOld()){
                    key.addWord(pp.getPersonName());
                    businessKey.addWord(pp.getPersonName());
                    key.addWord(pp.getCredentialsNumber());
                    businessKey.addWord(pp.getCredentialsNumber());


                }


            }
            hb.setSearchKey(key.getKey());
        }
        ownerBusinessHome.getInstance().setSearchKey(businessKey.getKey());



    }
}