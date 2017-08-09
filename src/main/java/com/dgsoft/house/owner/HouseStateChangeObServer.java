package com.dgsoft.house.owner;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.log.Logging;

/**
 * Created by cooper on 08/08/2017.
 */
@Name("houseStateChangeObServer")
public class HouseStateChangeObServer {

    @Observer("cc.coopersoft.house.HouseStateChanged")
    public void houseStateChanged(String houseCode){
        //TODO update out change
        Logging.getLog(getClass()).debug("houseStateChanged :" + houseCode);
    }
}
