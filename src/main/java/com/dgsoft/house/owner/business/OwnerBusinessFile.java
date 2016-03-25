package com.dgsoft.house.owner.business;

import com.dgsoft.common.FileExtendUploadPublish;
import com.dgsoft.common.helper.JsonDataProvider;
import com.dgsoft.common.system.*;
import com.dgsoft.common.system.action.BusinessDefineHome;
import com.dgsoft.common.system.model.BusinessNeedFile;
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



    //    public String getSelectId(){
//        if (selectNode == null)
//            return null;
//        if (selectNode instanceof BusinessFileTreeNode){
//            if (((BusinessFileTreeNode)selectNode).getBusinessNeedFile() == null){
//                return "I_ROOT";
//            }
//            return (((BusinessFileTreeNode)selectNode).getBusinessNeedFile().getId());
//        }else {
//            if (selectNode.getBusinessFile() == null) {
//                return "ROOT";
//            }
//            return selectNode.getBusinessFile().getId();
//        }
//
//    }
//
//    public BusinessFileNode getSelectNode() {
//        return selectNode;
//    }
//
//    public void setSelectNode(BusinessFileNode selectNode) {
//        this.selectNode = selectNode;
//    }
//
//    public String getSelectName(){
//        if (selectNode == null){
//            return null;
//        }
//        if (selectNode instanceof BusinessFileTreeNode){
//            if (((BusinessFileTreeNode)selectNode).getBusinessNeedFile() == null){
//                return null;
//            }
//            return ((BusinessFileTreeNode)selectNode).getBusinessNeedFile().getName();
//        }else{
//            if (selectNode.getBusinessFile() == null){
//                return null;
//            }
//            return selectNode.getBusinessFile().getName();
//        }
//    }

//
//    public BusinessNeedFile.NeedFileNodeFile getSelectType(){
//        if (selectNode == null){
//            return null;
//        }
//        if (selectNode instanceof BusinessFileTreeNode){
//            if (((BusinessFileTreeNode)selectNode).getBusinessNeedFile() == null){
//                return null;
//            }
//            return ((BusinessFileTreeNode)selectNode).getBusinessNeedFile().getType();
//        }else{
//            if (selectNode.getBusinessFile() == null){
//                return BusinessNeedFile.NeedFileNodeFile.ALL;
//            }
//            return BusinessNeedFile.NeedFileNodeFile.OTHER;
//        }
//    }

    public String save() {
        //ownerBusinessHome.getInstance().getUploadFileses().clear();
        //saveImportNodeFile(getTree());
        return "saved";
    }
//
//    public void saveImportNodeFile(List<BusinessFileNode> nodes) {
//        for (BusinessFileNode node : nodes) {
//            if (node.getBusinessFile() != null) {
//                node.getBusinessFile().setOwnerBusiness(ownerBusinessHome.getInstance());
//                ownerBusinessHome.getInstance().getUploadFileses().add(node.getBusinessFile());
//                Logging.getLog(getClass()).debug("save node:" + node.getBusinessFile().getName());
//            }
//            if (node.getChildFileNode() != null && !node.getChildFileNode().isEmpty()) {
//                saveImportNodeFile(node.getChildFileNode());
//            }
//        }
//    }

//    public void loadFromFile() {
//        try {
//            BusinessFileOperation fileOperation = BusinessFileCheckFactory.getFileOperation(ownerBusinessHome.getInstance().getId());
//            try {
//                loadFromFile(getTree(), fileOperation);
//                changeListener();
//            } finally {
//                fileOperation.close();
//            }
//
//
//
//        } catch (IOException e) {
//            facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR, "FILE_SERVER_FIAIL");
//        }
//    }

    public void changeListener(){
    }

    @In
    private AuthenticationInfo authInfo;
