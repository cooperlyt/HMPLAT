package com.dgsoft.common.system.action;

import com.dgsoft.common.Entry;
import com.dgsoft.common.system.business.Subscribe;
import com.dgsoft.common.system.business.TaskSubscribeReg;
import com.dgsoft.common.system.model.CreateComponent;
import com.dgsoft.common.system.model.TaskSubscribe;
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

    private Subscribe.SubscribeType groupType;

    @In
    private BusinessDefineHome businessDefineHome;

    private String editSubscribeId;

    private String createRegName;

    @In
    private TaskSubscribeReg taskSubscribeReg;

    public String getGroupTypeName() {
        if (groupType == null)
            return null;
        return groupType.name();
    }

    public void setGroupTypeName(String name) {
        if (name == null || name.trim().equals("")) {
            groupType = null;
        }
        groupType = Subscribe.SubscribeType.valueOf(name);
    }


    public List<TaskSubscribeReg.SubscribeDefine> getCanAddViewSubscribeDefines() {
        List<TaskSubscribeReg.SubscribeDefine> result = new ArrayList<TaskSubscribeReg.SubscribeDefine>();
        List<? extends TaskSubscribeReg.SubscribeDefine> defines;
        if (Subscribe.SubscribeType.TASK_INFO.equals(groupType)) {
            defines = taskSubscribeReg.getViewSubScribeDefines();
        } else {
            defines = taskSubscribeReg.getEditSubscribeDefines();
        }
        for (TaskSubscribeReg.SubscribeDefine define : defines) {

            boolean existsInTask = false;
            for (SubscribeGroup group : getSubscribeGroups()) {

                if (group.getTask().equals(businessDefineHome.getTaskName())) {
                    for (ViewSubscribe subScribe : group.getViewSubscribes()) {
                        if (subScribe.getRegName().equals(define.getName())) {
                            existsInTask = true;
                            break;
                        }
                    }
                }
                if (existsInTask) {
                    break;
                }
            }
            if (!existsInTask) {
                result.add(define);
            }

        }
        return result;
    }

    public List<TaskSubscribeReg.CompleteSubscribeDefine> getCanAddCompleteSubscribeDefines() {
        List<TaskSubscribeReg.CompleteSubscribeDefine> result = new ArrayList<TaskSubscribeReg.CompleteSubscribeDefine>();
        for (TaskSubscribeReg.CompleteSubscribeDefine define : taskSubscribeReg.getCompleteSubscribeDefines()) {
            boolean existsInTask = false;
            for (TaskSubscribe subscribe : businessDefineHome.getInstance().getTaskSubscribes()) {
                if (subscribe.getRegName().equals(define.getName()) && subscribe.getTask().equals(businessDefineHome.getTaskName())) {
                    existsInTask = true;
                    break;
                }
            }
            if (!existsInTask) {
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


    private int getSubscribeMaxPriority(Subscribe.SubscribeType type) {
        int result = 0;
        for (TaskSubscribe subscribe : businessDefineHome.getInstance().getTaskSubscribes()) {
            if (type.equals(subscribe.getType()) && businessDefineHome.getTaskName().equals(subscribe.getTaskName()) && (subscribe.getPriority() > result)) {
                result = subscribe.getPriority();
            }
        }
        return result;
    }


    private TaskSubscribe getSelectTaskSubscribe() {
        for (TaskSubscribe subscribe : businessDefineHome.getInstance().getTaskSubscribes()) {
            if (subscribe.getId().equals(editSubscribeId)) {
                return subscribe;
            }
        }
        return null;
    }

    @Transactional
    public void createTaskSubscribe() {

        TaskSubscribe editSubscribe =
                new TaskSubscribe(UUID.randomUUID().toString().replace("-", "").toUpperCase(),
                        businessDefineHome.getTaskName(), createRegName, Subscribe.SubscribeType.TASK_INFO,
                        businessDefineHome.getInstance(), getSubscribeMaxPriority(Subscribe.SubscribeType.TASK_INFO) + 1);

        businessDefineHome.getEntityManager().persist(editSubscribe);
        businessDefineHome.getEntityManager().flush();
        createRegName = null;
        businessDefineHome.refresh();

    }

    @Transactional
    public void createCompleteSubscribe() {
        TaskSubscribe editSubscribe =
                new TaskSubscribe(UUID.randomUUID().toString().replace("-", "").toUpperCase(),
                        businessDefineHome.getTaskName(), createRegName, Subscribe.SubscribeType.TASK_COMPLETE,
                        businessDefineHome.getInstance(), getSubscribeMaxPriority(Subscribe.SubscribeType.TASK_COMPLETE) + 1);

        businessDefineHome.getEntityManager().persist(editSubscribe);
        businessDefineHome.getEntityManager().flush();
        createRegName = null;
        businessDefineHome.refresh();
    }


    public void upSelectTaskSubscribe() {
        TaskSubscribe editEditSubscribe = getSelectTaskSubscribe();
        int selectPriority = editEditSubscribe.getPriority();

        //Integer maxPriority = null;

        TaskSubscribe maxSub = null;
        for (TaskSubscribe subscribe : businessDefineHome.getInstance().getTaskSubscribes()) {
            if (editEditSubscribe.getType().equals(subscribe.getType()) &&
                    editEditSubscribe.getTaskName().equals(subscribe.getTaskName()) && (subscribe.getPriority() < selectPriority)) {
                if ((maxSub == null) || (maxSub.getPriority() < subscribe.getPriority())) {
                    maxSub = subscribe;
                }
                //subscribe.setPriority(subscribe.getPriority() - 1);
            }
        }

        if (maxSub != null) {
            int maxPriority = maxSub.getPriority();
            maxSub.setPriority(editEditSubscribe.getPriority());
            editEditSubscribe.setPriority(maxPriority);
            businessDefineHome.refreshSubscribe();
        }

        businessDefineHome.update();
        businessDefineHome.refreshSubscribe();
    }

    public void downSelectTaskSubscribe() {
        TaskSubscribe editEditSubscribe = getSelectTaskSubscribe();
        int selectPriority = editEditSubscribe.getPriority();
        TaskSubscribe minSub = null;
        for (TaskSubscribe subscribe : businessDefineHome.getInstance().getTaskSubscribes()) {
            if (editEditSubscribe.getType().equals(subscribe.getType()) &&
                    editEditSubscribe.getTaskName().equals(subscribe.getTaskName()) && (subscribe.getPriority() > selectPriority)) {
                if ((minSub == null) || (minSub.getPriority() > subscribe.getPriority())) {
                    minSub = subscribe;
                }
            }
        }
        if (minSub != null) {
            int minPriority = minSub.getPriority();
            minSub.setPriority(editEditSubscribe.getPriority());
            editEditSubscribe.setPriority(minPriority);
            businessDefineHome.refreshSubscribe();
        }
        businessDefineHome.update();
        businessDefineHome.refreshSubscribe();
    }

    public void deleteSelectSubscribe() {
        businessDefineHome.getInstance().getTaskSubscribes().remove(getSelectTaskSubscribe());
        businessDefineHome.update();
        businessDefineHome.refreshSubscribe();
    }


    public int getViewGroupMaxPriority() {
        int result = 0;
        for (SubscribeGroup group : getSubscribeGroups()) {
            if (businessDefineHome.getTaskName().equals(group.getTaskName()) && (group.getPriority() > result)) {
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

    public void removeGroup() {
        for (SubscribeGroup group : businessDefineHome.getInstance().getSubscribeGroups()) {
            if (group.getId().equals(selectGroupId)) {
                businessDefineHome.getInstance().getSubscribeGroups().remove(group);
                businessDefineHome.update();
                businessDefineHome.refreshSubscribe();
                return;
            }
        }

    }

    private SubscribeGroup getSelectGroup() {
        for (SubscribeGroup group : businessDefineHome.getInstance().getSubscribeGroups()) {
            if (group.getId().equals(selectGroupId)) {
                return group;
            }
        }
        return null;
    }

    private List<SubscribeGroup> getSubscribeGroups(){
        List<SubscribeGroup> result = new ArrayList<SubscribeGroup>();
        for (SubscribeGroup group: businessDefineHome.getInstance().getSubscribeGroups()){
            if (group.getType().equals(groupType)){
                result.add(group);
            }
        }
        return result;
    }


    public void upSelectGroup() {
        SubscribeGroup selectGroup = getSelectGroup();
        int selectPriority = selectGroup.getPriority();

        //Integer maxPriority = null;

        SubscribeGroup maxGroup = null;
        for (SubscribeGroup group : getSubscribeGroups()) {
            if (group.getTaskName().equals(businessDefineHome.getTaskName()) && (group.getPriority() < selectPriority)) {
                if ((maxGroup == null) || (maxGroup.getPriority() < group.getPriority())) {
                    maxGroup = group;
                }
                //subscribe.setPriority(subscribe.getPriority() - 1);
            }
        }

        if (maxGroup != null) {
            int maxPriority = maxGroup.getPriority();
            maxGroup.setPriority(selectGroup.getPriority());
            selectGroup.setPriority(maxPriority);
        }

        businessDefineHome.update();
        businessDefineHome.refreshSubscribe();
    }

    public void downSelectGroup() {
        SubscribeGroup selectGroup = getSelectGroup();
        int selectPriority = selectGroup.getPriority();

        //Integer maxPriority = null;

        SubscribeGroup maxGroup = null;
        for (SubscribeGroup group : getSubscribeGroups()) {
            if (group.getTaskName().equals(businessDefineHome.getTaskName()) && (group.getPriority() > selectPriority)) {
                if ((maxGroup == null) || (maxGroup.getPriority() > group.getPriority())) {
                    maxGroup = group;
                }
                //subscribe.setPriority(subscribe.getPriority() - 1);
            }
        }

        if (maxGroup != null) {
            int maxPriority = maxGroup.getPriority();
            maxGroup.setPriority(selectGroup.getPriority());
            selectGroup.setPriority(maxPriority);
        }

        businessDefineHome.update();
        businessDefineHome.refreshSubscribe();
    }

    //private String newGroupName;

    //private String newGroupCssName;

//    public String getNewGroupCssName() {
//        return newGroupCssName;
//    }

    private SubscribeGroup newGroup = new SubscribeGroup();

//    public void setNewGroupCssName(String newGroupCssName) {
//        this.newGroupCssName = newGroupCssName;
//    }
//
//    public String getNewGroupName() {
//        return newGroupName;
//    }
//
//    public void setNewGroupName(String newGroupName) {
//        this.newGroupName = newGroupName;
//    }


    public SubscribeGroup getNewGroup() {
        return newGroup;
    }

    public void setNewGroup(SubscribeGroup newGroup) {
        this.newGroup = newGroup;
    }

    @Transactional
    public String createNewGroup() {

        newGroup.setTaskName(businessDefineHome.getTaskName());
        newGroup.setType(groupType);
        newGroup.setBusinessDefine(businessDefineHome.getInstance());
        newGroup.setPriority(getViewGroupMaxPriority() + 1);
        businessDefineHome.getInstance().getSubscribeGroups().add(newGroup);

        businessDefineHome.update();
        businessDefineHome.refreshSubscribe();
        newGroup = new SubscribeGroup();

        return "updated";
    }


    @In(create = true)
    private SubscribeGroupHome subscribeGroupHome;


    @Transactional
    public void createViewSubscribe() {
        subscribeGroupHome.getInstance().getViewSubscribes().add(
                new ViewSubscribe(UUID.randomUUID().toString().replace("-", "").toUpperCase(), createRegName, subscribeGroupHome.getMaxPriority() + 1, subscribeGroupHome.getInstance()));
        subscribeGroupHome.update();
    }

    private String selectViewSubscribeId;

    public String getSelectViewSubscribeId() {
        return selectViewSubscribeId;
    }

    public void setSelectViewSubscribeId(String selectViewSubscribeId) {
        this.selectViewSubscribeId = selectViewSubscribeId;
    }

    private ViewSubscribe getSelectViewSubscribe() {
        for (ViewSubscribe subscribe : subscribeGroupHome.getInstance().getViewSubscribes()) {
            if (subscribe.getId().equals(selectViewSubscribeId)) {
                return subscribe;
            }
        }
        return null;
    }

    public void removeViewSubscribe() {
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
            if ((subscribe.getPriority() < selectPriority)) {
                if ((maxSub == null) || (maxSub.getPriority() < subscribe.getPriority())) {
                    maxSub = subscribe;
                }
                //subscribe.setPriority(subscribe.getPriority() - 1);
            }
        }

        if (maxSub != null) {
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
            if ((subscribe.getPriority() > selectPriority)) {
                if ((maxSub == null) || (maxSub.getPriority() > subscribe.getPriority())) {
                    maxSub = subscribe;
                }
                //subscribe.setPriority(subscribe.getPriority() - 1);
            }
        }

        if (maxSub != null) {
            int maxPriority = maxSub.getPriority();
            maxSub.setPriority(selectSubscribe.getPriority());
            selectSubscribe.setPriority(maxPriority);
        }

        subscribeGroupHome.update();
    }

}
