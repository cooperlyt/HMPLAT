package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.LandInfo;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-9-23
 * Time: 下午4:28
 * To change this template use File | Settings | File Templates.
 */
@Name("businessNewLandInfoSubscribe")
public class BusinessNewLandInfoSubscribe extends BaseBusinessLandInfoSubscribe {
    @Override
    protected LandInfo.LandInfoType getType() {
        return LandInfo.LandInfoType.NEW_LAND_INFO;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
