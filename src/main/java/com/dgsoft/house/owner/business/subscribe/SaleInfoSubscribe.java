package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.HouseBusinessHome;
import com.dgsoft.house.owner.model.SaleInfo;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-8-28
 * Time: 下午3:09
 * To change this template use File | Settings | File Templates.
 */
@Name("saleInfoSubscribe")
public class SaleInfoSubscribe extends OwnerEntityHome<SaleInfo> {

    @In
    private HouseBusinessHome houseBusinessHome;



    @Override
    public void create()
    {
        super.create();
        if (!houseBusinessHome.getInstance().getSaleInfos().isEmpty()) {
            setId(houseBusinessHome.getInstance().getSaleInfos().iterator().next().getId());

        }else {
            getInstance().setOwnerBusiness(houseBusinessHome.getInstance());
            houseBusinessHome.getInstance().getSaleInfos().add(getInstance());
        }
    }





}
