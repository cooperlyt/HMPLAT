package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.common.system.PersonEntityAdapter;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessPool;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;

import java.util.*;

/**
 * Created by cooper on 9/19/14.
 */
@Name("poolOwnerSubscribe")
@Scope(ScopeType.CONVERSATION)
public class PoolOwnerSubscribe{

    private List<PersonEntityAdapter<BusinessPool>> poolOwners;

    @DataModelSelection
    private PersonEntityAdapter<BusinessPool> selectPoolOwner;

    @In
    private OwnerBusinessHome ownerBusinessHome;

    protected void initPoolOwners(){
        if (poolOwners == null){
            poolOwners = new ArrayList<PersonEntityAdapter<BusinessPool>>();
            for (BusinessPool pool: ownerBusinessHome.getSingleHoues().getBusinessPools()){
                poolOwners.add(new PersonEntityAdapter<BusinessPool>(pool));
            }
            Collections.sort(poolOwners, new Comparator<PersonEntityAdapter<BusinessPool>>() {
                @Override
                public int compare(PersonEntityAdapter<BusinessPool> o1, PersonEntityAdapter<BusinessPool> o2) {
                    return o1.getPersonEntity().getCreateTime().compareTo(o2.getPersonEntity().getCreateTime());
                }
            });
        }

    }

    @DataModel(value = "newEditPoolOwners")
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
        if (selectPoolOwner != null) {
            ownerBusinessHome.getSingleHoues().getBusinessPools().remove(selectPoolOwner.getPersonEntity());
            refreshPoolOwners();
        }
    }

    public void addNewOwner(){
        ownerBusinessHome.getSingleHoues().getBusinessPools().add(new BusinessPool(new Date()));
        refreshPoolOwners();
    }
}
