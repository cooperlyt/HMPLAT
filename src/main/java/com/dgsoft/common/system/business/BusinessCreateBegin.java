package com.dgsoft.common.system.business;

import com.dgsoft.common.system.action.BusinessDefineHome;
import com.dgsoft.common.system.model.BusinessDefine;
import org.jboss.seam.Component;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.log.Logging;

import javax.management.remote.rmi._RMIConnection_Stub;

/**
 * Created by cooper on 8/11/15.
 */
@Name("businessCreateBegin")
public class BusinessCreateBegin {


    @RequestParameter
    private String bussinessDefineId;


    @In(create = true)
    private BusinessDefineHome businessDefineHome;

    public String startCreate(){

        businessDefineHome.setId(bussinessDefineId);
        businessDefineHome.setTaskType(BusinessInstance.BusinessType.NORMAL_BIZ);
        String componentName = businessDefineHome.getInstance().getStartPage();
        Logging.getLog(getClass()).debug("Start component Name:" + componentName);
        BusinessCreateComponent startComponent = ((BusinessCreateComponent) Component.getInstance(businessDefineHome.getInstance().getStartPage(),true,true));
        if (startComponent == null) {
            Logging.getLog(getClass()).error("start Component [" + componentName + "] is not found");
        }
        return startComponent.startCreate();
    }


    public String searchModify(){
        businessDefineHome.setId(bussinessDefineId);
        businessDefineHome.setTaskType(BusinessInstance.BusinessType.NORMAL_BIZ);
        return ((BusinessCreateComponent) Component.getInstance(businessDefineHome.getInstance().getStartPage(),true,true)).searchModify();

    }

}
