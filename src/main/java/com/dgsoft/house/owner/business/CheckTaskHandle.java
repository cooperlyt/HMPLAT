package com.dgsoft.house.owner.business;

import com.dgsoft.common.system.AuthenticationInfo;
import com.dgsoft.common.system.business.TaskPublish;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.TaskOper;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.*;
import org.jboss.seam.annotations.bpm.EndTask;
import org.jbpm.taskmgmt.exe.TaskInstance;

import java.util.Date;

/**
 * Created by cooper on 9/9/14.
 */
@Name("checkTaskHandle")
@Scope(ScopeType.CONVERSATION)
public class CheckTaskHandle {

    @In
    private TaskPublish taskPublish;

    @In(scope = ScopeType.BUSINESS_PROCESS)
    @Out(scope = ScopeType.BUSINESS_PROCESS)
    private String lastCheckComments;

    public String getLastCheckComments() {
        return lastCheckComments;
    }

    public void setLastCheckComments(String lastCheckComments) {
        this.lastCheckComments = lastCheckComments;
    }

    @Create
    public void init(){
        lastCheckComments = "";
    }

    @In
    private AuthenticationInfo authInfo;

    @In
    private TaskInstance taskInstance;


    @Transactional
    @EndTask(transition = "NEXT")
    public String accept() {
        if ("success".equals(taskPublish.save())) {
            ownerBusinessHome.getInstance().getTaskOpers().add(new TaskOper(ownerBusinessHome.getInstance(),authInfo.getLoginEmployee().getId(),authInfo.getLoginEmployee().getPersonName(),taskInstance.getName(),lastCheckComments,true));
            return completeTask();
        }
        return null;
    }

    @Transactional
    @EndTask(transition = "BACK")
    public String unAccept(){
        ownerBusinessHome.getInstance().getTaskOpers().add(new TaskOper(ownerBusinessHome.getInstance(),authInfo.getLoginEmployee().getId(),authInfo.getLoginEmployee().getPersonName(),taskInstance.getName(),lastCheckComments,false));
        return "taskCompleted";
    }

    @In
    private OwnerBusinessHome ownerBusinessHome;

    protected String completeTask() {
        if ("updated".equals(ownerBusinessHome.update())){
            return "taskCompleted";
        }
        return null;
    }

}
