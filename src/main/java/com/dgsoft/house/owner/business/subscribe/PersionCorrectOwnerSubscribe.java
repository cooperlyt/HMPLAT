package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.common.system.PersonEntity;
import com.dgsoft.house.owner.model.BusinessPersion;
import com.dgsoft.house.owner.model.BusinessProject;
import com.dgsoft.house.owner.model.ContractOwner;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Logging;

/**
 * Created by wxy on 2015-09-09.
 * 备案人转成申请人
 */
@Name("persionCorrectOwnerSubscribe")
public class PersionCorrectOwnerSubscribe extends BaseBusinessPersionSubscribe {
    @Override
    protected BusinessPersion.PersionType getType() {
        return  BusinessPersion.PersionType.CORRECT;
    }

    @Override
    public void create() {
        super.create();
        if (!isHave()) {
            clearInstance();

            ContractOwner contractOwner = ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getContractOwner();
            if (contractOwner != null) {
                getInstance().setCredentialsType(contractOwner.getCredentialsType());
                getInstance().setCredentialsNumber(contractOwner.getCredentialsNumber());
                getInstance().setPersonName(contractOwner.getPersonName());
                getInstance().setPhone(contractOwner.getPhone());
            }
            getInstance().setOwnerBusiness(ownerBusinessHome.getInstance());
            ownerBusinessHome.getInstance().getBusinessPersions().add(getInstance());
            setHave(true);
        }
    }
}
