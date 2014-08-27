package com.dgsoft.common.system.action;

import com.dgsoft.common.TotalDataGroup;
import com.dgsoft.common.TotalGroupStrategy;
import com.dgsoft.common.helper.ActionExecuteState;
import com.dgsoft.common.system.SystemEntityHome;
import com.dgsoft.common.system.model.BusinessDefine;
import com.dgsoft.common.system.model.TaskSubscribe;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.bpm.ManagedJbpmContext;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;
import org.jbpm.graph.def.ProcessDefinition;

import javax.faces.event.ValueChangeEvent;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: cooperlee
 * Date: 5/23/13
 * Time: 2:39 PM
 */

@Name("businessDefineHome")
public class BusinessDefineHome extends SystemEntityHome<BusinessDefine> {

    @In
    private FacesMessages facesMessages;

    public List<TaskSubscribe> getTaskSubscribeList(){
        List<TaskSubscribe> result = new ArrayList<TaskSubscribe>(getInstance().getTaskSubscribes());
        Collections.sort(result,new Comparator<TaskSubscribe>() {
            @Override
            public int compare(TaskSubscribe o1, TaskSubscribe o2) {
                return new Integer(o1.getPriority()).compareTo(o2.getPriority());
            }
        });
        return result;
    }

    @In
    private ActionExecuteState actionExecuteState;

    @Factory(value = "taskSubscribeTypes",scope = ScopeType.SESSION)
    public TaskSubscribe.SubscribeType[] getTaskSubscribeTypes(){
        return TaskSubscribe.SubscribeType.values();
    }

    private List<TotalDataGroup<String,TaskSubscribe>> taskSubscribeGroups;


    public List<TotalDataGroup<String, TaskSubscribe>> getTaskSubscribeGroups() {
        if (taskSubscribeGroups == null){
            taskSubscribeGroups = TotalDataGroup.groupBy(getInstance().getTaskSubscribes(),new TotalGroupStrategy<String, TaskSubscribe>() {
                @Override
                public String getKey(TaskSubscribe taskSubscribe) {
                    return taskSubscribe.getTaskName();
                }

                @Override
                public Object totalGroupData(Collection<TaskSubscribe> datas) {
                    return null;
                }
            });
        }
        return taskSubscribeGroups;
    }

    public List<String> getWfTaskNames(){
        ProcessDefinition lasterPD = ManagedJbpmContext.instance().getGraphSession().findLatestProcessDefinition(getInstance().getWfName());

        return new ArrayList<String>(lasterPD.getTaskMgmtDefinition().getTasks().keySet());
    }


    private TaskSubscribe editTaskSubscribe;

    public boolean isHaveNeedFile(){
        //TODO need file
        return false;
    };

    public TaskSubscribe getEditTaskSubscribe() {
        return editTaskSubscribe;
    }

    public void setEditTaskSubscribe(TaskSubscribe editTaskSubscribe) {
        this.editTaskSubscribe = editTaskSubscribe;
    }


    public void setSelectTaskSubscribeId(String id){
        for(TaskSubscribe ts: getInstance().getTaskSubscribes()){
            if (ts.getId().equals(id)){
                editTaskSubscribe = ts;
                return;
            }
        }
        editTaskSubscribe = null;
    }

    public String getSelectTaskSubscribeId(){
        if (editTaskSubscribe == null){
            return null;
        }
        return editTaskSubscribe.getId();
    }

    private boolean createing = false;

    public boolean isCreateing() {
        return createing;
    }

    public void setCreateing(boolean createing) {
        this.createing = createing;
    }

    public void createTaskSubscribe(){
        createing = true;
        actionExecuteState.clearState();
        editTaskSubscribe = new TaskSubscribe(UUID.randomUUID().toString().replace("-", "").toUpperCase(),getInstance());
    }

    public void editTaskSubscribe(){
        createing = false;
        actionExecuteState.clearState();
    }

    public void saveTaskSubscribe(){
        if (createing) {
            getInstance().getTaskSubscribes().add(editTaskSubscribe);
            taskSubscribeGroups = null;
            if (editTaskSubscribe.getType().equals(TaskSubscribe.SubscribeType.START_TASK)) {
                editTaskSubscribe.setTaskName("start");
            }
        }
        actionExecuteState.actionExecute();

    }


    @Override
    public void refresh(){
        super.refresh();
        taskSubscribeGroups = null;
    }


    public void verifyIdAvailable(ValueChangeEvent e) {
        String id = (String) e.getNewValue();
        if (!isIdAvailable(id)) {
            facesMessages.addToControlFromResourceBundle(e.getComponent().getId(), StatusMessage.Severity.ERROR, "fieldConflict", id);
        }
    }

    @Override
    protected boolean verifyPersistAvailable() {
        String newId = this.getInstance().getId();
        if (!isIdAvailable(newId)) {
            facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR, "fieldConflict", newId);
            return false;
        } else
            return true;

    }

    public boolean isIdAvailable(String newId) {
        return getEntityManager().createQuery("select bd from BusinessDefine bd where bd.id = ?1").setParameter(1, newId).getResultList().size() == 0;
    }

}
