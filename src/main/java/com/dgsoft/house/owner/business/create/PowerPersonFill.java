package com.dgsoft.house.owner.business.create;

import com.dgsoft.common.system.business.BusinessDataFill;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.HouseBusiness;
import com.dgsoft.house.owner.model.PowerPerson;
import org.jboss.seam.annotations.In;

/**
 * Created by cooper on 27/11/2016.
 */
public abstract class PowerPersonFill implements BusinessDataFill {

    protected abstract PowerPerson.PowerPersonType getType();

    protected abstract boolean isfillToOld();

    @In
    private OwnerBusinessHome ownerBusinessHome;

    @Override
    public void fillData() {
        for(HouseBusiness hb: ownerBusinessHome.getInstance().getHouseBusinesses()){
            for(PowerPerson pp: hb.getStartBusinessHouse().getPowerPersons()){
                if (!pp.isOld() && getType().equals(pp.getType())){
                    hb.getAfterBusinessHouse().getPowerPersons().add(new PowerPerson(getType(),isfillToOld(),pp));
                }
            }
        }
    }
}
