package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.common.system.PersonEntity;
import com.dgsoft.common.system.PersonHelper;
import com.dgsoft.common.system.RunParam;
import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.*;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Logging;

import javax.faces.event.ValueChangeEvent;
import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-10-14
 * Time: 下午4:55
 * To change this template use File | Settings | File Templates.
 */

//TODO remove this
@Name("financialSubscribe")
public class FinancialSubscribe extends FinancialBaseSubscribe {

    @Override
    protected void addMortgage() {
        //单房屋抵押人添加到抵押登记信息
        if (ownerBusinessHome.getInstance().getHouseBusinesses().size() == 1) {
            if (ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getBusinessHouseOwner() != null) {
                mortgaegeRegiste.setBusinessHouseOwner(ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getBusinessHouseOwner());
                mortgaegeRegiste.getBusinessHouseOwner();
            }

      }


    }
}
