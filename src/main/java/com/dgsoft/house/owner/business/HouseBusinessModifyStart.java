package com.dgsoft.house.owner.business;

import com.dgsoft.common.system.action.BusinessDefineHome;
import com.dgsoft.common.system.business.BusinessInstance;
import com.dgsoft.house.HouseEntityLoader;
import com.dgsoft.house.model.House;
import com.dgsoft.house.owner.HouseInfoCompare;
import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.*;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.web.RequestParameter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cooper on 7/31/15.
 */
@Name("houseBusinessModifyStart")
@Scope(ScopeType.CONVERSATION)
public class HouseBusinessModifyStart {


    @RequestParameter
    private String selectBusinessId;


    @In(create = true)
    private OwnerEntityLoader ownerEntityLoader;

    @In(create = true)
    private OwnerBusinessHome ownerBusinessHome;

    @In(create = true)
    private BusinessDefineHome businessDefineHome;


    private List<ModifyHouse> houseBusinessAdapters;

    private OwnerBusiness selectOwnerBusiness;


    public String startModify() {
        //TODO ROLE
        //HouseState inBiz and locked


        selectOwnerBusiness = ownerEntityLoader.getEntityManager().find(OwnerBusiness.class, selectBusinessId);

        if (selectOwnerBusiness.getHouseBusinesses().isEmpty()){
            return "DATA_COMPLETE";
        }

        houseBusinessAdapters = new ArrayList<ModifyHouse>(selectOwnerBusiness.getHouseBusinesses().size());
        for (HouseBusiness houseBusiness : selectOwnerBusiness.getHouseBusinesses()) {
            houseBusinessAdapters.add(new SelectBusinessModify(houseBusiness));
        }

        return "HOUSE_OPERATOR";
    }

    public String dataModify() {
        cloneData(selectOwnerBusiness);

        for (ModifyHouse houseBusinessAdapter : houseBusinessAdapters) {
            selectOwnerBusiness.getHouseBusinesses().add(houseBusinessAdapter.genModifyHouseBusiness(ownerBusinessHome.getInstance()));
        }


        return null;
    }

    private void cloneData(OwnerBusiness ownerBusiness) {
        //TODO needFile
        ownerBusiness.setStatus(BusinessInstance.BusinessStatus.MODIFYING);

        businessDefineHome.setId(ownerBusiness.getDefineId());
        ownerBusinessHome.clearInstance();
        ownerBusinessHome.getInstance().setSelectBusiness(ownerBusiness);
        ownerBusinessHome.getInstance().setType(BusinessInstance.BusinessType.MODIFY_BIZ);
        for(Evaluate evaluate: ownerBusiness.getEvaluates()) {
            ownerBusinessHome.getInstance().getEvaluates().add(new Evaluate(ownerBusinessHome.getInstance(),evaluate));
        }

        for(MappingCorp mappingCorp: ownerBusiness.getMappingCorps()) {
            ownerBusinessHome.getInstance().getMappingCorps().add(new MappingCorp(ownerBusinessHome.getInstance(), mappingCorp));
        }

        for(Card card: ownerBusiness.getCards()) {
            ownerBusinessHome.getInstance().getCards().add(new Card(ownerBusinessHome.getInstance(), card));
        }

        for(BusinessPersion businessPersion: ownerBusiness.getBusinessPersions()) {
            ownerBusinessHome.getInstance().getBusinessPersions().add(new BusinessPersion(ownerBusinessHome.getInstance(), businessPersion));
        }

        for(Reason reason: ownerBusiness.getReasons()) {
            ownerBusinessHome.getInstance().getReasons().add(new Reason(ownerBusinessHome.getInstance(), reason));
        }

        for(SaleInfo saleInfo: ownerBusiness.getSaleInfos()) {
            ownerBusinessHome.getInstance().getSaleInfos().add(new SaleInfo(saleInfo,ownerBusinessHome.getInstance()));
        }


        //TODO clone

    }

    public List<ModifyHouse> getHouseBusinessAdapters() {
        return houseBusinessAdapters;
    }


    public static abstract class ModifyHouse {

        private boolean useMapInfo;

        private List<HouseInfoCompare.ChangeData> changeDatas;

        private House mapHouse;

        private Boolean haveMapHouse = null;

        public abstract BusinessHouse getStartHouse();

        public abstract HouseBusiness genModifyHouseBusiness(OwnerBusiness ownerBusiness);

        public boolean isUseMapInfo() {
            if (mapHouse == null) {
                return false;
            }
            return useMapInfo;
        }

        public void setUseMapInfo(boolean useMapInfo) {
            this.useMapInfo = useMapInfo;
        }

