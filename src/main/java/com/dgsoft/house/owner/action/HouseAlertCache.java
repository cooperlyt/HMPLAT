package com.dgsoft.house.owner.action;

import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.model.LockedHouse;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cooper on 26/10/2017.
 */
@Name("houseAlertCache")
@Scope(ScopeType.CONVERSATION)
public class HouseAlertCache {

    @In
    private OwnerEntityLoader ownerEntityLoader;

    private Map<String,List<LockedHouse>> cache = new HashMap<String, List<LockedHouse>>();

    public List<LockedHouse> getHouseAlertList(String houseCode){
        List<LockedHouse> result = cache.get(houseCode);
        if (result == null){
            result = ownerEntityLoader.getEntityManager().createQuery("select lh from LockedHouse lh where lh.houseCode = :houseCode",LockedHouse.class)
                    .setParameter("houseCode",houseCode).getResultList();
            cache.put(houseCode,result);
        }
        return result;

    }


}
