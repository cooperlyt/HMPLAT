package com.dgsoft.house.owner.business.create;

import com.dgsoft.common.system.business.BusinessDataFill;
import com.dgsoft.common.system.business.BusinessInstance;
import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.HouseBusiness;
import com.dgsoft.house.owner.model.OwnerBusiness;
import com.dgsoft.house.owner.model.SaleInfo;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 15-7-20.
 * 单房屋 购房款，付款方式填充
 */
@Name("saleInfoFill")
@Scope(ScopeType.STATELESS)
public class SaleInfoFill implements BusinessDataFill {

    @In
    private OwnerBusinessHome ownerBusinessHome;


    @In
     private OwnerEntityLoader ownerEntityLoader;

    @Override
    public void fillData() {

     if (!ownerBusinessHome.getInstance().getType().equals(BusinessInstance.BusinessType.MODIFY_BIZ)) {
         if (ownerBusinessHome.getInstance().getHouseBusinesses().size() == 1) {
             List defineIdNames = new ArrayList();
             defineIdNames.add("WP42");
             List<HouseBusiness> result = ownerEntityLoader.getEntityManager().createQuery("select houseBusiness from HouseBusiness houseBusiness where houseBusiness.ownerBusiness.status='COMPLETE' and houseBusiness.houseCode=:houseCode and houseBusiness.id <>:id and houseBusiness.ownerBusiness.defineId in(:defineIdNames) order by houseBusiness.ownerBusiness.recordTime DESC ", HouseBusiness.class)
                     .setParameter("houseCode", ownerBusinessHome.getSingleHoues().getHouseCode())
                     .setParameter("id", ownerBusinessHome.getSingleHoues().getId())
                     .setParameter("defineIdNames", defineIdNames).getResultList();

             if (result != null && result.size() > 0) {
                 SaleInfo saleInfo = new SaleInfo(result.get(0).getOwnerBusiness().getSaleInfos().iterator().next(), ownerBusinessHome.getInstance());
                 ownerBusinessHome.getInstance().getSaleInfos().add(saleInfo);
             }

         }
     }
    }
}
