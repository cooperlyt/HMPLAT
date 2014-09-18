package com.dgsoft.house.owner.business;

import com.dgsoft.common.system.AuthenticationInfo;
import com.dgsoft.common.system.NumberBuilder;
import com.dgsoft.common.system.action.BusinessDefineHome;
import com.dgsoft.house.HouseEntityLoader;
import com.dgsoft.house.model.House;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.action.OwnerNumberBuilder;
import com.dgsoft.house.owner.model.BusinessHouse;
import com.dgsoft.house.owner.model.TaskOper;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.bpm.BusinessProcess;
import org.jboss.seam.log.Logging;

import java.util.Date;

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

    private static final String BUSINESS_START_PAGE = "/business/houseOwner/BizStartSubscribe.xhtml";

    private String selectHouseId;

    public String getSelectHouseId() {
        return selectHouseId;
    }

    public void setSelectHouseId(String selectHouseId) {
        this.selectHouseId = selectHouseId;
    }

    @In(create = true)
    private HouseEntityLoader houseEntityLoader;

    public String singleHouseSelectet() {
        Logging.getLog(getClass()).debug("singleHouseSelectet:" + selectHouseId);

        ownerBusinessHome.getInstance().getBusinessHouses().clear();
        ownerBusinessHome.getInstance().getBusinessHouses().add(new BusinessHouse(ownerBusinessHome.getInstance(),houseEntityLoader.getEntityManager().find(House.class,selectHouseId)));

        initBusinessData();
        return BUSINESS_START_PAGE;
    }

    private void initBusinessData(){

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
    public String createProcess(){

        ownerBusinessHome.getInstance().setId(businessDefineHome.getInstance().getId() + "-" + OwnerNumberBuilder.instance().useDayNumber("businessId"));
        Logging.getLog(getClass()).debug("businessID:" + ownerBusinessHome.getInstance().getId());
        ownerBusinessHome.getInstance().getTaskOpers().add(new TaskOper(ownerBusinessHome.getInstance(),authInfo.getLoginEmployee().getId(),authInfo.getLoginEmployee().getPersonName()));
        String result = ownerBusinessHome.persist();
        if ((result != null) && result.equals("persisted")) {
            BusinessProcess.instance().createProcess(businessDefineHome.getInstance().getWfName(), ownerBusinessHome.getInstance().getId());
            return result;
        }else{
            return null;
        }
    }


}
