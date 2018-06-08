package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.model.BusinessPersion;
import com.dgsoft.house.owner.model.PowerPerson;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2018-06-07.
 * 承租人
 */
@Name("persionLesseeSubscribe")
public class PersionLesseeSubscribe extends BaseBusinessPersionSubscribe {
    @Override
    protected BusinessPersion.PersionType getType() {
        return BusinessPersion.PersionType.LESSEE_PEOPLE;
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
