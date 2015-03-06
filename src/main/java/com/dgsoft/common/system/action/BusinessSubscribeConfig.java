package com.dgsoft.common.system.action;

import com.dgsoft.common.system.business.Subscribe;
import com.dgsoft.common.system.business.TaskSubscribeReg;
import com.dgsoft.common.system.model.EditSubscribe;
import com.dgsoft.common.system.model.SubscribeGroup;
import com.dgsoft.common.system.model.ViewSubscribe;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;

import java.util.*;

/**
 * Created by cooper on 3/3/15.
 */
@Name("businessSubscribeConfig")
public class BusinessSubscribeConfig {

    @In
    private BusinessDefineHome businessDefineHome;

    private String editSubscribeId;

    private String createRegName;


    private Subscribe.SubscribeType getSubscribeType(){
        if ((businessDefineHome.getTaskName() == null) || businessDefineHome.getTaskName().trim().equals("") || businessDefineHome.getTaskName().trim().toUpperCase().equals(BusinessDefineHome.CREATE_TASK_NAME)){
            return Subscribe.SubscribeType.START_TASK;
        }else if (businessDefineHome.getTaskName().trim().toUpperCase().equals(BusinessDefineHome.BUSINESS_VIEW_TASK_NAME)){
            return Subscribe.SubscribeType.BUSINESS_VIEW;
        }else{
            return Subscribe.SubscribeType.TASK_OPER;
        }
    }

    @In
    private TaskSubscribeReg taskSubscribeReg;

    public List<TaskSubscribeReg.TaskSubscribeDefine> getCanAddViewSubscribeDefines(){
        List<TaskSubscribeReg.TaskSubscribeDefine> result = new ArrayList<TaskSubscribeReg.TaskSubscribeDefine>();
        for(TaskSubscribeReg.TaskSubscribeDefine define: taskSubscribeReg.getViewSubScribeDefines()){
            boolean existsInTask = false;
            for(SubscribeGroup group: businessDefineHome.getInstance().getSubscribeGroups()){
                if (group.getTask().equals(businessDefineHome.getTaskName())){
                    for (ViewSubscribe subScribe: group.getViewSubscribes()){
                        if (subScribe.getRegName().equals(define.getName())){
                            existsInTask = true;
                            break;
                        }
                    }
                }
                if (existsInTask){
                    break;
                }
            }
            if (! existsInTask){
                result.add(define);
            }

        }
        return result;
    }

