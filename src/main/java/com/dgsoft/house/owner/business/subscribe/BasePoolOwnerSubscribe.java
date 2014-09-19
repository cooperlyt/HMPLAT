package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.common.helper.ActionExecuteState;
import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessPool;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Scope;

import java.util.List;

/**
 * Created by cooper on 9/19/14.
 */
@Scope(ScopeType.CONVERSATION)
public abstract class BasePoolOwnerSubscribe {

    protected abstract BusinessPool.BusinessPoolType getType();

    private List<BusinessPool> poolOwners;

    protected abstract BusinessPool getSelectPoolOwner();

   // private PoolOwnerEntityHelper poolOwnerEntityHelper;

    private BusinessPool editOwner;
//
//    @Override
//    public void create() {
//        super.create();
//        poolOwnerEntityHelper = new PoolOwnerEntityHelper(this);
//    }
//
//    @Override
//    protected BusinessPool createInstance() {
//        return new BusinessPool(getType());
//    }
//
//
//    public PoolOwnerEntityHelper getPoolOwnerEntityHelper() {
//        return poolOwnerEntityHelper;
//    }
//
//    @Override
//    public Class<BusinessPool> getEntityClass() {
//        return BusinessPool.class;
//    }

    @In
    private OwnerBusinessHome ownerBusinessHome;

    protected void initPoolOwners(){
        if (poolOwners == null){
            poolOwners = ownerBusinessHome.getSingleHoues().getPoolsByType(getType());
        }
    }

    public List<BusinessPool> getPoolOwners(){
        initPoolOwners();
        return poolOwners;
    }

    public void setPoolOwners(List<BusinessPool> poolOwners) {
        this.poolOwners = poolOwners;
    }

    public void refreshPoolOwners(){
        poolOwners = null;
    }

    public void deleteSelectOwner(){
        ownerBusinessHome.getSingleHoues().getBusinessPools().remove(getSelectPoolOwner());
        refreshPoolOwners();
    }

    public void createOwner(){
        editOwner = new BusinessPool(getType(),ownerBusinessHome.getSingleHoues());
        ActionExecuteState.instance().clearState();
    }

    public void addOwner(){
        ownerBusinessHome.getSingleHoues().getBusinessPools().add(editOwner);
        ActionExecuteState.instance().actionExecute();
        refreshPoolOwners();
    }

}
