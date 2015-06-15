package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.common.DataFormat;
import com.dgsoft.common.system.business.TaskSubscribeComponent;
import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.*;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.log.Logging;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 15-6-11.
 */

@Name("recordStoreSubscribe")
@Scope(ScopeType.CONVERSATION)
public class RecordStoreSubscribe {

    @In
    private OwnerBusinessHome ownerBusinessHome;

    @In
    private OwnerEntityLoader ownerEntityLoader;

    private String selectHouseBusinessId;

    private String selectRecordStoreId;

    private String oldRecordStoreId;


    public String getSelectHouseBusinessId() {
        return selectHouseBusinessId;
    }

    public void setSelectHouseBusinessId(String selectHouseBusinessId) {
        this.selectHouseBusinessId = selectHouseBusinessId;

    }

    public String getSelectRecordStoreId() {
        return selectRecordStoreId;
    }

    public void setSelectRecordStoreId(String selectRecordStoreId) {
        this.selectRecordStoreId = selectRecordStoreId;
    }

    public String getOldRecordStoreId() {
        return oldRecordStoreId;
    }

    public void setOldRecordStoreId(String oldRecordStoreId) {
        this.oldRecordStoreId = oldRecordStoreId;
    }

    public void addNewStore(){
        HouseBusiness houseBusiness = getSelectHouseBusiness();
        houseBusiness.getRecordStores().add(new RecordStore(UUID.randomUUID().toString().replace("-", "").toUpperCase(), houseBusiness));
    }

    private HouseBusiness getSelectHouseBusiness(){
        for(HouseBusiness houseBusiness : ownerBusinessHome.getInstance().getHouseBusinesses()) {
            if (houseBusiness.getId().equals(selectHouseBusinessId)) {
                return houseBusiness;
            }
        }
        return null;
    }

    public List<RecordStore> getExistsStore(String houseCode){
       return ownerEntityLoader.getEntityManager().createQuery("select recordStore from RecordStore recordStore where recordStore.houseRecord.houseCode = :houseCode order by recordStore.houseBusiness.ownerBusiness.recordTime desc",RecordStore.class).setParameter("houseCode",houseCode).getResultList();
    }


    public void putToExistsStore(){
        for(RecordStore store: getSelectHouseBusiness().getRecordStores()){
            if (store.getId().equals(selectRecordStoreId)){
                RecordStore oldStore = ownerEntityLoader.getEntityManager().find(RecordStore.class,oldRecordStoreId);
                store.setFrame(oldStore.getFrame());
                store.setCabinet(oldStore.getCabinet());
                store.setBox(oldStore.getBox());
            }
        }
    }

    @Create
    public void initSubscribe() {
        for (HouseBusiness houseBusiness : ownerBusinessHome.getInstance().getHouseBusinesses()) {
            if (houseBusiness.getRecordStores().isEmpty()) {
                houseBusiness.getRecordStores().add(new RecordStore(UUID.randomUUID().toString().replace("-", "").toUpperCase(),houseBusiness));
            }
        }

    }


}
