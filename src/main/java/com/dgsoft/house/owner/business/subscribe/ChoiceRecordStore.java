package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.common.OrderBeanComparator;
import com.dgsoft.common.system.action.BusinessDefineHome;
import com.dgsoft.common.system.business.TaskSubscribeComponent;
import com.dgsoft.common.system.model.BusinessNeedFile;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.business.TaskOwnerBusinessFile;
import com.dgsoft.house.owner.model.BusinessFile;
import com.dgsoft.house.owner.model.RecordStore;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.*;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by cooper on 4/14/16.
 */
@Name("choiceRecordStore")
@Scope(ScopeType.CONVERSATION)
public class ChoiceRecordStore implements java.io.Serializable, TaskSubscribeComponent {


    @Create
    public void init() {
        recordStores = ownerBusinessHome.getInstance().getRecordStoreList();
    }

    @Override
    public void initSubscribe() {
        fileTreeData = null;
    }

    @In
    private FacesMessages facesMessages;

    @Override
    public void validSubscribe() {
        if (!taskOwnerBusinessFile.isPass()) {
            facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR, "needFileNotUpload");
        } else if (!isAllAssign()) {
            facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR, "recordFileNotAssign");
        }

    }

    @Override
    public boolean isPass() {
        return taskOwnerBusinessFile.isPass() && isAllAssign();
    }

    private boolean isAllAssign() {

        for (BusinessFile businessFile : ownerBusinessHome.getInstance().getUploadFileses()) {
            boolean exists = false;
            for (RecordStore recordStore : recordStores) {
                for (BusinessFile assignFile : recordStore.getBusinessFiles()) {
                    if (assignFile.getId().equals(businessFile.getId())) {
                        exists = true;
                        break;
                    }
                }
                if (exists) {
                    break;
                }
            }
            if (!exists) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean saveSubscribe() {

        if (taskOwnerBusinessFile.isPass() && isAllAssign()) {
            for(RecordStore recordStore: recordStores){
                for(BusinessFile file: recordStore.getBusinessFiles()){
                    file.setRecordStore(recordStore);
                }
                ownerBusinessHome.getInstance().getRecordStores().add(recordStore);
            }

            return true;
        }
        validSubscribe();
        return false;
    }

    @In
    private TaskOwnerBusinessFile taskOwnerBusinessFile;

    private class FileNode {

        //private String id;

        public String getId() {
            if (BusinessNeedFile.NeedFileNodeFile.CHILDREN.equals(businessNeedFile.getType())) {
                return businessFile.getId();
            } else {
                return "";
            }

        }

        public FileNode(BusinessNeedFile businessNeedFile) {
            this.businessNeedFile = businessNeedFile;
            if (!BusinessNeedFile.NeedFileNodeFile.CHILDREN.equals(businessNeedFile.getType())) {
                childs = new ArrayList<FileNode>(businessNeedFile.getChildren().size());
                for (BusinessNeedFile childNeedFile : businessNeedFile.getChildrenList()) {
                    childs.add(new FileNode(childNeedFile));
                }


            } else {
                for (BusinessFile businessFile : ownerBusinessHome.getInstance().getUploadFileses()) {
                    if (businessFile.isImportant() &&
                            (businessFile.isNoFile() || !businessFile.getUploadFiles().isEmpty())
                            && businessNeedFile.getId().equals(businessFile.getImportantCode())) {
                        this.businessFile = businessFile;
                        break;
                    }
                }
                if (businessFile == null) {
                    throw new IllegalArgumentException("file not upload:" + businessNeedFile.getName());
                }
                enable = !fileIsAssign(this.businessFile.getId());

            }
        }

        private BusinessFile businessFile;

        private BusinessNeedFile businessNeedFile;

        private List<FileNode> childs;

        private boolean enable = true;

        private boolean isEnable() {
            if (BusinessNeedFile.NeedFileNodeFile.CHILDREN.equals(businessNeedFile.getType())) {
                return enable;
            } else {
                for (FileNode child : childs) {
                    if (child.isEnable()) {
                        return true;
                    }
                }
                return false;
            }

        }


        public JSONObject getJsonData() throws JSONException {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("text", businessNeedFile.getName());
            jsonObject.put("id", getId());
            JSONObject state = new JSONObject();
            if (BusinessNeedFile.NeedFileNodeFile.CHILDREN.equals(businessNeedFile.getType())) {
                if (businessFile == null) {
                    return null;
                }
            } else {
                state.put("expanded", true);
                JSONArray jo = new JSONArray();
                for (FileNode fileNode : childs) {
                    JSONObject childData = fileNode.getJsonData();
                    if (childData != null) {
                        jo.put(childData);
                    }
                }

                if (jo.length() > 0) {
                    jsonObject.put("nodes", jo);

                } else {
                    return null;
                }
            }


            if (!isEnable()) {
                state.put("checked", false);
                state.put("disabled", true);
            }
            jsonObject.put("state", state);
            return jsonObject;

        }

    }

    @In
    private OwnerBusinessHome ownerBusinessHome;

    @In
    private BusinessDefineHome businessDefineHome;

    @DataModel("saveMultiRecordStores")
    private List<RecordStore> recordStores;

    @DataModelSelection
    private RecordStore selectRecordStore;


    public void deleteRecordStore() {
        for (BusinessFile businessFile : selectRecordStore.getBusinessFiles()) {
            businessFile.setRecordStore(null);
        }
        recordStores.remove(selectRecordStore);
        ownerBusinessHome.getInstance().getRecordStores().remove(selectRecordStore);
        fileTreeData = null;
    }

    private String fileTreeData;


    public String getFileTreeData() {
        if (fileTreeData == null) {
            try {
                genTreeData();
            } catch (JSONException e) {
                throw new IllegalArgumentException(e.getMessage(), e);
            }
        }
        return fileTreeData;
    }

    private boolean fileIsAssign(String fileId) {



            for (RecordStore recordStore : recordStores) {
                for (BusinessFile assignFile : recordStore.getBusinessFiles()) {
                    if (assignFile.getId().equals(fileId)) {
                        return true;
                    }
                }
            }


        return false;
    }


    private void genTreeData() throws JSONException {
        List<BusinessNeedFile> rootNeedFiles = businessDefineHome.getNeedFileRootList();
        JSONArray result = new JSONArray();
        JSONArray importFileData = new JSONArray();
        boolean enable = false;
        for (BusinessNeedFile rootFile : rootNeedFiles) {

            FileNode fileNode = new FileNode(rootFile);
            JSONObject nodeData = fileNode.getJsonData();
            if (fileNode.isEnable()) {
                enable = true;
            }
            if (nodeData != null) {
                importFileData.put(nodeData);
            }
        }

        if (importFileData.length() > 0) {
            JSONObject importNode = new JSONObject();
            importNode.put("text", "要件");
            JSONObject state = new JSONObject();
            state.put("expanded", true);
            if (!enable) {
                state.put("checked", false);
                state.put("disabled", true);
            }
            importNode.put("state", state);
            importNode.put("nodes", importFileData);
            result.put(importNode);
        }

        JSONArray otherFileNodes = new JSONArray();
        List<BusinessFile> businessFileList = new ArrayList<BusinessFile>(ownerBusinessHome.getInstance().getUploadFileses());
        Collections.sort(businessFileList, OrderBeanComparator.getInstance());
        enable = false;
        for (BusinessFile businessFile : businessFileList) {
            if (!businessFile.isImportant() && (businessFile.isNoFile() || !businessFile.getUploadFiles().isEmpty())) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("text", businessFile.getName());
                jsonObject.put("id", businessFile.getId());

                if (fileIsAssign(businessFile.getId())) {
                    JSONObject state = new JSONObject();
                    state.put("checked", false);
                    state.put("disabled", true);
                    jsonObject.put("state", state);
                } else {
                    enable = true;
                }

                otherFileNodes.put(jsonObject);
            }
        }
        if (otherFileNodes.length() > 0) {
            JSONObject otherFileNode = new JSONObject();
            otherFileNode.put("text", "附件");
            JSONObject state = new JSONObject();
            state.put("expanded", true);
            if (!enable) {
                state.put("checked", false);
                state.put("disabled", true);
            }
            otherFileNode.put("state", state);
            otherFileNode.put("nodes", otherFileNodes);
            result.put(otherFileNode);
        }
        if (result.length() > 0) {
            fileTreeData = result.toString();
        } else {
            fileTreeData = "";
        }

    }


    private String checkedString;

    public String getCheckedString() {
        return checkedString;
    }

    public void setCheckedString(String checkedString) {
        this.checkedString = checkedString;
    }

    public void createRecordStore() {
        if (checkedString != null && !"".equals(checkedString)) {
            String[] ids = checkedString.split(",");
            if (ids.length > 0) {
                RecordStore recordStore = new RecordStore(ownerBusinessHome.getInstance());
                //ownerBusinessHome.getInstance().getRecordStores().add(recordStore);
                for (String id : ids) {


                    for (BusinessFile businessFile : ownerBusinessHome.getInstance().getUploadFileses()) {
                        if (businessFile.getId().equals(id)) {
                            recordStore.getBusinessFiles().add(businessFile);
                            //businessFile.setRecordStore(recordStore);
                        }
                    }


                }
                recordStores.add(recordStore);
                fileTreeData = null;

            }
        }


    }

    public void reset() {
        ownerBusinessHome.getInstance().getRecordStores().clear();
        for (BusinessFile businessFile : ownerBusinessHome.getInstance().getUploadFileses()) {
            businessFile.setRecordStore(null);
        }
        recordStores = new ArrayList<RecordStore>();
        fileTreeData = null;
    }

}
