package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.common.system.RunParam;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.helper.ExtendsDataCreator;
import com.dgsoft.house.owner.model.BusinessPool;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2015-10-20.
 * 预告登记证明打印 非抵押
 */
@Name("noticePrint")
public class NoticePrint {

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
            Integer poolType = RunParam.instance().getIntParamValue("PoolInfoPrint");
            if (poolType==1){
                str="所有权人:"+ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getBusinessHouseOwner().getPersonName();
                for (BusinessPool businessPool : ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getBusinessPools()) {
                    str=str+" 共有权人"+businessPool.getPersonName()+" 身份证明号: "+businessPool.getCredentialsNumber();
                }

            }


            if (poolType==2){
                String poolStr="";
                for (BusinessPool businessPool : ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getBusinessPools()) {
                    poolStr = poolStr + businessPool.getPersonName()+"  ";
                }
                str = "共有权人: " + poolStr;
            }

            if (poolType==3){
                str="所有权人:"+ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getBusinessHouseOwner().getPersonName() +" 身份证明号: "
                + ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getBusinessHouseOwner().getCredentialsNumber();
                for (BusinessPool businessPool : ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getBusinessPools()) {
                    str=str+" "+businessPool.getPersonName()+" 身份证明号: "+businessPool.getCredentialsNumber();
                }

            }

        }
        return str;
    }

    public void preparePrintOwnerFee(){
        printUrl = extendsDataCreator.extendsPrintNotice(ownerBusinessHome.getInstance().getSingleHoues().getAfterBusinessHouse(),
                ownerBusinessHome.getCardByType("NOTICE"),ownerBusinessHome.getInstance(),getPoolInfo());
    }
}
