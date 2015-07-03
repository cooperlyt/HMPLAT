package com.dgsoft.common.system.action;

import com.dgsoft.common.OrderBeanComparator;
import com.dgsoft.common.OrderModel;
import com.dgsoft.common.system.SystemEntityHome;
import com.dgsoft.common.system.business.*;
import com.dgsoft.common.system.model.*;
import com.google.common.collect.Iterators;
import org.jboss.seam.Component;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.bpm.ManagedJbpmContext;
import org.jboss.seam.core.Expressions;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;
import org.jbpm.graph.def.ProcessDefinition;
import org.richfaces.model.SortMode;

import javax.faces.event.ValueChangeEvent;
import javax.persistence.criteria.Order;
import javax.swing.tree.TreeNode;
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
    private TaskSubscribeReg taskSubscribeReg;

    @In
    private FacesMessages facesMessages;


    public String validSubscribe() {
        return validSubscribes().name();
    }

    public TaskSubscribeComponent.ValidResult validSubscribes() {
        TaskSubscribeComponent.ValidResult result = TaskSubscribeComponent.ValidResult.SUCCESS;

        for (TaskSubscribeReg.EditSubscribeDefine define : getEditSubscribeDefines()) {
            if (define.isHaveComponent()) {
                TaskSubscribeComponent.ValidResult r = define.getComponents().validSubscribe();
                if (r.getPri() > result.getPri()) {
                    result = r;
                }
            }
        }
        return result;
    }

    public String getDescription() {
        String val = getInstance().getDescription();
        if ((val == null) || val.trim().equals("")) {
            return "";
        } else {
            return new Expressions().createValueExpression(val).toString();
        }

    }


    public boolean saveSubscribes() {


        for (TaskSubscribeReg.EditSubscribeDefine define : getEditSubscribeDefines()) {
            if (define.isHaveComponent()) {
                if (!define.getComponents().saveSubscribe()) {
                    return false;
                }
            }
        }
        return true;
    }

    public TaskSubscribeComponent.ValidResult validTaskComplete() {
        TaskSubscribeComponent.ValidResult result = TaskSubscribeComponent.ValidResult.SUCCESS;
        for (TaskSubscribeReg.CompleteSubscribeDefine define : getCompleteSubscribeDefines()) {
            TaskSubscribeComponent.ValidResult r = define.getComponents().valid();
            if (r.getPri() > result.getPri()) {
                result = r;
            }
        }
        return result;
    }

    public void completeTask() {
        for (TaskSubscribeReg.CompleteSubscribeDefine define : getCompleteSubscribeDefines()) {
            define.getComponents().complete();
        }

    }

    private String taskName = Subscribe.CREATE_TASK_NAME;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        if ((taskName == null) || (this.taskName == null) || !taskName.equals(this.getTaskName())) {
            refreshSubscribe();
        }
        this.taskName = taskName;
    }


    public List<String> getWfTaskNames() {
        ProcessDefinition lasterPD = ManagedJbpmContext.instance().getGraphSession().findLatestProcessDefinition(getInstance().getWfName());

        return new ArrayList<String>(lasterPD.getTaskMgmtDefinition().getTasks().keySet());
    }


    public List<BusinessNeedFile> getFileSubscribe(String taskName) {
        List<BusinessNeedFile> result = new ArrayList<BusinessNeedFile>(0);
        for (BusinessNeedFile businessNeedFile : getInstance().getBusinessNeedFiles()) {
            if (((taskName == null) && ((businessNeedFile.getTaskName() == null) || (businessNeedFile.getTaskName().equals(""))))
                    || ((taskName != null) && taskName.equals(businessNeedFile.getTaskName()))) {
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

    public void refreshSubscribe() {
        editSubscribes = null;
        viewSubscribeGroups = null;
        operSubscribes = null;
        completeSubscribes = null;
    }

    private List<TaskSubscribe> editSubscribes;

    private List<TaskSubscribe> operSubscribes;

    private List<TaskSubscribe> completeSubscribes;

    private List<SubscribeGroup> viewSubscribeGroups;


    public List<TaskSubscribe> getOperSubscribes() {
        if (operSubscribes == null) {
            loadSubscribes();
        }
        return operSubscribes;
    }

    public List<TaskSubscribe> getCompleteSubscribes() {
        if (completeSubscribes == null) {
            loadSubscribes();
        }
        return completeSubscribes;
    }


    private void loadSubscribes() {
        operSubscribes = new ArrayList<TaskSubscribe>();
        editSubscribes = new ArrayList<TaskSubscribe>();
        completeSubscribes = new ArrayList<TaskSubscribe>();
        for (TaskSubscribe subscribe : getInstance().getTaskSubscribes()) {
            if (subscribe.getTaskName().equals(taskName)) {
                if (Subscribe.SubscribeType.TASK_INFO.equals(subscribe.getType())) {
                    editSubscribes.add(subscribe);
                } else if (Subscribe.SubscribeType.TASK_COMPLETE.equals(subscribe.getType())) {
                    completeSubscribes.add(subscribe);
                } else if (Subscribe.SubscribeType.TASK_OPERATOR.equals(subscribe.getType())) {
                    operSubscribes.add(subscribe);
                }

            }
        }
        Collections.sort(editSubscribes, new Comparator<TaskSubscribe>() {
            @Override
            public int compare(TaskSubscribe o1, TaskSubscribe o2) {
                return new Integer(o2.getPriority()).compareTo(o1.getPriority());
            }
        });
    }

    public List<TaskSubscribe> getEditSubscribes() {
        if (editSubscribes == null) {
            loadSubscribes();
        }

        return editSubscribes;
    }

    public void initEditSubscribes() {
        for (TaskSubscribeReg.EditSubscribeDefine define : getEditSubscribeDefines()) {
            if (define.isHaveComponent()) {
                define.getComponents().initSubscribe();

            }
        }
    }

    public List<SubscribeGroup> getViewSubscribeGroups() {
        if (viewSubscribeGroups == null) {
            viewSubscribeGroups = new ArrayList<SubscribeGroup>();
            for (SubscribeGroup group : getInstance().getSubscribeGroups()) {
                if (group.getTaskName().equals(getTaskName())) {
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


    public List<TaskSubscribeReg.EditSubscribeDefine> getEditSubscribeDefines() {
        List<TaskSubscribeReg.EditSubscribeDefine> result = new ArrayList<TaskSubscribeReg.EditSubscribeDefine>();
        for (TaskSubscribe subscribe : getEditSubscribes()) {
            result.add(taskSubscribeReg.getEditDefineByName(subscribe.getRegName()));
        }
        return result;
    }

    public List<TaskSubscribeReg.CompleteSubscribeDefine> getCompleteSubscribeDefines() {
        List<TaskSubscribeReg.CompleteSubscribeDefine> result = new ArrayList<TaskSubscribeReg.CompleteSubscribeDefine>();
        for (TaskSubscribe subscribe : getCompleteSubscribes()) {
            result.add(taskSubscribeReg.getCompleteDefineByName(subscribe.getRegName()));
        }
        return result;
    }

    public List<TaskSubscribeReg.SubscribeDefine> getOperSubscribeDefines() {
        List<TaskSubscribeReg.SubscribeDefine> result = new ArrayList<TaskSubscribeReg.SubscribeDefine>();
        for (TaskSubscribe subscribe : getOperSubscribes()) {
            result.add(taskSubscribeReg.getOperDefineByName(subscribe.getRegName()));
        }
        return result;
    }

    public List<TaskSubscribeReg.SubscribeDefineGroup> getViewSubscribeDefineGroups() {
        List<TaskSubscribeReg.SubscribeDefineGroup> result = new ArrayList<TaskSubscribeReg.SubscribeDefineGroup>();

        for (SubscribeGroup group : getViewSubscribeGroups()) {
            TaskSubscribeReg.SubscribeDefineGroup defineGroup = new TaskSubscribeReg.SubscribeDefineGroup(group);

            for (ViewSubscribe subscribe : group.getViewSubscribeList()) {
                defineGroup.add(taskSubscribeReg.getViewDefineByName(subscribe.getRegName()));
            }
            if (!defineGroup.getDefineList().isEmpty())
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

    public int getSubscribeCount(String taskName) {
        int result = 0;
        for (TaskSubscribe subscribe : getInstance().getTaskSubscribes()) {
            if (subscribe.getTask().equals(taskName)) {
                result++;
            }
        }
        for (SubscribeGroup group : getInstance().getSubscribeGroups()) {
            if (group.getTask().equals(taskName)) {
                result += group.getViewSubscribes().size();
            }
        }
        return result;
    }

    public List<BusinessDataValid> getCreateDataValidComponents() {
        List<BusinessDataValid> result = new ArrayList<BusinessDataValid>();

        for (CreateComponent component : getInstance().getBusinessCreateDataValids()) {
            if (component.getType().equals(CreateComponent.CreateComponentType.DATA_VALID)) {
                result.add((BusinessDataValid) Component.getInstance(component.getComponent(), true));
            }
        }

        return result;
    }

    public List<BusinessDataFill> getCreateDataFillComponents() {
        List<BusinessDataFill> result = new ArrayList<BusinessDataFill>();

        for (CreateComponent component : getInstance().getBusinessCreateDataValids()) {
            if (component.getType().equals(CreateComponent.CreateComponentType.DATA_FILL)) {
                result.add((BusinessDataFill) Component.getInstance(component.getComponent(), true));
            }
        }

        return result;
    }

    public List<BusinessPickSelect> getCreateBizSelectComponents() {
        List<BusinessPickSelect> result = new ArrayList<BusinessPickSelect>();

        for (CreateComponent component : getInstance().getBusinessCreateDataValids()) {
            if (component.getType().equals(CreateComponent.CreateComponentType.BIZ_SELECT)) {
                result.add((BusinessPickSelect) Component.getInstance(component.getComponent(), true));
            }
        }

        return result;
    }

    public List<BusinessNeedFile> getNeedFileRootList(){
        List<BusinessNeedFile> rootNodes = new ArrayList<BusinessNeedFile>();

        for (BusinessNeedFile file : getInstance().getBusinessNeedFiles()) {
            if (file.getParent() == null) {
                rootNodes.add(file);
            }
        }
        Collections.sort(rootNodes, OrderBeanComparator.getInstance());
        return rootNodes;
    }


    public static class NeedFileTreeNode implements TreeNode, OrderModel {

        private boolean expanded = true;

        private BusinessNeedFile businessNeedFile;

        private List<NeedFileTreeNode> child;

        private NeedFileTreeNode parent;

        private NeedFileTreeNode(BusinessNeedFile businessNeedFile, NeedFileTreeNode parent) {
            this(businessNeedFile.getChildren());
            this.businessNeedFile = businessNeedFile;
            this.parent = parent;
        }



        public NeedFileTreeNode(Collection<BusinessNeedFile> needFiles) {
            this.child = new ArrayList<NeedFileTreeNode>(needFiles.size());
            for (BusinessNeedFile needFile: needFiles){
                this.child.add(new NeedFileTreeNode(needFile,this));
            }
            Collections.sort(child, OrderBeanComparator.getInstance());
        }

        public BusinessNeedFile getBusinessNeedFile() {
            return businessNeedFile;
        }

        public String getType(){

            return (businessNeedFile == null) ? "ROOT" : businessNeedFile.getType().name();
        }

        public boolean isExpanded() {
            return expanded;
        }

        public void setExpanded(boolean expanded) {
            this.expanded = expanded;
        }

        public List<NeedFileTreeNode> getChild() {
            return child;
        }

        @Override
        public TreeNode getChildAt(int childIndex) {
            return child.get(childIndex);
        }

        @Override
        public int getChildCount() {
            return child.size();
        }

        @Override
        public TreeNode getParent() {
            return parent;
        }

        @Override
        public int getIndex(TreeNode node) {
            return child.indexOf(node);
        }

        @Override
        public boolean getAllowsChildren() {
            return (businessNeedFile == null) || !businessNeedFile.getType().equals(BusinessNeedFile.NeedFileNodeFile.CHILDREN);
        }

        @Override
        public boolean isLeaf() {
            return (businessNeedFile != null) && businessNeedFile.getType().equals(BusinessNeedFile.NeedFileNodeFile.CHILDREN);
        }

        @Override
        public Enumeration children() {
            return Iterators.asEnumeration(child.iterator());
        }

        @Override
        public int getPriority() {
            return (businessNeedFile == null) ? 0 : businessNeedFile.getPriority();
        }

        @Override
        public void setPriority(int priority) {
        }
    }

}
