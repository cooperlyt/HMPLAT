package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.common.system.business.BusinessInstance;
import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.house.HouseInfo;
import com.dgsoft.house.HouseStatus;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.action.OwnerHouseHelper;
import com.dgsoft.house.owner.model.AddHouseStatus;
import com.dgsoft.house.owner.model.HouseBusiness;
import org.apache.poi.ss.formula.functions.Now;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;
import org.jboss.seam.log.Logging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 15-7-28.
 * 添加备案状态,
 */
@Name("addContractsRecord")
public class AddContractsRecord implements TaskCompleteSubscribeComponent {

    @In
    private OwnerBusinessHome ownerBusinessHome;

    @Override
    public void valid() {

    }



    @Override
    public boolean isPass() {
        return true;
    }



    @Override
    public void complete() {



            for (HouseBusiness houseBusiness : ownerBusinessHome.getInstance().getHouseBusinesses()) {

                  houseBusiness.getAddHouseStatuses().add(new AddHouseStatus(HouseStatus.CONTRACTS_RECORD, houseBusiness));

            }







    }
}
