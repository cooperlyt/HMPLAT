package com.dgsoft.house.owner.business;

import com.dgsoft.common.BatchOperData;
import com.dgsoft.common.system.action.BusinessDefineHome;
import com.dgsoft.common.system.business.BusinessDataValid;
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
import org.jboss.seam.log.Logging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by wxy on 2019-08-17.
 */
@Name("moneyBackBusinessStart")
@Scope(ScopeType.CONVERSATION)
public class MoneyBackBusinessStart {

    public static class SelectBusinessHouseItem extends BatchOperData<BusinessHouse> {

        private List<BusinessDataValid.ValidResult> validInfoList;

        private BusinessDataValid.ValidResultLevel validLevel;

        public SelectBusinessHouseItem(BusinessHouse data, List<BusinessDataValid.ValidResult> validInfo) {
            super(data, false);
            this.validInfoList = validInfo;
            validLevel = BusinessDataValid.ValidResultLevel.SUCCESS;
            for(BusinessDataValid.ValidResult vr: validInfoList){
                if (vr.getResult().getPri() > validLevel.getPri()){
                    validLevel = vr.getResult();
                }
            }
        }

        public List<BusinessDataValid.ValidResult> getValidInfoList() {
            return validInfoList;
        }

        public BusinessDataValid.ValidResultLevel getValidLevel() {
            return validLevel;
        }

        public boolean isCanSelect(){
            return validLevel.getPri() <= BusinessDataValid.ValidResultLevel.WARN.getPri();
        }
    }

    @In(create = true)
    private OwnerBusinessHome ownerBusinessHome;

    @In(create = true)
    private OwnerBusinessStart ownerBusinessStart;

    @In
    private BusinessDefineHome businessDefineHome;

    @In(create = true)
    private FacesMessages facesMessages;

    private List<SelectBusinessHouseItem> houseBusinessList = new ArrayList<SelectBusinessHouseItem>(0);

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

    public List<SelectBusinessHouseItem> getHouseBusinessList() {
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
        for(SelectBusinessHouseItem data: houseBusinessList){
            if (data.isCanSelect()) {
                data.setSelected(selectAll);
            }
        }
    }


    public String businessSelected() {
        ownerBusinessHome.getInstance().setSelectBusiness(ownerBusinessHome.getEntityManager().find(OwnerBusiness.class, selectBizId));
        houseBusinessList = new ArrayList<SelectBusinessHouseItem>();
        for(HouseBusiness houseBusiness: ownerBusinessHome.getInstance().getSelectBusiness().getHouseBusinesses()){
                List<BusinessDataValid.ValidResult> validResults = new ArrayList<BusinessDataValid.ValidResult>();
                for(BusinessDataValid valid: businessDefineHome.getCreateDataValidComponents()){

                    try {
                        BusinessDataValid.ValidResult validResult = valid.valid(houseBusiness);
                        if (validResult.getResult().getPri() > BusinessDataValid.ValidResultLevel.SUCCESS.getPri()) {
                            validResults.add(validResult);
                        }
                        if (validResult.getResult().equals(BusinessDataValid.ValidResultLevel.FATAL)){
                            throw new IllegalArgumentException(validResult.getMsgKey());
                        }
                        if (!validResult.getResult().equals(BusinessDataValid.ValidResultLevel.SUCCESS)){
                            facesMessages.addFromResourceBundle(validResult.getResult().getSeverity(),validResult.getMsgKey(),validResult.getParams());
                        }

                    }catch (Exception e){
                        Logging.getLog(getClass()).error(e.getMessage(),e,"config error:" + valid.getClass().getSimpleName());
                        throw new IllegalArgumentException("config error:" + valid.getClass().getSimpleName());
                    }
                }

                houseBusinessList.add(new SelectBusinessHouseItem(houseBusiness.getAfterBusinessHouse(), validResults));
        }
        Collections.sort(houseBusinessList, new Comparator<BatchOperData<BusinessHouse>>() {
            @Override
            public int compare(BatchOperData<BusinessHouse> o1, BatchOperData<BusinessHouse> o2) {
                String a, b;
                a = (o1.getData().getInFloorName() == null) ? "" : o1.getData().getInFloorName();
                b = (o2.getData().getInFloorName() == null) ? "" : o2.getData().getInFloorName();
                int result = a.compareTo(b);
                if (result == 0) {
                    a = (o1.getData().getHouseUnitName() == null) ? "" : o1.getData().getHouseUnitName();
                    b = (o2.getData().getHouseUnitName() == null) ? "" : o2.getData().getHouseUnitName();
                    result = a.compareTo(b);
                    if (result == 0) {
                        result = o1.getData().getHouseOrder().compareTo(o2.getData().getHouseOrder());
                    }
                }
                return result;
            }
        });
        return "backMoneySelected";
    }

    public String houseSelected(){
        ownerBusinessHome.getInstance().getHouseBusinesses().clear();
        if (ownerBusinessHome.getInstance().getSelectBusiness()==null){
            facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR,"selectInBusinessIsEmpty");
            return null;
        }
        return ownerBusinessStart.dataSelected();
    }

    private String validAndStart(){


        return ownerBusinessStart.dataSelected();

    }


}
