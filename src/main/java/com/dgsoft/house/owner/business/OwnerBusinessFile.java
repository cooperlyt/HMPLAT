package com.dgsoft.house.owner.business;

import com.dgsoft.common.helper.JsonDataProvider;
import com.dgsoft.common.system.AuthenticationInfo;
import com.dgsoft.common.system.BusinessFileOperation;
import com.dgsoft.common.system.BusinessFtpFile;
import com.dgsoft.common.system.action.BusinessDefineHome;
import com.dgsoft.common.system.business.Subscribe;
import com.dgsoft.common.system.model.BusinessNeedFile;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessFile;
import com.dgsoft.house.owner.model.UploadFile;
import com.google.common.collect.Iterators;
import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.core.Expressions;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;
import org.jboss.seam.log.Logging;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.richfaces.component.UITree;
import org.richfaces.event.TreeSelectionChangeEvent;

import javax.swing.tree.TreeNode;
import java.io.IOException;
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
    private JsonDataProvider jsonDataProvider;

    @In
    private FacesMessages facesMessages;

    private List<BusinessFileNode> tree;

    private BusinessFileNode selectNode;

    public String getSelectId(){
        if (selectNode == null)
            return null;
        if (selectNode instanceof BusinessFileTreeNode){
            if (((BusinessFileTreeNode)selectNode).getBusinessNeedFile() == null){
                return "I_ROOT";
            }
            return (((BusinessFileTreeNode)selectNode).getBusinessNeedFile().getId());
        }else {
            if (selectNode.getBusinessFile() == null) {
                return "ROOT";
            }
            return selectNode.getBusinessFile().getId();
        }

    }

    public BusinessFileNode getSelectNode() {
        return selectNode;
    }

    public void setSelectNode(BusinessFileNode selectNode) {
        this.selectNode = selectNode;
    }

    public String getSelectName(){
        if (selectNode == null){
            return null;
        }
        if (selectNode instanceof BusinessFileTreeNode){
            if (((BusinessFileTreeNode)selectNode).getBusinessNeedFile() == null){
                return null;
            }
            return ((BusinessFileTreeNode)selectNode).getBusinessNeedFile().getName();
        }else{
            if (selectNode.getBusinessFile() == null){
                return null;
            }
            return selectNode.getBusinessFile().getName();
        }
    }


    public BusinessNeedFile.NeedFileNodeFile getSelectType(){
        if (selectNode == null){
            return null;
        }
        if (selectNode instanceof BusinessFileTreeNode){
            if (((BusinessFileTreeNode)selectNode).getBusinessNeedFile() == null){
                return null;
            }
            return ((BusinessFileTreeNode)selectNode).getBusinessNeedFile().getType();
        }else{
            if (selectNode.getBusinessFile() == null){
                return BusinessNeedFile.NeedFileNodeFile.ALL;
            }
            return BusinessNeedFile.NeedFileNodeFile.OTHER;
        }
    }

    public String save() {
        ownerBusinessHome.getInstance().getUploadFileses().clear();
        saveImportNodeFile(getTree());
        return "saved";
    }

    public void saveImportNodeFile(List<BusinessFileNode> nodes) {
        for (BusinessFileNode node : nodes) {
            if (node.getBusinessFile() != null) {
                node.getBusinessFile().setOwnerBusiness(ownerBusinessHome.getInstance());
                ownerBusinessHome.getInstance().getUploadFileses().add(node.getBusinessFile());
                Logging.getLog(getClass()).debug("save node:" + node.getBusinessFile().getName());
            }
            if (node.getChildFileNode() != null && !node.getChildFileNode().isEmpty()) {
                saveImportNodeFile(node.getChildFileNode());
            }
        }
    }

    public void loadFromFile() {
        try {
            BusinessFileOperation fileOperation = new BusinessFtpFile(ownerBusinessHome.getInstance().getId());
            try {
                loadFromFile(getTree(), fileOperation);
                changeListener();
            } finally {
                fileOperation.close();
            }
        } catch (IOException e) {
            facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR, "FILE_SERVER_FIAIL");
        }
    }

    public void changeListener(){
    }

    @In
    private AuthenticationInfo authInfo;

    private void loadFromFile(List<BusinessFileNode> nodes, BusinessFileOperation fileOperation) {
        for (BusinessFileNode node : nodes) {
            if (BusinessNeedFile.NeedFileNodeFile.CHILDREN.name().equals(node.getType()) || "OTHER_FILE".equals(node.getType())) {
                try {
                    Logging.getLog(getClass()).debug("check file:" + node.getDirName());
                    node.putFile(fileOperation.listFiles(node.getDirName()),
                            authInfo.getLoginEmployee().getId(), authInfo.getLoginEmployee().getPersonName(), "0");//TODO: calcmd5
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (node.getChildFileNode() != null && !node.getChildFileNode().isEmpty()) {
                loadFromFile(node.getChildFileNode(), fileOperation);
            }
        }
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


    public void selectionChanged(TreeSelectionChangeEvent selectionChangeEvent) {
        // considering only single selection
        List<Object> selection = new ArrayList<Object>(selectionChangeEvent.getNewSelection());
        Logging.getLog(getClass()).debug(selection.size());
        if (selection.size() == 0) {
            selectNode = null;
            return;
        }
        Object currentSelectionKey = selection.get(0);
        UITree tree = (UITree) selectionChangeEvent.getSource();

        Object storedKey = tree.getRowKey();
        tree.setRowKey(currentSelectionKey);
        Logging.getLog(getClass()).debug(tree.getRowData());
        if (tree.getRowData() instanceof BusinessFileNode) {
            Logging.getLog(getClass()).debug("selected node");
            selectNode = (BusinessFileNode) tree.getRowData();
        } else {
            Logging.getLog(getClass()).debug("selected empty");
            selectNode = null;
        }
        tree.setRowKey(storedKey);
    }


    public List<BusinessFileNode> getTree() {
        if (tree == null) {
            initBusinessNeedFiles();
        }
        return tree;
    }

    public FileStatus getImportantStatus() {
        if (getTree().size() < 2) {
            return FileStatus.OK;
        }
        return ((BusinessFileTreeNode) getTree().get(0)).getStatus(businessDefineHome.getTaskName());
    }

    public boolean isPass() {
        for(BusinessFileNode node : getTree()){
            if (FileStatus.NO_UPLOAD.equals(node.getStatus())){
                return false;
            }
        }
        return true;
    }

    private void initBusinessNeedFiles() {

        tree = new ArrayList<BusinessFileNode>(2);

        List<BusinessNeedFile> rootNeedFile = businessDefineHome.getNeedFileRootList();
        if (!rootNeedFile.isEmpty()) {
            BusinessFileTreeNode importantNode = new BusinessFileTreeNode(rootNeedFile);
            fillImportNode(importantNode);
            tree.add(importantNode);
        }

        List<BusinessFile> otherBusinessFiles = new ArrayList<BusinessFile>();

        for (BusinessFile file : ownerBusinessHome.getInstance().getUploadFileses()) {
            if (!file.isImportant()) {
                otherBusinessFiles.add(file);
            }
        }

        tree.add(new OtherFileTreeNode(otherBusinessFiles));

    }

    private void fillImportNode(BusinessFileTreeNode node) {
        for (BusinessFileTreeNode child : node.getChild()) {
            if (child.getBusinessNeedFile() != null && BusinessNeedFile.NeedFileNodeFile.CHILDREN.equals(child.getBusinessNeedFile().getType())) {
                for (BusinessFile file : getAllImportantFile()) {
                    if ((file.getImportantCode() != null) && (file.getImportantCode().trim().equals(child.getBusinessNeedFile().getId()))) {
                        child.setBusinessFile(file);
                        break;
                    }
                }
            }
            fillImportNode(child);
        }

    }


    private final static String EXTENDS_PRINT_PROTOCOL = "ExtendsUpload://";

    private String extendsAddress;

    public String getExtendsAddress() {
        return extendsAddress;
    }

    public void extendsUpload() {
        String jsonData;

        try {


            JSONObject jsonObject = new JSONObject();
            jsonObject.put("BUSINESS", ownerBusinessHome.getInstance().getId());



            jsonObject.put("IMPORT", getTree().get(0).getJson());
            jsonObject.put("OTHER_FILE",getTree().get(1).getJson());

            //jsonObject.put("OTHER")
            jsonData = jsonObject.toString();
        } catch (JSONException e) {
            Logging.getLog(getClass()).debug(e.getMessage(), e);
            jsonData = null;
        }


        extendsAddress = EXTENDS_PRINT_PROTOCOL + jsonDataProvider.putData(jsonData);
    }

    public enum FileStatus {
        OK(1), OTHER_UPLOAD(2), NO_FILE(3), NO_UPLOAD(4);

        private int order;

        FileStatus(int order) {
            this.order = order;
        }

        public int getOrder() {
            return order;
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
        ((OtherFileTreeNode)getTree().get(1)).addNode(otherFileName);
        otherFileName = null;
    }

    private String otherFileAutoComplete;

    public String getOtherFileAutoComplete(){

        if (otherFileAutoComplete == null){
            otherFileAutoComplete = "";
            for(String name:  ownerBusinessHome.getEntityManager().createQuery("select distinct f.name from BusinessFile f where f.ownerBusiness.status in ('COMPLETE','COMPLETE_CANCEL')",String.class).getResultList()){
                if (!"".equals(otherFileAutoComplete)){
                    otherFileAutoComplete += ",";
                }
                otherFileAutoComplete += "'" + name + "'";
            }
        }
        return otherFileAutoComplete;
    }

    public interface BusinessFileNode extends TreeNode {

        BusinessFile getBusinessFile();

        List<BusinessFileNode> getChildFileNode();

        String getType();

        String getDirName();

        String getFullPath();

        boolean isImport();

        boolean isReadOnly();

        FileStatus getStatus();

        void putFile(List<String> fileNames, String empCode, String empName, String md5);

        JSONObject getJson() throws JSONException;
    }

    public class BusinessFileTreeNode extends BusinessDefineHome.NeedFileTreeNode<BusinessFileTreeNode> implements BusinessFileNode {


        private BusinessFileTreeNode(BusinessNeedFile businessNeedFile, BusinessFileTreeNode parent) {
            super(businessNeedFile, parent);
        }

        @Override
        protected BusinessFileTreeNode createNewChild(BusinessNeedFile businessNeedFile) {

            if (businessNeedFile.getCondition() == null ||
                    businessNeedFile.getCondition().trim().equals("") ||
                    Expressions.instance().createValueExpression(businessNeedFile.getCondition(), Boolean.class).getValue()) {
                return new BusinessFileTreeNode(businessNeedFile, this);
            } else {
                return null;
            }
        }


        public BusinessFileTreeNode(Collection<BusinessNeedFile> needFiles) {
            super(needFiles);
        }

        private BusinessFile businessFile;

        public boolean isNoFile() {
            if ((businessNeedFile == null) || !getBusinessNeedFile().getType().equals(BusinessNeedFile.NeedFileNodeFile.CHILDREN)) {
                return false;
            }
            return getBusinessFile() != null && getBusinessFile().isNoFile();
        }

        public void setNoFile(boolean value) {
            if ((businessNeedFile == null) || !getBusinessNeedFile().getType().equals(BusinessNeedFile.NeedFileNodeFile.CHILDREN)) {
                return;
            }
            if (value) {
                if (getBusinessFile() == null) {
                    if (value) {
                        BusinessFile newBizFile = new BusinessFile(UUID.randomUUID().toString().replace("-", ""), getBusinessNeedFile().getName(), getBusinessNeedFile().getId(), true, true,getBusinessNeedFile().getPriority());
                        setBusinessFile(newBizFile);
                    }
                } else {
                    getBusinessFile().setNoFile(value);
                }
            } else {
                if (getBusinessFile() != null) {
                    getBusinessFile().setNoFile(value);
                }
            }


        }

        public String getFullPath() {
            return getFullPath(null);
        }

        private String getFullPath(String sub) {
            if (businessNeedFile != null) {

                if (getParent() != null) {
                    sub = ((BusinessFileTreeNode) getParent()).getFullPath(sub) + "/" + businessNeedFile.getName();
                } else {
                    sub = businessNeedFile.getName();
                }
                return sub;
            } else
                return "";
        }

        public BusinessFile getBusinessFile() {
            return businessFile;
        }

        @Override
        public List<BusinessFileNode> getChildFileNode() {
            return new ArrayList<BusinessFileNode>(getChild());
        }

        public void setBusinessFile(BusinessFile businessFile) {
            this.businessFile = businessFile;
        }

        public int getFileCount() {
            if ((getBusinessNeedFile() != null) && getBusinessNeedFile().getType().equals(BusinessNeedFile.NeedFileNodeFile.CHILDREN)) {
                return (getBusinessFile() == null) ? 0 : getBusinessFile().getUploadFiles().size();
            } else {
                int result = 0;
                for (BusinessFileTreeNode child : getChild()) {
                    result += child.getFileCount();
                }
                return result;
            }
        }

        public boolean isEmptyFile() {
            return getFileCount() == 0;
        }

        public FileStatus getStatus(){
            return getStatus(((BusinessDefineHome)Component.getInstance(BusinessDefineHome.class)).getTaskName());
        }

        @Override
        public boolean isReadOnly() {
            if (Subscribe.BUSINESS_VIEW_TASK_NAME.equals(((BusinessDefineHome)Component.getInstance(BusinessDefineHome.class)).getTaskName())){
                return true;
            }

            if ((getBusinessNeedFile() == null) || !getBusinessNeedFile().getType().equals(BusinessNeedFile.NeedFileNodeFile.CHILDREN)){
                return false;
            }

            return !getBusinessNeedFile().getTaskNameList().contains(((BusinessDefineHome)Component.getInstance(BusinessDefineHome.class)).getTaskName());
        }

        public FileStatus getStatus(String taskName) {
            if ((getBusinessNeedFile() != null) && getBusinessNeedFile().getType().equals(BusinessNeedFile.NeedFileNodeFile.CHILDREN)) {
                FileStatus result;
                if (getBusinessFile() == null) {
                    result = FileStatus.NO_UPLOAD;
                } else if (getBusinessFile().isNoFile()) {
                    result = FileStatus.NO_FILE;

                } else if (!getBusinessFile().getUploadFiles().isEmpty()) {
                    result = FileStatus.OK;
                } else {
                    result = FileStatus.NO_UPLOAD;
                }

                if (FileStatus.NO_UPLOAD.equals(result) && !getBusinessNeedFile().getTaskNameList().contains(taskName)) {
                    return FileStatus.OTHER_UPLOAD;
                } else {
                    return result;
                }


            } else if ((getBusinessNeedFile() == null) || getBusinessNeedFile().getType().equals(BusinessNeedFile.NeedFileNodeFile.ALL)) {
                FileStatus result = FileStatus.OK;
                for (BusinessFileTreeNode child : getChild()) {
                    if (result.getOrder() < child.getStatus(taskName).getOrder()) {
                        result = child.getStatus(taskName);
                    }
                }
                return result;
            } else {
                FileStatus result = FileStatus.NO_UPLOAD;
                for (BusinessFileTreeNode child : getChild()) {
                    if (result.getOrder() > child.getStatus(taskName).getOrder()) {
                        return child.getStatus(taskName);
                    }
                }
                return result;
            }

        }

        public boolean isImport() {
            return true;
        }

        @Override
        public String getType() {

            return (businessNeedFile == null) ? "I_ROOT" : businessNeedFile.getType().name();
        }

        @Override
        public String getDirName() {
            if (!businessNeedFile.getType().equals(BusinessNeedFile.NeedFileNodeFile.CHILDREN)) {
                return null;
            }
            return businessNeedFile.getId();
        }

        @Override
        public void putFile(List<String> fileNames, String empCode, String empName, String md5) {
            if (!BusinessNeedFile.NeedFileNodeFile.CHILDREN.equals(businessNeedFile.getType())) {
                throw new IllegalArgumentException("noe Children node");
            }
            if (businessFile == null) {
                businessFile = new BusinessFile(UUID.randomUUID().toString().replace("-", ""), getBusinessNeedFile().getName(), getBusinessNeedFile().getId(), false, true,getBusinessNeedFile().getPriority());
            }
            List<UploadFile> existsFiles = new ArrayList<UploadFile>(businessFile.getUploadFiles());
            for (String name : fileNames) {

                String extension = "";
                String fileName;
                int i = name.lastIndexOf('.');
                if (i > 0) {
                    extension = name.substring(i + 1);
                    fileName = name.substring(0, i);
                } else {
                    fileName = name;
                }


                boolean exists = false;
                for (UploadFile file : businessFile.getUploadFiles()) {
                    if (file.getFileName().equals(fileName) && file.getExt().equals(extension)) {
                        exists = true;
                        existsFiles.remove(file);
                        break;
                    }
                }
                if (!exists) {
                    businessFile.setNoFile(false);
                    businessFile.getUploadFiles().add(new UploadFile(empName, empCode, md5, fileName, businessFile, extension));
                }
            }
            businessFile.getUploadFiles().removeAll(existsFiles);
        }

        public JSONObject getJson() throws JSONException {
            JSONObject result = new JSONObject();
            result.put("TYPE", getType());
            result.put("NAME", (businessNeedFile == null) ? "" : businessNeedFile.getName());
            result.put("ID", (businessNeedFile == null) ? "" : businessNeedFile.getId());
            JSONArray childArray = new JSONArray();
            for (TreeNode treeNode : getChild()) {
                childArray.put(((BusinessFileTreeNode) treeNode).getJson());
            }
            result.put("CHILD", childArray);
            return result;
        }
    }


    public static class OtherFileTreeNode implements BusinessFileNode {

        private List<OtherFileTreeNode> businessFiles;

        public OtherFileTreeNode(List<BusinessFile> businessFiles) {

            this.businessFiles = new ArrayList<OtherFileTreeNode>(businessFiles.size());
            for (BusinessFile businessFile : businessFiles) {
                this.businessFiles.add(new OtherFileTreeNode(businessFile, this));
            }
        }

        public OtherFileTreeNode(BusinessFile businessFile, TreeNode parent) {
            this.businessFile = businessFile;
            this.parent = parent;
        }

        public void addNode(String name){
            //String id, String name, String importantCode, boolean noFile, boolean important,int priority
            if (businessFile == null) {
                businessFiles.add(new OtherFileTreeNode(new BusinessFile(UUID.randomUUID().toString().replace("-", ""), name, businessFiles.size()), this));
            }else{
                throw new IllegalArgumentException("not root node");
            }


        }

        public boolean isEmptyFile() {
            return getFileCount() == 0;
        }

        public boolean isNoFile() {
            if ((businessFile == null)) {
                return false;
            }
            return businessFile.isNoFile();
        }

        public void setNoFile(boolean value) {
            if ((businessFile == null)) {
                return;
            }
           businessFile.setNoFile(value);


        }

        private BusinessFile businessFile;

        private TreeNode parent;

        public BusinessFile getBusinessFile() {
            return businessFile;
        }

        @Override
        public List<BusinessFileNode> getChildFileNode() {
            if (businessFiles == null) {
                return new ArrayList<BusinessFileNode>(0);
            }
            return new ArrayList<BusinessFileNode>(businessFiles);
        }

        @Override
        public TreeNode getChildAt(int childIndex) {
            if (businessFiles == null) {
                return null;
            }
            return businessFiles.get(childIndex);
        }

        @Override
        public int getChildCount() {
            if (businessFile != null) {
                return 0;
            }
            return businessFiles.size();
        }

        @Override
        public TreeNode getParent() {
            if (businessFile != null) {
                return parent;
            } else {
                return null;
            }
        }

        @Override
        public int getIndex(TreeNode node) {
            if (businessFile == null) {
                return businessFiles.indexOf(node);
            } else {
                return 0;
            }

        }

        @Override
        public boolean getAllowsChildren() {
            return businessFile == null;
        }

        @Override
        public boolean isLeaf() {
            return businessFile != null;
        }

        @Override
        public Enumeration children() {
            if (businessFile == null) {
                return Iterators.asEnumeration(businessFiles.iterator());
            } else {
                return null;
            }
        }

        public String getType() {
            if (businessFile == null) {

                return "ROOT";
            } else {
                return "OTHER_FILE";
            }
        }

        @Override
        public String getDirName() {
            if (businessFile == null) {
                return null;
            }
            return businessFile.getId();
        }

        public boolean isImport() {
            return false;
        }

        public int getFileCount() {
            if (businessFile == null) {
                int result = 0;
                for (OtherFileTreeNode node : businessFiles) {
                    result += node.getFileCount();
                }
                return result;
            } else
                return businessFile.getUploadFiles().size();
        }

        public String getFullPath() {
            return getFullPath(null);
        }

        private String getFullPath(String sub) {
            if (businessFile != null) {
                return businessFile.getName();

            }
            return null;
        }

        @Override
        public boolean isReadOnly() {
            return Subscribe.BUSINESS_VIEW_TASK_NAME.equals(((BusinessDefineHome)Component.getInstance(BusinessDefineHome.class)).getTaskName());
        }

        public FileStatus getStatus(){
            if (businessFile != null){
                if (businessFile.getUploadFiles().isEmpty()){
                    if (businessFile.isNoFile()){
                        return FileStatus.NO_FILE;
                    }else{
                        return FileStatus.NO_UPLOAD;
                    }

                }else{
                    return FileStatus.OK;
                }
            }else{
                FileStatus result = FileStatus.OK;
                for(OtherFileTreeNode child: businessFiles){
                    if (result.getOrder() < child.getStatus().getOrder()){
                        result = child.getStatus();
                    }
                }
                return result;
            }
        }

        public JSONObject getJson() throws JSONException {


            if (businessFile == null){
                JSONArray jsonArray = new JSONArray();
                for(OtherFileTreeNode child: businessFiles){
                    jsonArray.put(child.getJson());
                }
                JSONObject result = new JSONObject();
                result.put("TYPE", "ROOT");
                result.put("NAME", "");
                result.put("ID", "");
                result.put("CHILD",jsonArray);
                return  result;
            }else{
                JSONObject result = new JSONObject();
                result.put("TYPE", "CHILDREN");
                result.put("NAME", businessFile.getName());
                result.put("ID", businessFile.getId());
                return result;
            }


        }

        @Override
        public void putFile(List<String> fileNames, String empCode, String empName, String md5) {
            if (businessFile == null) {
                throw new IllegalArgumentException("noe Children node");
            }
            List<UploadFile> existsFiles = new ArrayList<UploadFile>(businessFile.getUploadFiles());
            for (String name : fileNames) {

                String extension = "";
                String fileName;
                int i = name.lastIndexOf('.');
                if (i > 0) {
                    extension = name.substring(i + 1);
                    fileName = name.substring(0, i);
                } else {
                    fileName = name;
                }

                boolean exists = false;
                for (UploadFile file : businessFile.getUploadFiles()) {
                    if (file.getFileName().equals(fileName) && file.getExt().equals(extension)) {
                        exists = true;
                        existsFiles.remove(file);
                        break;
                    }
                }
                if (!exists) {
                    businessFile.setNoFile(false);
                    businessFile.getUploadFiles().add(new UploadFile(empName, empCode, md5, fileName, businessFile, extension));
                }
            }
            businessFile.getUploadFiles().removeAll(existsFiles);

        }
    }
}
