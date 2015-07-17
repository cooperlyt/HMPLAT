package com.dgsoft.house.owner.business.create;

import com.dgsoft.house.owner.model.BusinessHouse;
import com.dgsoft.house.owner.model.OwnerBusiness;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import java.util.List;

/**
 *  抵押业务选择
 */

@Name("pledgeBizSelect")
@Scope(ScopeType.STATELESS)
public class PledgeBizSelect extends BusinessHouseBizSelect {


    @Override
    protected List<OwnerBusiness> getAllowBusiness(BusinessHouse businessHouse) {
        //TODO search biz
        return null;
    }


}
