package com.dgsoft.house.owner.ws;

import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.model.LockedHouse;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Logging;

/**
 * Created by cooper on 11/18/15.
 */
@Name("tempComponent")
public class TempComponent {


    @In(create = true)
    private OutsideBusinessCreate outsideBusinessCreate;

    @In(create = true)
    private OwnerEntityLoader ownerEntityLoader;

    public void createOldContract(){
        for(LockedHouse lh:  ownerEntityLoader.getEntityManager().createQuery("select lh from LockedHouse lh where lh.description like '%房屋状态为 : 商品房网签%'",LockedHouse.class).setMaxResults(100).getResultList()) {

//            ContractOwner co = ownerEntityLoader.getEntityManager().find(ContractOwner.class,lh.getHouseCode());
//
//            if (co != null){
//                outsideBusinessCreate.createOldContract(lh,co);
//            }

        }

        Logging.getLog(getClass()).debug("complete:");
    }


}
