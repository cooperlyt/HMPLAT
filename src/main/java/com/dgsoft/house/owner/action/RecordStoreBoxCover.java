package com.dgsoft.house.owner.action;

import com.dgsoft.house.owner.model.BusinessFile;
import com.dgsoft.house.owner.model.RecordStore;
import com.dgsoft.house.owner.model.UploadFile;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by cooper on 4/22/16.
 */
@Name("recordStoreBoxCover")
public class RecordStoreBoxCover {

    @In(create = true)
    private EntityManager ownerEntityManager;

    @In
    private RecordRoomMgr recordRoomMgr;

    private List<RecordStore> result;


    public List<RecordStore> getRecordStoreList(){
        if (result == null){
            if (recordRoomMgr.getFrame() == null || recordRoomMgr.getFrame().trim().equals("")
                    || recordRoomMgr.getBox() == null || recordRoomMgr.getBox().trim().equals("")
                    || recordRoomMgr.getCabinet() == null || recordRoomMgr.getCabinet().trim().equals("") ){
                result = new ArrayList<RecordStore>(0);
            }else{

                result = ownerEntityManager.createQuery("select distinct recordStore from RecordStore recordStore left join recordStore.businessFiles bf " +
                        "where bf.recordLocal.frame = :frame and bf.recordLocal.cabinet = :cabinet and bf.recordLocal.box = :box",RecordStore.class)
                        .setParameter("cabinet",recordRoomMgr.getCabinet()).setParameter("frame",recordRoomMgr.getFrame()).setParameter("box",recordRoomMgr.getBox())
                        .getResultList();
            }

        }
        return result;

    }

    public List<UploadFile> getAllPicture(){
        List<UploadFile> result = new ArrayList<UploadFile>();
        for (RecordStore recordStore: getRecordStoreList()){

            for(BusinessFile businessFile: recordStore.getBusinessShowFileList()){
                result.addAll(businessFile.getUploadFileList());
            }
        }
        return result;
    }

}
