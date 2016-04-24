package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessFile;
import com.dgsoft.house.owner.model.HouseBusiness;
import com.dgsoft.house.owner.model.RecordStore;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;
import org.jboss.seam.international.StatusMessage;
import org.jboss.seam.log.Logging;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cooper on 4/21/16.
 */
@Name("multiHouseChoiceRecordStore")
public class MultiHouseChoiceRecordStore extends ChoiceRecordStore{

    @In
    private OwnerBusinessHome ownerBusinessHome;

    @DataModel("recordStoreHouseList")
    private List<HouseBusiness> houseBusinesses;


    @DataModelSelection
    private HouseBusiness selectHouseBusiness;

    private RecordStore selectAssignRecordStore;



    public void initHouseBusiness(){
        Logging.getLog(getClass()).debug("call init house business, house count:" + ownerBusinessHome.getInstance().getHouseBusinessList().size());

        selectAssignRecordStore = multiVolumeRecord.getSelectRecordStore();
        houseBusinesses = new ArrayList<HouseBusiness>();
        for (HouseBusiness houseBusiness: ownerBusinessHome.getInstance().getHouseBusinessList()){

            boolean exists = false;
            for(RecordStore recordStore: multiVolumeRecord.getRecordStores()){
                if (recordStore.getHouseBusinesses().contains(houseBusiness)){
                    exists = true;
                    break;
                }
            }
            if (!exists){
                houseBusinesses.add(houseBusiness);
            }
        }
    }



    public void assignHouse(){
        selectAssignRecordStore.getHouseBusinesses().clear();
        selectAssignRecordStore.getHouseBusinesses().add(selectHouseBusiness);
    }

    private boolean isAssignHouse(){
        for (RecordStore recordStore:  multiVolumeRecord.getRecordStores()){
            if (recordStore.getHouseBusinesses().isEmpty()){
                return false;
            }
        }
        return true;
    }


    @Override
    public void validSubscribe() {
        super.validSubscribe();
        if (!isAssignHouse()){
            facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR, "recordFileNotAssignHouse");
        }

    }

    @Override
    public boolean isPass() {
        return super.isPass() && isAssignHouse();
    }



    @Override
    public boolean saveSubscribe() {

        if (isAssignHouse()){
            return super.saveSubscribe();
        }else {
            facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR, "recordFileNotAssignHouse");
        }
        return false;
    }



}
