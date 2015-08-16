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
import org.jboss.seam.log.Logging;
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


    public String getDescription() {
        String val = getInstance().getDescription();
        if ((val == null) || val.trim().equals("")) {
            return "";
        } else {
            return Expressions.instance().createValueExpression(val, String.class).getValue();
        }

    }

    public void validSubscribes() {


        for(SubscribeGroup group: getEditSubscribeGroups()){
            for(ViewSubscribe subscribe: group.getViewSubscribeList()){
                TaskSubscribeReg.EditSubscribeDefine define =
                        taskSubscribeReg.getEditDefineByName(subscribe.getRegName());
                if (define.isHaveComponent()) {
                    define.getComponents().validSubscribe();
                }
            }
        }

        log.debug("call validSubscribes");
    }

    public boolean isSubscribesPass() {
        for(SubscribeGroup group: getEditSubscribeGroups()){
            for(ViewSubscribe subscribe: group.getViewSubscribeList()){
                TaskSubscribeReg.EditSubscribeDefine define =
                        taskSubscribeReg.getEditDefineByName(subscribe.getRegName());
                if (define.isHaveComponent()) {
                    if (!define.getComponents().isPass()){
                        return false;
                    }
                }
            }
        }
        return true;
    }




    public void validComplete() {
        for (TaskSubscribeReg.CompleteSubscribeDefine define : getCompleteSubscribeDefines()) {
            define.getComponents().valid();
        }
        log.debug("call validComplete");
    }

    public boolean isCompletePass() {
        for (TaskSubscribeReg.CompleteSubscribeDefine define : getCompleteSubscribeDefines()) {
            if (!define.getComponents().isPass()) {
                return false;
            }
        }
        return true;
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

    public boolean isHaveNeedFile() {
        return !getFileSubscribe(getTaskName()).isEmpty();
    }


    @Override
    public void refresh() {
        super.refresh();
        refreshSubscribe();
    }

    public void refreshSubscribe() {
        editSubscribeGroups = null;
        viewSubscribeGroups = null;
        operSubscribes = null;
        completeSubscribes = null;
    }

    //private List<TaskSubscribe> editSubscribes;

    private List<TaskSubscribe> operSubscribes;

    private List<TaskSubscribe> completeSubscribes;

    private List<SubscribeGroup> viewSubscribeGroups;

    private List<SubscribeGroup> editSubscribeGroups;

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

    public List<SubscribeGroup> getEditSubscribeGroups() {
        if (editSubscribeGroups == null){
            loadSubscribeGroup();
        }
        return editSubscribeGroups;
    }

    public List<SubscribeGroup> getViewSubscribeGroups() {
        if (viewSubscribeGroups == null) {
            loadSubscribeGroup();
        }

        return viewSubscribeGroups;
    }

    private void loadSubscribes() {
        operSubscribes = new ArrayList<TaskSubscribe>();
        completeSubscribes = new ArrayList<TaskSubscribe>();
        for (TaskSubscribe subscribe : getInstance().getTaskSubscribes()) {
            if (subscribe.getTaskName().equals(taskName)) {
                if (Subscribe.SubscribeType.TASK_COMPLETE.equals(subscribe.getType())) {
                    completeSubscribes.add(subscribe);
                } else if (Subscribe.SubscribeType.TASK_OPERATOR.equals(subscribe.getType())) {
                    operSubscribes.add(subscribe);
                }

            }
        }
        Collections.sort(completeSubscribes, new Comparator<TaskSubscribe>() {
            @Override
            public int compare(TaskSubscribe o1, TaskSubscribe o2) {
                return new Integer(o1.getPriority()).compareTo(o2.getPriority());
            }
        });
        Collections.sort(operSubscribes, new Comparator<TaskSubscribe>() {
            @Override
            public int compare(TaskSubscribe o1, TaskSubscribe o2) {
                return new Integer(o1.getPriority()).compareTo(o2.getPriority());
            }
        });

    }

    private void loadSubscribeGroup(){
        viewSubscribeGroups = new ArrayList<SubscribeGroup>();
        editSubscribeGroups = new ArrayList<SubscribeGroup>();
        for (SubscribeGroup group : getInstance().getSubscribeGroups()) {
            if (group.getTaskName().equals(getTaskName())) {
                if (Subscribe.SubscribeType.TASK_INFO.equals(group.getType())){
                    viewSubscribeGroups.add(group);
                }else if (Subscribe.SubscribeType.TASK_OPERATOR.equals(group.getType())){
                    editSubscribeGroups.add(group);
                }


            }
        }
        Collections.sort(viewSubscribeGroups, new Comparator<SubscribeGroup>() {
            @Override
            public int compare(SubscribeGroup o1, SubscribeGroup o2) {
                return new Integer(o1.getPriority()).compareTo(o2.getPriority());
            }
        });
        Collections.sort(editSubscribeGroups, new Comparator<SubscribeGroup>() {
            @Override
            public int compare(SubscribeGroup o1, SubscribeGroup o2) {
                return new Integer(o1.getPriority()).compareTo(o2.getPriority());
            }
        });
    }


    private SubscribeGroup curEditGroup;

    public SubscribeGroup getCurEditGroup() {
        return curEditGroup;
    }

    public void setCurEditGroup(SubscribeGroup curEditGroup) {
        this.curEditGroup = curEditGroup;
        if (curEditGroup != null){
            initEditSubscribes();
        }
    }

    public String getCurEditGroupId(){
        if (curEditGroup == null){
            return null;
        }
        return curEditGroup.getId();
    }

    public void setCurEditGroupId(String id){
        if (id == null || id.trim().equals("")){
           setCurEditGroup(null);
        }
        for (SubscribeGroup group: getEditSubscribeGroups()){
            if (id.equals(group.getId())){
                setCurEditGroup(group);
                return;
            }
        }
        throw new IllegalArgumentException("edit gorup id not found");
    }

    public boolean saveEditSubscribes() {

        for (TaskSubscribeReg.EditSubscribeDefine define : getEditSubscribeDefines()) {
            if (define.isHaveComponent()) {
                if (!define.getComponents().saveSubscribe()) {
                    return false;
                }
            }
        }
        return true;
    }

    public void firstEditGroup(){
        setCurEditGroup(getEditSubscribeGroups().get(0));
    }

    public boolean nextEditGroup(){
        // exception
        if (curEditGroup == null){
            setCurEditGroup(getEditSubscribeGroups().get(0));
        }else{
            if (!saveEditSubscribes()) {
                return false;
            }
            setCurEditGroup(getEditSubscribeGroups().get(getEditSubscribeGroups().indexOf(curEditGroup) + 1));

        }

        return true;
    }

    private void initEditSubscribes() {
        for (TaskSubscribeReg.EditSubscribeDefine define : getEditSubscribeDefines()) {
            if (define.isHaveComponent()) {
                define.getComponents().initSubscribe();

            }
        }
    }

    public boolean isHaveNextEditGroup() {
        if (getEditSubscribeGroups().isEmpty()) {
            return false;
        }
        if (curEditGroup == null) {
            return true;
        }
        return (getEditSubscribeGroups().indexOf(curEditGroup) != (getEditSubscribeGroups().size() - 1));
    }

    public void previousEditGroup(){
        //exception
        curEditGroup = getEditSubscribeGroups().get(getEditSubscribeGroups().indexOf(curEditGroup) - 1);
        initEditSubscribes();
    }

    public boolean isHavePreviousEditGroup(){
        if (getEditSubscribeGroups().isEmpty()) {
            return false;
        }
        if (curEditGroup == null) {
            return false;
        }
        return getEditSubscribeGroups().indexOf(curEditGroup) > 0;
    }

    public List<SubscribeGroup> getPastEditGroups(){
        List<SubscribeGroup> result = new ArrayList<SubscribeGroup>();
        if (curEditGroup != null){
            for (SubscribeGroup group: getEditSubscribeGroups()){
                if (curEditGroup.equals(group)){
                    return result;
                }else{
                    result.add(group);
                }
            }
        }
        return result;
    }

    public List<SubscribeGroup> getAfterEditGroups(){

        if (curEditGroup != null){
            List<SubscribeGroup> result = new ArrayList<SubscribeGroup>();
            boolean find = false;
            for (SubscribeGroup group: getEditSubscribeGroups()){
                if (curEditGroup.equals(group)){
                    find = true;
                }else if (find){
                    result.add(group);
                }
            }
            return result;
        }else{
            return getEditSubscribeGroups();
        }

    }

    public boolean isOnePageEdit(){
        return getEditSubscribeGroups().size() == 1;
    }


    public boolean isHaveEditSubscribe(){
        return !getEditSubscribeGroups().isEmpty();
    }

    public List<TaskSubscribeReg.EditSubscribeDefine> getEditSubscribeDefines() {
        if(curEditGroup == null){
            return new ArrayList<TaskSubscribeReg.EditSubscribeDefine>(0);
        }
        List<TaskSubscribeReg.EditSubscribeDefine> result = new ArrayList<TaskSubscribeReg.EditSubscribeDefine>();

        for(ViewSubscribe subscribe: curEditGroup.getViewSubscribeList()){
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
        } else {
            getInstance().setEnable(true);
            return true;
        }

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

        if (Subscribe.CREATE_TASK_NAME.equals(taskName)){
            result += getInstance().getBusinessCreateDataValids().size();
        }
        return result;
    }

    public List<BusinessDataValid> getCreateDataValidComponents() {
        List<BusinessDataValid> result = new ArrayList<BusinessDataValid>();

        for (CreateComponent component : getInstance().getBusinessCreateDataValids()) {
            if (component.getType().equals(CreateComponent.CreateComponentType.DATA_VALID)) {
                BusinessDataValid valid = (BusinessDataValid) Component.getInstance(component.getComponent(), true);
                if (valid == null) {
                    Logging.getLog(getClass()).error("confing error validatio not found:" + component.getComponent());
                    throw new IllegalArgumentException("confing error validatio not found:" + component.getComponent());
                }
                result.add(valid);
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

    public void runAction(String name){

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

    public List<BusinessNeedFile> getNeedFileRootList() {
        List<BusinessNeedFile> rootNodes = new ArrayList<BusinessNeedFile>();

        for (BusinessNeedFile file : getInstance().getBusinessNeedFiles()) {
            if (file.getParent() == null) {
                rootNodes.add(file);
            }
        }
        Collections.sort(rootNodes, OrderBeanComparator.getInstance());
        return rootNodes;
    }

    public boolean isHaveReport(){
        return !getReprotList().isEmpty();
    }

    public List<Report> getReprotList(){
        List<BusinessReport> result = new ArrayList<BusinessReport>();
        for (BusinessReport br: getInstance().getBusinessReports()){
            if (br.getTaskName().equals(taskName)){
                result.add(br);
            }
        }
        Collections.sort(result,OrderBeanComparator.getInstance());

        List<Report> reports = new ArrayList<Report>(result.size());
        for(BusinessReport br: result){
            reports.add(br.getReport());
        }
        return reports;
    }

    public static abstract class NeedFileTreeNode<T extends NeedFileTreeNode> implements TreeNode, OrderModel {

        private boolean expanded = true;

        protected BusinessNeedFile businessNeedFile;

        private List<T> child;

        private NeedFileTreeNode parent;

        protected NeedFileTreeNode() {
        }

        protected NeedFileTreeNode(BusinessNeedFile businessNeedFile, T parent) {
            this(businessNeedFile.getChildren());
            this.businessNeedFile = businessNeedFile;
            this.parent = parent;
        }

        protected abstract T createNewChild(BusinessNeedFile businessNeedFile);


        public NeedFileTreeNode(Collection<BusinessNeedFile> needFiles) {
            this.child = new ArrayList<T>(needFiles.size());
            for (BusinessNeedFile needFile : needFiles) {
                T newChild = createNewChild(needFile);
                if (newChild != null)
                    this.child.add(newChild);
            }
            Collections.sort(child, OrderBeanComparator.getInstance());
        }

        public BusinessNeedFile getBusinessNeedFile() {
            return businessNeedFile;
        }

        public String getType() {

            return (businessNeedFile == null) ? "ROOT" : businessNeedFile.getType().name();
        }

        public boolean isExpanded() {
            return expanded;
        }

        public void setExpanded(boolean expanded) {
            this.expanded = expanded;
        }

        public List<T> getChild() {
            return child;
        }

        protected void setChild(List<T> child) {
            this.child = child;
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
