package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.MoneyBusiness;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2018-04-24.
 * 资金监管撤回，取消 审批阶段修改MoneyBusiness状态
 */
@Name("moneyBusinessCanceldState")
public class MoneyBusinessCanceldState implements TaskCompleteSubscribeComponent {


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
        if (!ownerBusinessHome.getInstance().getMoneyBusinesses().isEmpty()){
                for (MoneyBusiness moneyBusiness:ownerBusinessHome.getInstance().getMoneyBusinesses()){
                    moneyBusiness.setStatus(MoneyBusiness.MoneyBusinessStatus.CANCEL);
                    moneyBusiness.setChecked(true);
            }

        }

    }
}
