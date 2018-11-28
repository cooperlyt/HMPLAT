package com.dgsoft.house.owner.business.validation;

import com.dgsoft.common.system.business.BusinessDataValid;
import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.model.HouseBusiness;
import com.dgsoft.house.owner.model.HouseRecord;
import com.dgsoft.house.owner.model.OwnerBusiness;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Logging;

import java.util.List;

/**
 * Created by wxy on 2018-11-27.
 *
 */
@Name("houseBusinessInBizValid")
@Scope(ScopeType.STATELESS)
public class HouseBusinessInBizValid extends HouseBusinessValid {

    @In(create = true)
    protected OwnerEntityLoader ownerEntityLoader;

    @Override
    public ValidResult valid(HouseBusiness houseBusiness) {
        List<String> inBizNames = ownerEntityLoader.getEntityManager().createQuery("select (houseBusiness.ownerBusiness.defineName) from HouseBusiness houseBusiness where (houseBusiness.ownerBusiness.status in (:runingStatus)) and houseBusiness.houseCode =:houseCode", String.class)
                .setParameter("houseCode", houseBusiness.getHouseCode()).setParameter("runingStatus", OwnerBusiness.BusinessStatus.runningStatus()).getResultList();
        if (inBizNames.size() > 0){
            if(inBizNames.size() > 1){
                Logging.getLog(getClass()).warn("one house have mulit business");
            }
            return new ValidResult("houseBusiness_in_biz", ValidResultLevel.ERROR, inBizNames.get(0),houseBusiness.getHouseCode());
        }

        return new ValidResult(ValidResultLevel.SUCCESS);
    }
}
