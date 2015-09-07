package com.dgsoft.house.owner.business.validation;

import com.dgsoft.common.system.business.TaskSubscribeComponent;
import com.dgsoft.house.HouseInfo;
import com.dgsoft.house.HouseStatus;
import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.model.BusinessHouse;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.intercept.BypassInterceptors;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cooper on 6/25/15.
 * 房屋有必须有抵押状态 抵押注销
 */
@Name("houseStatusHavePledge")
@Scope(ScopeType.STATELESS)
@BypassInterceptors
public class HouseStatusHavePledge extends BusinessHouseValid {


  @In
  private OwnerEntityLoader ownerEntityLoader;

    @Override
    public ValidResult valid(BusinessHouse businessHouse) {
        //List defineIdNames = new ArrayList();
       // defineIdNames.add("WP9");
       // defineIdNames.add("WP10");
       // defineIdNames.add("WP11");
    //    ownerEntityLoader.getEntityManager().createQuery("select count(houseBusiness) from HouseBusiness houseBusiness where houseBusiness.houseCode=:houseCode and houuseBusiness.ownerBusiness.status='COMPLETE' and houseBusiness.ownerBusiness.defineId in(:defineIds)",Long.class).setParameter("houseCode",businessHouse.getHouseCode()).setParameter("defineIds",defineIdNames).getSingleResult().compareTo(Long.valueOf(0))>0;

        if (businessHouse.getAllStatusList().contains(HouseStatus.PLEDGE)){


            return new ValidResult(ValidResultLevel.SUCCESS);
        }
        return new ValidResult("business_house_status_have_pledge", ValidResultLevel.ERROR);
}
        }
