package com.dgsoft.house.owner.business;

import com.dgsoft.common.FileExtendUploadPublish;
import com.dgsoft.common.helper.JsonDataProvider;
import com.dgsoft.common.system.*;
import com.dgsoft.common.system.action.BusinessDefineHome;
import com.dgsoft.common.system.model.BusinessNeedFile;
import com.dgsoft.house.owner.AttachFileNameCache;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessFile;
import com.dgsoft.house.owner.model.UploadFile;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.*;
import org.jboss.seam.core.Expressions;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.log.Logging;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.richfaces.application.push.TopicKey;
import org.richfaces.application.push.TopicsContext;
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
    protected OwnerBusinessHome ownerBusinessHome;

    @In
    private BusinessDefineHome businessDefineHome;

    @In
    private FacesMessages facesMessages;

    @In
    private AttachFileNameCache attachFileNameCache;

    private List<ParentNode> tree;

    private String selectNodeId;

    public String getSelectNodeId() {
        return selectNodeId;
    }

    public void setSelectNodeId(String selectNodeId) {
        this.selectNodeId = selectNodeId;
    }

    public BusinessFileNode getSelectNode(){
        return findNode(selectNodeId);
    }

    private BusinessFileNode findNode(List<? extends BusinessFileNode> src, String id){

        if (id == null || id.trim().equals("")){
            return null;
        }

        for (BusinessFileNode node: src){
            if (id.equals(node.getId())){
                return node;
            }
            if (node instanceof ParentNode){
                BusinessFileNode result = findNode (((ParentNode) node).getChildFileNode(), id);
                if (result != null){
                    return result;
                }
            }
        }

        return null;

    }

    private BusinessFileNode findNode(String id){
        return findNode(tree,id);
    }


    public String save() {
        //ownerBusinessHome.getInstance().getUploadFileses().clear();
        //saveImportNodeFile(getTree());
        return "saved";
    }


    public void changeListener(){
    }


    @In
    private AuthenticationInfo authInfo;


    private List<BusinessFile> getAllImportantFile() {
        List<BusinessFile> result = new ArrayList<BusinessFile>();
        for (BusinessFile file : ownerBusinessHome.getInstance().getUploadFileses()) {
            if (file.isImportant()) {
                result.add(file);
            }
        }
        return result;
    }


    public String getTree() {
        if (tree == null) {
            initBusinessNeedFiles();
        }

        JSONArray result = new JSONArray();
        for (BusinessFileNode rootNode: tree){
            try {
                result.put(rootNode.getJsonData());
            } catch (JSONException e) {
                throw new IllegalArgumentException("tree data exception");
            }
        }
        return result.toString();
    }
