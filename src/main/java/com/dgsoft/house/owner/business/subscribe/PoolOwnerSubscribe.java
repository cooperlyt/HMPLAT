package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.common.system.PersonHelper;
import com.dgsoft.common.system.business.TaskSubscribeComponent;
import com.dgsoft.house.PoolType;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessHouse;
import com.dgsoft.house.owner.model.BusinessPool;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;

import java.util.*;

/**
 * Created by cooper on 9/19/14.
 */
@Name("poolOwnerSubscribe")
@Scope(ScopeType.CONVERSATION)
public class PoolOwnerSubscribe implements TaskSubscribeComponent {

    private List<PersonHelper<BusinessPool>> poolOwners;

    @DataModelSelection
    private PersonHelper<BusinessPool> selectPoolOwner;

    @In
    private OwnerBusinessHome ownerBusinessHome;


    @In
    private FacesMessages facesMessages;

    @Create
    public void initPoolOwners() {


        poolOwners = new ArrayList<PersonHelper<BusinessPool>>();
        for (BusinessPool pool : ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getBusinessPools()) {
            poolOwners.add(new PersonHelper<BusinessPool>(pool));
        }
        Collections.sort(poolOwners, new Comparator<PersonHelper<BusinessPool>>() {
            @Override
            public int compare(PersonHelper<BusinessPool> o1, PersonHelper<BusinessPool> o2) {
                return o1.getPersonEntity().getCreateTime().compareTo(o2.getPersonEntity().getCreateTime());
            }
        });
    }

    @DataModel(value = "newEditPoolOwners")
    public List<PersonHelper<BusinessPool>> getPoolOwners() {
        return poolOwners;
    }

    public void deleteSelectOwner() {
        if (selectPoolOwner != null) {
            ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getBusinessPools().remove(selectPoolOwner.getPersonEntity());
            poolOwners.remove(selectPoolOwner);
        }
    }

    public void addNewOwner() {
        BusinessPool newOwner = new BusinessPool(new Date());
        newOwner.setOwnerBusiness(ownerBusinessHome.getInstance());
        ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getBusinessPools().add(newOwner);
        poolOwners.add(0,new PersonHelper<BusinessPool>(newOwner));
    }

    public void clearOwner(){
        poolOwners.clear();
        ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getBusinessPools().clear();
    }

    @Override
    public void initSubscribe() {
    }

    @Override
    public void validSubscribe() {
        if (ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getPoolType() == null){
            facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR, "PoolNotInput");
        }
        if (!PoolType.SINGLE_OWNER.equals(ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getPoolType()) && poolOwners.isEmpty()) {
            facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR, "PoolIsEmptyError");
        }
    }

    @Override
    public boolean isPass() {
        if (ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getPoolType() == null){
            return false;
        }
        if (!PoolType.SINGLE_OWNER.equals(ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getPoolType()) && poolOwners.isEmpty()) {
            return false;
        }
        return true;
    }


    @Override
    public boolean saveSubscribe() {
        if (!PoolType.SINGLE_OWNER.equals(ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getPoolType()) && poolOwners.isEmpty()) {
            facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR, "PoolIsEmptyError");
            return false;
        }
        if (ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getPoolType().equals(PoolType.SINGLE_OWNER)) {
            ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getBusinessPools().clear();
            poolOwners.clear();
        } else if (ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getPoolType().equals(PoolType.TOGETHER_OWNER)) {
            for (BusinessPool pool : ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getBusinessPools()) {
                pool.setPerc(null);
                pool.setPoolArea(null);
            }
        }
        return true;
    }
}
