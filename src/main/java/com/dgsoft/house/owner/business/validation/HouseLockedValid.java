package com.dgsoft.house.owner.business.validation;

import cc.coopersoft.house.LockType;
import com.dgsoft.common.system.DictionaryWord;
import com.dgsoft.common.system.business.TaskSubscribeComponent;
import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.model.BusinessHouse;
import com.dgsoft.house.owner.model.LockedHouse;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Logging;

import java.util.List;
import java.util.Map;

/**
 * Created by cooper on 6/25/15.
 * 不能冻结
 */
@Name("houseLockedValid")
@Scope(ScopeType.STATELESS)
public class HouseLockedValid extends BusinessHouseValid {

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
                if (!lockType.equals(LockType.CANT_SALE) ){
                    return new ValidResult("business_house_locked", ValidResultLevel.ERROR, messages.get(lockedHouseCode.get(0).name()));
                }
            }
        }
        return new ValidResult(ValidResultLevel.SUCCESS);

    }
}
