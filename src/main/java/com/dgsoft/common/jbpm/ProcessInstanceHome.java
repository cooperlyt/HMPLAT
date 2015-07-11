package com.dgsoft.common.jbpm;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.bpm.ManagedJbpmContext;
import org.jboss.seam.core.Events;
import org.jboss.seam.log.Logging;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;
import org.jbpm.taskmgmt.exe.TaskInstance;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: cooperlee
 * Date: 03/04/14
 * Time: 10:37
 */

@Name("processInstanceHome")
@Scope(ScopeType.CONVERSATION)
public class ProcessInstanceHome {

    public static class ProcessInstanceKey{

        private String processDefineName;

        private String processKey;

        public ProcessInstanceKey(String processDefineName, String processKey) {
            this.processDefineName = processDefineName;
            this.processKey = processKey;
        }

        public String getProcessDefineName() {
            return processDefineName;
        }

        public String getProcessKey() {
            return processKey;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ProcessInstanceKey that = (ProcessInstanceKey) o;

            if (!processDefineName.equals(that.processDefineName)) return false;
            if (!processKey.equals(that.processKey)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = processDefineName.hashCode();
            result = 31 * result + processKey.hashCode();
            return result;
        }
    }

    private ProcessInstance instance;

    private ProcessInstanceKey key;


    public ProcessInstance getInstance() {
        initInstance();
        return instance;
    }

    public ProcessInstanceKey getKey() {
        return key;
    }

    public void setKey(ProcessInstanceKey key) {
        if ((key == null) || !key.equals(this.key)){
            instance = null;
        }
        this.key = key;
    }


    public void initInstance() {
        if (instance == null) {
            if (key != null) {
                ProcessDefinition definition = ManagedJbpmContext.instance().getGraphSession().findLatestProcessDefinition(key.getProcessDefineName());
                instance = definition == null ?
                        null : ManagedJbpmContext.instance().getProcessInstanceForUpdate(definition, key.getProcessKey());
            }
        }
    }


    public void stop() {

        Collection listTasks = getInstance().getTaskMgmtInstance().getTaskInstances();
        if (listTasks.size() > 0) {
            for (Iterator iter = listTasks.iterator(); iter.hasNext(); ) {
                TaskInstance ti = (TaskInstance) iter.next();
                if (!ti.hasEnded() && !ti.isSuspended()) {

                    ti.setSignalling(false);
                    ti.cancel();
                    ti.setEnd(new java.util.Date());


                    Logging.getLog(getClass()).debug("task instance " + ti.getName() + " has ended");
                    Token tk = ti.getToken();
                    tk.end();
                    Logging.getLog(getClass()).debug("token " + tk.getName() + " has ended");
                }
            }
        }
        if (!getInstance().hasEnded()) {
            getInstance().end();

        }

        Events.instance().raiseEvent("org.jboss.seam.stopProcess", getInstance());

        Events.instance().raiseTransactionSuccessEvent("org.jboss.seam.processStoped");
    }

    public void suspend() {
        getInstance().suspend();
        Events.instance().raiseTransactionSuccessEvent("org.jboss.seam.processSuspended");
    }

    public void resume() {

        getInstance().resume();
        Events.instance().raiseTransactionSuccessEvent("org.jboss.seam.processResumed");
    }

    public void assign(String actorId){
        Collection listTasks = getInstance().getTaskMgmtInstance().getTaskInstances();
        if (listTasks.size() > 0) {
            for (Iterator iter = listTasks.iterator(); iter.hasNext(); ) {
                TaskInstance ti = (TaskInstance) iter.next();
                if (!ti.hasEnded() && ti.isOpen()) {

                    ti.setActorId(actorId);


                    Logging.getLog(getClass()).debug("task instance " + ti.getName() + " has assigned");


                }
            }
        }
        Events.instance().raiseTransactionSuccessEvent("com.dgsot.jbpm.taskAssign");
    }

    public void assign(long taskInstanceId, String actorId){
        Collection listTasks = getInstance().getTaskMgmtInstance().getTaskInstances();
        if (listTasks.size() > 0) {
            for (Iterator iter = listTasks.iterator(); iter.hasNext(); ) {
                TaskInstance ti = (TaskInstance) iter.next();
                if ((ti.getId() == taskInstanceId) && !ti.hasEnded() && !ti.isSuspended() && ti.isOpen()) {

                    ti.setActorId(actorId);


                    Logging.getLog(getClass()).debug("task instance " + ti.getName() + " has assigned");
                    return;


                }
            }
        }
    }


    public List<TaskInstance> getTaskInstanceList() {

        List<TaskInstance> result = new ArrayList<TaskInstance>(getInstance().getTaskMgmtInstance().getTaskInstances());
        Collections.sort(result,new Comparator<TaskInstance>() {
            @Override
            public int compare(TaskInstance o1, TaskInstance o2) {
                return o1.getCreate().compareTo(o2.getCreate());
            }
        });
        return result;
    }


    public List<TaskInstance> getOpenTasks(){
        List<TaskInstance> result = new ArrayList<TaskInstance>();
        if (getInstance() != null){
            for(TaskInstance instance: getTaskInstanceList()){
                if (instance.isOpen()){
                    result.add(instance);
                }
            }
        }
        return result;
    }


}