//
//    public FileStatus getImportantStatus() {
//        if (getTree().size() < 2) {
//            return FileStatus.OK;
//        }
//        return ((BusinessFileTreeNode) getTree().get(0)).getStatus(businessDefineHome.getTaskName());
//    }
//
    public boolean isPass() {
        if (tree == null) {
            initBusinessNeedFiles();
        }
        for(ParentNode node: tree){
            if (FileStatus.NO_UPLOAD.equals(node.getStatus())){
                return false;
            }
        }
        return true;
    }

    private void initBusinessNeedFiles() {

        tree = new ArrayList<ParentNode>(2);


        List<BusinessNeedFile> rootNeedFile = businessDefineHome.getNeedFileRootList();


        if (!rootNeedFile.isEmpty()) {
            ParentNode fileNode = new AllNode(null,"要件","必要要件");
            fillImportTree(fileNode,rootNeedFile);
            tree.add(fileNode);
        }

        ParentNode otherNode = new AllNode(null,"附件","自定义附件");


        for (BusinessFile file : ownerBusinessHome.getInstance().getUploadFileses()) {
            if (!file.isImportant()) {
                otherNode.addChild(new OtherChildNode(otherNode,file,""));
            }
        }

        tree.add(otherNode);


    }

    private void fillImportTree(ParentNode node,List<BusinessNeedFile> defineNodes){
        for(BusinessNeedFile defineNode: defineNodes){

            if (defineNode.getCondition() == null || defineNode.getCondition().trim().equals("") ||
                    Expressions.instance().createValueExpression(defineNode.getCondition(), Boolean.class).getValue()) {


                if (BusinessNeedFile.NeedFileNodeFile.CHILDREN.equals(defineNode.getType())) {

                    BusinessFile linkFile = null;
                    for (BusinessFile file : ownerBusinessHome.getInstance().getUploadFileses()) {
                        if (file.isImportant() && file.getImportantCode().equals(defineNode.getId())) {
                            linkFile = file;
                            break;
                        }
                    }
                    if (linkFile == null) {
                        linkFile = new BusinessFile(ownerBusinessHome.getInstance(),defineNode.getName(), defineNode.getId(), defineNode.getPriority());
                        ownerBusinessHome.getInstance().getUploadFileses().add(linkFile);
                    }

                    node.addChild(new FileChildNode(node,linkFile, defineNode.getDescription(), defineNode.getTaskNameList().contains(businessDefineHome.getTaskName())));

                } else {

                    ParentNode fileNode = BusinessNeedFile.NeedFileNodeFile.ALL.equals(defineNode.getType()) ? new AllNode(node,defineNode.getName(),defineNode.getDescription()) : new AnyNode(node,defineNode.getName(),defineNode.getDescription());

                    node.addChild(fileNode);

                    fillImportTree(fileNode, defineNode.getChildrenList());
                }
            }

        }
    }

    private String fileUploadData;

    public String getFileUploadData() {
        return fileUploadData;
    }

    public void setFileUploadData(String fileUploadData) {
        this.fileUploadData = fileUploadData;
    }

    public void fileUploaded(){
        try {
            JSONObject jsonObject = new JSONObject(fileUploadData);


                String key = jsonObject.getString("key");

                BusinessFileNode node = findNode(key);

                if ((node != null) && (node instanceof ChildNode)){
                    JSONArray fileIdArray = jsonObject.getJSONArray("files");
                    if (fileIdArray.length() > 0) {
                        selectNodeId = node.getId();
                        for (int i = 0; i < fileIdArray.length(); i++) {
                            JSONObject fileInfo = fileIdArray.getJSONObject(i);
                            BusinessFile businessFile = ((ChildNode) node).getBusinessFile();
                            businessFile.getUploadFiles().add(new UploadFile(fileInfo.getString("fid"), authInfo.getLoginEmployee().getPersonName(), authInfo.getLoginEmployee().getId(), fileInfo.getString("md5"), fileInfo.getString("name"), businessFile, fileInfo.getLong("size")));
                        }
                    }


                }


        } catch (JSONException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Destroy
    public void onDestroy(){
        destroyPush(tree);
    }

    private void destroyPush(List<? extends BusinessFileNode> src){

        for (BusinessFileNode node: src){
            if (node instanceof ChildNode){
                TopicKey topicKey = new TopicKey(node.getId());
                TopicsContext topicsContext = TopicsContext.lookup();
                topicsContext.removeTopic(topicKey);
            }else if (node instanceof ParentNode){
                destroyPush (((ParentNode) node).getChildFileNode());
            }
        }

    }


    public enum FileStatus {
        OK(1,"#5cb85c"), OTHER_UPLOAD(2,"#aaaaaa"), NO_FILE(3,"#f0ad4e"), NO_UPLOAD(4,"#d9534f");

        private int order;

        private String color;

        FileStatus(int order, String color) {
            this.order = order;
            this.color = color;
        }

        public int getOrder() {
            return order;
        }

        public String getColor() {
            return color;
        }
    }
    private String otherFileName;

    public String getOtherFileName() {
        return otherFileName;
    }

    public void setOtherFileName(String otherFileName) {
        this.otherFileName = otherFileName;
    }


    public void addOtherFile(){

        ParentNode rootNode = tree.get(tree.size() - 1);

        BusinessFile businessFile = new BusinessFile(ownerBusinessHome.getInstance(),otherFileName,1000 + rootNode.getChildFileNode().size() + 1);
        ownerBusinessHome.getInstance().getUploadFileses().add(businessFile);
        rootNode.addChild(new OtherChildNode(rootNode, businessFile,""));
        attachFileNameCache.putName(otherFileName);
    }

    public void deleteSelectNode(){
        BusinessFileNode selectNode = getSelectNode();
        if ( selectNode != null && selectNode instanceof OtherChildNode && ((OtherChildNode) selectNode).getBusinessFile().getUploadFiles().isEmpty()){

            ((OtherChildNode) selectNode).getBusinessFile().setOwnerBusiness(null);
            ownerBusinessHome.getInstance().getUploadFileses().remove(((OtherChildNode) selectNode).getBusinessFile());

            ParentNode rootNode = tree.get(tree.size() - 1);

            rootNode.getChildFileNode().remove(selectNode);

            selectNodeId = null;

        }
    }


    public String fileId;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }


    private boolean canDeleteFile;

    public boolean isCanDeleteFile() {
        return canDeleteFile;
    }

    public void setCanDeleteFile(boolean canDeleteFile) {
        this.canDeleteFile = canDeleteFile;
    }

    public void tryDeleteFile(){

        for (ParentNode node: tree){
            if (node.canDeleteImg(authInfo.getLoginEmployee().getId(),fileId)){
                canDeleteFile = true;
                return;
            }
        }
        canDeleteFile = false;

    }


    public void deleteFile(){
        if (fileId != null){
            deleteFile(tree,fileId);
        }

    }

    private void deleteFile(List<? extends BusinessFileNode> nodes, String fileId){

        for(BusinessFileNode node: nodes){
            if (node instanceof ParentNode){
                deleteFile(((ParentNode) node).getChildFileNode(),fileId);
            }else if (node instanceof ChildNode){
                for(UploadFile uploadFile: ((ChildNode) node).getBusinessFile().getUploadFileList()){
                    if (fileId.equals(uploadFile.getId())){
                        ((ChildNode) node).getBusinessFile().getUploadFiles().remove(uploadFile);
                    }
                }
            }
        }

    }

    private void deleteServiceFile(String fileId){

    }


    public static abstract class BusinessFileNode{

        public abstract JSONObject getJsonData() throws JSONException;

        public abstract FileStatus getStatus();

        public abstract String getId();

        public abstract String getName();

        public abstract List<UploadFile> getImages();

        public abstract BusinessNeedFile.NeedFileNodeFile getNodeType();

        public abstract boolean isCanPutFile();

        public abstract boolean isCanEditTitle();

        public abstract boolean canDeleteImg(String empId,String id);

        private String description;

        public String getDescription() {
            return description;
        }

        private ParentNode parent;

        public BusinessFileNode(ParentNode parentNode,String description) {
            this.parent = parentNode;
            this.description = description;
        }

        protected String getColor(){


            if ((parent != null) && (parent instanceof AnyNode) &&
                    (FileStatus.NO_UPLOAD.equals(getStatus())) &&
                    (parent.getStatus().getOrder() < FileStatus.NO_UPLOAD.getOrder()) ){
                return "#aaaaaa";
            }

            return getStatus().getColor();
        }

    }

    public static abstract class ParentNode extends BusinessFileNode{

        public ParentNode(ParentNode parentNode,String name, String description) {
            super(parentNode,description);
            id = UUID.randomUUID().toString().replace("-", "");
            this.name = name;
        }

        private String id;

        @Override
        public String getId() {
            return id;
        }

        protected String name;


        protected List<BusinessFileNode> childFileNode = new ArrayList<BusinessFileNode>();

        public List<BusinessFileNode> getChildFileNode() {
            return childFileNode;
        }

        public void addChild(BusinessFileNode child){
            childFileNode.add(child);
        }

        @Override
        public boolean isCanPutFile(){
            return false;
        }

        @Override
        public boolean isCanEditTitle(){
            return false;
        }

        @Override
        public boolean canDeleteImg(String empId, String id){
            for(BusinessFileNode node: childFileNode){
                if (node.canDeleteImg(empId,id))
                    return true;
            }
            return false;
        }

        public List<UploadFile> getImages(){
            List<UploadFile> result = new ArrayList<UploadFile>();
            for(BusinessFileNode fileNode: childFileNode){
                result.addAll(fileNode.getImages());
            }
            return result;
        }

        protected abstract String getIcon();

        @Override
        public String getName(){
            return name;
        }

        @Override
        public JSONObject getJsonData() throws JSONException {
            JSONObject result = new JSONObject();
            result.put("text",name );
            //putJsonProperty(result);
            result.put("id",id);
            result.put("icon",getIcon());

            result.put("color",getColor());

            JSONObject stateObj = new JSONObject();

            stateObj.put("expanded","true");

            result.put("state",stateObj);

            JSONArray childData = new JSONArray();
            for(BusinessFileNode node: childFileNode){
                childData.put(node.getJsonData());
            }
            result.put("nodes",childData);
            return result;
        }
    }

    public static class AllNode extends ParentNode{

        public AllNode(ParentNode parentNode, String name,String description) {
            super(parentNode, name,description);
        }

        @Override
        public FileStatus getStatus() {
            FileStatus result = FileStatus.OK;
            for (BusinessFileNode fileNode: childFileNode){
                if (fileNode.getStatus().getOrder() > result.getOrder()){
                    result = fileNode.getStatus();
                }
            }

            return result;
        }

        public BusinessNeedFile.NeedFileNodeFile getNodeType(){
            return BusinessNeedFile.NeedFileNodeFile.ALL;
        }

        @Override
        protected String getIcon() {
            return "glyphicon glyphicon-align-justify";
        }
    }

    public static class AnyNode extends ParentNode{

        public AnyNode(ParentNode parentNode, String name,String description) {
            super(parentNode, name,description);
        }

        @Override
        public FileStatus getStatus() {
            if (childFileNode.isEmpty()){
                return FileStatus.OK;
            }
            FileStatus result = FileStatus.NO_UPLOAD;
            for (BusinessFileNode fileNode: childFileNode){
                if (fileNode.getStatus().getOrder() < result.getOrder()){
                    result = fileNode.getStatus();
                }
            }
            return result;
        }

        public BusinessNeedFile.NeedFileNodeFile getNodeType(){
            return BusinessNeedFile.NeedFileNodeFile.ANYONE;
        }

        @Override
        protected String getIcon() {
            return "glyphicon glyphicon-list";
        }
    }


    public static class OtherChildNode extends ChildNode{

        public OtherChildNode(ParentNode parentNode,BusinessFile businessFile,String description) {
            super(parentNode,businessFile,description);
        }

        @Override
        protected boolean isInTask() {
            // all task can upload and delete other file
            return true;
        }

        @Override
        public boolean isCanEditTitle(){
            return true;
        }

        @Override
        public boolean canDeleteImg(String empId, String id){
            for (UploadFile file: getBusinessFile().getUploadFiles()){
                if (file.getId().equals(id)){
                    return empId.equals(file.getEmpCode());
                }
            }
            return false;
        }
    }

    public static class FileChildNode extends ChildNode {

        private boolean inTask;

        public FileChildNode(ParentNode parentNode, BusinessFile businessFile,String description, boolean inTask) {
            super(parentNode,businessFile,description);
            this.inTask = inTask;
        }

        @Override
        public boolean isCanEditTitle(){
            return false;
        }

        @Override
        protected boolean isInTask() {
            return inTask;
        }

        @Override
        public boolean canDeleteImg(String empId, String id){
            for (UploadFile file: getBusinessFile().getUploadFiles()){
                if (file.getId().equals(id)){
                    return inTask;
                }
            }
            return false;
        }
    }

    public static abstract class ChildNode extends BusinessFileNode{

        private BusinessFile businessFile;

        public ChildNode(ParentNode parentNode, BusinessFile businessFile,String description) {
            super(parentNode,description);
            this.businessFile = businessFile;
        }

        public BusinessFile getBusinessFile() {
            return businessFile;
        }

        @Override
        public String getId(){
            return businessFile.getId();
        }

        @Override
        public String getName(){
            return businessFile.getName();
        }

        @Override
        public JSONObject getJsonData() throws JSONException {
            JSONObject result = new JSONObject();
            result.put("text",businessFile.getName());
            result.put("id", getId());
            result.put("color", getColor());
            result.put("icon", "glyphicon glyphicon-picture");
            return result;
        }

        protected abstract boolean isInTask();

        @Override
        public boolean isCanPutFile(){
            return isInTask();
        }

        public List<UploadFile> getImages(){
            return businessFile.getUploadFileList();
        }

        @Override
        public FileStatus getStatus() {

            if (getBusinessFile().getUploadFiles().isEmpty()){

                if (isInTask()){
                    return getBusinessFile().isNoFile() ? FileStatus.NO_FILE : FileStatus.NO_UPLOAD;
                }else{
                    return FileStatus.OTHER_UPLOAD;
                }

            }else {
                return FileStatus.OK;
            }

        }

        public BusinessNeedFile.NeedFileNodeFile getNodeType(){
            return BusinessNeedFile.NeedFileNodeFile.CHILDREN;
        }

    }

}
