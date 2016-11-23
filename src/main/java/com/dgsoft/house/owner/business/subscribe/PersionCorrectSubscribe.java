package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.model.BusinessPersion;
import com.dgsoft.house.owner.model.PowerPerson;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2015-08-16.
 * 产权人转申请人
 */
@Name("persionCorrectSubscribe")
public class PersionCorrectSubscribe extends BaseBusinessPersionSubscribe {
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
                PowerPerson businessHouseOwner = ownerBusinessHome.getInstance().getHouseBusinesses().iterator().next().getAfterBusinessHouse().getMainPowerPerson();
                if (businessHouseOwner !=null){
                    getInstance().setCredentialsType(businessHouseOwner.getCredentialsType());
                    getInstance().setCredentialsNumber(businessHouseOwner.getCredentialsNumber());
                    getInstance().setPersonName(businessHouseOwner.getPersonName());
                    getInstance().setPhone(businessHouseOwner.getPhone());
                }

            //}
            getInstance().setOwnerBusiness(ownerBusinessHome.getInstance());
            ownerBusinessHome.getInstance().getBusinessPersions().add(getInstance());
            setHave(true);
        }

    }
}
