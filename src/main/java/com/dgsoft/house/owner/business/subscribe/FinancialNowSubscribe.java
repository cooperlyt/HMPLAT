package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.model.Financial;
import org.jboss.seam.annotations.Name;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-10-14
 * Time: 下午5:28
 * To change this template use File | Settings | File Templates.
 */
@Name("financialNowSubscribe")
public class FinancialNowSubscribe extends BaseFinancialSubscribe {

    @Override
    protected Financial.FinancialUseType getType() {
        return Financial.FinancialUseType.NOW;
    }
}
