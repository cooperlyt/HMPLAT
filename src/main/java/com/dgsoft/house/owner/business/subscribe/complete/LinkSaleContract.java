package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.house.SaleType;
import org.jboss.seam.annotations.Name;

import java.util.EnumSet;

/**
 * Created by cooper on 04/12/2016.
 */
@Name("linkSaleContract")
public class LinkSaleContract extends LinkContract {
    @Override
    protected EnumSet<SaleType> getLinkTypes() {
        return EnumSet.of(SaleType.MAP_SELL,SaleType.NOW_SELL,SaleType.OLD_SELL);
    }
}
