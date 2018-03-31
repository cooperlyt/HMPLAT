package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.common.system.business.TaskSubscribeComponent;
import com.dgsoft.house.HouseEntityLoader;
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


    @In(create = true)
    private HouseEntityLoader houseEntityLoader;


    private MoneyBank moneyBank;

    private boolean have;


    public boolean isHave() {
        return have;
    }

    public void setHave(boolean have) {
        this.have = have;
    }

    public MoneyBank getMoneyBank() {
        if (getInstance().getBank()!=null && !getInstance().getBank().equals("")){
            moneyBank =  houseEntityLoader.getEntityManager().find(MoneyBank.class,getInstance().getBank());
        }
        return moneyBank;
    }

    public void setMoneyBank(MoneyBank moneyBank) {
        getInstance().setAccountNumber(moneyBank.getAccountNumber());
        getInstance().setBank(moneyBank.getId());
        getInstance().setBankName(moneyBank.getName());
        this.moneyBank = moneyBank;
    }
    @Override
    public MoneyBusiness createInstance(){
        MoneyBusiness result = new  MoneyBusiness ();
        result.setStatus(MoneyBusiness.MoneyBusinessStatus.CREATED);
        result.setVer(1);
        MoneyPayInfo amoneyPayInfo = new  MoneyPayInfo();
        amoneyPayInfo.setMoneyBusiness(result);
        result.setMoneyPayInfo(amoneyPayInfo);
        result.setOwnerBusiness(ownerBusinessHome.getInstance());
        return result;
    }


    @Override
    public void create(){
        super.create();
        if (!ownerBusinessHome.getInstance().getMoneyBusinesses().isEmpty()){
            if (ownerBusinessHome.getInstance().getMoneyBusinesses().iterator().next().getId()==null){
                setInstance(ownerBusinessHome.getInstance().getMoneyBusinesses().iterator().next());
                have=true;
            }else {
                setId(ownerBusinessHome.getInstance().getMoneyBusinesses().iterator().next().getId());
                have=true;
            }
        }else {
            have =false;
        }
    }
    public void checkHave() {
        if (have) {

            if (!ownerBusinessHome.getInstance().getMoneyBusinesses().isEmpty() &&
                    ownerBusinessHome.getInstance().getMoneyBusinesses().iterator().next().getId()!= null) {
                setId(ownerBusinessHome.getInstance().getMoneyBusinesses().iterator().next().getId());
            }
            getInstance().setOwnerBusiness(ownerBusinessHome.getInstance());
            ownerBusinessHome.getInstance().getMoneyBusinesses().add(getInstance());
        }
        else {
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
        return ownerBusinessHome.getInstance().getSingleHoues().getHouseContract()!=null;
    }

    @Override
    public boolean saveSubscribe() {
        getInstance().setHouseContract(ownerBusinessHome.getInstance().getSingleHoues().getHouseContract());
        return isPass();
    }
}
