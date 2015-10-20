package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.helper.ExtendsDataCreator;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2015-10-19.
 * 在建工程抵押登记
 */
@Name("projectMortgagePrint")
public class ProjectMortgagePrint {
    @In
    private OwnerBusinessHome ownerBusinessHome;

    @In
    private ExtendsDataCreator extendsDataCreator;


    private String printUrl;

    public String getPrintUrl() {
        return printUrl;
    }


    public void preparePrintOwnerFee(){
        printUrl = extendsDataCreator.extendsPrintProjectMortgage(ownerBusinessHome.getInstance().getMakeCards().iterator().next(),ownerBusinessHome.getInstance());
    }
}
