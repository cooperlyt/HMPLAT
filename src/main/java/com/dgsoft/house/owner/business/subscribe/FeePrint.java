package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.helper.ExtendsDataCreator;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by cooper on 10/9/15.
 */
@Name("feePrint")
public class FeePrint {

    @In
    private OwnerBusinessHome ownerBusinessHome;

    @In
    private ExtendsDataCreator extendsDataCreator;


    private String printUrl;

    public String getPrintUrl() {
        return printUrl;
    }

    public void preparePrintOwnerFee(){
        printUrl = extendsDataCreator.extendsPrintFee(ownerBusinessHome.getInstance().getId(),
                ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getBusinessHouseOwner().getPersonName(),
                ownerBusinessHome.getInstance().getDefineName(),ownerBusinessHome.getInstance().getFactMoneyInfo());

    }

}
