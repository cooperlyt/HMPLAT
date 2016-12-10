package com.dgsoft.house.owner.business.create;

import com.dgsoft.house.owner.model.PowerPerson;
import org.jboss.seam.annotations.Name;

/**
 * Created by cooper on 10/12/2016.
 */
@Name("extractContractPersonToOld")
public class ExtractContractPersonToOld extends PowerPersonFill{

    @Override
    protected PowerPerson.PowerPersonType getType() {
        return PowerPerson.PowerPersonType.CONTRACT;
    }

    @Override
    protected boolean isfillToOld() {
        return true;
    }
}
