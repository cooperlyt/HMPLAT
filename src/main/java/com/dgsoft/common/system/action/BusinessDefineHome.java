package com.dgsoft.common.system.action;

import com.dgsoft.common.system.SystemEntityHome;
import com.dgsoft.common.system.model.BusinessDefine;
import com.dgsoft.common.system.model.BusinessNeedFile;
import com.dgsoft.common.system.model.TaskSubscribe;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;
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

    private Map<String, List<TaskSubscribe>> taskSubscribeMap;

    private String editSubscribeId;

    private String taskName;

    private String createRegName;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    protected String getTask(){
        return ((getTaskName() == null) || "".equals(getTaskName().trim())) ? "" : getTaskName();
    }

    public String getCreateRegName() {
        return createRegName;
    }

    public void setCreateRegName(String createRegName) {
        this.createRegName = createRegName;
    }

    public String getEditSubscribeId() {
        return editSubscribeId;
    }

    public void setEditSubscribeId(String editSubscribeId) {
        this.editSubscribeId = editSubscribeId;
    }

    public Map<String, List<TaskSubscribe>> getTaskSubscribeMap(){
       if (taskSubscribeMap == null){
           taskSubscribeMap = new HashMap<String, List<TaskSubscribe>>();
           List<TaskSubscribe> all = new ArrayList<TaskSubscribe>(getInstance().getTaskSubscribes());
           for (TaskSubscribe subscribe: all){


               List<TaskSubscribe> result = taskSubscribeMap.get(subscribe.getTask());
               if (result == null){
                   result = new ArrayList<TaskSubscribe>();
                   taskSubscribeMap.put(subscribe.getTask(),result);
               }
               result.add(subscribe);
           }

           for(List<TaskSubscribe> subscribes: taskSubscribeMap.values()){
               Collections.sort(subscribes, new Comparator<TaskSubscribe>() {
                   @Override
                   public int compare(TaskSubscribe o1, TaskSubscribe o2) {
                       return new Integer(o1.getPriority()).compareTo(o2.getPriority());
                   }
               });
           }
       }
        return taskSubscribeMap;
    }

    public List<TaskSubscribe> getTaskSubscribeList() {
        List<TaskSubscribe> result = getTaskSubscribeMap().get(getTask());
        return (result == null) ? new ArrayList<TaskSubscribe>(0) : result;
    }


    public List<String> getWfTaskNames() {
        ProcessDefinition lasterPD = ManagedJbpmContext.instance().getGraphSession().findLatestProcessDefinition(getInstance().getWfName());

        return new ArrayList<String>(lasterPD.getTaskMgmtDefinition().getTasks().keySet());
    }




    public List<BusinessNeedFile> getFileSubscribe(String taskName){
        List<BusinessNeedFile> result = new ArrayList<BusinessNeedFile>(0);
        for(BusinessNeedFile businessNeedFile: getInstance().getBusinessNeedFiles()){
            if (((taskName == null) &&  ((businessNeedFile.getTaskName() == null) || (businessNeedFile.getTaskName().equals(""))) )
                || ( (taskName != null) && taskName.equals(businessNeedFile.getTaskName()))){
                result.add(businessNeedFile);
            }
        }
        return result;
    }

    public boolean haveNeedFile(String taskName) {
        return !getFileSubscribe(taskName).isEmpty();
    }



    @Transactional
    public void createTaskSubscribe() {

        TaskSubscribe taskSubscribe =
                new TaskSubscribe(UUID.randomUUID().toString().replace("-", "").toUpperCase(),
                        getTask(), createRegName,((taskName == null) || taskName.trim().equals("")) ? TaskSubscribe.SubscribeType.START_TASK : TaskSubscribe.SubscribeType.TASK_OPER,
                        getInstance(),getMaxPriority(getTask()) + 1);

        getEntityManager().persist(taskSubscribe);
        getEntityManager().flush();
        createRegName = null;
        refresh();

    }


    protected TaskSubscribe getEditTaskSubscribe(){
        for(TaskSubscribe subscribe: getTaskSubscribeMap().get(getTask())){
            if (subscribe.getId().equals(editSubscribeId)){
                return subscribe;
            }
        }
        return null;
    }

    private int getMaxPriority(String taskName) {
        int result = 0;
        for (TaskSubscribe subscribe : getInstance().getTaskSubscribes()) {
            if (taskName.equals(subscribe.getTaskName()) && (subscribe.getPriority() > result)) {
                result = subscribe.getPriority();
            }
        }
        return result;
    }

    public void upSelectTaskSubscribe() {
        TaskSubscribe editTaskSubscribe = getEditTaskSubscribe();
        int selectPriority = editTaskSubscribe.getPriority();

        //Integer maxPriority = null;

        TaskSubscribe maxSub = null;
        for (TaskSubscribe subscribe : getInstance().getTaskSubscribes()) {
            if (editTaskSubscribe.getTaskName().equals(subscribe.getTaskName()) && (subscribe.getPriority() < selectPriority)) {
                if ((maxSub == null) || (maxSub.getPriority() < subscribe.getPriority())){
                    maxSub = subscribe;
                }
                //subscribe.setPriority(subscribe.getPriority() - 1);
            }
        }

        if (maxSub != null){
            int maxPriority = maxSub.getPriority();
            maxSub.setPriority(editTaskSubscribe.getPriority());
            editTaskSubscribe.setPriority(maxPriority);
            taskSubscribeMap = null;
        }

        update();
    }

    public void downSelectTaskSubscribe() {
        TaskSubscribe editTaskSubscribe = getEditTaskSubscribe();
        int selectPriority = editTaskSubscribe.getPriority();
        TaskSubscribe minSub = null;
        for (TaskSubscribe subscribe : getInstance().getTaskSubscribes()) {
            if (editTaskSubscribe.getTaskName().equals(subscribe.getTaskName()) && (subscribe.getPriority() > selectPriority)) {
                if ((minSub == null) || (minSub.getPriority() > subscribe.getPriority())){
                    minSub = subscribe;
                }
            }
        }
        if (minSub != null){
            int minPriority = minSub.getPriority();
            minSub.setPriority(editTaskSubscribe.getPriority());
            editTaskSubscribe.setPriority(minPriority);
            taskSubscribeMap = null;
        }
        update();
    }

    public void deleteSelectSubscribe(){
        getInstance().getTaskSubscribes().remove(getEditTaskSubscribe());
        update();
        taskSubscribeMap = null;
    }


    @Override
    public void refresh() {
        super.refresh();
        taskSubscribeMap = null;
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
