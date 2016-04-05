package com.dgsoft.house.owner.business;

import com.dgsoft.common.OrderBeanComparator;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessFile;
import com.dgsoft.house.owner.model.UploadFile;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by cooper on 3/29/16.
 */
@Name("businessFileView")
public class BusinessFileView {

    @In
    private OwnerBusinessHome ownerBusinessHome;


    private String activeId;

    public String getActiveId() {
        return activeId;
    }

    public void setActiveId(String activeId) {

        if (!(activeId == null && this.activeId == null) || ((activeId != null) && !activeId.equals(this.activeId))){
            activeFiles = null;
        }
        this.activeId = activeId;
    }


    private List<BusinessFile> fileList;

    public List<BusinessFile> getFileList() {
        if (fileList == null){
            fileList = new ArrayList<BusinessFile>();
            for(BusinessFile businessFile: ownerBusinessHome.getInstance().getUploadFileses()){
                if (businessFile.isNoFile() || !businessFile.getUploadFiles().isEmpty()){
                    fileList.add(businessFile);
                }
            }
            Collections.sort(fileList, OrderBeanComparator.getInstance());
        }
        return fileList;
    }

    public List<UploadFile> activeFiles;

    public List<UploadFile> getActiveFiles() {
        if (activeFiles == null){
            activeFiles = new ArrayList<UploadFile>();
            if (activeId == null || activeId.trim().equals("")){
                for(BusinessFile file: getFileList()){
                    activeFiles.addAll(file.getUploadFileList());
                }
            }else{
                for(BusinessFile file: getFileList()){
                    if (activeId.equals(file.getId()))
                        activeFiles.addAll(file.getUploadFileList());
                }
            }
        }
        return activeFiles;
    }
}
