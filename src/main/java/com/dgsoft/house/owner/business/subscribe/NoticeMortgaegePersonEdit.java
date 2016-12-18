package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.model.PowerPerson;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2016-12-18.
 * 预告人
 */
@Name("noticeMortgaegePersonEdit")
public class NoticeMortgaegePersonEdit extends BasePowerPersonInput {
    @Override
    protected PowerPerson.PowerPersonType getPowerPersonType() {
        return PowerPerson.PowerPersonType.PREPARE;
    }

    @Override
    protected boolean isOld() {
        return false;
    }
}
