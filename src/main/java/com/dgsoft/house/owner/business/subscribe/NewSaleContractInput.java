package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.HouseStatus;
import com.dgsoft.house.SaleType;
import com.dgsoft.house.owner.action.OwnerHouseHelper;
import org.jboss.seam.annotations.Name;

import java.util.List;

/**
 * Created by cooper on 27/11/2016.
 */
@Name("newSaleContractInput")
public class NewSaleContractInput extends SaleContractInput {

    @Override
    protected SaleType getSaleType() {
        List<HouseStatus> allStatus = OwnerHouseHelper.instance().getHouseAllStatus(ownerBusinessHome.getSingleHoues().getHouseCode());
        return (allStatus.contains(HouseStatus.INIT_REG_CONFIRM) || allStatus.contains(HouseStatus.INIT_REG)) ? SaleType.NOW_SELL : SaleType.MAP_SELL;

    }
}
