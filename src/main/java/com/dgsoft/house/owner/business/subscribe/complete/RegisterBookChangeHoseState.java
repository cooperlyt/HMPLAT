package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.common.system.business.TaskSubscribeComponent;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.MoneyBusiness;
import com.dgsoft.house.owner.model.OwnerBusiness;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by Administrator on 15-5-25.
 */
@Name("registerBookChangeHoseState")

public class RegisterBookChangeHoseState implements TaskCompleteSubscribeComponent {

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
        ownerBusinessHome.getInstance().setRecorded(true);
        if (!ownerBusinessHome.getInstance().getMoneyBusinesses().isEmpty()) {
            for (MoneyBusiness mb : ownerBusinessHome.getInstance().getMoneyBusinesses()) {
                mb.setChecked(true);
            }
        }

    }


}
