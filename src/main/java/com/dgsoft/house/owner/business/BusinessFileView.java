package com.dgsoft.house.owner.business;

import com.dgsoft.common.system.AuthenticationInfo;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessFile;
import com.dgsoft.house.owner.model.UploadFile;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Logging;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cooper on 3/29/16.
 */
@Name("businessFileView")
public class BusinessFileView {

    @In
    private OwnerBusinessHome ownerBusinessHome;

    @In
    private AuthenticationInfo authInfo;

    private String createDocGroupName;

    //private String activeId;

    private BusinessFile activeItem;

    private String fileUploadData;

    private String activeName;

    public String getCreateDocGroupName() {
        return createDocGroupName;
    }

    public void setCreateDocGroupName(String createDocGroupName) {
        this.createDocGroupName = createDocGroupName;
    }

    public String getActiveName() {
        return activeName;
    }

    public void setActiveName(String activeName) {
        this.activeName = activeName;
    }

    public String getFileUploadData() {
        return fileUploadData;
    }

    public void setFileUploadData(String fileUploadData) {
        this.fileUploadData = fileUploadData;
    }

    public String getActiveId() {
        if (activeItem == null)
            return null;
        return activeItem.getId();
    }

    public void setActiveId(String activeId) {

        if (!(activeId == null && this.activeItem == null) || ((activeItem != null) && !activeItem.getId().equals(activeId))){
            activeFiles = null;
        }
        if (activeId == null || activeId.trim().equals("")){
            activeItem = null;
        }else{
            for(BusinessFile file: getFileList()){
                if (activeId.equals(file.getId())) {
                    activeItem = file;
                    //activeName = file.getName();
                }
            }
        }
    }

    public BusinessFile getActiveItem(){
        return activeItem;
    }

    private List<BusinessFile> fileList;

    public List<BusinessFile> getFileList() {
        if (fileList == null){
            fileList = ownerBusinessHome.getInstance().getVaidBusinessFileList();
        }
        return fileList;
    }

    public List<UploadFile> activeFiles;

    public List<UploadFile> getActiveFiles() {
        if (activeFiles == null){
            activeFiles = new ArrayList<UploadFile>();
            if (activeItem == null){
                for(BusinessFile file: getFileList()){
                    activeFiles.addAll(file.getUploadFileList());
                }
            }else{

                activeFiles.addAll(activeItem.getUploadFileList());

            }
        }
        return activeFiles;
    }

    public void addNewDocGroup(){
        if (createDocGroupName != null && !createDocGroupName.trim().equals("")) {
            BusinessFile businessFile = new BusinessFile(ownerBusinessHome.getInstance(), createDocGroupName, 1000 + getFileList().size() + 1);
            businessFile.setType(BusinessFile.DocType.AFTER);
            ownerBusinessHome.getInstance().getUploadFileses().add(businessFile);
            ownerBusinessHome.update();
            fileList = null;
            setActiveId(businessFile.getId());

        }
    }

    public void fileUploaded(){
        try {
            JSONObject jsonObject = new JSONObject(fileUploadData);


            String key = jsonObject.getString("key");

            for (BusinessFile bf: getFileList()){
                if (bf.getId().equals(key)){
                    JSONArray fileIdArray = jsonObject.getJSONArray("files");
                    if (fileIdArray.length() > 0) {
                        setActiveId(bf.getId());
                        for (int i = 0; i < fileIdArray.length(); i++) {
                            JSONObject fileInfo = fileIdArray.getJSONObject(i);

                            bf.getUploadFiles().add(new UploadFile(fileInfo.getString("fid"), authInfo.getLoginEmployee().getPersonName(), authInfo.getLoginEmployee().getId(), fileInfo.getString("md5"), fileInfo.getString("name"), bf, fileInfo.getLong("size")));
                        }
                    }
                    ownerBusinessHome.update();
                }
            }

        } catch (JSONException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void deleteSelectNode(){
        Logging.getLog(getClass()).debug("begin delete : " + ownerBusinessHome.getInstance().getId() + "| :" + getActiveItem().getId());
        ownerBusinessHome.getInstance().getUploadFileses().remove(getActiveItem());
        ownerBusinessHome.update();
        setActiveId(null);
        fileList = null;
    }

    public void changeDocName(){
        getActiveItem().setName(activeName);
        ownerBusinessHome.update();
        fileList = null;
        activeName = null;
    }
}
