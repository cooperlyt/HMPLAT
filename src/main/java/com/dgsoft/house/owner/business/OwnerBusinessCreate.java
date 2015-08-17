package com.dgsoft.house.owner.business;

import com.dgsoft.common.system.AuthenticationInfo;
import com.dgsoft.common.system.RunParam;
import com.dgsoft.common.system.action.BusinessDefineHome;
import com.dgsoft.common.system.business.BusinessCreate;
import com.dgsoft.common.system.business.TaskSubscribeComponent;
import com.dgsoft.common.system.model.BusinessDefine;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessEmp;
import com.dgsoft.house.owner.model.HouseBusiness;
import com.dgsoft.house.owner.model.TaskOper;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.bpm.ManagedJbpmContext;
import org.jboss.seam.international.StatusMessage;
import org.jboss.seam.log.Logging;
import org.jbpm.graph.def.ProcessDefinition;
import sun.rmi.runtime.Log;

import java.util.Date;

/**
 * Created by cooper on 1/15/15.
 */
@Name("ownerBusinessCreate")
public class OwnerBusinessCreate extends BusinessCreate {


    @In
    private BusinessDefineHome businessDefineHome;

    @In
    private OwnerBusinessHome ownerBusinessHome;

    @In
    private AuthenticationInfo authInfo;

    @Override
    protected boolean verifyCreate() {
        boolean verify;
        if (businessDefineHome.isSubscribesPass() && businessDefineHome.isCompletePass()){
            businessDefineHome.completeTask();

            ownerBusinessHome.getInstance().getBusinessEmps().add(new BusinessEmp(ownerBusinessHome.getInstance(),
                    BusinessEmp.EmpType.CREATE_EMP, authInfo.getLoginEmployee().getId(),
                    authInfo.getLoginEmployee().getPersonName(), new Date()));
            ownerBusinessHome.getInstance().getTaskOpers().add(
                    new TaskOper(ownerBusinessHome.getInstance(), TaskOper.OperType.CREATE,
                            RunParam.instance().getStringParamValue("CreateBizTaskName"),
                            authInfo.getLoginEmployee().getId(), authInfo.getLoginEmployee().getPersonName(), businessDefineHome.getDescription()));


            ProcessDefinition definition = ManagedJbpmContext.instance().getGraphSession().findLatestProcessDefinition(businessDefineHome.getInstance().getWfName());

            ownerBusinessHome.getInstance().setDefineVersion(definition == null ? null : definition.getVersion());
            String result = ownerBusinessHome.persist();
            verify = ((result != null) && result.equals("persisted") && (businessDefineHome.getInstance().getWfName() != null) &&
                    !businessDefineHome.getInstance().getWfName().trim().equals(""));

        }else{
            verify = false;
        }

        if (!verify){
            throw new IllegalArgumentException("create business fail");
        }

        return verify;
    }


    @Override
    protected String getBusinessKey() {
        return ownerBusinessHome.getInstance().getId();
    }

    @Override
    protected BusinessDefine getBusinessDefine() {
        return businessDefineHome.getInstance();
    }
}
