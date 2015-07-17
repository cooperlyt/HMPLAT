package com.dgsoft.common.system.action;

import com.dgsoft.common.system.model.BusinessNeedFile;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.*;
import org.jboss.seam.log.Logging;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;

/**
 * Created by cooper on 7/1/15.
 */
@Name("businessNeedFileConfig")
@Scope(ScopeType.CONVERSATION)
public class BusinessNeedFileConfig {

    @In(create = true)
    private BusinessDefineHome businessDefineHome;

    private BusinessNeedFile selectNeedFile;

    public List<BusinessNeedFile.NeedFileNodeFile> getNodeTypes() {
        return new ArrayList<BusinessNeedFile.NeedFileNodeFile>(
                EnumSet.of(BusinessNeedFile.NeedFileNodeFile.ALL,
                        BusinessNeedFile.NeedFileNodeFile.ANYONE));
    }

    public String getSelectId() {
        if (selectNeedFile == null) {
            return null;
        }
        return selectNeedFile.getId();
    }

    public void setSelectId(String selectId) {
        selectNeedFile = getNeedFileById(selectId);
    }


    private BusinessNeedFile getNeedFileById(String id) {
        if ((id == null) || id.trim().equals("")) {
            return null;
        }
        for (BusinessNeedFile needFile : businessDefineHome.getInstance().getBusinessNeedFiles()) {
            if (needFile.getId().equals(id)) {
                selectNeedFile = needFile;
                return needFile;
            }
        }
        return null;
    }

    public BusinessNeedFile getSelectNeedFile() {
        return selectNeedFile;
    }


    private List<BusinessDefineHome.NeedFileTreeNode> tree;

    public List<BusinessDefineHome.NeedFileTreeNode> getTaskFileSubscribeTree() {
        if (tree == null) {
            tree = new ArrayList<BusinessDefineHome.NeedFileTreeNode>(1);
            tree.add(new DefineNeedFileTreeNode(businessDefineHome.getNeedFileRootList()));
        }
        return tree;
    }


    public static class DefineNeedFileTreeNode extends BusinessDefineHome.NeedFileTreeNode<DefineNeedFileTreeNode> {

        public DefineNeedFileTreeNode(Collection<BusinessNeedFile> needFiles) {
            super(needFiles);
        }

        private DefineNeedFileTreeNode(BusinessNeedFile businessNeedFile, DefineNeedFileTreeNode parent) {
            super(businessNeedFile, parent);
        }

        @Override
        protected DefineNeedFileTreeNode createNewChild(BusinessNeedFile businessNeedFile) {
            return new DefineNeedFileTreeNode(businessNeedFile,this);
        }
    }

//    private void refreshTree(){
//        if (tree != null){
//            List<BusinessDefineHome.NeedFileTreeNode> newTree = new ArrayList<BusinessDefineHome.NeedFileTreeNode>(1);
//            newTree.add(new BusinessDefineHome.NeedFileTreeNode(businessDefineHome.getNeedFileRootList()));
//            refreshTree(newTree.get(0));
//            tree = newTree;
//        }
//    }
//
//    private void refreshTree(BusinessDefineHome.NeedFileTreeNode node){
//        BusinessDefineHome.NeedFileTreeNode old = findNode(tree.get(0),node);
//        if (node.equals(selectNeedFile)){
//            node.setExpanded(true);
//        }else if (old == null){
//            node.setExpanded(true);
//        }else{
//            node.setExpanded(old.isExpanded());
//        }
//        for(BusinessDefineHome.NeedFileTreeNode child: node.getChild()){
//            refreshTree(child);
//        }
//    }
//
//    private BusinessDefineHome.NeedFileTreeNode findNode(BusinessDefineHome.NeedFileTreeNode srcNode, BusinessDefineHome.NeedFileTreeNode targetNode){
//        if (srcNode.equals(targetNode)){
//            return targetNode;
//        }
//        for(BusinessDefineHome.NeedFileTreeNode node: srcNode.getChild()){
//            BusinessDefineHome.NeedFileTreeNode result = findNode(node,targetNode);
//            if (result != null){
//                return result;
//            }
//        }
//        return null;
//    }

    public void delete() {
        if (businessDefineHome.getInstance().getBusinessNeedFiles().remove(selectNeedFile)) {
            businessDefineHome.update();
        }
    }

    @Begin(flushMode = FlushModeType.MANUAL)
    public void addNode(){
        selectNeedFile = new BusinessNeedFile(businessDefineHome.getInstance(), BusinessNeedFile.NeedFileNodeFile.ALL,selectNeedFile,getNewPri());
    }

    @Begin(flushMode = FlushModeType.MANUAL)
    public void addFile(){
        selectNeedFile = new BusinessNeedFile(businessDefineHome.getInstance(), BusinessNeedFile.NeedFileNodeFile.CHILDREN,selectNeedFile,getNewPri());
    }

    @End
    public void save(){
        if (!isSelectManager()){
            businessDefineHome.getInstance().getBusinessNeedFiles().add(selectNeedFile);
            BusinessNeedFile parent = selectNeedFile.getParent();
            if (parent != null){
                parent.getChildren().add(selectNeedFile);
            }
            tree = null;
        }
        businessDefineHome.update();
    }

    public boolean isSelectManager() {
        return (selectNeedFile != null) && businessDefineHome.getEntityManager().contains(selectNeedFile);
    }

    public void up(){
        List<BusinessNeedFile> nodes;
        if (selectNeedFile.getParent() == null){
            nodes = businessDefineHome.getNeedFileRootList();
        }else {
            nodes = selectNeedFile.getParent().getChildrenList();
        }
        for(int i = 0; i < nodes.size(); i++){
            if (nodes.get(i).equals(selectNeedFile)){
                if ((i - 1) >= 0){
                    int curPri = selectNeedFile.getPriority();
                    selectNeedFile.setPriority(nodes.get(i-1).getPriority());
                    nodes.get(i-1).setPriority(curPri);
                }
                return;
            }
        }
        businessDefineHome.update();

    }

    public void down(){
        List<BusinessNeedFile> nodes;
        if (selectNeedFile.getParent() == null){
            nodes = businessDefineHome.getNeedFileRootList();
        }else {
            nodes = selectNeedFile.getParent().getChildrenList();
        }
        for(int i = 0; i < nodes.size(); i++){
            if (nodes.get(i).equals(selectNeedFile)){
                if ((i + 1) < nodes.size()){
                    int curPri = selectNeedFile.getPriority();
                    selectNeedFile.setPriority(nodes.get(i+1).getPriority());
                    nodes.get(i+1).setPriority(curPri);
                }
                return;
            }
        }
        businessDefineHome.update();
    }

    private int getNewPri(){
        int result = 1;
        Collection<BusinessNeedFile> children;
        if (selectNeedFile == null){
            children = businessDefineHome.getNeedFileRootList();
        }else{
            children= selectNeedFile.getChildren();
        }
        for(BusinessNeedFile needFile: children){
            if (needFile.getPriority() >= result){
                result = needFile.getPriority() + 1;
            }
        }
        return result;

    }


}
