package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.MakeCard;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2015-11-10.
 * 缮证完成后将相关权证生效
 */
@Name("makeCardEnable")
public class MakeCardEnable implements TaskCompleteSubscribeComponent {


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
      if (!ownerBusinessHome.getInstance().getMakeCards().isEmpty()) {
          for (MakeCard m : ownerBusinessHome.getInstance().getMakeCards()) {
              m.setEnable(true);
          }
      }
    }
}
