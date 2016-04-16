package com.dgsoft.house.owner.action;

import com.dgsoft.house.owner.model.BusinessFile;
import com.dgsoft.house.owner.model.RecordLocal;
import com.dgsoft.house.owner.model.RecordStore;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;
import org.jboss.seam.log.Logging;
import org.json.JSONArray;
import org.json.JSONException;

import javax.persistence.EntityManager;

/**
 * Created by cooper on 4/16/16.
 */
@Name("recordStorePut")
public class RecordStorePut {

   private String frame;

    private String cabinet;

    private String box;

    private String selectRecord;

    @In(create = true)
    private RecordStoreList recordStoreList;

    @In(create = true)
    private EntityManager ownerEntityManager;

    @In
    private FacesMessages facesMessages;

    public String getSelectRecord() {
        return selectRecord;
    }

    public void setSelectRecord(String selectRecord) {
        this.selectRecord = selectRecord;
    }

    public String getFrame() {
        return frame;
    }

    public void setFrame(String frame) {
        this.frame = frame;
    }

    public String getCabinet() {
        return cabinet;
    }

    public void setCabinet(String cabinet) {
        this.cabinet = cabinet;
    }

    public String getBox() {
        return box;
    }

    public void setBox(String box) {
        this.box = box;
    }

    @Transactional
    public void put(){
        Logging.getLog(getClass()).debug("put data: " + selectRecord);
        try {
            JSONArray select = new JSONArray(selectRecord);

            if (select.length() <= 0){
                facesMessages.addFromResourceBundle(StatusMessage.Severity.WARN,"record.put.noselect.error");
                return;
            }

            for(int i=0; i<select.length() ;i++){
                RecordStore store = ownerEntityManager.find(RecordStore.class, select.get(i));
                if (store == null){
                    throw new IllegalArgumentException("recordStore not found:" + select.get(i));
                }
                int index = 1;
                for(BusinessFile businessFile: store.getBusinessFiles()){
                    RecordLocal local = new RecordLocal(frame,cabinet,box,store.getRecordCode() + "-" + index,businessFile);
                    ownerEntityManager.persist(local);
                    index++;
                }
                store.setInRoom(true);

            }
        } catch (JSONException e) {
            facesMessages.addFromResourceBundle(StatusMessage.Severity.WARN,"record.put.noselect.error");
            return;
        }

        ownerEntityManager.flush();
        facesMessages.addFromResourceBundle(StatusMessage.Severity.INFO,"record.put.success");
        recordStoreList.refresh();
        Logging.getLog(getClass()).debug("page: " + recordStoreList.getPage() + ";count:" + recordStoreList.getPageCount());
        if (recordStoreList.getPage().compareTo(new Long(recordStoreList.getPageCount())) > 0  ){
            recordStoreList.last();
        }
        frame= null;
        cabinet = null;
        box = null;
        selectRecord = null;
    }
}
