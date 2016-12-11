package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.model.PowerPerson;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2016-12-11.
 * 产权人共有权人，编辑
 */
@Name("ownerPersonEdit")
public class OwnerPersonEdit extends BasePowerPersonInput {

    @Override
    protected PowerPerson.PowerPersonType getPowerPersonType() {
        return PowerPerson.PowerPersonType.OWNER;
    }

    @Override
    protected boolean isOld() {
        return false;
    }
}
