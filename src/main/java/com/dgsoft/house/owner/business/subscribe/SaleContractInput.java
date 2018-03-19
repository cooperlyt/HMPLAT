package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.common.system.business.TaskSubscribeComponent;
import com.dgsoft.house.SaleType;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.HouseContract;
import org.jboss.seam.annotations.In;

/**
 * Created by cooper on 27/11/2016.
 */

public abstract class SaleContractInput implements TaskSubscribeComponent {

    protected abstract SaleType getSaleType();

    @In
    protected OwnerBusinessHome ownerBusinessHome;

    private HouseContract houseContract;

    public HouseContract getHouseContract() {
        if (houseContract == null){
            houseContract = ownerBusinessHome.getSingleHoues().getHouseContract();
            if (houseContract == null){
                ownerBusinessHome.getSingleHoues().setHouseContract(new HouseContract(getSaleType(),ownerBusinessHome.getSingleHoues()));
            }
        }
        return houseContract;
    }


    @Override
    public void initSubscribe() {

    }

    @Override
    public void validSubscribe() {

    }

    @Override
    public boolean isPass() {
        return ownerBusinessHome.getSingleHoues().getHouseContract() != null;
    }

    @Override
    public boolean saveSubscribe() {
        return isPass();
    }
}
