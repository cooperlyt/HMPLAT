package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.common.system.business.TaskSubscribeComponent;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by Administrator on 15-6-4.
 */
@Name("otherPowerCardComplete")
public class OtherPowerCardComplete implements TaskCompleteSubscribeComponent {


    @In
    private OwnerBusinessHome ownerBusinessHome;



    @Override
    public TaskSubscribeComponent.ValidResult valid() {
        if (ownerBusinessHome.getInstance().getFinancials().isEmpty()){
            return TaskSubscribeComponent.ValidResult.WARN;
        }
        return TaskSubscribeComponent.ValidResult.SUCCESS;
    }

    @Override
    public void complete() {
        if(!ownerBusinessHome.getInstance().getFinancials().isEmpty()){



        }

    }
}
