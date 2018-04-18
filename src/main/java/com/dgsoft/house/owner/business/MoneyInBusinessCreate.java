package com.dgsoft.house.owner.business;

import com.dgsoft.house.owner.business.subscribe.PeopleRegEmpSubscribe;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2018-04-16.
 * 存量房资金监管变更（撤销） 启动
 */
@Name("moneyInBusinessCreate")
public class MoneyInBusinessCreate extends OwnerBusinessCreateComponent {

    private static final String MONEY_BUSINESS_START_PAGE = "/business/houseOwner/MoneyInBusinessPick.xhtml";

    private static final String MONEY_BUSINESS_PATCH_PAGE = "";


    @Override
    protected String getNormalBusinessPage() {

        return MONEY_BUSINESS_START_PAGE;
    }

    @Override
    protected String getPatchBusinessPage() {

        return MONEY_BUSINESS_PATCH_PAGE;
    }


}
