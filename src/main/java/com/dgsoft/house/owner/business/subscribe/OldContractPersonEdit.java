package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.model.PowerPerson;
import org.jboss.seam.annotations.Name;

/**
 * Created by cooper on 10/12/2016.
 */
@Name("oldContractPersonEdit")
public class OldContractPersonEdit extends BasePowerPersonInput{
    @Override
    protected PowerPerson.PowerPersonType getPowerPersonType() {
        return PowerPerson.PowerPersonType.CONTRACT;
    }

    @Override
    protected boolean isOld() {
        return true;
    }
}
