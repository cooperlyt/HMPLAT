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

    private HouseRegInfo houseRegInfo;

    public HouseRegInfo getHouseRegInfo() {
        return houseRegInfo;
    }

    public void setHouseRegInfo(HouseRegInfo houseRegInfo) {
        this.houseRegInfo = houseRegInfo;
    }

    @In
    private FacesMessages facesMessages;

    protected void initPoolOwners() {

        houseRegInfo = ownerBusinessHome.getSingleHoues().getHouseRegInfo();
        if (houseRegInfo == null){
            houseRegInfo = new HouseRegInfo();
            ownerBusinessHome.getSingleHoues().setHouseRegInfo(houseRegInfo);
        }

        poolOwners = new ArrayList<PersonEntityAdapter<BusinessPool>>();
        for (BusinessPool pool : houseRegInfo.getBusinessPools()) {
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

    public void refreshPoolOwners() {
        poolOwners = null;
    }

    public void deleteSelectOwner() {
        if (selectPoolOwner != null) {
            houseRegInfo.getBusinessPools().remove(selectPoolOwner.getPersonEntity());
            refreshPoolOwners();
        }
    }

    public void addNewOwner() {
        houseRegInfo.getBusinessPools().add(new BusinessPool(new Date()));
        refreshPoolOwners();
    }

    @Override
    public void initSubscribe() {
        initPoolOwners();
    }

    @Override
    public String validSubscribe() {
        if (!BusinessHouse.PoolType.SINGLE_OWNER.equals(ownerBusinessHome.getSingleHoues().getHouseRegInfo().getPoolType()) && poolOwners.isEmpty()) {
            facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR, "PoolIsEmptyError");
            return null;
        }
        return "success";
    }

    @Override
    public String wireSubscribe() {
        if (ownerBusinessHome.getSingleHoues().getHouseRegInfo().getPoolType().equals(BusinessHouse.PoolType.SINGLE_OWNER)) {
            houseRegInfo.getBusinessPools().clear();
        }else if (ownerBusinessHome.getSingleHoues().getHouseRegInfo().getPoolType().equals(BusinessHouse.PoolType.TOGETHER_OWNER)){
            for(BusinessPool pool: houseRegInfo.getBusinessPools()){
                pool.setPerc(null);
                pool.setPoolArea(null);
            }
        }
        return "success";
    }

    @Override
    public String saveSubscribe() {
        return "saved";
    }
}
