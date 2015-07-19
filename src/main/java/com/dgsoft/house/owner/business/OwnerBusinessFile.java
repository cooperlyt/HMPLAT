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

import java.util.*;

/**
 * Created by cooper on 7/17/15.
 */
@Name("ownerBusinessFile")
@Scope(ScopeType.SESSION)
public class OwnerBusinessFile {

    @In
    private OwnerBusinessHome ownerBusinessHome;

    @In
    private BusinessDefineHome businessDefineHome;

    private List<BusinessFileTreeNode> tree;

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


    public List<BusinessFileTreeNode> getTree() {
        if (tree == null){
            initBusinessNeedFiles();
        }
        return tree;
    }

    private void initBusinessNeedFiles() {

        tree = new ArrayList<BusinessFileTreeNode>(2);

        BusinessFileTreeNode importantNode = new BusinessFileTreeNode(businessDefineHome.getNeedFileRootList(), true);

        tree.add(importantNode);

        fillTree(importantNode);

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
        OK(1),NO_FILE(2),NO_UPLOAD(3);

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

        private BusinessFile businessFile;

        public BusinessFile getBusinessFile() {
            return businessFile;
        }

        public void setBusinessFile(BusinessFile businessFile) {
            this.businessFile = businessFile;
        }

        public int getItemCount(){


            if ((getBusinessNeedFile() != null) && getBusinessNeedFile().getType().equals(BusinessNeedFile.NeedFileNodeFile.CHILDREN)){
                return 1;
            }else{
                int result = 0;
                    for (BusinessFileTreeNode child: getChild()){

                        switch (child.getBusinessNeedFile().getType()){

                            case ALL:
                                result += child.getItemCount();
                                break;
                            case ANYONE:
                                result ++;
                                break;
                            case CHILDREN:
                                result ++;
                                break;
                        }


                    }
                return result;
            }

        }

        public int getFileCount(){
            if ((getBusinessNeedFile() != null) && getBusinessNeedFile().getType().equals(BusinessNeedFile.NeedFileNodeFile.CHILDREN)){
                return getBusinessFile().getUploadFiles().size();
            }else{
                int result = 0;
                for (BusinessFileTreeNode child: getChild()){
                    result += child.getFileCount();
                }
                return result;
            }
        }

        public FileStatus getStatus(){
            if ((getBusinessNeedFile() != null) && getBusinessNeedFile().getType().equals(BusinessNeedFile.NeedFileNodeFile.CHILDREN)){
                if (getBusinessFile() == null){
                    return FileStatus.NO_UPLOAD;
                }else if (getBusinessFile().isNoFile()){
                    return FileStatus.NO_FILE;
                }else if (!getBusinessFile().getUploadFiles().isEmpty()){
                    return FileStatus.OK;
                }else
                    return FileStatus.NO_UPLOAD;

            }else if ((getBusinessNeedFile() == null) || getBusinessNeedFile().getType().equals(BusinessNeedFile.NeedFileNodeFile.ALL)){
                FileStatus result = FileStatus.OK;
                for (BusinessFileTreeNode child: getChild()){
                    if (result.getOrder() < child.getStatus().getOrder()){
                        result = child.getStatus();
                    }
                }
                return result;
            }else {
                FileStatus result = FileStatus.NO_UPLOAD;
                for (BusinessFileTreeNode child: getChild()){
                    if (result.getOrder() > child.getStatus().getOrder()){
                        return child.getStatus();
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
