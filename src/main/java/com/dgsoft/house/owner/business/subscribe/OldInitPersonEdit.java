package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.model.PowerPerson;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2016-12-17.
 */
@Name("oldInitPersonEdit")
public class OldInitPersonEdit extends BasePowerPersonInput {
    @Override
    protected PowerPerson.PowerPersonType getPowerPersonType() {
        return PowerPerson.PowerPersonType.INIT;
    }

    @Override
    protected boolean isOld() {
        return true;
    }
}
