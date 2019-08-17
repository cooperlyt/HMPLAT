package com.dgsoft.house.owner.business;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2019-08-17.
 */
@Name("moneyBackBusinessCreate")
public class MoneyBackBusinessCreate extends OwnerBusinessCreateComponent {

    private static final String NORMAL_BIZ_BEGIN_PAGE = "/business/houseOwner/MoneyBackBusinessPick.xhtml";

    //档案补录
    private static final String PATCH_BIZ_BEGIN_PAGE = "";


    @In(create = true)
    private MoneyBackBusinessStart moneyBackBusinessStart;

    @Override
    protected String getNormalBusinessPage() {
        moneyBackBusinessStart.setSingleHouse(true);
        return NORMAL_BIZ_BEGIN_PAGE;
    }

    @Override
    protected String getPatchBusinessPage() {
        return PATCH_BIZ_BEGIN_PAGE;
    }


}
