package com.dgsoft.house.owner.business.validation;

import cc.coopersoft.house.LockType;
import com.dgsoft.common.system.business.BusinessDataValid;
import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.model.HouseBusiness;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import java.util.List;
import java.util.Map;

/**
 * Created by wxy on 2019-08-17.
 */
@Name("houseBusinessLockedValid")
@Scope(ScopeType.STATELESS)
public class HouseBusinessLockedValid extends HouseBusinessValid {

    @In(create = true)
    private OwnerEntityLoader ownerEntityLoader;

    @In(create = true)
    private Map<String,String> messages;

    @Override
    public ValidResult valid(HouseBusiness houseBusiness) {
        List<LockType> lockedHouseCode = ownerEntityLoader.getEntityManager().createQuery("select lockedHouse.type from LockedHouse lockedHouse where lockedHouse.houseCode =:houseCode", LockType.class)
                .setParameter("houseCode", houseBusiness.getAfterBusinessHouse().getHouseCode()).getResultList();
        if (lockedHouseCode.size() > 0) {
            for (LockType lockType:lockedHouseCode){
                if (!lockType.equals(LockType.CANT_SALE) ){
                    return new ValidResult("business_house_locked", ValidResultLevel.ERROR, messages.get(lockedHouseCode.get(0).name()));
                }
            }
        }
        return new ValidResult(ValidResultLevel.SUCCESS);
    }
}
