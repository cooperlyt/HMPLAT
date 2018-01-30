package com.dgsoft.house.owner.business.create;

import com.dgsoft.house.owner.model.PowerPerson;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2018-01-30.
 * 初始登记业务提预告人到现
 */
@Name("preparePowerPersonFill")
public class PreparePowerPersonFill extends PowerPersonFill {
    @Override
    protected PowerPerson.PowerPersonType getType() {
        return PowerPerson.PowerPersonType.PREPARE;
    }

    @Override
    protected boolean isfillToOld() {
        return false;
    }
}
