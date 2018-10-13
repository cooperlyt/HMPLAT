package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.OldSellContractInfo;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2018-10-13.
 */
@Name("oldSellContractInfoSubsrcibe")
public class OldSellContractInfoSubsrcibe extends OwnerEntityHome<OldSellContractInfo> {

    @In
    private OwnerBusinessHome ownerBusinessHome;



    @Override
    public void create() {
        super.create();
        if (!ownerBusinessHome.getInstance().getOldSellContractInfos().isEmpty()) {
            setId(ownerBusinessHome.getInstance().getOldSellContractInfos().iterator().next().getId());
        } else {
            getInstance().setOwnerBusiness(ownerBusinessHome.getInstance());
            ownerBusinessHome.getInstance().getOldSellContractInfos().add(getInstance());
        }
    }
}
