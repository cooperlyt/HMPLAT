package com.dgsoft.house.owner.business.create;

import com.dgsoft.common.system.business.BusinessDataFill;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.PowerPerson;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2017-06-13.
 */
@Name("contractPersonFill")
public class ContractPersonFill implements BusinessDataFill {
    @In
    private OwnerBusinessHome ownerBusinessHome;
    @Override
    public void fillData() {



            for(PowerPerson pp: ownerBusinessHome.getInstance().getSelectBusiness().getSingleHoues().getAfterBusinessHouse().getPowerPersons()){

                if (!pp.isOld() && pp.getType().equals(PowerPerson.PowerPersonType.CONTRACT) ){

                    ownerBusinessHome.getInstance().getSingleHoues().getAfterBusinessHouse().getPowerPersons().add(new PowerPerson(PowerPerson.PowerPersonType.CONTRACT, true, pp, pp.getPriority()));
                }
            }


    }
}
