package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
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
    private OwnerBusinessHome ownerBusinessHome;



    @Override
    public void create()
    {
        super.create();
        if (!ownerBusinessHome.getInstance().getSaleInfos().isEmpty()) {
            setId(ownerBusinessHome.getInstance().getSaleInfos().iterator().next().getId());

        }else {
            getInstance().setOwnerBusiness(ownerBusinessHome.getInstance());
            ownerBusinessHome.getInstance().getSaleInfos().add(getInstance());
        }
    }





}
