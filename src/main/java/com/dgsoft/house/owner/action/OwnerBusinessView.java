package com.dgsoft.house.owner.action;

import com.dgsoft.common.jbpm.ProcessInstanceHome;
import com.dgsoft.common.system.AuthenticationInfo;
import com.dgsoft.common.system.DictionaryWord;
import com.dgsoft.common.system.action.BusinessDefineHome;
import com.dgsoft.common.system.business.BusinessInstance;
import com.dgsoft.common.system.business.Subscribe;
import com.dgsoft.house.owner.model.OwnerBusiness;
import com.dgsoft.house.owner.model.TaskOper;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.framework.EntityNotFoundException;
import org.jboss.seam.security.Identity;
import org.jbpm.taskmgmt.exe.TaskInstance;

import java.util.*;

/**
 * Created by cooper on 7/6/15.
 */
@Name("ownerBusinessView")
public class OwnerBusinessView {

    @In(create = true)
    private OwnerBusinessHome ownerBusinessHome;
    

    @In(create = true)
    private BusinessDefineHome businessDefineHome;

    @In(create = true)
    private ProcessInstanceHome processInstanceHome;

    @In
    private AuthenticationInfo authInfo;

    @In
    private Identity identity;

    public void setId(String id){
        if ((id == null) || id.trim().equals("")){
            ownerBusinessHome.createInstance();
            businessDefineHome.clearInstance();
            processInstanceHome.setKey(null);
            return;
        }
        ownerBusinessHome.setId(id.trim());
        try {

            businessDefineHome.setId(ownerBusinessHome.getInstance().getDefineId());
            businessDefineHome.setTaskName(Subscribe.BUSINESS_VIEW_TASK_NAME);
            try {
                if (ownerBusinessHome.getInstance().getDefineVersion() == null) {
                    processInstanceHome.setKey(new ProcessInstanceHome.ProcessInstanceKey(businessDefineHome.getInstance().getWfName(), id));
                }else{
                    processInstanceHome.setKey(new ProcessInstanceHome.ProcessInstanceKey(businessDefineHome.getInstance().getWfName(), id, ownerBusinessHome.getInstance().getDefineVersion()));
                }
            }catch (EntityNotFoundException e){
                businessDefineHome.clearInstance();
            }
        }catch (EntityNotFoundException e){
            ownerBusinessHome.clearInstance();
        }
    }

    public String getId(){
       return (String)ownerBusinessHome.getId();
    }

    public boolean isCanSuspend(){
       return  identity.hasRole("system.runBusinessMgr") && OwnerBusiness.BusinessStatus.RUNNING.equals(ownerBusinessHome.getInstance().getStatus()) &&
                (processInstanceHome.getInstance() != null);
    }

    public boolean isCanResume(){
        return  identity.hasRole("system.runBusinessMgr") && OwnerBusiness.BusinessStatus.SUSPEND.equals(ownerBusinessHome.getInstance().getStatus()) &&
                (processInstanceHome.getInstance() != null);
    }

    public boolean isCanStop(){
        return  !ownerBusinessHome.getInstance().getSource().equals(BusinessInstance.BusinessSource.BIZ_OUTSIDE) && identity.hasRole("system.runBusinessMgr") && (OwnerBusiness.BusinessStatus.RUNNING.equals(ownerBusinessHome.getInstance().getStatus()) || OwnerBusiness.BusinessStatus.SUSPEND.equals(ownerBusinessHome.getInstance().getStatus())) &&
                (processInstanceHome.getInstance() != null) && !ownerBusinessHome.getInstance().isRecorded();
    }

    public boolean isCanAssign(){
        return  identity.hasRole("system.runBusinessMgr") && (OwnerBusiness.BusinessStatus.RUNNING.equals(ownerBusinessHome.getInstance().getStatus()) || OwnerBusiness.BusinessStatus.SUSPEND.equals(ownerBusinessHome.getInstance().getStatus())) &&
                (processInstanceHome.getInstance() != null);
    }


    @Transactional
    public void suspendBiz(){
        if (isCanSuspend()) {
            ownerBusinessHome.getInstance().setStatus(OwnerBusiness.BusinessStatus.SUSPEND);
            ownerBusinessHome.getInstance().getTaskOpers().add(new TaskOper(TaskOper.OperType.SUSPEND, ownerBusinessHome.getInstance(),
                    authInfo.getLoginEmployee().getId(), authInfo.getLoginEmployee().getPersonName(), comments));
            processInstanceHome.suspend();
            ownerBusinessHome.update();
        }
    }

    @Transactional
    public void resumeBiz(){
        if (isCanResume()) {
            ownerBusinessHome.getInstance().setStatus(OwnerBusiness.BusinessStatus.RUNNING);
            ownerBusinessHome.getInstance().getTaskOpers().add(new TaskOper(TaskOper.OperType.CONTINUE, ownerBusinessHome.getInstance(),
                    authInfo.getLoginEmployee().getId(), authInfo.getLoginEmployee().getPersonName(), comments));
            processInstanceHome.resume();
            ownerBusinessHome.update();
        }
    }

    @Transactional
    public void terminationBiz(){
        if (isCanStop()) {
            ownerBusinessHome.getInstance().setStatus(OwnerBusiness.BusinessStatus.ABORT);
            if (ownerBusinessHome.getInstance().getSelectBusiness() != null &&
                    BusinessInstance.BusinessStatus.MODIFYING.equals(ownerBusinessHome.getInstance().getSelectBusiness().getStatus())){
                ownerBusinessHome.getInstance().getSelectBusiness().setStatus(BusinessInstance.BusinessStatus.COMPLETE);
            }
            ownerBusinessHome.getInstance().getTaskOpers().add(new TaskOper(TaskOper.OperType.TERMINATION, ownerBusinessHome.getInstance(),
                    authInfo.getLoginEmployee().getId(), authInfo.getLoginEmployee().getPersonName(), comments));
            processInstanceHome.stop();
            ownerBusinessHome.update();
        }
    }

    private String assignActorId;

    private Long taskInstanceId;

    private String comments;

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getAssignActorId() {
        return assignActorId;
    }

    public void setAssignActorId(String assignActorId) {
        this.assignActorId = assignActorId;
    }

    public Long getTaskInstanceId() {
        return taskInstanceId;
    }

    public void setTaskInstanceId(Long taskInstanceId) {
        this.taskInstanceId = taskInstanceId;
    }

    @Transactional
    public void assignTo(){
        if (isCanAssign()) {

            ownerBusinessHome.getInstance().getTaskOpers().add(new TaskOper(TaskOper.OperType.ASSIGN, ownerBusinessHome.getInstance(),
                    authInfo.getLoginEmployee().getId(), authInfo.getLoginEmployee().getPersonName(), " > " + DictionaryWord.instance().getEmpNameById(assignActorId) + "  " + comments));

            if (taskInstanceId == null){
                processInstanceHome.assign(assignActorId);
            }else{
                processInstanceHome.assign(taskInstanceId,assignActorId);
            }
            ownerBusinessHome.update();
        }
    }

    public boolean isHasCreateRole(){
        if (businessDefineHome.isIdDefined()) {
            return authInfo.isHasCreateRole(businessDefineHome.getInstance().getId());
        }else return false;
    }


    public boolean isHasViewRole(){
        if (identity.hasRole("owner.businessView")) {
            return true;
        }
        if (businessDefineHome.isIdDefined()){
            return authInfo.isHasCreateRole(businessDefineHome.getInstance().getId());
        }else{
            return false;
        }
    }
}
