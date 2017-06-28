package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.model.BusinessHouse;
import com.dgsoft.house.owner.model.ProjectMortgage;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Logging;

/**
 * Created by wxy on 2017-06-13.
 */
@Name("financialProjectCancelSubscribe")
public class FinancialProjectCancelSubscribe extends FinancialBaseCancelSubscribe {
    @Override
    protected void addMortgage() {
        if (ownerBusinessHome.getInstance().getHouseBusinesses().iterator().next()!=null &&
                ownerBusinessHome.getInstance().getHouseBusinesses().iterator().next().getAfterBusinessHouse()!=null){

            BusinessHouse businessHouse = ownerBusinessHome.getInstance().getHouseBusinesses().iterator().next().getAfterBusinessHouse();
            ProjectMortgage projectMortgage = new ProjectMortgage();
            projectMortgage.setDeveloperCode(businessHouse.getDeveloperCode());
            projectMortgage.setDeveloperName(businessHouse.getDeveloperName());
            projectMortgage.setMortgaegeRegiste(mortgaegeRegiste);
            mortgaegeRegiste.setProjectMortgage(projectMortgage);

        }
    }
}
