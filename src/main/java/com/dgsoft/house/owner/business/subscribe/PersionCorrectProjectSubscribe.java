package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.common.system.PersonEntity;
import com.dgsoft.house.owner.model.BusinessHouseOwner;
import com.dgsoft.house.owner.model.BusinessPersion;
import com.dgsoft.house.owner.model.BusinessProject;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2015-09-05.
 * 在建工程抵抵押申请人
 */
@Name("persionCorrectProjectSubscribe")
public class PersionCorrectProjectSubscribe extends BaseBusinessPersionSubscribe {


    @Override
    protected BusinessPersion.PersionType getType() {
        return  BusinessPersion.PersionType.CORRECT;
    }

    @Override
    public void create() {
        super.create();
        if (!isHave()) {
            clearInstance();

                BusinessPersion businessPersion=ownerBusinessHome.getInstance().getMortgageProject();
                if (businessPersion != null) {
                    getInstance().setCredentialsType(businessPersion.getCredentialsType());
                    getInstance().setCredentialsNumber(businessPersion.getCredentialsNumber());
                    getInstance().setPersonName(businessPersion.getPersonName());
                    getInstance().setPhone(businessPersion.getPhone());
                }

            getInstance().setOwnerBusiness(ownerBusinessHome.getInstance());
            ownerBusinessHome.getInstance().getBusinessPersions().add(getInstance());
            setHave(true);
        }
    }
}