//
//    private void loadFromFile(List<BusinessFileNode> nodes, BusinessFileOperation fileOperation) {
//        for (BusinessFileNode node : nodes) {
//            if (BusinessNeedFile.NeedFileNodeFile.CHILDREN.name().equals(node.getType()) || "OTHER_FILE".equals(node.getType())) {
//                try {
//                    List<String> listFile = fileOperation.listFiles(node.getDirName());
//                    Logging.getLog(getClass()).info("check file:" + node.getDirName() + "|size:" + listFile.size());
//                    node.putFile(listFile,
//                            authInfo.getLoginEmployee().getId(), authInfo.getLoginEmployee().getPersonName(), "0");//TODO: calcmd5
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (node.getChildFileNode() != null && !node.getChildFileNode().isEmpty()) {
//                loadFromFile(node.getChildFileNode(), fileOperation);
//            }
//        }
//    }

    private List<BusinessFile> getAllImportantFile() {
        List<BusinessFile> result = new ArrayList<BusinessFile>();
        for (BusinessFile file : ownerBusinessHome.getInstance().getUploadFileses()) {
            if (file.isImportant()) {
                result.add(file);
            }
        }
        return result;
    }

//    public List<UploadFile> getAllImportanFile() {
//        List<UploadFile> result = new ArrayList<UploadFile>();
//        for (BusinessFile file : getAllImportantFile()) {
//            result.addAll(file.getUploadFiles());
//            if (file.isNoFile()) {
//                result.add(new UploadFile(file));
//            }
//        }
//        return result;
//    }




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
//        for(BusinessFileNode node : getTree()){
//            if (FileStatus.NO_UPLOAD.equals(node.getStatus())){
//                return false;
//            }
//        }
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
                        linkFile = new BusinessFile(defineNode.getName(), defineNode.getId(), defineNode.getPriority());
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
                    for (int i = 0; i < fileIdArray.length(); i++) {
                        JSONObject fileInfo = fileIdArray.getJSONObject(i);
                        BusinessFile businessFile = ((ChildNode) node).getBusinessFile();
                        businessFile.getUploadFiles().add(new UploadFile(fileInfo.getString("fid"),authInfo.getLoginEmployee().getPersonName(),authInfo.getLoginEmployee().getId(),fileInfo.getString("md5"),fileInfo.getString("name"),businessFile,fileInfo.getLong("size")));
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

    //
//    public void extendsUpload() {
//        String jsonData;
//
//        try {
//
//
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("BUSINESS", ownerBusinessHome.getInstance().getId());
//
//
//
//            jsonObject.put("IMPORT", getTree().get(0).getJson());
//            jsonObject.put("OTHER_FILE",getTree().get(1).getJson());
//
//            //jsonObject.put("OTHER")
//            jsonData = jsonObject.toString();
//        } catch (JSONException e) {
//            Logging.getLog(getClass()).debug(e.getMessage(), e);
//            jsonData = null;
//        }
//
//
//        extendsAddress = EXTENDS_PRINT_PROTOCOL + jsonDataProvider.putData(jsonData);
//    }

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

        BusinessFile businessFile = new BusinessFile(otherFileName,1000 + rootNode.getChildFileNode().size() + 1);
        ownerBusinessHome.getInstance().getUploadFileses().add(businessFile);
        rootNode.addChild(new OtherChildNode(rootNode, businessFile,""));
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
                    if (fileId.equals(uploadFile.getFileName())){
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


//
//    public class BusinessFileTreeNode extends BusinessDefineHome.NeedFileTreeNode<BusinessFileTreeNode> implements BusinessFileNode {
//
//
//        private BusinessFileTreeNode(BusinessNeedFile businessNeedFile, BusinessFileTreeNode parent) {
//            super(businessNeedFile, parent);
//        }
//
//        @Override
//        protected BusinessFileTreeNode createNewChild(BusinessNeedFile businessNeedFile) {
//
//            if (businessNeedFile.getCondition() == null ||
//                    businessNeedFile.getCondition().trim().equals("") ||
//                    Expressions.instance().createValueExpression(businessNeedFile.getCondition(), Boolean.class).getValue()) {
//                return new BusinessFileTreeNode(businessNeedFile, this);
//            } else {
//                return null;
//            }
//        }
//
//
//        public BusinessFileTreeNode(Collection<BusinessNeedFile> needFiles) {
//            super(needFiles);
//        }
//
//        private BusinessFile businessFile;
//
//        public boolean isNoFile() {
//            if ((businessNeedFile == null) || !getBusinessNeedFile().getType().equals(BusinessNeedFile.NeedFileNodeFile.CHILDREN)) {
//                return false;
//            }
//            return getBusinessFile() != null && getBusinessFile().isNoFile();
//        }
//
//        public void setNoFile(boolean value) {
//            if ((businessNeedFile == null) || !getBusinessNeedFile().getType().equals(BusinessNeedFile.NeedFileNodeFile.CHILDREN)) {
//                return;
//            }
//            if (value) {
//                if (getBusinessFile() == null) {
//                    if (value) {
//                        BusinessFile newBizFile = new BusinessFile(UUID.randomUUID().toString().replace("-", ""), getBusinessNeedFile().getName(), getBusinessNeedFile().getId(), true, true,getBusinessNeedFile().getPriority());
//                        setBusinessFile(newBizFile);
//                    }
//                } else {
//                    getBusinessFile().setNoFile(value);
//                }
//            } else {
//                if (getBusinessFile() != null) {
//                    getBusinessFile().setNoFile(value);
//                }
//            }
//
//
//        }
//
//        public String getFullPath() {
//            return getFullPath(null);
//        }
//
//        private String getFullPath(String sub) {
//            if (businessNeedFile != null) {
//
//                if (getParent() != null) {
//                    sub = ((BusinessFileTreeNode) getParent()).getFullPath(sub) + "/" + businessNeedFile.getName();
//                } else {
//                    sub = businessNeedFile.getName();
//                }
//                return sub;
//            } else
//                return "";
//        }
//
//        public BusinessFile getBusinessFile() {
//            return businessFile;
//        }
//
//        @Override
//        public List<BusinessFileNode> getChildFileNode() {
//            return new ArrayList<BusinessFileNode>(getChild());
//        }
//
//        public void setBusinessFile(BusinessFile businessFile) {
//            this.businessFile = businessFile;
//        }
//
//        public int getFileCount() {
//            if ((getBusinessNeedFile() != null) && getBusinessNeedFile().getType().equals(BusinessNeedFile.NeedFileNodeFile.CHILDREN)) {
//                return (getBusinessFile() == null) ? 0 : getBusinessFile().getUploadFiles().size();
//            } else {
//                int result = 0;
//                for (BusinessFileTreeNode child : getChild()) {
//                    result += child.getFileCount();
//                }
//                return result;
//            }
//        }
//
//        public boolean isEmptyFile() {
//            return getFileCount() == 0;
//        }
//
//        public FileStatus getStatus(){
//            return getStatus(((BusinessDefineHome)Component.getInstance(BusinessDefineHome.class)).getTaskName());
//        }
//
//        @Override
//        public boolean isReadOnly() {
//            if (Subscribe.BUSINESS_VIEW_TASK_NAME.equals(((BusinessDefineHome)Component.getInstance(BusinessDefineHome.class)).getTaskName())){
//                return true;
//            }
//
//            if ((getBusinessNeedFile() == null) || !getBusinessNeedFile().getType().equals(BusinessNeedFile.NeedFileNodeFile.CHILDREN)){
//                return false;
//            }
//
//            return !getBusinessNeedFile().getTaskNameList().contains(((BusinessDefineHome)Component.getInstance(BusinessDefineHome.class)).getTaskName());
//        }
//
//        public FileStatus getStatus(String taskName) {
//            if ((getBusinessNeedFile() != null) && getBusinessNeedFile().getType().equals(BusinessNeedFile.NeedFileNodeFile.CHILDREN)) {
//                FileStatus result;
//                if (getBusinessFile() == null) {
//                    result = FileStatus.NO_UPLOAD;
//                } else if (getBusinessFile().isNoFile()) {
//                    result = FileStatus.NO_FILE;
//
//                } else if (!getBusinessFile().getUploadFiles().isEmpty()) {
//                    result = FileStatus.OK;
//                } else {
//                    result = FileStatus.NO_UPLOAD;
//                }
//
//                if (FileStatus.NO_UPLOAD.equals(result) && !getBusinessNeedFile().getTaskNameList().contains(taskName)) {
//                    return FileStatus.OTHER_UPLOAD;
//                } else {
//                    return result;
//                }
//
//
//            } else if ((getBusinessNeedFile() == null) || getBusinessNeedFile().getType().equals(BusinessNeedFile.NeedFileNodeFile.ALL)) {
//                FileStatus result = FileStatus.OK;
//                for (BusinessFileTreeNode child : getChild()) {
//                    if (result.getOrder() < child.getStatus(taskName).getOrder()) {
//                        result = child.getStatus(taskName);
//                    }
//                }
//                return result;
//            } else {
//                FileStatus result = FileStatus.NO_UPLOAD;
//                for (BusinessFileTreeNode child : getChild()) {
//                    if (result.getOrder() > child.getStatus(taskName).getOrder()) {
//                        return child.getStatus(taskName);
//                    }
//                }
//                return result;
//            }
//
//        }
//
//        public boolean isImport() {
//            return true;
//        }
//
//        @Override
//        public String getType() {
//
//            return (businessNeedFile == null) ? "I_ROOT" : businessNeedFile.getType().name();
//        }
//
//        @Override
//        public String getDirName() {
//            if (!businessNeedFile.getType().equals(BusinessNeedFile.NeedFileNodeFile.CHILDREN)) {
//                return null;
//            }
//            return businessNeedFile.getId();
//        }
//
//        @Override
//        public void putFile(List<String> fileNames, String empCode, String empName, String md5) {
//            if (!BusinessNeedFile.NeedFileNodeFile.CHILDREN.equals(businessNeedFile.getType())) {
//                throw new IllegalArgumentException("noe Children node");
//            }
//            if (businessFile == null) {
//                businessFile = new BusinessFile(UUID.randomUUID().toString().replace("-", ""), getBusinessNeedFile().getName(), getBusinessNeedFile().getId(), false, true,getBusinessNeedFile().getPriority());
//            }
//            List<UploadFile> existsFiles = new ArrayList<UploadFile>(businessFile.getUploadFiles());
//            for (String name : fileNames) {
//
//                String extension = "";
//                String fileName;
//                int i = name.lastIndexOf('.');
//                if (i > 0) {
//                    extension = name.substring(i + 1);
//                    fileName = name.substring(0, i);
//                } else {
//                    fileName = name;
//                }
//
//
//                boolean exists = false;
//                for (UploadFile file : businessFile.getUploadFiles()) {
//                    if (file.getFileName().equals(fileName) && file.getExt().equals(extension)) {
//                        exists = true;
//                        existsFiles.remove(file);
//                        break;
//                    }
//                }
//                if (!exists) {
//                    businessFile.setNoFile(false);
//                    businessFile.getUploadFiles().add(new UploadFile(empName, empCode, md5, fileName, businessFile, extension));
//                }
//            }
//            businessFile.getUploadFiles().removeAll(existsFiles);
//        }
//
//        public JSONObject getJson() throws JSONException {
//            JSONObject result = new JSONObject();
//            result.put("TYPE", getType());
//            result.put("NAME", (businessNeedFile == null) ? "" : businessNeedFile.getName());
//            result.put("ID", (businessNeedFile == null) ? "" : businessNeedFile.getId());
//            JSONArray childArray = new JSONArray();
//            for (TreeNode treeNode : getChild()) {
//                childArray.put(((BusinessFileTreeNode) treeNode).getJson());
//            }
//            result.put("CHILD", childArray);
//            return result;
//        }
//    }
//
//
//    public static class OtherFileTreeNode implements BusinessFileNode {
//
//        private List<OtherFileTreeNode> businessFiles;
//
//        public OtherFileTreeNode(List<BusinessFile> businessFiles) {
//
//            this.businessFiles = new ArrayList<OtherFileTreeNode>(businessFiles.size());
//            for (BusinessFile businessFile : businessFiles) {
//                this.businessFiles.add(new OtherFileTreeNode(businessFile, this));
//            }
//        }
//
//        public OtherFileTreeNode(BusinessFile businessFile, TreeNode parent) {
//            this.businessFile = businessFile;
//            this.parent = parent;
//        }
//
//        public void addNode(String name){
//            //String id, String name, String importantCode, boolean noFile, boolean important,int priority
//            if (businessFile == null) {
//                businessFiles.add(new OtherFileTreeNode(new BusinessFile(UUID.randomUUID().toString().replace("-", ""), name, businessFiles.size()), this));
//            }else{
//                throw new IllegalArgumentException("not root node");
//            }
//
//
//        }
//
//        public boolean isEmptyFile() {
//            return getFileCount() == 0;
//        }
//
//        public boolean isNoFile() {
//            if ((businessFile == null)) {
//                return false;
//            }
//            return businessFile.isNoFile();
//        }
//
//        public void setNoFile(boolean value) {
//            if ((businessFile == null)) {
//                return;
//            }
//           businessFile.setNoFile(value);
//
//
//        }
//
//        private BusinessFile businessFile;
//
//        private TreeNode parent;
//
//        public BusinessFile getBusinessFile() {
//            return businessFile;
//        }
//
//        @Override
//        public List<BusinessFileNode> getChildFileNode() {
//            if (businessFiles == null) {
//                return new ArrayList<BusinessFileNode>(0);
//            }
//            return new ArrayList<BusinessFileNode>(businessFiles);
//        }
//
//        @Override
//        public TreeNode getChildAt(int childIndex) {
//            if (businessFiles == null) {
//                return null;
//            }
//            return businessFiles.get(childIndex);
//        }
//
//        @Override
//        public int getChildCount() {
//            if (businessFile != null) {
//                return 0;
//            }
//            return businessFiles.size();
//        }
//
//        @Override
//        public TreeNode getParent() {
//            if (businessFile != null) {
//                return parent;
//            } else {
//                return null;
//            }
//        }
//
//        @Override
//        public int getIndex(TreeNode node) {
//            if (businessFile == null) {
//                return businessFiles.indexOf(node);
//            } else {
//                return 0;
//            }
//
//        }
//
//        @Override
//        public boolean getAllowsChildren() {
//            return businessFile == null;
//        }
//
//        @Override
//        public boolean isLeaf() {
//            return businessFile != null;
//        }
//
//        @Override
//        public Enumeration children() {
//            if (businessFile == null) {
//                return Iterators.asEnumeration(businessFiles.iterator());
//            } else {
//                return null;
//            }
//        }
//
//        public String getType() {
//            if (businessFile == null) {
//
//                return "ROOT";
//            } else {
//                return "OTHER_FILE";
//            }
//        }
//
//        @Override
//        public String getDirName() {
//            if (businessFile == null) {
//                return null;
//            }
//            return businessFile.getId();
//        }
//
//        public boolean isImport() {
//            return false;
//        }
//
//        public int getFileCount() {
//            if (businessFile == null) {
//                int result = 0;
//                for (OtherFileTreeNode node : businessFiles) {
//                    result += node.getFileCount();
//                }
//                return result;
//            } else
//                return businessFile.getUploadFiles().size();
//        }
//
//        public String getFullPath() {
//            return getFullPath(null);
//        }
//
//        private String getFullPath(String sub) {
//            if (businessFile != null) {
//                return businessFile.getName();
//
//            }
//            return null;
//        }
//
//        @Override
//        public boolean isReadOnly() {
//            return Subscribe.BUSINESS_VIEW_TASK_NAME.equals(((BusinessDefineHome)Component.getInstance(BusinessDefineHome.class)).getTaskName());
//        }
//
//        public FileStatus getStatus(){
//            if (businessFile != null){
//                if (businessFile.getUploadFiles().isEmpty()){
//                    if (businessFile.isNoFile()){
//                        return FileStatus.NO_FILE;
//                    }else{
//                        return FileStatus.NO_UPLOAD;
//                    }
//
//                }else{
//                    return FileStatus.OK;
//                }
//            }else{
//                FileStatus result = FileStatus.OK;
//                for(OtherFileTreeNode child: businessFiles){
//                    if (result.getOrder() < child.getStatus().getOrder()){
//                        result = child.getStatus();
//                    }
//                }
//                return result;
//            }
//        }
//
//        public JSONObject getJson() throws JSONException {
//
//
//            if (businessFile == null){
//                JSONArray jsonArray = new JSONArray();
//                for(OtherFileTreeNode child: businessFiles){
//                    jsonArray.put(child.getJson());
//                }
//                JSONObject result = new JSONObject();
//                result.put("TYPE", "ROOT");
//                result.put("NAME", "");
//                result.put("ID", "");
//                result.put("CHILD",jsonArray);
//                return  result;
//            }else{
//                JSONObject result = new JSONObject();
//                result.put("TYPE", "CHILDREN");
//                result.put("NAME", businessFile.getName());
//                result.put("ID", businessFile.getId());
//                return result;
//            }
//
//
//        }
//
//        @Override
//        public void putFile(List<String> fileNames, String empCode, String empName, String md5) {
//            if (businessFile == null) {
//                throw new IllegalArgumentException("noe Children node");
//            }
//            Logging.getLog(getClass()).info("call other file put file");
//            List<UploadFile> existsFiles = new ArrayList<UploadFile>(businessFile.getUploadFiles());
//            for (String name : fileNames) {
//
//                String extension = "";
//                String fileName;
//                int i = name.lastIndexOf('.');
//                if (i > 0) {
//                    extension = name.substring(i + 1);
//                    fileName = name.substring(0, i);
//                } else {
//                    fileName = name;
//                }
//
//                boolean exists = false;
//                for (UploadFile file : businessFile.getUploadFiles()) {
//                    if (file.getFileName().equals(fileName) && file.getExt().equals(extension)) {
//                        exists = true;
//                        existsFiles.remove(file);
//                        break;
//                    }
//                }
//                if (!exists) {
//                    Logging.getLog(getClass()).info("---put file");
//                    businessFile.setNoFile(false);
//                    businessFile.getUploadFiles().add(new UploadFile(empName, empCode, md5, fileName, businessFile, extension));
//                }
//            }
//            Logging.getLog(getClass()).info("---remove file" + existsFiles.size());
//            businessFile.getUploadFiles().removeAll(existsFiles);
//
//        }
//    }
}
