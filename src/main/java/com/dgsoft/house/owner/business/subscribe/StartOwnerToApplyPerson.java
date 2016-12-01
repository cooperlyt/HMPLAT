package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.model.BusinessPersion;
import com.dgsoft.house.owner.model.PowerPerson;
import org.jboss.seam.annotations.Name;

/**
 * Created by cooper on 30/11/2016.
 */
@Name("startOwnerToApplyPerson")
public class StartOwnerToApplyPerson extends BaseBusinessPersionSubscribe {
    @Override
    protected BusinessPersion.PersionType getType() {
        return  BusinessPersion.PersionType.CORRECT;
    }

    @Override
    public void create(){
        super.create();
        if (!isHave()){
            clearInstance();

            //if (ownerBusinessHome.getInstance().getHouseBusinesses().size()==1){
            PowerPerson businessHouseOwner = ownerBusinessHome.getInstance().getHouseBusinesses().iterator().next().getStartBusinessHouse().getMainPowerPerson();

            convertToPerson(businessHouseOwner);
            //}
            getInstance().setOwnerBusiness(ownerBusinessHome.getInstance());
            ownerBusinessHome.getInstance().getBusinessPersions().add(getInstance());
            setHave(true);
        }

    }
}