        protected House getMapHouse(){
            if (haveMapHouse == null){
                mapHouse = HouseEntityLoader.instance().getEntityManager().find(House.class, getStartHouse().getHouseCode());
                haveMapHouse = (mapHouse != null);
            }
            if (haveMapHouse){
                return mapHouse;
            }else
                return null;
        }

        public List<HouseInfoCompare.ChangeData> getChangeDatas() {
            if (changeDatas == null){
                if (getMapHouse() != null){
                    changeDatas = HouseInfoCompare.compare(getStartHouse(), mapHouse);
                }else{
                    changeDatas = new ArrayList<HouseInfoCompare.ChangeData>(0);
                }
            }
            return changeDatas;
        }


        protected void refresh(){
            haveMapHouse = null;
            changeDatas = null;
        }

    }

    public static class NewHouseBusinessModify extends ModifyHouse {

        private BusinessHouse startHouse;

        public NewHouseBusinessModify(BusinessHouse startHouse) {
            this.startHouse = startHouse;
        }

        @Override
        public BusinessHouse getStartHouse() {
            return startHouse;
        }

        @Override
        public HouseBusiness genModifyHouseBusiness(OwnerBusiness ownerBusiness) {
            HouseBusiness result = new HouseBusiness(ownerBusiness);
            result.setStartBusinessHouse(startHouse);
            result.setHouseCode(startHouse.getHouseCode());
            result.setAfterBusinessHouse(new BusinessHouse(startHouse));
            if (isUseMapInfo()){
                result.getAfterBusinessHouse().modifyFormMapHouse(getMapHouse());
            }
            return result;
        }
    }


    public static class SelectBusinessModify extends ModifyHouse {

        private HouseBusiness houseBusiness;

        private BusinessHouse changeHouse;

        public BusinessHouse getChangeHouse() {
            return changeHouse;
        }

        public void setChangeHouse(BusinessHouse changeHouse) {
            this.changeHouse = changeHouse;
            refresh();
        }

        public boolean isChangeHouse() {
            return changeHouse != null;
        }

        public SelectBusinessModify(HouseBusiness houseBusiness) {
            this.houseBusiness = houseBusiness;
            setUseMapInfo(false);
        }


        @Override
        public BusinessHouse getStartHouse() {
            if (isChangeHouse()) {
                return changeHouse;
            } else{
                return houseBusiness.getStartBusinessHouse();
            }
        }

        @Override
        public HouseBusiness genModifyHouseBusiness(OwnerBusiness ownerBusiness) {
            HouseBusiness result = new HouseBusiness(ownerBusiness);

            result.setStartBusinessHouse(getStartHouse());

            result.setHouseCode(result.getStartBusinessHouse().getHouseCode());

            result.setAfterBusinessHouse(new BusinessHouse(houseBusiness.getStartBusinessHouse()));

            if (isUseMapInfo()) {
                result.getAfterBusinessHouse().modifyFormMapHouse(getMapHouse());
            }

            if (houseBusiness.getAfterBusinessHouse().getLandInfo() != null) {
                if (result.getAfterBusinessHouse().getLandInfo() == null) {
                    result.getAfterBusinessHouse().setLandInfo(new LandInfo(houseBusiness.getAfterBusinessHouse().getLandInfo()));
                } else {
                    result.getAfterBusinessHouse().getLandInfo().assignInfo(houseBusiness.getAfterBusinessHouse().getLandInfo());
                }
            } else {
                result.getAfterBusinessHouse().setLandInfo(null);
            }

            result.getAfterBusinessHouse().getOtherPowerCards().addAll(houseBusiness.getAfterBusinessHouse().getOtherPowerCards());

            for (BusinessPool pool : houseBusiness.getAfterBusinessHouse().getBusinessPools()) {
                if (pool.getOwnerBusiness().getId().equals(houseBusiness.getOwnerBusiness().getId())) {
                    result.getAfterBusinessHouse().getBusinessPools().add(new BusinessPool(ownerBusiness, pool));
                } //else {
                  //  result.getAfterBusinessHouse().getBusinessPools().add(pool);
                //}
            }
            result.getAfterBusinessHouse().setPoolType(houseBusiness.getAfterBusinessHouse().getPoolType());

            if (houseBusiness.getAfterBusinessHouse().getBusinessHouseOwner() != null) {

                if (houseBusiness.getAfterBusinessHouse().getBusinessHouseOwner().getOwnerBusiness().getId().equals(houseBusiness.getOwnerBusiness().getId())) {
                    result.getAfterBusinessHouse().setBusinessHouseOwner(new BusinessHouseOwner(ownerBusiness, result.getAfterBusinessHouse(), houseBusiness.getAfterBusinessHouse().getBusinessHouseOwner()));
                } //else {
                  //  result.getAfterBusinessHouse().setBusinessHouseOwner(houseBusiness.getAfterBusinessHouse().getBusinessHouseOwner());
                //}
            }

            return result;
        }


    }

}
