package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.common.system.AuthenticationInfo;
import com.dgsoft.house.model.MoneyBank;
import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.MoneyBusiness;
import com.dgsoft.house.owner.model.RepairMoneyInfo;
import com.dgsoft.house.owner.model.RepairMoneyPay;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by wxy on 2018-10-08.
 */
@Name("repairMoneyPaySubscribe")
public class RepairMoneyPaySubscribe extends OwnerEntityHome<RepairMoneyPay> {

    @In
    private OwnerBusinessHome ownerBusinessHome;

    @In
    private AuthenticationInfo authInfo;

    private boolean have;

    public boolean isHave() {
        return have;
    }

    public void setHave(boolean have) {
        this.have = have;
    }

    @Override
    public RepairMoneyPay createInstance(){
        RepairMoneyPay result = new RepairMoneyPay();
        result.setEmpName(authInfo.getLoginEmployee().getPersonName());
        result.setEmpCode(authInfo.getLoginEmployee().getId());
        result.setNoticeTime(new Date());
        result.setHouseBusiness(ownerBusinessHome.getInstance().getSingleHoues());
        RepairMoneyInfo repairMoneyInfo = new RepairMoneyInfo();
//        repairMoneyInfo.setPrivateArea(new BigDecimal(0));
//        repairMoneyInfo.setPrivateRate(new BigDecimal(0));
        result.setRepairMoneyInfo(repairMoneyInfo);
        repairMoneyInfo.setRepairMoneyPay(result);
        return result;
    }

    @Override
    public void create(){
        super.create();
        if (!ownerBusinessHome.getInstance().getSingleHoues().getRepairMoneyPays().isEmpty()){
            if (ownerBusinessHome.getInstance().getMoneyBusinesses().iterator().next().getId()==null){
                setInstance(ownerBusinessHome.getInstance().getSingleHoues().getRepairMoneyPays().iterator().next());
                have=true;
            }else {
                setId(ownerBusinessHome.getInstance().getSingleHoues().getRepairMoneyPays().iterator().next().getId());
                have=true;
            }
        }else {
            have =false;
        }
    }


    public void checkHave() {
        if (have) {
            if (!ownerBusinessHome.getInstance().getSingleHoues().getRepairMoneyPays().isEmpty() &&
                    ownerBusinessHome.getInstance().getSingleHoues().getRepairMoneyPays().iterator().next().getId()!= null) {
                setId(ownerBusinessHome.getInstance().getSingleHoues().getRepairMoneyPays().iterator().next().getId());
            }
            getInstance().setHouseBusiness(ownerBusinessHome.getSingleHoues());
            ownerBusinessHome.getInstance().getSingleHoues().getRepairMoneyPays().add(getInstance());
        }
        else {
            ownerBusinessHome.getInstance().getSingleHoues().getRepairMoneyPays().remove(getInstance());
            clearInstance();
        }
    }





}
