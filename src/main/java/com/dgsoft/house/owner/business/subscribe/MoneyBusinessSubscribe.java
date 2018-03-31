package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.common.system.business.TaskSubscribeComponent;
import com.dgsoft.house.model.MoneyBank;
import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.MoneyBusiness;
import com.dgsoft.house.owner.model.MoneyPayInfo;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Logging;

/**
 * Created by wxy on 2018-03-28.
 */

@Name("moneyBusinessSubscribe")
public class MoneyBusinessSubscribe extends OwnerEntityHome<MoneyBusiness> implements TaskSubscribeComponent {

    @In
    private OwnerBusinessHome ownerBusinessHome;

    private MoneyBank moneyBank;

    private boolean have;


    public boolean isHave() {
        return have;
    }

    public void setHave(boolean have) {
        this.have = have;
    }




    public MoneyBank getMoneyBank() {
        return moneyBank;
    }

    public void setMoneyBank(MoneyBank moneyBank) {
        Logging.getLog(getClass()).debug("ddddddd--");
        Logging.getLog(getClass()).debug("ddddd1111111--"+moneyBank.getAccountNumber());
        getInstance().setAccountNumber(moneyBank.getAccountNumber());
        getInstance().setBank(moneyBank.getId());
        getInstance().setBankName(moneyBank.getName());
        this.moneyBank = moneyBank;
    }
    @Override
    public MoneyBusiness createInstance(){
        Logging.getLog(getClass()).debug("555555--");
        MoneyBusiness result = new  MoneyBusiness ();
        result.setStatus(MoneyBusiness.MoneyBusinessStatus.CREATED);
        result.setVer(1);
        MoneyPayInfo amoneyPayInfo = new  MoneyPayInfo();
        amoneyPayInfo.setMoneyBusiness(result);
        result.setMoneyPayInfo(amoneyPayInfo);
        result.setOwnerBusiness(ownerBusinessHome.getInstance());
        Logging.getLog(getClass()).debug("555555aaaaaaaaa--");
        return result;
    }


    @Override
    public void create(){
        super.create();
        Logging.getLog(getClass()).debug("444444--");
        if (!ownerBusinessHome.getInstance().getMoneyBusinesses().isEmpty()){
            Logging.getLog(getClass()).debug("444444aaaaaaaaa--");
            if (ownerBusinessHome.getInstance().getMoneyBusinesses().iterator().next().getId()==null){
                setInstance(ownerBusinessHome.getInstance().getMoneyBusinesses().iterator().next());
                have=true;
            }else {
                setId(ownerBusinessHome.getInstance().getMoneyBusinesses().iterator().next().getId());
                have=true;
            }
        }else {
            Logging.getLog(getClass()).debug("444444bbbbb--");
            have =false;
        }
    }
    public void checkHave() {
        if (have) {
            Logging.getLog(getClass()).debug("33333--");

            if (!ownerBusinessHome.getInstance().getMoneyBusinesses().isEmpty() &&
                    ownerBusinessHome.getInstance().getMoneyBusinesses().iterator().next().getId()!= null) {
                setId(ownerBusinessHome.getInstance().getMoneyBusinesses().iterator().next().getId());
            }
            Logging.getLog(getClass()).debug("33333aaaaaaaaaaa--");
            getInstance().setOwnerBusiness(ownerBusinessHome.getInstance());
            ownerBusinessHome.getInstance().getMoneyBusinesses().add(getInstance());
            Logging.getLog(getClass()).debug("33333bbbbbbbbbbbbb--");
        }
        else {
            Logging.getLog(getClass()).debug("88888--");
            ownerBusinessHome.getInstance().getMoneyBusinesses().remove(getInstance());
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
        Logging.getLog(getClass()).debug("22222--");
        Logging.getLog(getClass()).debug("11111--"+ownerBusinessHome.getInstance().getSingleHoues().getHouseContract());
        if (ownerBusinessHome.getInstance().getSingleHoues().getHouseContract()!=null){
            Logging.getLog(getClass()).debug("22222ccccc--");
            Logging.getLog(getClass()).debug("22222ddddd--"+ownerBusinessHome.getInstance().getSingleHoues().getHouseContract().getId());
        }else {

            Logging.getLog(getClass()).debug("ffffffffff--");
        }
        getInstance().setHouseContract(ownerBusinessHome.getInstance().getSingleHoues().getHouseContract());
        return true;
    }
}
