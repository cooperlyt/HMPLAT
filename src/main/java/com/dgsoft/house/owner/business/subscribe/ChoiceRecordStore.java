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

    @In
    protected FacesMessages facesMessages;

    @In
    private OwnerBusinessHome ownerBusinessHome;

    @In(create = true)
    protected MultiVolumeRecord multiVolumeRecord;


    @Override
    public void initSubscribe() {
        multiVolumeRecord.refreshTree();
    }



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



    @Override
    public boolean saveSubscribe() {

        if (taskOwnerBusinessFile.isPass() && isAllAssign()) {
            for(RecordStore recordStore: multiVolumeRecord.getRecordStores()){
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

    private boolean isAllAssign() {

        for (BusinessFile businessFile : ownerBusinessHome.getInstance().getVaidBusinessFileList()) {
            boolean exists = false;
            for (RecordStore recordStore : multiVolumeRecord.getRecordStores()) {
                for (BusinessFile assignFile : recordStore.getBusinessShowFileList()) {
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


}
