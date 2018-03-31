package com.dgsoft.house.owner.business.create;

import com.dgsoft.house.owner.model.PowerPerson;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2018-01-30.
 * 初始登记（查封）业务提备案人到现
 */
@Name("contractPowerPersonFill")
public class ContractPowerPersonFill extends PowerPersonFill {
    @Override
    protected PowerPerson.PowerPersonType getType() {
        return PowerPerson.PowerPersonType.CONTRACT;
    }

    @Override
    protected boolean isfillToOld() {
        return false;
    }
}
