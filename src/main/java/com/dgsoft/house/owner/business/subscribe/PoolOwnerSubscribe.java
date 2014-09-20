package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.common.system.PersonEntityAdapter;
import com.dgsoft.house.owner.model.BusinessPool;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;

import java.util.List;

/**
 * Created by cooper on 9/19/14.
 */
@Name("poolOwnerSubscribe")
public class PoolOwnerSubscribe extends BasePoolOwnerSubscribe{

    @Override
    protected BusinessPool.BusinessPoolType getType() {
        return BusinessPool.BusinessPoolType.NOW_POOL;
    }

    @Override
    protected PersonEntityAdapter<BusinessPool> getSelectPoolOwner() {
        return selectPoolOwner;
    }

    @Override
    @DataModel(value = "nowEditPoolOwners")
    public List<PersonEntityAdapter<BusinessPool>> getPoolOwners(){
        return super.getPoolOwners();
    }

    @DataModelSelection
    private PersonEntityAdapter<BusinessPool> selectPoolOwner;

}
