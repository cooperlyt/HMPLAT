package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.common.system.PersonEntityAdapter;
import com.dgsoft.common.system.business.TaskSubscribeComponent;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessHouse;
import com.dgsoft.house.owner.model.BusinessPool;
import com.dgsoft.house.owner.model.HouseBusiness;
import com.dgsoft.house.owner.model.HouseRegInfo;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;

import javax.faces.event.ValueChangeEvent;
import java.util.*;

/**
 * Created by cooper on 9/19/14.
 */
@Name("poolOwnerSubscribe")
@Scope(ScopeType.CONVERSATION)
public class PoolOwnerSubscribe implements TaskSubscribeComponent {

    private List<PersonEntityAdapter<BusinessPool>> poolOwners;

    @DataModelSelection
    private PersonEntityAdapter<BusinessPool> selectPoolOwner;

    @In
    private OwnerBusinessHome ownerBusinessHome;


    @In
    private FacesMessages facesMessages;

    protected void initPoolOwners() {


        poolOwners = new ArrayList<PersonEntityAdapter<BusinessPool>>();
        for (BusinessPool pool : ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getBusinessPools()) {
            poolOwners.add(new PersonEntityAdapter<BusinessPool>(pool));
        }
        Collections.sort(poolOwners, new Comparator<PersonEntityAdapter<BusinessPool>>() {
            @Override
            public int compare(PersonEntityAdapter<BusinessPool> o1, PersonEntityAdapter<BusinessPool> o2) {
                return o1.getPersonEntity().getCreateTime().compareTo(o2.getPersonEntity().getCreateTime());
            }
        });
    }

    @DataModel(value = "newEditPoolOwners")
    public List<PersonEntityAdapter<BusinessPool>> getPoolOwners() {
        return poolOwners;
    }

    public void setPoolOwners(List<PersonEntityAdapter<BusinessPool>> poolOwners) {
        this.poolOwners = poolOwners;
    }


    public void deleteSelectOwner() {
        if (selectPoolOwner != null) {
            ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getBusinessPools().remove(selectPoolOwner.getPersonEntity());
            poolOwners.remove(selectPoolOwner);
        }
    }

    public void addNewOwner() {
        BusinessPool newOwner = new BusinessPool(new Date());
        ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getBusinessPools().add(newOwner);
        poolOwners.add(0,new PersonEntityAdapter<BusinessPool>(newOwner));
    }

    public void clearOwner(){
        poolOwners.clear();
        ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getBusinessPools().clear();
    }

    @Override
    public void initSubscribe() {
        initPoolOwners();
    }

    @Override
    public ValidResult validSubscribe() {
        if (!BusinessHouse.PoolType.SINGLE_OWNER.equals(ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getPoolType()) && poolOwners.isEmpty()) {
            facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR, "PoolIsEmptyError");
            return ValidResult.ERROR;
        }
        return ValidResult.SUCCESS;
    }


    @Override
    public boolean saveSubscribe() {
        if (ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getPoolType().equals(BusinessHouse.PoolType.SINGLE_OWNER)) {
            ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getBusinessPools().clear();
            poolOwners.clear();
        } else if (ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getPoolType().equals(BusinessHouse.PoolType.TOGETHER_OWNER)) {
            for (BusinessPool pool : ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getBusinessPools()) {
                pool.setPerc(null);
                pool.setPoolArea(null);
            }
        }
        return true;
    }
}
