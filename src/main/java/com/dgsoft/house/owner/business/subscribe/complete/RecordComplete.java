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
public class RecordComplete extends HouseRecordCompleteBase {


    @Override
    protected String getSearchKey(HouseBusiness houseBusiness) {
        KeyGeneratorHelper key = OwnerHouseHelper.genHouseSearchKey(houseBusiness.getAfterBusinessHouse());
        return key.getKey();
    }

    @Override
    protected String getDisplay(HouseBusiness houseBusiness) {

        return OwnerHouseHelper.genHouseDisplay(houseBusiness.getAfterBusinessHouse());
    }
}
