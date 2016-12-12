package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.model.PowerPerson;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2016-12-11.
 * 原产权人编辑
 */
@Name("oldOwnerPersonEdit")
public class OldOwnerPersonEdit extends BasePowerPersonInput {
    @Override
    protected PowerPerson.PowerPersonType getPowerPersonType() {
        return PowerPerson.PowerPersonType.OWNER;
    }

    @Override
    protected boolean isOld() {
        return true;
    }
}
