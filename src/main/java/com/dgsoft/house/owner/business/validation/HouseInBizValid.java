package com.dgsoft.house.owner.business.validation;

import com.dgsoft.common.system.business.TaskSubscribeComponent;
import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.model.BusinessHouse;
import com.dgsoft.house.owner.model.OwnerBusiness;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Logging;

import java.util.List;

/**
 * Created by cooper on 6/25/15.
 * 不能在业务中
 */
@Name("houseInBizValid")
@Scope(ScopeType.STATELESS)
public class HouseInBizValid extends BusinessHouseValid {

    @In(create = true)
    protected OwnerEntityLoader ownerEntityLoader;

    @Override
    public ValidResult valid(BusinessHouse businessHouse) {

        List<String> inBizNames = ownerEntityLoader.getEntityManager().createQuery("select (houseBusiness.ownerBusiness.defineName) from HouseBusiness houseBusiness where (houseBusiness.ownerBusiness.status in (:runingStatus)) and houseBusiness.houseCode =:houseCode",String.class)
                .setParameter("houseCode", businessHouse.getHouseCode()).setParameter("runingStatus", OwnerBusiness.BusinessStatus.runningStatus()).getResultList();

        if (inBizNames.size() > 0){
            if(inBizNames.size() > 1){
                Logging.getLog(getClass()).warn("one house have mulit business");
            }
            return new ValidResult("business_house_in_biz", TaskSubscribeComponent.ValidResult.ERROR, inBizNames.get(0));
        }

        return new ValidResult(TaskSubscribeComponent.ValidResult.SUCCESS);
    }
}
