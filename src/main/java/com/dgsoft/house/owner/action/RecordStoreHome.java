package com.dgsoft.house.owner.action;

import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.model.BusinessFile;
import com.dgsoft.house.owner.model.RecordStore;
import com.dgsoft.house.owner.model.UploadFile;
import org.jboss.seam.annotations.Name;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cooper on 4/16/16.
 */
@Name("recordStoreHome")
public class RecordStoreHome extends OwnerEntityHome<RecordStore> {


    public List<UploadFile> getAllPictureList(){
        List<UploadFile> result = new ArrayList<UploadFile>();
        for(BusinessFile businessFile: getInstance().getBusinessShowFileList()){
            result.addAll(businessFile.getUploadFileList());
        }
        return result;
    }
}
