package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.common.system.PersonEntity;
import com.dgsoft.house.owner.model.BusinessPersion;
import com.dgsoft.house.owner.model.BusinessProject;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2015-09-05.
 * 在建工程的抵押人
 */
@Name("persionMortgageProjectSubscribe")
public class PersionMortgageProjectSubscribe extends BaseBusinessPersionSubscribe {
    @Override
    protected BusinessPersion.PersionType getType() {
        return  BusinessPersion.PersionType.MORTGAGE_PROJECT;
    }
    @Override
    public void create(){
        super.create();
        if (!isHave()){
            clearInstance();

            for(BusinessPersion businessPersion:ownerBusinessHome.getInstance().getBusinessPersions()){
                if (businessPersion.getType().equals(BusinessPersion.PersionType.MORTGAGE_PROJECT)){
                    if (businessPersion.getId()==null){
                        setInstance(businessPersion);
                    }else {
                        setId(businessPersion.getId());
                    }
                    setHave(true);
                    return;

                }

            }

            getInstance().setOwnerBusiness(ownerBusinessHome.getInstance());
            ownerBusinessHome.getInstance().getBusinessPersions().add(getInstance());
            setHave(true);
        }

    }
}
