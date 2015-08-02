package com.dgsoft.house.owner.business;

import com.dgsoft.common.system.action.BusinessDefineHome;
import com.dgsoft.common.system.business.BusinessInstance;
import com.dgsoft.house.HouseEntityLoader;
import com.dgsoft.house.HouseInfo;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private HouseEntityLoader houseEntityLoader;

    @In(create = true)
    private OwnerBusinessHome ownerBusinessHome;

    @In(create = true)
    private BusinessDefineHome businessDefineHome;


    private Map<String, List<HouseInfoCompare.ChangeData>> houseChangeDatas;

    public String startModify() {
        //TODO ROLE
        OwnerBusiness selectOwnerBusiness = ownerEntityLoader.getEntityManager().find(OwnerBusiness.class, selectBusinessId);
        cloneData(selectOwnerBusiness);

        houseChangeDatas = new HashMap<String, List<HouseInfoCompare.ChangeData>>();
        for (HouseBusiness houseBusiness : selectOwnerBusiness.getHouseBusinesses()) {
            HouseInfo mapHouse = houseEntityLoader.getEntityManager().find(House.class, houseBusiness.getHouseCode());
            if (mapHouse != null) {
                HouseInfoCompare.compare(houseBusiness.getStartBusinessHouse(), mapHouse);
            }

        }
        for (BusinessProject project : selectOwnerBusiness.getBusinessProjects()) {

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

        //TODO clone

    }


    public class HouseChangeData {

        private HouseBusiness oldBusiness;

        private BusinessHouse startHouse;

        private BusinessHouse afterHouse;

        private OwnerBusiness newBusiness;

        private House mapHouse;

        private List<HouseInfoCompare.ChangeData> changeDatas;

        public HouseChangeData(HouseBusiness oldBusiness, House mapHouse, OwnerBusiness newBusiness) {
            this.oldBusiness = oldBusiness;
            this.startHouse = oldBusiness.getStartBusinessHouse();
            this.mapHouse = mapHouse;
            this.newBusiness = newBusiness;
            assignOld();
            changeDatas = HouseInfoCompare.compare(startHouse, mapHouse);

        }

        public BusinessHouse getStartHouse() {
            return startHouse;
        }

        public House getMapHouse() {
            return mapHouse;
        }

        public BusinessHouse getAfterHouse() {
            return afterHouse;
        }

        public void setAfterHouse(BusinessHouse afterHouse) {
            this.afterHouse = afterHouse;
        }

        public boolean isChangeHouse() {
            return !startHouse.getHouseCode().equals(afterHouse.getHouseCode());
        }

        public void assignOld() {
            afterHouse = new BusinessHouse(oldBusiness.getAfterBusinessHouse());
            afterHouse.setPoolType(oldBusiness.getAfterBusinessHouse().getPoolType());

            if (oldBusiness.getAfterBusinessHouse().getHouseRegInfo() != null){
                if (oldBusiness.getAfterBusinessHouse().getHouseRegInfo().getId().equals(oldBusiness.getOwnerBusiness().getId())){
                    afterHouse.setHouseRegInfo(new HouseRegInfo(newBusiness,oldBusiness.getAfterBusinessHouse().getHouseRegInfo()));
                }else{
                    afterHouse.setHouseRegInfo(oldBusiness.getAfterBusinessHouse().getHouseRegInfo());
                }
            }

            for(MortgaegeRegiste mr: oldBusiness.getAfterBusinessHouse().getMortgaegeRegistes()){
                if (mr.getOwnerBusiness().getId().equals(oldBusiness.getId())){
                   // newBusiness.
                }
            }




        }

        public void modifyHouseFormMap() {
            //afterHouse =
        }

    }

}
