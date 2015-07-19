package com.dgsoft.house.owner.business.validation;

import com.dgsoft.common.system.business.TaskSubscribeComponent;
import com.dgsoft.house.HouseInfo;
import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.model.BusinessHouse;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.intercept.BypassInterceptors;

/**
 * Created by cooper on 6/25/15.
 * 房屋有必须有抵押状态 抵押注销
 */
@Name("houseStatusHavePledge")
@Scope(ScopeType.STATELESS)

public class HouseStatusHavePledge extends BusinessHouseValid {


  @In
  private OwnerEntityLoader ownerEntityLoader;

    @Override
    public ValidResult valid(BusinessHouse businessHouse) {

        if (businessHouse.getHouseStates().contains(HouseInfo.HouseStatus.PLEDGE)
        && ownerEntityLoader.getEntityManager().createQuery("select count(houseBusiness) from HouseBusiness houseBusiness where houseBusiness.houseCode=:houseCode and houuseBusiness.ownerBusiness.status='COMPLETE' and houseBusiness.ownerBusiness.defineId=:defineId",Long.class).setParameter("houseCode",businessHouse.getHouseCode()).setParameter("defineId","WP9").getSingleResult().compareTo(Long.valueOf(0))>0){


            return new ValidResult(TaskSubscribeComponent.ValidResult.SUCCESS);
        }
        return new ValidResult("business_house_status_have_pledge", TaskSubscribeComponent.ValidResult.ERROR);
}
        }
