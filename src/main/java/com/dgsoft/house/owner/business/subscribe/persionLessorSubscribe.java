package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.model.BusinessPersion;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2018-06-07.
 * 出租人
 */
@Name("persionLessorSubscribe")
public class persionLessorSubscribe extends BaseBusinessPersionSubscribe {
    @Override
    protected BusinessPersion.PersionType getType() {
        return BusinessPersion.PersionType.LESSOR_PEOPLE;
    }

    @Override
    public void create(){
        super.create();
        if (!isHave()){
            clearInstance();


            getInstance().setOwnerBusiness(ownerBusinessHome.getInstance());
            ownerBusinessHome.getInstance().getBusinessPersions().add(getInstance());
            setHave(true);
        }

    }
}
