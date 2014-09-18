package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.HouseEntityLoader;
import com.dgsoft.house.model.House;
import com.dgsoft.house.owner.action.BusinessHouseOwnerHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessHouseOwner;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by cooper on 8/26/14.
 */
@Name("businessHouseOwnerSubscribe")
public class BusinessHouseOwnerSubscribe extends BusinessHouseOwnerHome {

    @In
    private OwnerBusinessHome ownerBusinessHome;

//    @In
//    private HouseEntityLoader houseEntityLoader;

    @Override
    public Class<BusinessHouseOwner> getEntityClass() {
        return BusinessHouseOwner.class;
    }

    @Override
    public void create() {
        super.create();
        for (BusinessHouseOwner owner : ownerBusinessHome.getInstance().getBusinessHouseOwners()) {
            if (owner.getType().equals(BusinessHouseOwner.HouseOwnerType.OWNER_PERSON)) {
                setId(owner.getId());
                return;
            }
        }
//        if (!ownerBusinessHome.isManaged()) {
//            House house = houseEntityLoader.getEntityManager().find(House.class, ownerBusinessHome.getSingleHoues().getHouseCode());
//            if (house.getHouseOwner() != null){
//                getInstance().setPersonName(house.getHouseOwner().getName());
//                getInstance().setCredentialsType(house.getHouseOwner().getCredentialsType());
//                getInstance().setCredentialsNumber(house.getHouseOwner().getCerdentialsNumber());
//                getInstance().setPhone(house.getHouseOwner().getPhone());
//                getInstance().setRootAddress(house.getHouseOwner().getRootAddress());
//            }
//        }
        getInstance().setOwnerBusiness(ownerBusinessHome.getInstance());
        ownerBusinessHome.getInstance().getBusinessHouseOwners().add(getInstance());
    }

    @Override
    protected BusinessHouseOwner createInstance() {
        return new BusinessHouseOwner(BusinessHouseOwner.HouseOwnerType.OWNER_PERSON);
    }


}
