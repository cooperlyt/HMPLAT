package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.common.system.business.BusinessInstance;
import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.house.HouseStatus;
import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.action.OwnerHouseHelper;
import com.dgsoft.house.owner.model.*;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Logging;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
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

    @Override
    public void complete() {
        for (MakeCard makeCard: ownerBusinessHome.getInstance().getMakeCards()){
            makeCard.setEnable(true);
        }

        if (!ownerBusinessHome.getInstance().getType().equals(BusinessInstance.BusinessType.NORMAL_BIZ)) {
            if (ownerBusinessHome.getInstance().getSelectBusiness() == null){
                throw new IllegalArgumentException("Modify or Cancel Business not have selectBiz");
            }

            ownerBusinessHome.getInstance().getSelectBusiness().setStatus(BusinessInstance.BusinessStatus.CANCEL);

            Set<MakeCard> cancelCards = new HashSet<MakeCard>(ownerBusinessHome.getInstance().getSelectBusiness().getMakeCards());

            Set<HouseBusiness> cancelHouse = new HashSet<HouseBusiness>(ownerBusinessHome.getInstance().getSelectBusiness().getHouseBusinesses());

            if (ownerBusinessHome.getInstance().getType().equals(BusinessInstance.BusinessType.MODIFY_BIZ)) {
                for (HouseBusiness old : ownerBusinessHome.getInstance().getSelectBusiness().getHouseBusinesses()) {
                    for (HouseBusiness now : ownerBusinessHome.getInstance().getHouseBusinesses()) {
                        if (old.getHouseCode().equals(now.getHouseCode())) {
                            cancelHouse.remove(old);
                            HouseRecord houseRecord = ownerEntityLoader.getEntityManager().find(HouseRecord.class, now.getHouseCode());
                            if (houseRecord != null){
                                houseRecord.setBusinessHouse(now.getAfterBusinessHouse());
                            }else{
                                Logging.getLog(getClass()).warn("MODIFY_BIZ select biz not have houseRecord. ");
                            }
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


                List<HouseStatus> oldStatus = OwnerHouseHelper.instance().getHouseAllStatus(houseBusiness.getHouseCode());
                for (HouseBusiness old : ownerBusinessHome.getInstance().getSelectBusiness().getHouseBusinesses()) {
                    if (old.getHouseCode().equals(houseBusiness.getHouseCode())){
                        for(AddHouseStatus addHouseStatus: old.getAddHouseStatuses()){
                            if (addHouseStatus.isRemove()){
                                oldStatus.add(addHouseStatus.getStatus());
                            }else{
                                oldStatus.remove(addHouseStatus.getStatus());
                            }
                        }
                    }
                }

                HouseStatus lastStatus;
                if (oldStatus.isEmpty()){
                    lastStatus = null;
                }else{
                    Collections.sort(oldStatus, HouseStatus.StatusComparator.getInstance());
                    lastStatus = oldStatus.get(0);
                }

                HouseRecord houseRecord = ownerEntityLoader.getEntityManager().find(HouseRecord.class, houseBusiness.getHouseCode());
                if (houseRecord != null){
                    houseRecord.setBusinessHouse(houseBusiness.getStartBusinessHouse());
                    houseRecord.setHouseStatus(lastStatus);
                }else{
                    Logging.getLog(getClass()).warn("MODIFY_BIZ select biz not have houseRecord. ");
                }
            }


        } else {
            for (HouseBusiness houseBusiness : ownerBusinessHome.getInstance().getHouseBusinesses()) {

                List<HouseStatus> oldStatus = OwnerHouseHelper.instance().getHouseAllStatus(houseBusiness.getHouseCode());


                for (AddHouseStatus addHouseStatus: houseBusiness.getAddHouseStatuses()){
                    if (addHouseStatus.isRemove()){
                        oldStatus.remove(addHouseStatus.getStatus());
                        Logging.getLog(getClass()).debug("remove status" + addHouseStatus.getStatus());
                    }else{
                        oldStatus.add(addHouseStatus.getStatus());
                        Logging.getLog(getClass()).debug("add status" + addHouseStatus.getStatus());
                    }
                }



                HouseStatus lastStatus;
                if (oldStatus.isEmpty()){
                    lastStatus = null;
                }else{
                    Collections.sort(oldStatus, HouseStatus.StatusComparator.getInstance());
                    lastStatus = oldStatus.get(0);
                }

                Logging.getLog(getClass()).debug("last status" + lastStatus);
                BusinessHouse house = houseBusiness.getAfterBusinessHouse();
                HouseRecord houseRecord = ownerEntityLoader.getEntityManager().find(HouseRecord.class, house.getHouseCode());
                if (houseRecord == null) {
                    houseRecord = new HouseRecord(house,lastStatus);

                    //ownerEntityLoader.getEntityManager().persist(houseRecord);
                } else {
                    houseRecord.setBusinessHouse(house);
                    houseRecord.setHouseStatus(lastStatus);
                    //ownerEntityLoader.getEntityManager().merge(houseRecord);
                }
                house.setHouseRecord(houseRecord);

            }
        }



    }
}
