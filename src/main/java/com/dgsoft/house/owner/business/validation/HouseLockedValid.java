package com.dgsoft.house.owner.business.validation;

import com.dgsoft.common.system.DictionaryWord;
import com.dgsoft.common.system.business.TaskSubscribeComponent;
import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.model.BusinessHouse;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import java.util.List;

/**
 * Created by cooper on 6/25/15.
 * 不能冻结
 */
@Name("houseLockedValid")
@Scope(ScopeType.STATELESS)
public class HouseLockedValid extends BusinessHouseValid{

    @In(create = true)
    private OwnerEntityLoader ownerEntityLoader;

    @Override
    public ValidResult valid(BusinessHouse businessHouse) {

        List<String> lockedHouseCode = ownerEntityLoader.getEntityManager().createQuery("select lockedHouse.type from LockedHouse lockedHouse where lockedHouse.houseCode =:houseCode", String.class)
                .setParameter("houseCode", businessHouse.getHouseCode()).getResultList();
        if (lockedHouseCode.size() > 0){
            return new ValidResult("business_house_locked", ValidResultLevel.ERROR, DictionaryWord.instance().getWordValue(lockedHouseCode.get(0)));
        }
        return new ValidResult(ValidResultLevel.SUCCESS);
    }
}
