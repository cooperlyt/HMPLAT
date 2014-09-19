package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.action.BusinessHouseOwnerHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessHouseOwner;
import org.jboss.seam.annotations.In;

/**
 * Created by cooper on 9/19/14.
 */
public abstract class BaseHouseOwnerSubscribe extends BusinessHouseOwnerHome {

    @In
    private OwnerBusinessHome ownerBusinessHome;

    protected abstract BusinessHouseOwner.HouseOwnerType getType();

//    @In
//    private HouseEntityLoader houseEntityLoader;

    @Override
    public Class<BusinessHouseOwner> getEntityClass() {
        return BusinessHouseOwner.class;
    }

    @Override
    public void create() {
        super.create();
        for (BusinessHouseOwner owner : ownerBusinessHome.getSingleHoues().getBusinessHouseOwners()) {
            if (owner.getType().equals(getType())) {
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

        getInstance().setBusinessHouse(ownerBusinessHome.getSingleHoues());
        ownerBusinessHome.getSingleHoues().getBusinessHouseOwners().add(getInstance());
    }

    @Override
    protected BusinessHouseOwner createInstance() {
        return new BusinessHouseOwner(getType());
    }

}
