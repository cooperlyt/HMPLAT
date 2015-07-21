package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.common.system.business.TaskSubscribeComponent;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Logging;

/**
 * Created by cooper on 5/18/15.
 */

@Name("writeHouseInfo")
public class WriteHouseInfo implements TaskCompleteSubscribeComponent {

    @Override
    public void valid() {

    }

    @Override
    public boolean isPass() {
        return false;
    }

    @Override
    public void complete() {
        //TODO write houseInfo
        Logging.getLog(getClass()).debug("TODO write HouseInfo");

    }
}
