package com.dgsoft.house.owner.business;

import com.dgsoft.common.system.action.BusinessDefineHome;
import com.dgsoft.common.system.model.BusinessNeedFile;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessFile;
import com.dgsoft.house.owner.model.UploadFile;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.core.Expressions;
import org.richfaces.component.UITree;
import org.richfaces.event.TreeSelectionChangeEvent;

import java.util.*;

/**
 * Created by cooper on 7/17/15.
 */
@Name("ownerBusinessFile")
@Scope(ScopeType.CONVERSATION)
public class OwnerBusinessFile {

    @In
    private OwnerBusinessHome ownerBusinessHome;

    @In
    private BusinessDefineHome businessDefineHome;

    private List<BusinessFileTreeNode> tree;

    private BusinessFileTreeNode selectNode;

    public BusinessFileTreeNode getSelectNode() {
        return selectNode;
    }

    public void setSelectNode(BusinessFileTreeNode selectNode) {
        this.selectNode = selectNode;
    }

    private List<BusinessFile> getAllImportantFile() {
        List<BusinessFile> result = new ArrayList<BusinessFile>();
        for (BusinessFile file : ownerBusinessHome.getInstance().getUploadFileses()) {
            if (file.isImportant()) {
                result.add(file);
            }
        }
        return result;
    }

    public List<UploadFile> getAllImportanFile() {
        List<UploadFile> result = new ArrayList<UploadFile>();
        for (BusinessFile file : getAllImportantFile()) {
            result.addAll(file.getUploadFiles());
            if (file.isNoFile()) {
                result.add(new UploadFile(file));
            }
        }
        return result;
    }

    public void setNoFile(boolean value){
        if (selectNode != null){
            if (selectNode.getBusinessFile() == null){
                if (value) {
                    BusinessFile newBizFile = new BusinessFile(ownerBusinessHome.getInstance(), selectNode.getBusinessNeedFile().getName(), selectNode.getBusinessNeedFile().getId(), true, true);
                    selectNode.setBusinessFile(newBizFile);
                    ownerBusinessHome.getInstance().getUploadFileses().add(newBizFile);
                }
            }else{
                selectNode.getBusinessFile().setNoFile(value);
            }
        }
    }

    public boolean isNoFile(){
        if (selectNode == null){
            return false;
        }
        if (selectNode.getBusinessFile() == null){
            return false;
        }
        return selectNode.getBusinessFile().isNoFile();
    }


    public void selectionChanged(TreeSelectionChangeEvent selectionChangeEvent) {
        // considering only single selection
        List<Object> selection = new ArrayList<Object>(selectionChangeEvent.getNewSelection());
        if (selection.size() == 0){
            selectNode = null;
            return;
        }
        Object currentSelectionKey = selection.get(0);
        UITree tree = (UITree) selectionChangeEvent.getSource();

        Object storedKey = tree.getRowKey();
        tree.setRowKey(currentSelectionKey);
        selectNode = (BusinessFileTreeNode) tree.getRowData();
        tree.setRowKey(storedKey);
    }


    public List<BusinessFileTreeNode> getTree() {
        if (tree == null){
            initBusinessNeedFiles();
        }
        return tree;
    }

    public FileStatus getImportantStatus(){
        if(getTree().size() < 2){
            return FileStatus.OK;
        }
        return getTree().get(0).getStatus(businessDefineHome.getTaskName());
    }

    public boolean isPass(){
        if(getTree().size() < 2){
            return true;
        }
       return ! FileStatus.NO_UPLOAD.equals(getTree().get(0).getStatus(businessDefineHome.getTaskName()));
    }

    private void initBusinessNeedFiles() {

        tree = new ArrayList<BusinessFileTreeNode>(2);

        List<BusinessNeedFile> rootNeedFile = businessDefineHome.getNeedFileRootList();
        if (!rootNeedFile.isEmpty()){
            BusinessFileTreeNode importantNode = new BusinessFileTreeNode(rootNeedFile, true);

            tree.add(importantNode);

            fillTree(importantNode);
        }



       // tree.add(new BusinessFileTreeNode());
    }



    private void fillTree(BusinessFileTreeNode node) {
        for (BusinessFileTreeNode child : node.getChild()) {
            if (BusinessNeedFile.NeedFileNodeFile.CHILDREN.equals(child.getBusinessNeedFile().getType())) {
                for (BusinessFile file : getAllImportantFile()) {
                    if ((file.getImportantCode() != null) && (file.getImportantCode().trim().equals(child.getBusinessNeedFile().getId()))) {
                        child.setBusinessFile(file);
                        break;
                    }
                }
            }
            fillTree(child);
        }

    }

