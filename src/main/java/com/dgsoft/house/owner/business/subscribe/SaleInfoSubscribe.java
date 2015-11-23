package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.HouseBusiness;
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
        for(HouseBusiness houseBusiness:ownerBusinessHome.getInstance().getHouseBusinesses()){
            if (!houseBusiness.getAfterBusinessHouse().getSaleInfos().isEmpty()){
                if (houseBusiness.getAfterBusinessHouse().getSaleInfos().iterator().next().getId()!=null){
                    setId(houseBusiness.getAfterBusinessHouse().getSaleInfos().iterator().next().getId());
                }else{
                    setInstance(houseBusiness.getAfterBusinessHouse().getSaleInfos().iterator().next());
                }
            }else{
                getInstance().setBusinessHouse(houseBusiness.getAfterBusinessHouse());
                houseBusiness.getAfterBusinessHouse().getSaleInfos().add(getInstance());
            }

        }

    }





}
