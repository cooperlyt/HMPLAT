package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.helper.ExtendsDataCreator;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2015-10-20.
 * 预售许可证存根打印
 */
@Name("projectRshipStubPrint")
public class ProjectRshipStubPrint {

    @In
    private OwnerBusinessHome ownerBusinessHome;

    @In
    private ExtendsDataCreator extendsDataCreator;


    private String printUrl;

    public String getPrintUrl() {
        return printUrl;
    }



    public void preparePrintOwnerFee(){
        printUrl = extendsDataCreator.extendsPrintProjectRshipStub(ownerBusinessHome.getInstance(),ownerBusinessHome.getCardByType("PROJECT_RSHIP"));
    }
}