    public enum FileStatus{
        OTHER_UPLOAD(1), OK(2),  NO_FILE(3),NO_UPLOAD(4),;

        private int order;

        FileStatus(int order) {
            this.order = order;
        }

        public int getOrder() {
            return order;
        }
    }


    public class BusinessFileTreeNode extends BusinessDefineHome.NeedFileTreeNode<BusinessFileTreeNode> {

        private boolean important;

        public BusinessFileTreeNode() {
            important = false;

        }

        private BusinessFileTreeNode(BusinessNeedFile businessNeedFile, BusinessFileTreeNode parent) {
            super(businessNeedFile, parent);
        }

        @Override
        protected BusinessFileTreeNode createNewChild(BusinessNeedFile businessNeedFile) {

            if (businessNeedFile.getCondition() == null ||
                    businessNeedFile.getCondition().trim().equals("") ||
                    Expressions.instance().createValueExpression(businessNeedFile.getCondition(),Boolean.class).getValue()) {
                return new BusinessFileTreeNode(businessNeedFile, this);
            }else{
                return null;
            }
        }


        public BusinessFileTreeNode(Collection<BusinessNeedFile> needFiles, boolean important) {
            super(needFiles);
            this.important = important;
        }

        public boolean isImportant() {
            return important;
        }

        private BusinessFile businessFile;

        public BusinessFile getBusinessFile() {
            return businessFile;
        }

        public void setBusinessFile(BusinessFile businessFile) {
            this.businessFile = businessFile;
        }

//        public int getItemCount(){
//
//
//            if ((getBusinessNeedFile() != null) && getBusinessNeedFile().getType().equals(BusinessNeedFile.NeedFileNodeFile.CHILDREN)){
//                return 1;
//            }else{
//                int result = 0;
//                    for (BusinessFileTreeNode child: getChild()){
//
//                        switch (child.getBusinessNeedFile().getType()){
//
//                            case ALL:
//                                result += child.getItemCount();
//                                break;
//                            case ANYONE:
//                                result ++;
//                                break;
//                            case CHILDREN:
//                                result ++;
//                                break;
//                        }
//
//
//                    }
//                return result;
//            }
//
//        }

        public int getFileCount(){
            if ((getBusinessNeedFile() != null) && getBusinessNeedFile().getType().equals(BusinessNeedFile.NeedFileNodeFile.CHILDREN)){
                return (getBusinessFile() == null) ? 0 : getBusinessFile().getUploadFiles().size();
            }else{
                int result = 0;
                for (BusinessFileTreeNode child: getChild()){
                    result += child.getFileCount();
                }
                return result;
            }
        }

        public boolean isEmptyFile(){
            return getFileCount() == 0;
        }

        public FileStatus getStatus(String taskName){
            if ((getBusinessNeedFile() != null) && getBusinessNeedFile().getType().equals(BusinessNeedFile.NeedFileNodeFile.CHILDREN)){


                if (getBusinessNeedFile().getTaskName().equals(taskName)){

                    if (getBusinessFile() == null) {
                        return FileStatus.NO_UPLOAD;
                    }else if (getBusinessFile().isNoFile()){
                        return FileStatus.NO_FILE;

                    }else if (!getBusinessFile().getUploadFiles().isEmpty()) {
                        return FileStatus.OK;
                    }else{
                        return FileStatus.NO_UPLOAD;
                    }

                }else
                    return FileStatus.OTHER_UPLOAD;

            }else if ((getBusinessNeedFile() == null) || getBusinessNeedFile().getType().equals(BusinessNeedFile.NeedFileNodeFile.ALL)){
                FileStatus result = FileStatus.OK;
                for (BusinessFileTreeNode child: getChild()){
                    if (result.getOrder() < child.getStatus(taskName).getOrder()){
                        result = child.getStatus(taskName);
                    }
                }
                return result;
            }else {
                FileStatus result = FileStatus.NO_UPLOAD;
                for (BusinessFileTreeNode child: getChild()){
                    if (result.getOrder() > child.getStatus(taskName).getOrder()){
                        return child.getStatus(taskName);
                    }
                }
                return result;
            }

        }


        @Override
        public String getType() {

            return (businessNeedFile == null) ? (important ? "I_ROOT" : "ROOT") : businessNeedFile.getType().name();
        }
    }


}
