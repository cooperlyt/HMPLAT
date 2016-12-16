package com.dgsoft.house.owner.business.create;

import com.dgsoft.common.system.business.BusinessDataFill;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessHouse;
import com.dgsoft.house.owner.model.HouseBusiness;
import com.dgsoft.house.owner.model.PowerPerson;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2016-12-16.
 * 备案人提取到当前产权人
 */
@Name("contractPersonToNewOwner")
public class ContractPersonToNewOwner extends PowerPersonFill {


    @Override
    protected PowerPerson.PowerPersonType getType() {
        return PowerPerson.PowerPersonType.CONTRACT;
    }

    @Override
    protected PowerPerson.PowerPersonType getToType(){
        return PowerPerson.PowerPersonType.OWNER;
    }

    @Override
    protected boolean isfillToOld() {
        return false;
    }
}
