package com.dgsoft.common.system.action;

import com.dgsoft.common.system.SystemEntityHome;
import com.dgsoft.common.system.business.TaskSubscribeReg;
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
    private TaskSubscribeReg taskSubscribeReg;

    @In
    private FacesMessages facesMessages;

    public String validSubscribe() {
        for (TaskSubscribeReg.EditSubscribeDefine define : getEditSubscribeDefines()) {
            if (define.isHaveComponent()) {
                if (!"success".equals(define.getComponents().validSubscribe())) {
                    return "";
                }
            }
        }
        return "success";
    }


    public String saveSubscribe() {

        for (TaskSubscribeReg.EditSubscribeDefine define : getEditSubscribeDefines()) {
            if (define.isHaveComponent()) {
                if (!"saved".equals(define.getComponents().saveSubscribe())) {
                    return "";
                }
            }
        }
        return "success";
    }

    private String taskName = CREATE_TASK_NAME;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        if ((taskName == null) || (this.taskName == null) || !taskName.equals(this.getTaskName())){
            refreshSubscribe();
        }
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
        refreshSubscribe();
    }

    public void refreshSubscribe(){
        editSubscribes = null;
        viewSubscribeGroups = null;
    }

    private List<EditSubscribe> editSubscribes;

    private List<SubscribeGroup> viewSubscribeGroups;

    public List<EditSubscribe> getEditSubscribes() {
        if (editSubscribes == null){
            editSubscribes = new ArrayList<EditSubscribe>();
            for(EditSubscribe subscribe: getInstance().getEditSubscribes()){
                if (subscribe.getTaskName().equals(taskName)){
                    editSubscribes.add(subscribe);
                }
            }
            Collections.sort(editSubscribes, new Comparator<EditSubscribe>() {
                @Override
                public int compare(EditSubscribe o1, EditSubscribe o2) {
                    return new Integer(o2.getPriority()).compareTo(o1.getPriority());
                }
            });
        }

        return editSubscribes;
    }

    public List<SubscribeGroup> getViewSubscribeGroups() {
        if (viewSubscribeGroups == null){
            viewSubscribeGroups = new ArrayList<SubscribeGroup>();
            for(SubscribeGroup group: getInstance().getSubscribeGroups()){
                if (group.getTaskName().equals(getTaskName())){
                    viewSubscribeGroups.add(group);
                }
            }
            Collections.sort(viewSubscribeGroups, new Comparator<SubscribeGroup>() {
                @Override
                public int compare(SubscribeGroup o1, SubscribeGroup o2) {
                    return new Integer(o2.getPriority()).compareTo(o1.getPriority());
                }
            });
        }

        return viewSubscribeGroups;
    }


    public List<TaskSubscribeReg.EditSubscribeDefine> getEditSubscribeDefines(){
        List<TaskSubscribeReg.EditSubscribeDefine> result = new ArrayList<TaskSubscribeReg.EditSubscribeDefine>();
        for(EditSubscribe subscribe: getEditSubscribes()){
           result.add(taskSubscribeReg.getEditDefineByName(subscribe.getRegName()));
        }
        return result;
    }

    public List<TaskSubscribeReg.SubscribeDefineGroup> getViewSubscribeDefineGroups(){
        List<TaskSubscribeReg.SubscribeDefineGroup> result = new ArrayList<TaskSubscribeReg.SubscribeDefineGroup>();

        for(SubscribeGroup group: getViewSubscribeGroups()){
            TaskSubscribeReg.SubscribeDefineGroup defineGroup = new TaskSubscribeReg.SubscribeDefineGroup(group.getName());

            for(ViewSubscribe subscribe : group.getViewSubscribeList()){
                defineGroup.add(taskSubscribeReg.getViewDefineByName(subscribe.getRegName()));
            }
            if (! defineGroup.isEmpty())
            result.add(defineGroup);

        }
        return result;
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
