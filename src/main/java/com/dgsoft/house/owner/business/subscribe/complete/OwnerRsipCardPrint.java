package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.helper.ExtendsDataCreator;
import com.dgsoft.house.owner.model.BusinessPool;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2015-10-18.
 * 房屋所有权证打印
 */
@Name("ownerRsipCardPrint")
public class OwnerRsipCardPrint {

    @In
    private OwnerBusinessHome ownerBusinessHome;

    @In
    private ExtendsDataCreator extendsDataCreator;


    private String printUrl;

    public String getPrintUrl() {
        return printUrl;
    }

    private String getPoolInfo(){
        String str="";
        if (!ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getBusinessPools().isEmpty()){
            str="所有权人:"+ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getBusinessHouseOwner().getPersonName();
            for (BusinessPool businessPool : ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getBusinessPools()) {
                str=str+businessPool.getPersonName()+"身份证明号:"+businessPool.getCredentialsNumber();
            }

        }
        return str;
    }

    public void preparePrintOwnerFee(){
        printUrl = extendsDataCreator.extendsPrintOwnerRsip(ownerBusinessHome.getInstance().getSingleHoues().getAfterBusinessHouse(),
                ownerBusinessHome.getInstance().getMakeCards().iterator().next(),ownerBusinessHome.getInstance(),getPoolInfo());
    }
}
