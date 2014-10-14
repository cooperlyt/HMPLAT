package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessPersion;
import com.dgsoft.house.owner.model.LandInfo;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-10-14
 * Time: 上午11:48
 * To change this template use File | Settings | File Templates.
 */

public abstract class BaseBusinessLandInfoSubscribe extends OwnerEntityHome<LandInfo> {
    @In
    private OwnerBusinessHome ownerBusinessHome;

    private boolean have;

    public boolean isHave() {
        return have;
    }

    public void setHave(boolean have) {
        this.have = have;
    }



   protected abstract LandInfo.LandInfoType getType();


    @Override
    public Class<LandInfo> getEntityClass() {
        return LandInfo.class;
    }

    @Override
    public LandInfo createInstance(){
        return new LandInfo(getType());
    }

    private String seachByDB(){
        if (ownerBusinessHome.getSingleHoues().getLandInfos() !=null){
            for(LandInfo landInfo:ownerBusinessHome.getSingleHoues().getLandInfos()){
                if (landInfo.getType().equals(getType())){
                    return landInfo.getId();
                }
            }
        }
        return null;
    }

    @Override
    public void create(){
        super.create();
        String findId = seachByDB();
        if (findId != null) {
            have = true;
            setId(findId);
        } else {
            have = false;
        }
    }

    public void checkHave(){
        if (have){
            String findId = seachByDB();
            if (findId != null) {
                setId(findId);
            }
            getInstance().setHouseBusiness(ownerBusinessHome.getSingleHoues());
            ownerBusinessHome.getSingleHoues().getLandInfos().add(getInstance());
        } else {
            for (LandInfo landInfo:ownerBusinessHome.getSingleHoues().getLandInfos()) {
                if (landInfo.getType().equals(getType())) {
                    ownerBusinessHome.getSingleHoues().getLandInfos().remove(landInfo);
                    break;
                }
            }
            clearInstance();
        }
    }
}
