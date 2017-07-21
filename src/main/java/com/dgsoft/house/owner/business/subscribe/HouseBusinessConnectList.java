package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.HouseBusiness;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cooper on 20/07/2017.
 */
@Name("houseBusinessConnectList")
public class HouseBusinessConnectList {



    @In
    private OwnerBusinessHome ownerBusinessHome;

    private List<HouseBusiness> resultList;

    public List<HouseBusiness> getResultList() {
        if (ownerBusinessHome.getInstance().getHouseBusinesses().size() != 1){
            return new ArrayList<HouseBusiness>(0);
        }


        if (resultList == null){
            resultList = ownerBusinessHome.getEntityManager().createQuery("select bh from HouseBusiness bh left join fetch bh.ownerBusiness ob where bh.houseCode = :houseCode order by ob.applyTime desc",HouseBusiness.class)
                    .setParameter("houseCode",ownerBusinessHome.getSingleHoues().getHouseCode()).getResultList();
        }

        return resultList;
    }
}
