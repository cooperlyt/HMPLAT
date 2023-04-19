package com.dgsoft.house.owner.business.validation;

import com.dgsoft.common.system.business.BusinessDataValid;
import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.model.HouseBusiness;
import com.dgsoft.house.owner.model.MoneyBackBusiness;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import java.util.List;

/**
 * Created by wxy on 2019-08-20.
 */
@Name("businessHouseOnlyDeleteMoneyBack")
@Scope(ScopeType.STATELESS)
public class BusinessHouseOnlyDeleteMoneyBack extends HouseBusinessValid  {

    @In(create = true)
    private OwnerEntityLoader ownerEntityLoader;

    @Override
    public ValidResult valid(HouseBusiness houseBusiness) {

        List<MoneyBackBusiness> moneyBackBusinessList = ownerEntityLoader.getEntityManager().createQuery("select mb from MoneyBackBusiness mb where mb.ownerBusiness.status in('COMPLETE','RUNNING','SUSPEND') and mb.ownerBusiness.type<>'CANCEL_BIZ' and mb.contract=:contract and mb.backType in ('CANCEL','ABORT')", MoneyBackBusiness.class)
                .setParameter("contract", houseBusiness.getHouseContract().getContractNumber()).getResultList();

        if (moneyBackBusinessList ==null || moneyBackBusinessList.size()<=0){
            return new ValidResult(ValidResultLevel.SUCCESS);

        }
        return new ValidResult("house_only_have_delete_moneyBackBusiness",ValidResultLevel.ERROR );
    }
}