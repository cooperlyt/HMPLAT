package com.dgsoft.house.owner.business.create;

import com.dgsoft.house.owner.model.PowerPerson;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2018-03-31.
 * 初始登记（查封）提产权人到现
 */
@Name("ownerPowerPersonFill")
public class OwnerPowerPersonFill extends PowerPersonFill {
    @Override
    protected PowerPerson.PowerPersonType getType() {
        return PowerPerson.PowerPersonType.OWNER;
    }

    @Override
    protected boolean isfillToOld() {
        return false;
    }
}
