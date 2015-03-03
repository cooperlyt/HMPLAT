package com.dgsoft.common.system.action;

import com.dgsoft.common.system.SystemEntityHome;
import com.dgsoft.common.system.model.*;
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

    public static final String CREATE_TASK_NAME = "_CREATE";

    public static final String BUSINESS_VIEW_TASK_NAME = "_VIEW";

    @In
    private FacesMessages facesMessages;

    private String taskName = CREATE_TASK_NAME;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
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


    @Override
    public void refresh() {
        super.refresh();
        refreshEditTaskSubScribe();
    }

    public void refreshEditTaskSubScribe(){
        editTaskSubscribeMap = null;
    }

    private Map<String, List<EditSubscribe>> editTaskSubscribeMap;



    public Map<String, List<EditSubscribe>> getEditTaskSubscribeMap(){
        if (editTaskSubscribeMap == null){
            editTaskSubscribeMap = new HashMap<String, List<EditSubscribe>>();
            List<EditSubscribe> all = new ArrayList<EditSubscribe>(getInstance().getEditSubscribes());
            for (EditSubscribe subscribe: all){


                List<EditSubscribe> result = editTaskSubscribeMap.get(subscribe.getTask());
                if (result == null){
                    result = new ArrayList<EditSubscribe>();
                    editTaskSubscribeMap.put(subscribe.getTask(),result);
                }
                result.add(subscribe);
            }

            for(List<EditSubscribe> subscribes: editTaskSubscribeMap.values()){
                Collections.sort(subscribes, new Comparator<EditSubscribe>() {
                    @Override
                    public int compare(EditSubscribe o1, EditSubscribe o2) {
                        return new Integer(o2.getPriority()).compareTo(o1.getPriority());
                    }
                });
            }
        }
        return editTaskSubscribeMap;
    }

    public List<EditSubscribe> getEditTaskSubscribeList() {
        List<EditSubscribe> result = getEditTaskSubscribeMap().get(getTaskName());
        return (result == null) ? new ArrayList<EditSubscribe>(0) : result;
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

    public int getSubscribeCount(String taskName){
        int result = 0;
        for (EditSubscribe subscribe: getInstance().getEditSubscribes()){
            if (subscribe.getTask().equals(taskName)){
                result ++;
            }
        }
        for (SubscribeGroup group: getInstance().getSubscribeGroups()){
            if (group.getTask().equals(taskName)){
                result += group.getViewSubscribes().size();
            }
        }
        return result;
    }

}
