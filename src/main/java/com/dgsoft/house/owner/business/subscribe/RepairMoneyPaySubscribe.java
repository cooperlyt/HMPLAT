package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.common.system.AuthenticationInfo;
import com.dgsoft.common.system.business.TaskSubscribeComponent;
import com.dgsoft.house.model.MoneyBank;
import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.MoneyBusiness;
import com.dgsoft.house.owner.model.RepairMoneyInfo;
import com.dgsoft.house.owner.model.RepairMoneyPay;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Logging;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by wxy on 2018-10-08.
 */
@Name("repairMoneyPaySubscribe")
public class RepairMoneyPaySubscribe extends OwnerEntityHome<RepairMoneyPay> implements TaskSubscribeComponent {

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
        result.setId(ownerBusinessHome.getInstance().getId());
        result.setEmpName(authInfo.getLoginEmployee().getPersonName());
        result.setEmpCode(authInfo.getLoginEmployee().getId());
        result.setNoticeTime(new Date());
        result.setHouseBusiness(ownerBusinessHome.getInstance().getSingleHoues());
        RepairMoneyInfo repairMoneyInfo = new RepairMoneyInfo();
        repairMoneyInfo.setId(ownerBusinessHome.getInstance().getId());
        repairMoneyInfo.setPublicArea(ownerBusinessHome.getInstance().getSingleHoues().getAfterBusinessHouse().getHouseArea());
        result.setRepairMoneyInfo(repairMoneyInfo);
        repairMoneyInfo.setRepairMoneyPay(result);
        return result;
    }

    @Override
    public void create(){
        super.create();
        if (!ownerBusinessHome.getInstance().getSingleHoues().getRepairMoneyPays().isEmpty()){
            if (ownerBusinessHome.getInstance().getSingleHoues().getRepairMoneyPays().iterator().next().getId()==null){
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


    @Override
    public void initSubscribe() {


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
        // 费率公式 元/㎡   四舍五入到元
        if (!ownerBusinessHome.getSingleHoues().getRepairMoneyPays().isEmpty()) {
            getInstance().setCalcDetails(getInstance().getRepairMoneyInfo().getPublicRate().toString() + "元/㎡");
            if (getInstance().getRepairMoneyInfo().getPrivateRate() != null) {
                getInstance().setCalcDetails(getInstance().getCalcDetails() + "," + getInstance().getRepairMoneyInfo().getPrivateRate().toString() + "元/㎡");
            }
            BigDecimal sumMoney = new BigDecimal(0);
            sumMoney = getInstance().getRepairMoneyInfo().getPublicArea().multiply(getInstance().getRepairMoneyInfo().getPublicRate());
            //私有面积计算
            if (getInstance().getRepairMoneyInfo().getPrivateRate() != null
                    && getInstance().getRepairMoneyInfo().getPrivateArea() != null) {
                BigDecimal privateMoney = new BigDecimal(0);
                privateMoney = getInstance().getRepairMoneyInfo().getPrivateRate().multiply(getInstance().getRepairMoneyInfo().getPrivateArea());
                sumMoney = sumMoney.add(privateMoney);
            }
            getInstance().setMustMoney(sumMoney.setScale(0, BigDecimal.ROUND_HALF_UP));
            getInstance().setMoney(getInstance().getMustMoney());
        }
        return true;
    }
}
