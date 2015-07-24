package com.dgsoft.house.owner.business;

import com.dgsoft.common.system.action.BusinessDefineHome;
import com.dgsoft.common.system.model.BusinessNeedFile;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessFile;
import com.dgsoft.house.owner.model.UploadFile;
import com.google.common.collect.Iterators;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.core.Expressions;
import org.richfaces.component.UITree;
import org.richfaces.event.TreeSelectionChangeEvent;

import javax.swing.tree.TreeNode;
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

    private List<TreeNode> tree;

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
        if (tree.getRowData() instanceof BusinessFileTreeNode) {
            selectNode = (BusinessFileTreeNode) tree.getRowData();
        }else{
            selectNode = null;
            //TODO select OtherFile
        }
        tree.setRowKey(storedKey);
    }


    public List<TreeNode> getTree() {
        if (tree == null){
            initBusinessNeedFiles();
        }
        return tree;
    }

    public FileStatus getImportantStatus(){
        if(getTree().size() < 2){
            return FileStatus.OK;
        }
        return ((BusinessFileTreeNode)getTree().get(0)).getStatus(businessDefineHome.getTaskName());
    }

    public boolean isPass(){
        if(getTree().size() < 2){
            return true;
        }
       return ! FileStatus.NO_UPLOAD.equals(((BusinessFileTreeNode)getTree().get(0)).getStatus(businessDefineHome.getTaskName()));
    }

    private void initBusinessNeedFiles() {

        tree = new ArrayList<TreeNode>(2);

        List<BusinessNeedFile> rootNeedFile = businessDefineHome.getNeedFileRootList();
        if (!rootNeedFile.isEmpty()){
            BusinessFileTreeNode importantNode = new BusinessFileTreeNode(rootNeedFile);

            tree.add(importantNode);

            fillTree(importantNode);
        }


        tree.add(new OtherFileTreeNode(new ArrayList<BusinessFile>(0)));

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
         OK(1), OTHER_UPLOAD(2), NO_FILE(3),NO_UPLOAD(4),;

        private int order;

        FileStatus(int order) {
            this.order = order;
        }

        public int getOrder() {
            return order;
        }
    }


    public static class BusinessFileTreeNode extends BusinessDefineHome.NeedFileTreeNode<BusinessFileTreeNode> {


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


        public BusinessFileTreeNode(Collection<BusinessNeedFile> needFiles) {
            super(needFiles);
        }

        private BusinessFile businessFile;

        public BusinessFile getBusinessFile() {
            return businessFile;
        }

        public void setBusinessFile(BusinessFile businessFile) {
            this.businessFile = businessFile;
        }

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




                    FileStatus result;

                    if (getBusinessFile() == null) {
                        result = FileStatus.NO_UPLOAD;
                    }else if (getBusinessFile().isNoFile()){
                        result = FileStatus.NO_FILE;

                    }else if (!getBusinessFile().getUploadFiles().isEmpty()) {
                        result = FileStatus.OK;
                    }else{
                        result = FileStatus.NO_UPLOAD;
                    }

                    if (FileStatus.NO_UPLOAD.equals(result) && !getBusinessNeedFile().getTaskName().equals(taskName)){
                        return FileStatus.OTHER_UPLOAD;
                    }else{
                        return result;
                    }



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

            return (businessNeedFile == null) ?   "I_ROOT" : businessNeedFile.getType().name();
        }
    }

    public static class OtherBusinessFileTreeNode implements TreeNode{
        private BusinessFile businessFile;

        private TreeNode parent;

        public OtherBusinessFileTreeNode(BusinessFile businessFile, TreeNode parent) {
            this.businessFile = businessFile;
            this.parent = parent;
        }

        public BusinessFile getBusinessFile() {
            return businessFile;
        }

        @Override
        public TreeNode getChildAt(int childIndex) {
            return null;
        }

        @Override
        public int getChildCount() {
            return 0;
        }

        @Override
        public TreeNode getParent() {
            return parent;
        }

        @Override
        public int getIndex(TreeNode node) {
            return 0;
        }

        @Override
        public boolean getAllowsChildren() {
            return false;
        }

        @Override
        public boolean isLeaf() {
            return true;
        }

        @Override
        public Enumeration children() {
            return null;
        }

        public String getType() {
            return "OTHER_FILE";
        }

        public int getFileCount(){
            return businessFile.getUploadFiles().size();
        }
    }

    public static class OtherFileTreeNode implements TreeNode{

        private List<OtherBusinessFileTreeNode> businessFiles;

        public OtherFileTreeNode(List<BusinessFile> businessFiles) {

            this.businessFiles = new ArrayList<OtherBusinessFileTreeNode>(businessFiles.size());
            for (BusinessFile businessFile: businessFiles){
                this.businessFiles.add(new OtherBusinessFileTreeNode(businessFile,this));
            }
        }

        @Override
        public TreeNode getChildAt(int childIndex) {
            return businessFiles.get(childIndex);
        }

        @Override
        public int getChildCount() {
            return businessFiles.size();
        }

        @Override
        public TreeNode getParent() {
            return null;
        }

        @Override
        public int getIndex(TreeNode node) {
            return businessFiles.indexOf(node);
        }

        @Override
        public boolean getAllowsChildren() {
            return true;
        }

        @Override
        public boolean isLeaf() {
            return false;
        }

        @Override
        public Enumeration children() {
            return Iterators.asEnumeration(businessFiles.iterator());
        }

        public String getType() {
            return "ROOT";
        }

        public int getFileCount(){
            int result = 0;
            for(OtherBusinessFileTreeNode node: businessFiles){
                result += node.getFileCount();
            }
            return result;
        }
    }
}
