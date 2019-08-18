package com.dgsoft.house.owner.business.validation;

import com.dgsoft.common.system.business.BusinessDataValid;
import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.model.BusinessBuild;
import com.dgsoft.house.owner.model.HouseBusiness;
import com.dgsoft.house.owner.model.MoneyBackBusiness;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Logging;

import java.util.List;

/**
 * Created by wxy on 2019-08-17.
 */
@Name("businessHouseNotHaveMoneyBack")
@Scope(ScopeType.STATELESS)
public class BusinessHouseNotHaveMoneyBack extends HouseBusinessValid {

    @In(create = true)
    private OwnerEntityLoader ownerEntityLoader;

    @Override
    public ValidResult valid(HouseBusiness houseBusiness) {

        List<MoneyBackBusiness> moneyBackBusinessList = ownerEntityLoader.getEntityManager().createQuery("select mb from MoneyBackBusiness mb where mb.ownerBusiness.status in('COMPLETE','RUNNING','SUSPEND') and mb.ownerBusiness.type<>'CANCEL_BIZ' and mb.contract=:contract", MoneyBackBusiness.class)
                .setParameter("contract", houseBusiness.getHouseContract().getContractNumber()).getResultList();

        if (moneyBackBusinessList ==null || moneyBackBusinessList.size()<=0){
            return new ValidResult(ValidResultLevel.SUCCESS);

        }
        return new ValidResult("house_have_moneyBackBusiness", ValidResultLevel.ERROR, moneyBackBusinessList.get(0).getOwnerBusiness().getId(),houseBusiness.getHouseContract().getContractNumber());
    }
}
