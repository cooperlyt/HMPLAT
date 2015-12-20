package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.HouseBusiness;
import com.dgsoft.house.owner.model.LandInfo;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2015-12-20.
 * 多房屋土地信息添加 商品房初始登记
 */
@Name("mulitBusinessLandInfoSubscribe")
public class MulitBusinessLandInfoSubscribe extends OwnerEntityHome<LandInfo> {

    @In
    private OwnerBusinessHome ownerBusinessHome;

    private boolean have;

    public boolean isHave() {
        return have;
    }

    public void setHave(boolean have) {
        this.have = have;
    }


    @Override
    public void create(){
        super.create();
        if (ownerBusinessHome.getInstance().getHouseBusinesses().iterator().next().getAfterBusinessHouse().getLandInfo()!=null) {
            have = true;
            setId(ownerBusinessHome.getInstance().getHouseBusinesses().iterator().next().getAfterBusinessHouse().getLandInfo().getId());
        } else {
            have = false;
        }
    }

    @Override
    public LandInfo createInstance(){
        LandInfo result = new LandInfo();
//        ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().setLandInfo(result);
        for (HouseBusiness houseBusiness:ownerBusinessHome.getInstance().getHouseBusinesses()){
            houseBusiness.getAfterBusinessHouse().setLandInfo(result);
        }
        return result;
    }

    public void checkHave(){
        clearInstance();
        if (have){
//            ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().setLandInfo(getInstance());
            for (HouseBusiness houseBusiness:ownerBusinessHome.getInstance().getHouseBusinesses()){
                houseBusiness.getAfterBusinessHouse().setLandInfo(getInstance());
            }
        } else {
           // ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().setLandInfo(null);
            for (HouseBusiness houseBusiness:ownerBusinessHome.getInstance().getHouseBusinesses()){
                houseBusiness.getAfterBusinessHouse().setLandInfo(null);
            }
        }
    }


}
