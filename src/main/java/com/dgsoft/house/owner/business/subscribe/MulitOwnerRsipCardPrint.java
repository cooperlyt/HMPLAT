package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.common.system.AuthenticationInfo;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.helper.ExtendsDataCreator;
import com.dgsoft.house.owner.model.BusinessPool;
import com.dgsoft.house.owner.model.CardInfo;
import com.dgsoft.house.owner.model.MakeCard;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.framework.EntityHome;

import java.util.Date;

/**
 * Created by wxy on 2015-12-21.
 * 多房屋权证打印，商品房初始登记
 */
@Name("mulitOwnerRsipCardPrint")
@Scope(ScopeType.CONVERSATION)
public class MulitOwnerRsipCardPrint extends EntityHome<MakeCard>{




    @In
    private OwnerBusinessHome ownerBusinessHome;

    @In
    private ExtendsDataCreator extendsDataCreator;


    private String printUrl;

    public String getPrintUrl() {
        return printUrl;
    }



    @Override
    protected void initInstance(){
        super.initInstance();
        cardInfo = null;
    }

    private CardInfo cardInfo;

    @In
    private AuthenticationInfo authInfo;

    public CardInfo getCardInfo() {
        if (cardInfo == null){
            if(getInstance().getCardInfo() != null){
                cardInfo = getInstance().getCardInfo();
            }else{
                cardInfo = new CardInfo();
            }
        }
        return cardInfo;
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

    public void saveAndPrint(){

        getCardInfo().setMakeCard(getInstance());
        getInstance().setCardInfo(cardInfo);

        cardInfo.setPrintTime(new Date());
        cardInfo.setMakeEmpCode(authInfo.getLoginEmployee().getId());
        cardInfo.setMakeEmpName(authInfo.getLoginEmployee().getPersonName());
        update();
        printUrl = extendsDataCreator.extendsPrintOwnerRsip(ownerBusinessHome.getInstance().getSingleHoues().getAfterBusinessHouse(),
                ownerBusinessHome.getInstance().getMakeCards().iterator().next());
    }
}

