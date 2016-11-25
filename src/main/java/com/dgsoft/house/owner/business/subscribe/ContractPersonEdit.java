package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.model.PowerPerson;
import org.jboss.seam.annotations.Name;

/**
 * Created by cooper on 24/11/2016.
 */
@Name("contractPersonEdit")
public class ContractPersonEdit extends BasePowerPersonInput {
    @Override
    protected PowerPerson.PowerPersonType getPowerPersonType() {
        return PowerPerson.PowerPersonType.CONTRACT;
    }
}
