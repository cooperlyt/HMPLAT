package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.common.system.business.TaskSubscribeComponent;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.Financial;
import com.dgsoft.house.owner.model.MakeCard;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Logging;

import java.util.EnumSet;

/**
 * Created by Administrator on 15-6-4.
 */
@Name("otherPowerCardSingleComplete")
public class OtherPowerCardSingleComplete implements TaskCompleteSubscribeComponent {


    @In
    private OwnerBusinessHome ownerBusinessHome;



    @Override
    public TaskSubscribeComponent.ValidResult valid() {

        return TaskSubscribeComponent.ValidResult.SUCCESS;
    }

    @Override
    public void complete() {



    }
}



