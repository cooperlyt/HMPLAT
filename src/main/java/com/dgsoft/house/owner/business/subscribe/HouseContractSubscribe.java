package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.SaleType;
import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.HouseBusiness;
import com.dgsoft.house.owner.model.HouseContract;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2018-03-20.
 * 交易，商品房 合同信息
 */
@Name("houseContractSubscribe")
public class HouseContractSubscribe extends OwnerEntityHome<HouseContract> {
    @In
    private OwnerBusinessHome ownerBusinessHome;

    @Override
    public void create(){
        super.create();
        for (HouseBusiness houseBusiness:ownerBusinessHome.getInstance().getHouseBusinesses()){
            if (houseBusiness.getHouseContract()!=null){
                if (houseBusiness.getHouseContract().getId()!=null){
                   setId(houseBusiness.getHouseContract().getId());
                }else{
                   setInstance(houseBusiness.getHouseContract());
                }
            }else{
                getInstance().setHouseBusiness(houseBusiness);
                houseBusiness.setHouseContract(getInstance());
            }
        }
    }
}
