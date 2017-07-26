package com.dgsoft.house.owner.business.validation;

import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.model.BusinessHouse;
import com.dgsoft.house.owner.model.OwnerBusiness;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Logging;

import java.util.List;

/**
 * Created by cooper on 26/07/2017.
 */
@Name("houseInBizRegValid")
public class HouseInBizRegValid extends BusinessHouseValid{

    @In(create = true)
    protected OwnerEntityLoader ownerEntityLoader;

    @Override
    public ValidResult valid(BusinessHouse businessHouse) {

        List<String> inBizNames = ownerEntityLoader.getEntityManager().createQuery("select (houseBusiness.ownerBusiness.defineName) from HouseBusiness houseBusiness where (houseBusiness.ownerBusiness.status in (:runingStatus)) and houseBusiness.ownerBusiness.recorded = false and houseBusiness.houseCode =:houseCode",String.class)
                .setParameter("houseCode", businessHouse.getHouseCode()).setParameter("runingStatus", OwnerBusiness.BusinessStatus.runningStatus()).getResultList();

        if (inBizNames.size() > 0){
            if(inBizNames.size() > 1){
                Logging.getLog(getClass()).warn("one house have mulit business");
            }
            return new ValidResult("business_house_in_biz", ValidResultLevel.ERROR, inBizNames.get(0));
        }

        return new ValidResult(ValidResultLevel.SUCCESS);
    }
}
