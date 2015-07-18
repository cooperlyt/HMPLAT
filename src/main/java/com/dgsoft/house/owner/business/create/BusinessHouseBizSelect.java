package com.dgsoft.house.owner.business.create;

import com.dgsoft.common.system.business.BusinessInstance;
import com.dgsoft.common.system.business.BusinessPickSelect;
import com.dgsoft.house.owner.model.BusinessHouse;
import com.dgsoft.house.owner.model.OwnerBusiness;

import java.util.List;

/**
 * Created by cooper on 7/17/15.
 */
public abstract class BusinessHouseBizSelect  implements BusinessPickSelect {

    protected abstract List<OwnerBusiness> getAllowBusiness(BusinessHouse businessHouse);

    @Override
    public List<? extends BusinessInstance> getAllowSelectBusiness(Object data) {
        if (data instanceof BusinessHouse){
            return getAllowBusiness((BusinessHouse)data);
        }
        throw new IllegalArgumentException("Config error: data master not businessHouse");

    }
}
