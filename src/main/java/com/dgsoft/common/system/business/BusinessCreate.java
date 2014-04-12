package com.dgsoft.common.system.business;

import com.dgsoft.common.exception.ProcessCreatePrepareException;
import com.dgsoft.common.system.model.BusinessDefine;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.*;
import org.jboss.seam.bpm.BusinessProcess;
import org.jboss.seam.bpm.ManagedJbpmContext;
import org.jboss.seam.core.Events;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;
import org.jboss.seam.log.Log;
import org.jbpm.graph.def.ProcessDefinition;

import javax.faces.event.ValueChangeEvent;

/**
 * Created with IntelliJ IDEA.
 * User: cooperlee
 * Date: 5/23/13
 * Time: 2:32 PM
 */

@Scope(ScopeType.CONVERSATION)
public abstract class BusinessCreate {

//    @Out(scope = ScopeType.BUSINESS_PROCESS)
//    private String businessDefineId;
//
//    @Out(scope = ScopeType.BUSINESS_PROCESS)
//    private String businessDescription;
//
//    @Out(scope = ScopeType.BUSINESS_PROCESS)
//    private String businessName;

    @In
    private FacesMessages facesMessages;

    @Logger
    private Log log;

    @In
    private Events events;

    protected abstract String getBusinessKey();

    protected abstract BusinessDefine getBusinessDefine();

//    @In(create = true)
//    private TaskPrepare taskPrepare;


    public void verifyBusinessKeyAvailable(ValueChangeEvent event) {
        String key = (String) event.getNewValue();
        if (!verifyBusinessKey(key)) {
            facesMessages.addToControlFromResourceBundle(event.getComponent().getId(),
                    StatusMessage.Severity.ERROR, "businessKeyConflict");
        }
    }


    public boolean verifyBusinessKey(String key) {

        ProcessDefinition definition = ManagedJbpmContext.instance().getGraphSession().findLatestProcessDefinition(getBusinessDefine().getWfName());
        return ManagedJbpmContext.instance().getProcessInstance(definition,
                key) == null;
    }

    @Transactional
    public String create() {


        log.debug("define Id:" + getBusinessDefine().getId());

        try {
            events.raiseEvent("com.dgsoft.BusinessCreatePrepare." + getBusinessDefine().getId());
        } catch (ProcessCreatePrepareException e) {
            log.debug("prepare other business data exception", e);
            return "prepare_fail";
        }

        BusinessProcess.instance().createProcess(getBusinessDefine().getWfName(),getBusinessKey());


        events.raiseEvent("com.dgsoft.BusinessCreating." + getBusinessDefine().getId());

        events.raiseTransactionSuccessEvent("com.dgsoft.BusinessCreated." + getBusinessDefine().getId());

        log.debug(getBusinessKey() + "verfy ok is start!");

        ManagedJbpmContext.instance().getSession().flush();
        return "businessCreated";
        // return navigation(startData.getBusinessKey());

    }
//
//    private String navigation(String businessKey) {
//
//
//        ManagedJbpmContext.instance().getSession().flush();
//
//        if (runParam.getBooleanParamValue("system.business.forwordToTask")) {
//            //ownerTaskInstanceListener.refresh();
//            int findTask = 0;
//            TaskInstance findTaskInstance = null;
//            ownerTaskInstanceCacheList.refresh();
//            for (TaskInstance taskInstance : ownerTaskInstanceCacheList.getTaskInstanceCreateList()) {
//                if (taskInstance.getProcessInstance().getKey().equals(businessKey)) {
//                    findTask++;
//                    if (findTask > 1) {
//                        return "businessCreated";
//                    } else {
//                        findTaskInstance = taskInstance;
//                    }
//                }
//            }
//
//            if (findTaskInstance != null) {
//                BusinessProcess.instance().resumeTask(findTaskInstance.getId());
//                return taskPrepare.getTaskDescription(findTaskInstance.getId()).getTaskOperationPage();
//            }
//
//        }
//        return "businessCreated";
//    }


}
