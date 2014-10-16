package com.dgsoft.house.owner.business;

import com.dgsoft.common.system.AuthenticationInfo;
import com.dgsoft.common.system.action.BusinessDefineHome;
import com.dgsoft.house.HouseEntityLoader;
import com.dgsoft.house.model.House;
import com.dgsoft.house.model.PoolOwner;
import com.dgsoft.house.owner.HouseLinkHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.action.OwnerNumberBuilder;
import com.dgsoft.house.owner.model.*;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.bpm.BusinessProcess;
import org.jboss.seam.log.Logging;

import javax.persistence.NoResultException;

/**
 * Created by cooper on 8/28/14.
 */
@Name("houseBusinessStart")
@Scope(ScopeType.CONVERSATION)
public class HouseBusinessStart {

    @In
    private BusinessDefineHome businessDefineHome;

    @In(create = true)
    private OwnerBusinessHome ownerBusinessHome;

    @In(create = true)
    private HouseLinkHome houseLinkHome;

    private static final String BUSINESS_START_PAGE = "/business/houseOwner/BizStartSubscribe.xhtml";


    private BusinessHouse createStartHouse(){
        if (houseLinkHome.getInstance() == null){
            throw new IllegalArgumentException("house not found");
        }

        BusinessHouse result;
        if (houseLinkHome.isRecord()){
            BusinessHouse businessHouse = (BusinessHouse) houseLinkHome.getInstance();
            result = new BusinessHouse(businessHouse);
            if (businessHouse.getLandInfo() != null){
                result.setLandInfo(new LandInfo(businessHouse.getLandInfo()));
            }
            if (businessHouse.getBusinessHouseOwner() != null){
                result.setBusinessHouseOwner(new BusinessHouseOwner(businessHouse.getBusinessHouseOwner()));
            }
            for(BusinessPool pool: businessHouse.getBusinessPools()){
                result.getBusinessPools().add(new BusinessPool(pool));
            }
            for(OtherPowerCard card: businessHouse.getOtherPowerCards()){
                result.getOtherPowerCards().add(card);
            }


        }else{
            House house = (House)houseLinkHome.getInstance();
            result = new BusinessHouse(house);
            if(house.getHouseOwner() != null){
                result.setBusinessHouseOwner(new BusinessHouseOwner(house.getHouseOwner()));
            }
            for(PoolOwner poolOwner: house.getPoolOwners()){
                result.getBusinessPools().add(new BusinessPool(poolOwner));
            }

        }
        return result;
    }

    public String singleHouseSelected() {

        ownerBusinessHome.getInstance().getHouseBusinesses().clear();
        ownerBusinessHome.getInstance().getHouseBusinesses().add(new HouseBusiness(ownerBusinessHome.getInstance(), createStartHouse()));

        initBusinessData();
        return BUSINESS_START_PAGE;
    }

    private void initBusinessData() {

        //ownerBusinessHome.getInstance().setId(NumberBuilder.instance().getDayNumber("businessId"));
        // Logging.getLog(getClass()).debug("business id is:" + ownerBusinessHome.getInstance().getId());
        ownerBusinessHome.getInstance().setDefineId(businessDefineHome.getInstance().getId());
        ownerBusinessHome.getInstance().setDefineName(businessDefineHome.getInstance().getName());


    }


    public String mulitHouseSelect() {

        initBusinessData();
        return BUSINESS_START_PAGE;
    }

    @In
    private AuthenticationInfo authInfo;

    //TODO valid House
    @Transactional
    public String createProcess() {

        ownerBusinessHome.getInstance().setId(businessDefineHome.getInstance().getId() + "-" + OwnerNumberBuilder.instance().useDayNumber("businessId"));
        Logging.getLog(getClass()).debug("businessID:" + ownerBusinessHome.getInstance().getId());
        ownerBusinessHome.getInstance().getTaskOpers().add(new TaskOper(ownerBusinessHome.getInstance(), authInfo.getLoginEmployee().getId(), authInfo.getLoginEmployee().getPersonName()));
        String result = ownerBusinessHome.persist();
        if ((result != null) && result.equals("persisted")) {
            BusinessProcess.instance().createProcess(businessDefineHome.getInstance().getWfName(), ownerBusinessHome.getInstance().getId());
            return result;
        } else {
            return null;
        }
    }


}
