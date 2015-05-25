package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.common.EntityHomeAdapter;
import com.dgsoft.common.system.PersonEntityHelper;
import com.dgsoft.common.system.model.Person;
import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessHouseOwner;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by cooper on 10/15/14.
 */
@Name("businessHouseOwnerSubscribe")
public class BusinessHouseOwnerSubscribe extends OwnerEntityHome<BusinessHouseOwner> {

    @In
    private OwnerBusinessHome ownerBusinessHome;

    public class HouseOwnerEntityHelper extends PersonEntityHelper<BusinessHouseOwner> {

        public HouseOwnerEntityHelper(BusinessHouseOwnerSubscribe businessHouseOwnerSubscribe) {
            this.businessHouseOwnerSubscribe = businessHouseOwnerSubscribe;
        }

        private BusinessHouseOwnerSubscribe businessHouseOwnerSubscribe;

        @Override
        protected EntityHomeAdapter<BusinessHouseOwner> getEntityHome() {
            return businessHouseOwnerSubscribe;
        }

        @Override
        protected void fillPerson(Person person){
            businessHouseOwnerSubscribe.getInstance().setRootAddress(person.getCredentialsOrgan());
        }
    }


    private HouseOwnerEntityHelper houseOwnerEntityHelper;

    @Override
    public  BusinessHouseOwner createInstance(){
        BusinessHouseOwner result = new BusinessHouseOwner();
        ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().setBusinessHouseOwner(result);
        return result;

    }

    @Override
    public void create() {
        super.create();
        houseOwnerEntityHelper = new HouseOwnerEntityHelper(this);

        if (ownerBusinessHome.getSingleHoues().getBusinessHouseOwner() != null) {
                setId(ownerBusinessHome.getSingleHoues().getBusinessHouseOwner().getId());
        }else{
            clearInstance();
            ownerBusinessHome.getSingleHoues().setBusinessHouseOwner(getInstance());
        }
    }

    public HouseOwnerEntityHelper getHouseOwnerEntityHelper() {
        return houseOwnerEntityHelper;
    }

}
