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
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;

import java.util.ArrayList;
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

    public String getSelectBizId() {
        return selectBizId;
    }

    public void setSelectBizId(String selectBizId) {
        this.selectBizId = selectBizId;
    }

    public List<BatchOperData<BusinessHouse>> getHouseBusinessList() {
        return houseBusinessList;
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
        if (houseBusinessList.size() == 1){
            return houseSelected();
        }
        return "businessSelected";
    }

    public String houseSelected(){
        ownerBusinessHome.getInstance().getHouseBusinesses().clear();
        for(BatchOperData<BusinessHouse> batchOperData : houseBusinessList){
            if (batchOperData.isSelected()){
                ownerBusinessHome.getInstance().getHouseBusinesses().add(new HouseBusiness(ownerBusinessHome.getInstance(), batchOperData.getData()));
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
