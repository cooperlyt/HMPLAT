package com.dgsoft.house.owner.business.validation;

import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.model.BusinessHouse;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

/** 房屋预售许可验证
 * Created by cooper on 21/10/2016.
 */
@Name("newHouseSaleValid")
@Scope(ScopeType.STATELESS)
public class NewHouseSaleValid extends BusinessHouseValid {

    @In(create = true)
    protected OwnerEntityLoader ownerEntityLoader;

    @Override
    public ValidResult valid(BusinessHouse businessHouse) {
        String projectCode = businessHouse.getProjectCode();
        if (projectCode == null){
            return new ValidResult("house_not_have_sale_card", ValidResultLevel.ERROR, businessHouse.getHouseCode());
        }else{
            Long count = ownerEntityLoader.getEntityManager().createQuery("select count(bp) from BusinessProject bp where bp.projectCode =:projectCode and bp.ownerBusiness.status = 'COMPLETE'", Long.class).getSingleResult();
            if (count.intValue() <= 0){
                return new ValidResult("house_not_have_sale_card", ValidResultLevel.ERROR, businessHouse.getHouseCode());
            }else{
                return new ValidResult(ValidResultLevel.SUCCESS);
            }
        }
    }
}
