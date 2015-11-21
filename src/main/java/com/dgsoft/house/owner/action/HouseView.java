package com.dgsoft.house.owner.action;

import com.dgsoft.house.HouseEntityLoader;
import com.dgsoft.house.model.House;
import com.dgsoft.house.owner.HouseInfoCompare;
import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.model.*;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Logging;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cooper on 11/21/15.
 */
@Name("houseView")
public class HouseView {

    @In(create = true)
    private OwnerEntityLoader ownerEntityLoader;

    @In(create = true)
    private HouseEntityLoader houseEntityLoader;

    private String houseCode;

    private Boolean haveRecord;

    private BusinessHouse businessHouse;

    public String getHouseCode() {
        return houseCode;
    }

    public void setHouseCode(String houseCode) {


        if (this.houseCode!=houseCode && (
                houseCode==null ||
                        !houseCode.equals(this.houseCode)
        )){
            businessHouse = null;
        }



        this.houseCode = houseCode;
    }

    public BusinessHouse getBusinessHouse() {
        if (businessHouse == null){
            initHouse();
        }
        return businessHouse;
    }

    public void setBusinessHouse(BusinessHouse businessHouse) {
        this.businessHouse = businessHouse;
        houseCode = businessHouse.getHouseCode();
    }


    protected void initHouse(){
        lockedHouseList = null;
        selectHouseChangeData = null;
        inBusiness = null;
        if (isCodeDefined()){
            HouseRecord houseRecord = ownerEntityLoader.getEntityManager().find(HouseRecord.class,houseCode);
            if (houseRecord == null){
                House house = houseEntityLoader.getEntityManager().find(House.class,houseCode);
                if (house != null){
                    businessHouse = new BusinessHouse(house);

                }
                haveRecord = false;
            }else{
                businessHouse = houseRecord.getBusinessHouse();
                haveRecord = true;
            }
        }
    }

    public boolean isCodeDefined(){
        return getHouseCode()!=null && !"".equals( getHouseCode() );
    }


    public boolean isHaveRecord() {
        if (!isCodeDefined()){
            return false;
        }
        if (haveRecord == null)
            initHouse();
        return haveRecord;
    }


    private Boolean inBusiness = null;

    private String inBusinessName;

    private String inBusinessCode;

    private void initInBusiness(){
        if (isCodeDefined()) {
            if (inBusiness == null) {
                List<HouseBusiness> inBusinessHouseCode = ownerEntityLoader.getEntityManager().createQuery("select houseBusiness from HouseBusiness houseBusiness left join fetch houseBusiness.ownerBusiness where (houseBusiness.ownerBusiness.status in (:runingStatus)) and houseBusiness.startBusinessHouse.houseCode in (:houseCodes)",HouseBusiness.class)
                        .setParameter("houseCodes", houseCode).setParameter("runingStatus", OwnerBusiness.BusinessStatus.runningStatus()).getResultList();
                if (inBusinessHouseCode.isEmpty()){
                    inBusiness = false;
                    inBusinessName = "";
                    inBusinessCode = "";
                }else{
                    inBusiness = true;
                    inBusinessName = inBusinessHouseCode.get(0).getOwnerBusiness().getDefineName();
                    inBusinessCode = inBusinessHouseCode.get(0).getOwnerBusiness().getId();
                }
            }
        }else{
            inBusiness = null;
            inBusinessName = null;
            inBusinessCode = null;
        }
    }

    public String getInBusinessCode() {
        initInBusiness();
        return inBusinessCode;
    }

    public boolean isInBusiness() {
        initInBusiness();
        if (inBusiness == null){
            return false;
        }
        return inBusiness;
    }

    public String getInBusinessName() {
        initInBusiness();
        return inBusinessName;
    }

    private List<LockedHouse> lockedHouseList;

    public List<LockedHouse> getLockedHouseList() {
        if (!isCodeDefined()){
            return null;
        }
        if (lockedHouseList == null){
            Logging.getLog(getClass()).debug("search local house");
            lockedHouseList = ownerEntityLoader.getEntityManager().createQuery("select lh from LockedHouse lh where lh.houseCode =:houseCode",LockedHouse.class)
                    .setParameter("houseCode", houseCode).getResultList();
        }
        return lockedHouseList;
    }

    private List<HouseInfoCompare.ChangeData> selectHouseChangeData;

    public List<HouseInfoCompare.ChangeData> getSelectHouseChangeData() {
        if (!isCodeDefined()){
            return null;
        }
        if (selectHouseChangeData == null) {

                House mapHouse = houseEntityLoader.getEntityManager().find(House.class, getHouseCode());
                if (mapHouse == null) {
                    selectHouseChangeData = new ArrayList<HouseInfoCompare.ChangeData>(0);
                } else {
                    selectHouseChangeData = HouseInfoCompare.compare(getBusinessHouse(), mapHouse, true);
                }


        }
        return selectHouseChangeData;
    }
}
