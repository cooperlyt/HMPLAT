package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.SaleType;
import org.jboss.seam.annotations.Name;

/**
 * Created by cooper on 27/11/2016.
 */
@Name("oldSaleContractInput")
public class OldSaleContractInput extends SaleContractInput {
    @Override
    protected SaleType getSaleType() {
        return SaleType.OLD_SELL;
    }
}
