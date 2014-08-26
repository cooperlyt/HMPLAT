package com.dgsoft.common.system.business;

import com.dgsoft.common.TotalDataGroup;
import com.dgsoft.common.TotalGroupStrategy;
import com.dgsoft.common.system.action.BusinessDefineHome;
import com.dgsoft.common.system.model.TaskSubscribe;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.web.RequestParameter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by cooper on 8/26/14.
 */
@Scope(ScopeType.CONVERSATION)
@Name("taskPublish")
public class TaskPublish {

    @In
    private BusinessDefineHome businessDefineHome;

    @In
    private TaskSubscribeReg taskSubscribeReg;

    @RequestParameter
    private String taskName;

    private List<TaskSubscribeReg.TaskSubscribeDefine> defines;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        if ((taskName != null) && taskName.trim().equals("")) {
            this.taskName = null;
        } else
            this.taskName = taskName;
    }

    public void setTaskNameAndPublish(String taskName) {
        this.taskName = taskName;
        publish();
    }

    public boolean wire() {
        boolean result = true;
        for (TaskSubscribeReg.TaskSubscribeDefine define : defines) {
            if (define.isHaveComponent()) {
                if (!define.getComponents().wire()) {
                    result = false;
                }
            }
        }
        return result;
    }

    public boolean valid() {
        boolean result = true;
        for (TaskSubscribeReg.TaskSubscribeDefine define : defines) {
            if (define.isHaveComponent()) {
                if (!define.getComponents().valid()) {
                    result = false;
                }
            }
        }
        return result;
    }


    private List<TotalDataGroup<String, TaskSubscribeReg.TaskSubscribeDefine>> getPageGroupByType(TaskSubscribeReg.TaskSubscribeDefineType type) {
        List<TaskSubscribeReg.TaskSubscribeDefine> result = new ArrayList<TaskSubscribeReg.TaskSubscribeDefine>();
        for (TaskSubscribeReg.TaskSubscribeDefine define : defines) {
            if (define.isHavePage() && define.getType().equals(type)) {
                result.add(define);
            }
        }
        return TotalDataGroup.groupBy(result, new SubscribeGroupStrategy());
    }

    public List<TotalDataGroup<String, TaskSubscribeReg.TaskSubscribeDefine>> getEditPageGroup() {
        return getPageGroupByType(TaskSubscribeReg.TaskSubscribeDefineType.EDIT);
    }

    public List<TotalDataGroup<String, TaskSubscribeReg.TaskSubscribeDefine>> getAloneEditPageGroup() {
        return getPageGroupByType(TaskSubscribeReg.TaskSubscribeDefineType.ALONE_EDIT);
    }

    public List<TotalDataGroup<String, TaskSubscribeReg.TaskSubscribeDefine>> getViewPageGroup() {
        return getPageGroupByType(TaskSubscribeReg.TaskSubscribeDefineType.VIEW);
    }

    public List<String> getPages() {
        List<String> result = new ArrayList<String>();
        for (TaskSubscribeReg.TaskSubscribeDefine define : defines) {
            if (define.isHavePage()) {
                result.add(define.getPage());
            }
        }
        return result;
    }

    public void publish() {
        defines = new ArrayList<TaskSubscribeReg.TaskSubscribeDefine>();
        for (TaskSubscribe sub : businessDefineHome.getTaskSubscribeList()) {
            if ((sub.getType().equals(TaskSubscribe.SubscribeType.START_TASK) &&
                    (taskName == null)) ||
                    (sub.getType().equals(TaskSubscribe.SubscribeType.TASK_OPER) && taskName.equals(sub.getTaskName()))) {
                TaskSubscribeReg.TaskSubscribeDefine define = taskSubscribeReg.getDefineByName(sub.getRegName());
                defines.add(define);
                if (define.isHaveComponent()) {
                    define.getComponents().init();
                }
            }
        }
    }

    public static class SubscribeGroupStrategy implements TotalGroupStrategy<String, TaskSubscribeReg.TaskSubscribeDefine> {

        @Override
        public String getKey(TaskSubscribeReg.TaskSubscribeDefine taskSubscribeDefine) {
            return taskSubscribeDefine.getCategory();
        }

        @Override
        public Object totalGroupData(Collection<TaskSubscribeReg.TaskSubscribeDefine> datas) {
            return null;
        }
    }

}
