package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.model.BusinessPersion;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2015-08-14.
 * 业务相关人 没有CHECKBOX
 */
@Name("basePersionMustHaveSubscribe")
public class BasePersionMustHaveSubscribe extends BaseBusinessPersionSubscribe {


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

    @Override
    protected BusinessPersion.PersionType getType() {
        return BusinessPersion.PersionType.RECORD_OWNER;
    }



}
