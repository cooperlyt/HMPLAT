package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessMoney;
import com.dgsoft.house.owner.model.FactMoneyInfo;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import java.util.Date;

/**
 * Created by Administrator on 15-8-9.
 * 缴费信息表
 */
@Name("factMoneyInfoSubsrcibe")
public class FactMoneyInfoSubscribe extends OwnerEntityHome<FactMoneyInfo> {

    @In
    private OwnerBusinessHome ownerBusinessHome;


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





}
