package com.dgsoft.house.owner.business.validation;

import cc.coopersoft.house.LockType;
import com.dgsoft.common.system.business.BusinessDataValid;
import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.model.BusinessHouse;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Logging;

import java.util.List;
import java.util.Map;

/**
 * Created by wxy on 2017-03-20.
 * 有预抵的预警可以启动商品房交易和转移登记
 */
@Name("houseLockedNMValid")
public class HouseLockedNMValid extends BusinessHouseValid {

    @In(create = true)
    private OwnerEntityLoader ownerEntityLoader;

    @In(create = true)
    private Map<String,String> messages;

    @Override
    public ValidResult valid(BusinessHouse businessHouse) {
        List<LockType> lockedHouseCode = ownerEntityLoader.getEntityManager().createQuery("select lockedHouse.type from LockedHouse lockedHouse where lockedHouse.houseCode =:houseCode", LockType.class)
                .setParameter("houseCode", businessHouse.getHouseCode()).getResultList();
        if (lockedHouseCode.size() > 0) {
            for (LockType lockType:lockedHouseCode){
                Logging.getLog(getClass()).debug(lockType);
                if (!lockType.equals(LockType.CANT_SALE) && !lockType.equals(LockType.PREAPRE_MORTGAGE) ){
                    return new ValidResult("business_house_locked", ValidResultLevel.ERROR, messages.get(lockedHouseCode.get(0).name()));
                }
            }
        }
        return new ValidResult(ValidResultLevel.SUCCESS);

    }

}
