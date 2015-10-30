package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.common.system.business.TaskSubscribeComponent;
import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.helper.ExtendsDataCreator;
import com.dgsoft.house.owner.model.BusinessMoney;
import com.dgsoft.house.owner.model.FactMoneyInfo;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 15-8-9.
 * 缴费信息表
 */
@Name("factMoneyInfoSubsrcibe")
public class FactMoneyInfoSubscribe extends OwnerEntityHome<FactMoneyInfo> implements TaskSubscribeComponent {

    @In
    private OwnerBusinessHome ownerBusinessHome;

    @In
    private ExtendsDataCreator extendsDataCreator;


    @In
    private FacesMessages facesMessages;

    private String printUrl;

    public String getPrintUrl() {
        return printUrl;
    }


    public void preparePrintOwnerFee(){
        for (BusinessMoney businessMoney:ownerBusinessHome.getInstance().getBusinessMoneys()) {
            if (businessMoney.getShouldMoney().compareTo(BigDecimal.ZERO)>0){
                businessMoney.setFactMoney(businessMoney.getShouldMoney());
            }
        }

        if (ownerBusinessHome.getInstance().getHouseBusinesses()!=null && ownerBusinessHome.getInstance().getHouseBusinesses().size()>1){
            printUrl = extendsDataCreator.extendsPrintFee(ownerBusinessHome.getInstance().getId(),
                    ownerBusinessHome.getInstance().getHouseBusinesses().iterator().next().getAfterBusinessHouse().getDeveloperName(),
                    ownerBusinessHome.getInstance().getDefineName(),ownerBusinessHome.getInstance().getFactMoneyInfo(),
                    ownerBusinessHome.getInstance());
        }else {


            printUrl = extendsDataCreator.extendsPrintFee(ownerBusinessHome.getInstance().getId(),
                    ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getBusinessHouseOwner().getPersonName(),
                    ownerBusinessHome.getInstance().getDefineName(), ownerBusinessHome.getInstance().getFactMoneyInfo(),
                    ownerBusinessHome.getInstance());
        }

    }


    @Override
    public void create(){
        super.create();
        if(!ownerBusinessHome.getInstance().getFactMoneyInfos().isEmpty()){
            setId(ownerBusinessHome.getInstance().getFactMoneyInfos().iterator().next().getId());
        }else{
            getInstance().setOwnerBusiness(ownerBusinessHome.getInstance());
            getInstance().setFactTime(new Date());
            ownerBusinessHome.getInstance().getFactMoneyInfos().add(getInstance());


            for (BusinessMoney businessMoney:ownerBusinessHome.getInstance().getBusinessMoneys()){
                businessMoney.setFactMoneyInfo(getInstance());
                getInstance().getBusinessMoneys().add(businessMoney);

            }
        }
    }


    @Override
    public void initSubscribe() {

            for (BusinessMoney businessMoney:ownerBusinessHome.getInstance().getBusinessMoneys()) {
                if (businessMoney.getFactMoney().compareTo(BigDecimal.ZERO)>0 &&
                        !businessMoney.getFactMoney().equals(businessMoney.getShouldMoney())
                        && businessMoney.getShouldMoney().compareTo(BigDecimal.ZERO)>0){

                    facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR,"MoneyInfo");

                }
            }



    }

    @Override
    public void validSubscribe() {

    }

    @Override
    public boolean isPass() {
        return true;
    }

    @Override
    public boolean saveSubscribe() {
        return true;
    }
}
