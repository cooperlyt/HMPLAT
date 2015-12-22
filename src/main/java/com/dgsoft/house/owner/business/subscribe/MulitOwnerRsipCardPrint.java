package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.common.system.AuthenticationInfo;
import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.helper.ExtendsDataCreator;
import com.dgsoft.house.owner.model.BusinessHouse;
import com.dgsoft.house.owner.model.BusinessPool;
import com.dgsoft.house.owner.model.CardInfo;
import com.dgsoft.house.owner.model.MakeCard;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.framework.EntityHome;
import org.jboss.seam.log.Logging;

import javax.faces.context.FacesContext;
import java.util.Date;

/**
 * Created by wxy on 2015-12-21.
 * 多房屋权证打印，商品房初始登记
 */
@Name("mulitOwnerRsipCardPrint")
@Scope(ScopeType.CONVERSATION)
public class MulitOwnerRsipCardPrint extends OwnerEntityHome<MakeCard>{




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

    private String businessHouseId;

    public String getBusinessHouseId() {
        return businessHouseId;
    }

    public void setBusinessHouseId(String businessHouseId) {
        this.businessHouseId = businessHouseId;
    }

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

    public void print(){
        Logging.getLog(getClass()).debug("bbbbbb--");

        Logging.getLog(getClass()).debug("aaa--"+FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("makeCardId"));
        setId(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("makeCardId"));
        Logging.getLog(getClass()).debug("id:" + isIdDefined() + "|" + getId());
        printUrl = extendsDataCreator.extendsPrintOwnerRsip(getEntityManager().find(BusinessHouse.class,FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("businessHouseId")),getInstance());

    }

    public void saveAndPrint(){

        getCardInfo().setMakeCard(getInstance());
        getInstance().setCardInfo(cardInfo);

        cardInfo.setPrintTime(new Date());
        cardInfo.setMakeEmpCode(authInfo.getLoginEmployee().getId());
        cardInfo.setMakeEmpName(authInfo.getLoginEmployee().getPersonName());
        update();
        printUrl = extendsDataCreator.extendsPrintOwnerRsip(getEntityManager().find(BusinessHouse.class,businessHouseId),getInstance());
    }
}

