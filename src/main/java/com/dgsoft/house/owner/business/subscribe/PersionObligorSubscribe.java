package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.model.BusinessHouseOwner;
import com.dgsoft.house.owner.model.BusinessPersion;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Logging;

/**
 * Created by wxy on 2015-08-26.
 * 债务人
 */
@Name("persionObligorSubscribe")
public class PersionObligorSubscribe extends BaseBusinessPersionSubscribe {
    @Override
    protected BusinessPersion.PersionType getType() {
        return BusinessPersion.PersionType.MORTGAGE_OBLIGOR;
    }
//    @Override
//    public void create(){
//        super.create();
//        if (!isHave()){
//            clearInstance();
//
//            for(BusinessPersion businessPersion:ownerBusinessHome.getInstance().getBusinessPersions()){
//                if (businessPersion.getType().equals(BusinessPersion.PersionType.MORTGAGE_OBLIGOR)){
//                    if (businessPersion.getId()==null){
//                        setInstance(businessPersion);
//                    }else {
//                        setId(businessPersion.getId());
//                    }
//                    setHave(true);
//                    return;
//
//                }
//
//            }
//
//            getInstance().setOwnerBusiness(ownerBusinessHome.getInstance());
//            ownerBusinessHome.getInstance().getBusinessPersions().add(getInstance());
//            setHave(true);
//        }
//
//    }
}
