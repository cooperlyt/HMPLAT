package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.common.system.business.BusinessInstance;
import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessHouse;
import com.dgsoft.house.owner.model.HouseBusiness;
import com.dgsoft.house.owner.model.HouseRecord;
import com.dgsoft.house.owner.model.MakeCard;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 15-7-30.
 * 房屋档案
 */
@Name("recordComplete")
public class RecordComplete implements TaskCompleteSubscribeComponent {


    @In
    private OwnerBusinessHome ownerBusinessHome;

    @In(create = true)
    private OwnerEntityLoader ownerEntityLoader;

    @Override
    public void valid() {

    }

    @Override
    public boolean isPass() {
        return true;
    }

    private void recordHouse(BusinessHouse house){
        HouseRecord houseRecord = ownerEntityLoader.getEntityManager().find(HouseRecord.class, house.getHouseCode());
        if (houseRecord == null) {
            houseRecord = new HouseRecord(house);
            ownerEntityLoader.getEntityManager().persist(houseRecord);
        } else {
            houseRecord.setBusinessHouse(house);
            ownerEntityLoader.getEntityManager().merge(houseRecord);
        }
    }

    @Override
    public void complete() {

        if (!ownerBusinessHome.getInstance().getType().equals(BusinessInstance.BusinessType.NORMAL_BIZ)) {

            Set<MakeCard> cancelCards = new HashSet<MakeCard>(ownerBusinessHome.getInstance().getSelectBusiness().getMakeCards());

            Set<HouseBusiness> cancelHouse = new HashSet<HouseBusiness>(ownerBusinessHome.getInstance().getSelectBusiness().getHouseBusinesses());

            if (ownerBusinessHome.getInstance().getType().equals(BusinessInstance.BusinessType.MODIFY_BIZ)) {
                for (HouseBusiness old : ownerBusinessHome.getInstance().getSelectBusiness().getHouseBusinesses()) {
                    for (HouseBusiness now : ownerBusinessHome.getInstance().getHouseBusinesses()) {
                        if (old.getHouseCode().equals(now.getHouseCode())) {
                            cancelHouse.remove(old);
                        }
                    }
                }
                for (MakeCard old: ownerBusinessHome.getInstance().getSelectBusiness().getMakeCards()){
                    for(MakeCard now: ownerBusinessHome.getInstance().getMakeCards()){
                        if (old.getId().equals(now.getId())){
                            cancelCards.remove(old);
                        }
                    }
                }
            }

            for(MakeCard card: cancelCards){
                card.setEnable(false);
            }

            for (HouseBusiness houseBusiness : cancelHouse) {
                recordHouse(houseBusiness.getStartBusinessHouse());
            }
        }


        for (MakeCard makeCard: ownerBusinessHome.getInstance().getMakeCards()){
            makeCard.setEnable(true);


        }


        if (!ownerBusinessHome.getInstance().getType().equals(BusinessInstance.BusinessType.CANCEL_BIZ)) {
            for (HouseBusiness houseBusiness : ownerBusinessHome.getInstance().getHouseBusinesses()) {
                recordHouse(houseBusiness.getAfterBusinessHouse());
            }
        }



    }
}
