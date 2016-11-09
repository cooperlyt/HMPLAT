package com.dgsoft.house.owner.business;

import com.dgsoft.common.BatchOperData;
import com.dgsoft.house.model.House;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessHouse;
import com.dgsoft.house.owner.model.HouseBusiness;
import com.dgsoft.house.owner.model.HouseRecord;
import com.dgsoft.house.owner.model.OwnerBusiness;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by cooper on 9/29/15.
 */

@Name("houseInBusinessStart")
@Scope(ScopeType.CONVERSATION)
public class HouseInBusinessStart {

    @In(create = true)
    private OwnerBusinessHome ownerBusinessHome;

    @In(create = true)
    private OwnerBusinessStart ownerBusinessStart;

    @In(create = true)
    private FacesMessages facesMessages;

    private List<BatchOperData<BusinessHouse>> houseBusinessList = new ArrayList<BatchOperData<BusinessHouse>>(0);

    private String selectBizId;

    private boolean singleHouse;

    @RequestParameter
    private String selectSingleHouseId;

    public String getSelectBizId() {
        return selectBizId;
    }

    public void setSelectBizId(String selectBizId) {
        this.selectBizId = selectBizId;
    }

    public String getSelectSingleHouseId() {
        return selectSingleHouseId;
    }

    public void setSelectSingleHouseId(String selectSingleHouseId) {
        this.selectSingleHouseId = selectSingleHouseId;
    }

    public List<BatchOperData<BusinessHouse>> getHouseBusinessList() {
        return houseBusinessList;
    }

    public boolean isSingleHouse() {
        return singleHouse;
    }

    public void setSingleHouse(boolean singleHouse) {
        this.singleHouse = singleHouse;
    }

    public boolean isHaveSelectHouse(){
        for(BatchOperData<BusinessHouse> data: houseBusinessList){
            if (data.isSelected()){
                return true;
            }
        }
        return false;
    }

    public boolean isSelectAll(){
        for(BatchOperData<BusinessHouse> data: houseBusinessList){
            if (!data.isSelected()){
                return false;
            }
        }
        return true;
    }

    public void setSelectAll(boolean selectAll){
        for(BatchOperData<BusinessHouse> data: houseBusinessList){
            data.setSelected(selectAll);
        }
    }

    public String businessSelected() {
        ownerBusinessHome.getInstance().setSelectBusiness(ownerBusinessHome.getEntityManager().find(OwnerBusiness.class, selectBizId));
        houseBusinessList = new ArrayList<BatchOperData<BusinessHouse>>();
        for(HouseBusiness houseBusiness: ownerBusinessHome.getInstance().getSelectBusiness().getHouseBusinesses()){
            if (!houseBusiness.isCanceled()) {
                HouseRecord houseRecord = ownerBusinessHome.getEntityManager().find(HouseRecord.class, houseBusiness.getHouseCode());
                houseBusinessList.add(new BatchOperData<BusinessHouse>(houseRecord.getBusinessHouse(), true));
            }
        }
        Collections.sort(houseBusinessList, new Comparator<BatchOperData<BusinessHouse>>() {
            @Override
            public int compare(BatchOperData<BusinessHouse> o1, BatchOperData<BusinessHouse> o2) {
                String a,b;
                a = (o1.getData().getInFloorName() == null) ? "" :o1.getData().getInFloorName();
                b = (o2.getData().getInFloorName() == null) ? "" :o2.getData().getInFloorName();
                int result = a.compareTo(b);
                if (result == 0){
                    a = (o1.getData().getHouseUnitName() == null) ? "" : o1.getData().getHouseUnitName();
                    b = (o2.getData().getHouseUnitName() == null) ? "" : o2.getData().getHouseUnitName();
                    result = a.compareTo(b);
                    if (result == 0){
                        result = o1.getData().getHouseOrder().compareTo(o2.getData().getHouseOrder());
                    }
                }
                return result;
            }
        });
        if (houseBusinessList.size() == 1){
            return houseSelected();
        }
        return "businessSelected";
    }


    public String houseSelected(){


        ownerBusinessHome.getInstance().getHouseBusinesses().clear();
        for(BatchOperData<BusinessHouse> batchOperData : houseBusinessList){
            if ((!singleHouse && batchOperData.isSelected()) || (singleHouse && batchOperData.getData().getId().equals(selectSingleHouseId))){
                ownerBusinessHome.getInstance().getHouseBusinesses().add(new HouseBusiness(ownerBusinessHome.getInstance(), batchOperData.getData()));
                if (singleHouse)
                    break;
            }
        }
        if (ownerBusinessHome.getInstance().getHouseBusinesses().isEmpty()){
            facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR,"selectHouseInBusinessIsEmpty");
            return null;
        }
        return ownerBusinessStart.dataSelected();
    }

    public String businessAllHouseSelected(){


        ownerBusinessHome.getInstance().setSelectBusiness(ownerBusinessHome.getEntityManager().find(OwnerBusiness.class, selectBizId));
        ownerBusinessHome.getInstance().getHouseBusinesses().clear();
        for(HouseBusiness houseBusiness: ownerBusinessHome.getInstance().getSelectBusiness().getHouseBusinesses()){
            if (!houseBusiness.isCanceled())
                ownerBusinessHome.getInstance().getHouseBusinesses().add(new HouseBusiness(ownerBusinessHome.getInstance(), ownerBusinessHome.getEntityManager().find(HouseRecord.class, houseBusiness.getHouseCode()).getBusinessHouse()));
        }
        return ownerBusinessStart.dataSelected();

    }

}
