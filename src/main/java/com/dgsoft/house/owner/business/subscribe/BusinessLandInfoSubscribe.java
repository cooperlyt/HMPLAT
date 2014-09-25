package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessLandInfo;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-9-23
 * Time: 下午4:28
 * To change this template use File | Settings | File Templates.
 */
@Name("businessLandInfoSubscribe")
public class BusinessLandInfoSubscribe extends OwnerEntityHome<BusinessLandInfo> {
    @In
    private OwnerBusinessHome ownerBusinessHome;

    private boolean have;

    public boolean isHave() {
        return have;
    }

    public void setHave(boolean have) {
        this.have = have;
    }


    private String seachByDB(){
        if (ownerBusinessHome.getSingleHoues().getBusinessLandInfo()!=null){
            return ownerBusinessHome.getSingleHoues().getBusinessLandInfo().getId();
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
            getInstance().setBusinessHouse(ownerBusinessHome.getSingleHoues());
            ownerBusinessHome.getSingleHoues().setBusinessLandInfo(getInstance());
        } else {
            ownerBusinessHome.getSingleHoues().setBusinessLandInfo(null);
            clearInstance();
        }
    }

}
