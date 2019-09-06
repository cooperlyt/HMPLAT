package com.dgsoft.house.owner.business.validation;

import com.dgsoft.common.system.business.BusinessDataValid;
import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.model.BusinessBuild;
import com.dgsoft.house.owner.model.ProjectCheck;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Logging;

import java.util.List;

/**
 * Created by wxy on 2019-09-06.
 */
@Name("businessBuildInPC")
@Scope(ScopeType.STATELESS)
public class BusinessBuildInPC extends BusinessBuildValid {

    @In(create = true)
    protected OwnerEntityLoader ownerEntityLoader;
    @Override

    public ValidResult valid(BusinessBuild build) {
        List<ProjectCheck> projectCheckList = ownerEntityLoader.getEntityManager().createQuery("select pc from ProjectCheck pc where pc.ownerBusiness.status in('RUNNING','SUSPEND','MODIFYING') and pc.ownerBusiness.type<>'CANCEL_BIZ' and pc.buildCode=:buildCode", ProjectCheck.class)
                .setParameter("buildCode", build.getBuildCode()).getResultList();
        if (projectCheckList.size() > 0){
            if(projectCheckList.size() > 1){
                Logging.getLog(getClass()).warn("one projecCheck have mulit business");
            }
            return new ValidResult("build_in_pc", ValidResultLevel.ERROR, projectCheckList.get(0).getOwnerBusiness().getId(),build.getBuildCode());
        }

        return new ValidResult(ValidResultLevel.SUCCESS);
    }
}
