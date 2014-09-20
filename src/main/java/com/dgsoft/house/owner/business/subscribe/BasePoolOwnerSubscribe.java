package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.common.system.PersonEntityAdapter;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessPool;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Scope;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cooper on 9/19/14.
 */
@Scope(ScopeType.CONVERSATION)
public abstract class BasePoolOwnerSubscribe {

    protected abstract BusinessPool.BusinessPoolType getType();

    private List<PersonEntityAdapter<BusinessPool>> poolOwners;

    protected abstract PersonEntityAdapter<BusinessPool> getSelectPoolOwner();

    @In
    private OwnerBusinessHome ownerBusinessHome;

    protected void initPoolOwners(){
        if (poolOwners == null){
            poolOwners = new ArrayList<PersonEntityAdapter<BusinessPool>>();
            for (BusinessPool pool: ownerBusinessHome.getSingleHoues().getPoolsByType(getType())){
                poolOwners.add(new PersonEntityAdapter<BusinessPool>(pool));
            }
        }
    }

    public List<PersonEntityAdapter<BusinessPool>> getPoolOwners(){
        initPoolOwners();
        return poolOwners;
    }

    public void setPoolOwners(List<PersonEntityAdapter<BusinessPool>> poolOwners) {
        this.poolOwners = poolOwners;
    }

    public void refreshPoolOwners(){
        poolOwners = null;
    }

    public void deleteSelectOwner(){
        if (getSelectPoolOwner() != null) {
            ownerBusinessHome.getSingleHoues().getBusinessPools().remove(getSelectPoolOwner().getPersonEntity());
            refreshPoolOwners();
        }
    }

    public void addNewOwner(){
        ownerBusinessHome.getSingleHoues().getBusinessPools().add(new BusinessPool(getType(),ownerBusinessHome.getSingleHoues()));
        refreshPoolOwners();
    }

}
