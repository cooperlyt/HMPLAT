package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.common.OrderBeanComparator;
import com.dgsoft.common.system.action.BusinessDefineHome;
import com.dgsoft.common.system.model.BusinessNeedFile;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessFile;
import com.dgsoft.house.owner.model.RecordStore;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by cooper on 4/21/16.
 */
@Name("multiVolumeRecord")
@Scope(ScopeType.CONVERSATION)
public class MultiVolumeRecord {


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

    @Create
    public void initData(){
        recordStores = ownerBusinessHome.getInstance().getRecordStoreList();
    }

    public void refreshTree(){
        fileTreeData = null;
    }


    @In
    private BusinessDefineHome businessDefineHome;

    @DataModel("saveMultiRecordStores")
    private List<RecordStore> recordStores;

    public RecordStore getSelectRecordStore() {
        return selectRecordStore;
    }

    public List<RecordStore> getRecordStores() {
        return recordStores;
    }

    @DataModelSelection
    private RecordStore selectRecordStore;

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



    public void deleteRecordStore() {
        for (BusinessFile businessFile : selectRecordStore.getBusinessFiles()) {
            businessFile.setRecordStore(null);
        }
        recordStores.remove(selectRecordStore);
        ownerBusinessHome.getInstance().getRecordStores().remove(selectRecordStore);
        fileTreeData = null;
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


    private String checkedString;

    public String getCheckedString() {
        return checkedString;
    }

    public void setCheckedString(String checkedString) {
        this.checkedString = checkedString;
    }

}
