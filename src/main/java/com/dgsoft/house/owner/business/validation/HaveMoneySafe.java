package com.dgsoft.house.owner.business.validation;

import com.dgsoft.common.system.business.BusinessDataValid;
import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.model.BusinessBuild;
import com.dgsoft.house.owner.model.BusinessHouse;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import java.util.List;

/**
 * Created by wxy on 2019-08-16.
 * 必须有监管账户信息
 */
@Name("haveMoneySafe")
@Scope(ScopeType.STATELESS)
public class HaveMoneySafe extends BusinessHouseValid {
    @In(create = true)
    private OwnerEntityLoader ownerEntityLoader;


    @Override
    public ValidResult valid(BusinessHouse businessHouse) {

        BusinessBuild  businessBuild = null;

        List<BusinessBuild> businessBuilds = ownerEntityLoader.getEntityManager().createQuery("select bizBuid from BusinessBuild bizBuid where bizBuid.businessProject.ownerBusiness.status in('COMPLETE') and bizBuid.businessProject.ownerBusiness.type<>'CANCEL_BIZ' and bizBuid.buildCode=:buildCode", BusinessBuild.class)
                .setParameter("buildCode", businessHouse.getBuildCode()).getResultList();

        if (businessBuilds != null && businessBuilds.size() > 0) {
            businessBuild = businessBuilds.get(0);
        }
        if (businessBuild!=null && businessBuild.getBusinessProject()!=null && businessBuild.getBusinessProject().getMoneySafe()!=null){
            return new ValidResult(ValidResultLevel.SUCCESS);

        }
        return new ValidResult("house_have_moneySafe",ValidResultLevel.ERROR);
    }
}
