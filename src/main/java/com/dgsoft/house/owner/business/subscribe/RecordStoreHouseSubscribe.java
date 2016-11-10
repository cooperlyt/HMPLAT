package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.common.system.business.TaskSubscribeComponent;
import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessFile;
import com.dgsoft.house.owner.model.HouseBusiness;
import com.dgsoft.house.owner.model.RecordStore;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Logging;

import java.util.UUID;

/**
 * Created by wxy on 2015-09-16.
 *
 */
@Name("recordStoreHouseSubscribe")
@Scope(ScopeType.CONVERSATION)
public class RecordStoreHouseSubscribe implements TaskSubscribeComponent {

    @In
    private OwnerBusinessHome ownerBusinessHome;

    private RecordStore recordStore;

    @Create
    public void create(){
        if (ownerBusinessHome.getInstance().getRecordStores().size() > 1){
            ownerBusinessHome.getInstance().getRecordStores().clear();
        }

        if (ownerBusinessHome.getInstance().getRecordStores().isEmpty()){
            recordStore = new RecordStore(ownerBusinessHome.getInstance());
        }else {
            recordStore = ownerBusinessHome.getInstance().getRecordStores().iterator().next();
        }
    }

    public RecordStore getRecordStore() {
        return recordStore;
    }

    public void setRecordStore(RecordStore recordStore) {
        this.recordStore = recordStore;
    }

    @Override
    public void initSubscribe() {

    }

    @Override
    public void validSubscribe() {

    }

    @Override
    public boolean isPass() {
        return true;
    }

    @Override
    public boolean saveSubscribe() {
        if (ownerBusinessHome.getInstance().getRecordStores().isEmpty()){
            ownerBusinessHome.getInstance().getRecordStores().add(recordStore);
        }

        for(BusinessFile businessFile: ownerBusinessHome.getInstance().getUploadFileses()){
            businessFile.setRecordStore(recordStore);
        }

        for(HouseBusiness houseBusiness: ownerBusinessHome.getInstance().getHouseBusinesses()){
            recordStore.getHouseBusinesses().add(houseBusiness);
            houseBusiness.getRecordStores().clear();
            houseBusiness.getRecordStores().add(recordStore);
        }

        return true;
    }
}