    public List<TaskSubscribeReg.TaskSubscribeDefine> getCanAddEditSubscribeDefines(){
        List<TaskSubscribeReg.TaskSubscribeDefine> result = new ArrayList<TaskSubscribeReg.TaskSubscribeDefine>();
        for(TaskSubscribeReg.TaskSubscribeDefine define: taskSubscribeReg.getEditSubscribeDefines()){
            boolean existsInTask = false;
            for(EditSubscribe subscribe: businessDefineHome.getInstance().getEditSubscribes()){
                if (subscribe.getRegName().equals(define.getName()) && subscribe.getTask().equals(businessDefineHome.getTaskName())){
                    existsInTask = true;
                    break;
                }
            }
            if (! existsInTask){
                result.add(define);
            }

        }
        return result;
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

    public List<SubscribeGroup> getSelectTaskViewGroupList(){
        List<SubscribeGroup> result = new ArrayList<SubscribeGroup>();
        for(SubscribeGroup group: businessDefineHome.getInstance().getSubscribeGroups()){
            if (group.getTaskName().equals(businessDefineHome.getTaskName())){
                result.add(group);
            }
        }
        Collections.sort(result, new Comparator<SubscribeGroup>() {
            @Override
            public int compare(SubscribeGroup o1, SubscribeGroup o2) {
                return new Integer(o2.getPriority()).compareTo(o1.getPriority());
            }
        });
        return result;
    }



    private int getEditSubscribeMaxPriority() {
        int result = 0;
        for (EditSubscribe subscribe : businessDefineHome.getInstance().getEditSubscribes()) {
            if (businessDefineHome.getTaskName().equals(subscribe.getTaskName()) && (subscribe.getPriority() > result)) {
                result = subscribe.getPriority();
            }
        }
        return result;
    }

    private EditSubscribe getSelectEditTaskSubscribe(){
        for(EditSubscribe subscribe: businessDefineHome.getEditTaskSubscribeMap().get(businessDefineHome.getTaskName())){
            if (subscribe.getId().equals(editSubscribeId)){
                return subscribe;
            }
        }
        return null;
    }

    @Transactional
    public void createTaskSubscribe() {

        EditSubscribe editSubscribe =
                new EditSubscribe(UUID.randomUUID().toString().replace("-", "").toUpperCase(),
                        businessDefineHome.getTaskName(), createRegName,getSubscribeType(),
                        businessDefineHome.getInstance(),getEditSubscribeMaxPriority() + 1);

        businessDefineHome.getEntityManager().persist(editSubscribe);
        businessDefineHome.getEntityManager().flush();
        createRegName = null;
        businessDefineHome.refresh();

    }



    public void upSelectTaskSubscribe() {
        EditSubscribe editEditSubscribe = getSelectEditTaskSubscribe();
        int selectPriority = editEditSubscribe.getPriority();

        //Integer maxPriority = null;

        EditSubscribe maxSub = null;
        for (EditSubscribe subscribe : businessDefineHome.getInstance().getEditSubscribes()) {
            if (editEditSubscribe.getTaskName().equals(subscribe.getTaskName()) && (subscribe.getPriority() < selectPriority)) {
                if ((maxSub == null) || (maxSub.getPriority() < subscribe.getPriority())){
                    maxSub = subscribe;
                }
                //subscribe.setPriority(subscribe.getPriority() - 1);
            }
        }

        if (maxSub != null){
            int maxPriority = maxSub.getPriority();
            maxSub.setPriority(editEditSubscribe.getPriority());
            editEditSubscribe.setPriority(maxPriority);
            businessDefineHome.refreshEditTaskSubScribe();
        }

        businessDefineHome.update();
    }

    public void downSelectTaskSubscribe() {
        EditSubscribe editEditSubscribe = getSelectEditTaskSubscribe();
        int selectPriority = editEditSubscribe.getPriority();
        EditSubscribe minSub = null;
        for (EditSubscribe subscribe : businessDefineHome.getInstance().getEditSubscribes()) {
            if (editEditSubscribe.getTaskName().equals(subscribe.getTaskName()) && (subscribe.getPriority() > selectPriority)) {
                if ((minSub == null) || (minSub.getPriority() > subscribe.getPriority())){
                    minSub = subscribe;
                }
            }
        }
        if (minSub != null){
            int minPriority = minSub.getPriority();
            minSub.setPriority(editEditSubscribe.getPriority());
            editEditSubscribe.setPriority(minPriority);
            businessDefineHome.refreshEditTaskSubScribe();
        }
        businessDefineHome.update();
    }

    public void deleteSelectSubscribe(){
        businessDefineHome.getInstance().getEditSubscribes().remove(getSelectEditTaskSubscribe());
        businessDefineHome.update();
        businessDefineHome.refreshEditTaskSubScribe();
    }



    public int getViewGroupMaxPriority(){
        int result = 0;
        for (SubscribeGroup group: businessDefineHome.getInstance().getSubscribeGroups()){
            if (businessDefineHome.getTaskName().equals(group.getTaskName()) && (group.getPriority() > result)){
                result = group.getPriority();
            }
        }
        return result;
    }



    private String selectGroupId;

    public String getSelectGroupId() {
        return selectGroupId;
    }

    public void setSelectGroupId(String selectGroupId) {
        this.selectGroupId = selectGroupId;
    }

    public void removeGroup(){
        for(SubscribeGroup group: businessDefineHome.getInstance().getSubscribeGroups()){
            if (group.getId().equals(selectGroupId)){
                businessDefineHome.getInstance().getSubscribeGroups().remove(group);
                businessDefineHome.update();
                return;
            }
        }

    }

    private SubscribeGroup getSelectGroup(){
        for(SubscribeGroup group: businessDefineHome.getInstance().getSubscribeGroups()){
            if (group.getId().equals(selectGroupId)){
                return group;
            }
        }
        return null;
    }



    public void upSelectGroup() {
        SubscribeGroup selectGroup = getSelectGroup();
        int selectPriority = selectGroup.getPriority();

        //Integer maxPriority = null;

        SubscribeGroup maxGroup = null;
        for (SubscribeGroup group : businessDefineHome.getInstance().getSubscribeGroups()) {
            if ( group.getTaskName().equals(businessDefineHome.getTaskName()) && (group.getPriority() < selectPriority)) {
                if ((maxGroup == null) || (maxGroup.getPriority() < group.getPriority())){
                    maxGroup = group;
                }
                //subscribe.setPriority(subscribe.getPriority() - 1);
            }
        }

        if (maxGroup != null){
            int maxPriority = maxGroup.getPriority();
            maxGroup.setPriority(selectGroup.getPriority());
            selectGroup.setPriority(maxPriority);
        }

        businessDefineHome.update();
    }

    public void downSelectGroup() {
        SubscribeGroup selectGroup = getSelectGroup();
        int selectPriority = selectGroup.getPriority();

        //Integer maxPriority = null;

        SubscribeGroup maxGroup = null;
        for (SubscribeGroup group : businessDefineHome.getInstance().getSubscribeGroups()) {
            if ( group.getTaskName().equals(businessDefineHome.getTaskName()) && (group.getPriority() > selectPriority)) {
                if ((maxGroup == null) || (maxGroup.getPriority() > group.getPriority())){
                    maxGroup = group;
                }
                //subscribe.setPriority(subscribe.getPriority() - 1);
            }
        }

        if (maxGroup != null){
            int maxPriority = maxGroup.getPriority();
            maxGroup.setPriority(selectGroup.getPriority());
            selectGroup.setPriority(maxPriority);
        }

        businessDefineHome.update();
    }

    private String newGroupName;

    public String getNewGroupName() {
        return newGroupName;
    }

    public void setNewGroupName(String newGroupName) {
        this.newGroupName = newGroupName;
    }


    @Transactional
    public String createNewGroup(){
        businessDefineHome.getInstance().getSubscribeGroups().add(
        new SubscribeGroup(businessDefineHome.getTaskName(),newGroupName,getSubscribeType(),businessDefineHome.getInstance(),getViewGroupMaxPriority() + 1)
        );
        newGroupName = "";
        return businessDefineHome.update();
    }


    @In(create = true)
    private SubscribeGroupHome subscribeGroupHome;


    @Transactional
    public void createViewSubscribe(){
        subscribeGroupHome.getInstance().getViewSubscribes().add(
        new ViewSubscribe(UUID.randomUUID().toString().replace("-", "").toUpperCase(),createRegName,subscribeGroupHome.getMaxPriority() + 1,subscribeGroupHome.getInstance()));
        subscribeGroupHome.update();
    }

    private String selectViewSubscribeId;

    public String getSelectViewSubscribeId() {
        return selectViewSubscribeId;
    }

    public void setSelectViewSubscribeId(String selectViewSubscribeId) {
        this.selectViewSubscribeId = selectViewSubscribeId;
    }

    private ViewSubscribe getSelectViewSubscribe(){
        for (ViewSubscribe subscribe: subscribeGroupHome.getInstance().getViewSubscribes()){
            if (subscribe.getId().equals(selectViewSubscribeId)){
                return  subscribe;
            }
        }
        return null;
    }

    public void removeViewSubscribe(){
        subscribeGroupHome.getInstance().getViewSubscribes().remove(getSelectViewSubscribe());

        subscribeGroupHome.update();
        businessDefineHome.refresh();
    }

    public void upSelectViewSubscribe() {
        ViewSubscribe selectSubscribe = getSelectViewSubscribe();
        int selectPriority = selectSubscribe.getPriority();

        //Integer maxPriority = null;

        ViewSubscribe maxSub = null;
        for (ViewSubscribe subscribe : subscribeGroupHome.getInstance().getViewSubscribes()) {
            if ( (subscribe.getPriority() < selectPriority)) {
                if ((maxSub == null) || (maxSub.getPriority() < subscribe.getPriority())){
                    maxSub = subscribe;
                }
                //subscribe.setPriority(subscribe.getPriority() - 1);
            }
        }

        if (maxSub != null){
            int maxPriority = maxSub.getPriority();
            maxSub.setPriority(selectSubscribe.getPriority());
            selectSubscribe.setPriority(maxPriority);
        }

        subscribeGroupHome.update();
    }

    public void downSelectViewSubscribe() {
        ViewSubscribe selectSubscribe = getSelectViewSubscribe();
        int selectPriority = selectSubscribe.getPriority();

        //Integer maxPriority = null;

        ViewSubscribe maxSub = null;
        for (ViewSubscribe subscribe : subscribeGroupHome.getInstance().getViewSubscribes()) {
            if ( (subscribe.getPriority() > selectPriority)) {
                if ((maxSub == null) || (maxSub.getPriority() > subscribe.getPriority())){
                    maxSub = subscribe;
                }
                //subscribe.setPriority(subscribe.getPriority() - 1);
            }
        }

        if (maxSub != null){
            int maxPriority = maxSub.getPriority();
            maxSub.setPriority(selectSubscribe.getPriority());
            selectSubscribe.setPriority(maxPriority);
        }

        subscribeGroupHome.update();
    }

}
