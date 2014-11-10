package com.dgsoft.house.owner.business;

import com.dgsoft.common.system.AuthenticationInfo;
import com.dgsoft.common.system.RunParam;
import com.dgsoft.common.system.action.BusinessDefineHome;
import com.dgsoft.common.system.business.TaskPublish;
import com.dgsoft.common.system.business.TaskSubscribeReg;
import com.dgsoft.house.HouseEntityLoader;
import com.dgsoft.house.model.House;
import com.dgsoft.house.model.PoolOwner;
import com.dgsoft.house.owner.HouseLinkHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.action.OwnerNumberBuilder;
import com.dgsoft.house.owner.model.*;
import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.bpm.BusinessProcess;
import org.jboss.seam.log.Logging;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

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

    private static final String BUSINESS_INFO_PAGE = "/business/houseOwner/BizStartSubscribe.xhtml";
    private static final String BUSINESS_FILE_PAGE = "/business/houseOwner/BizStartFileUpload.xhtml";
    private static final String BUSINESS_PRINT_PAGE = "/business/houseOwner/BizStartConfirm.xhtml";

    private BusinessHouse createStartHouse() {
        if (houseLinkHome.getInstance() == null) {
            throw new IllegalArgumentException("house not found");
        }

        if (houseLinkHome.isRecord()) {
            return (BusinessHouse) houseLinkHome.getInstance();
        } else {
            House house = (House) houseLinkHome.getInstance();
            BusinessHouse result = new BusinessHouse(house);
            return result;
        }

    }

    public String singleHouseSelected() {

        ownerBusinessHome.getInstance().getHouseBusinesses().clear();
        ownerBusinessHome.getInstance().getHouseBusinesses().add(new HouseBusiness(ownerBusinessHome.getInstance(), createStartHouse()));

        return houseIsSelected();
    }

    private void initBusinessData() {

        ownerBusinessHome.getInstance().setDefineId(businessDefineHome.getInstance().getId());
        ownerBusinessHome.getInstance().setDefineName(businessDefineHome.getInstance().getName());
        ownerBusinessHome.getInstance().setRecorded(false);
        ownerBusinessHome.getInstance().setCreateEmpCode(authInfo.getLoginEmployee().getId());
        ownerBusinessHome.getInstance().setCreateEmpName(authInfo.getLoginEmployee().getPersonName());
        ownerBusinessHome.getInstance().setId(businessDefineHome.getInstance().getId() + "-" + OwnerNumberBuilder.instance().useDayNumber("businessId"));
        Logging.getLog(getClass()).debug("businessID:" + ownerBusinessHome.getInstance().getId());
        ownerBusinessHome.getInstance().getTaskOpers().add(new TaskOper(ownerBusinessHome.getInstance(), authInfo.getLoginEmployee().getId(), authInfo.getLoginEmployee().getPersonName()));

    }

    private String houseIsSelected(){
        initBusinessData();
        TaskPublish taskPublish = (TaskPublish) Component.getInstance(TaskPublish.class,true);
        taskPublish.setTaskNameAndPublish(null);
        if (taskPublish.isHaveEditSubscribe()){
            return BUSINESS_INFO_PAGE;
        } else{
            if (RunParam.instance().getBooleanParamValue("BusinessPrintFirst")){
                return  BUSINESS_PRINT_PAGE;
            }else if (businessDefineHome.haveNeedFile(null)) {
                return BUSINESS_FILE_PAGE;
            } else {
                return BUSINESS_PRINT_PAGE;
            }
        }

    }


    public String mulitHouseSelect() {

        return houseIsSelected();
    }

    @In
    private AuthenticationInfo authInfo;

    //TODO valid House
    @Transactional
    public String createProcess() {

        String result = ownerBusinessHome.persist();
        if ((result != null) && result.equals("persisted") && (businessDefineHome.getInstance().getWfName() != null) &&
                !businessDefineHome.getInstance().getWfName().trim().equals("")) {
            BusinessProcess.instance().createProcess(businessDefineHome.getInstance().getWfName(), ownerBusinessHome.getInstance().getId());
            return result;
        } else {
            return null;
        }
    }


}
