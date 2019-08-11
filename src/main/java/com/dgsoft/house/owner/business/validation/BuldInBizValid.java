package com.dgsoft.house.owner.business.validation;

import com.dgsoft.common.system.business.BusinessDataValid;
import com.dgsoft.house.model.Build;
import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.model.BusinessBuild;
import com.dgsoft.house.owner.model.OwnerBusiness;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Logging;

import java.util.List;

/**
 * Created by wxy on 2019-08-11.
 */
@Name("buldInBizValid")
@Scope(ScopeType.STATELESS)
public class BuldInBizValid extends BulidValid {
    @In(create = true)
    protected OwnerEntityLoader ownerEntityLoader;

    @Override
    public ValidResult valid(Build build) {
        List<BusinessBuild> businessBuilds = ownerEntityLoader.getEntityManager().createQuery("select bizBuid from BusinessBuild bizBuid where bizBuid.businessProject.ownerBusiness.status in('RUNNING','SUSPEND','MODIFYING') and bizBuid.businessProject.ownerBusiness.type<>'CANCEL_BIZ' and bizBuid.buildCode=:buildCode", BusinessBuild.class)
                .setParameter("buildCode", build.getBuildCode()).getResultList();
        if (businessBuilds.size() > 0){
            if(businessBuilds.size() > 1){
                Logging.getLog(getClass()).warn("one build have mulit business");
            }
            return new ValidResult("build_in_biz", ValidResultLevel.ERROR, businessBuilds.get(0).getBusinessProject().getOwnerBusiness().getDefineName(),build.getBuildCode());
        }

        return new ValidResult(ValidResultLevel.SUCCESS);
    }
}
