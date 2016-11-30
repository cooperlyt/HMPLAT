package com.dgsoft.house.owner.business.create;

import com.dgsoft.house.owner.model.PowerPerson;
import org.jboss.seam.annotations.Name;

/**
 * Created by cooper on 27/11/2016.
 */
@Name("initPowerPersonFill")
public class InitPowerPersonFill extends PowerPersonFill{
    @Override
    protected PowerPerson.PowerPersonType getType() {
        return PowerPerson.PowerPersonType.INIT;
    }

    @Override
    protected boolean isfillToOld() {
        return false;
    }
}
