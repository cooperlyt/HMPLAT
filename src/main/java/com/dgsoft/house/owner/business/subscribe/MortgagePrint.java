package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.helper.ExtendsDataCreator;
import com.dgsoft.house.owner.model.BusinessPool;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2015-10-19.
 * 他项权证打印
 */
@Name("mortgagePrint")
public class MortgagePrint {
    @In
    private OwnerBusinessHome ownerBusinessHome;

    @In
    private ExtendsDataCreator extendsDataCreator;


    private String printUrl;

    public String getPrintUrl() {
        return printUrl;
    }


    public void preparePrintOwnerFee(){

        printUrl = extendsDataCreator.extendsPrintMortgageCard(ownerBusinessHome.getInstance().getSingleHoues().getAfterBusinessHouse(),
                ownerBusinessHome.getCardByType("MORTGAGE_CARD"),ownerBusinessHome.getInstance());
    }
}
