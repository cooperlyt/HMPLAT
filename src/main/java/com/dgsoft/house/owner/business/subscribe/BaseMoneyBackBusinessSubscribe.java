package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.common.system.NumberBuilder;
import com.dgsoft.common.system.RunParam;
import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.business.BankList;
import com.dgsoft.house.owner.model.Bank;
import com.dgsoft.house.owner.model.BusinessBuild;
import com.dgsoft.house.owner.model.MoneyBackBusiness;
import org.jboss.seam.annotations.In;
import org.jboss.seam.log.Logging;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by wxy on 2019-08-13.
 *
 */

public abstract class BaseMoneyBackBusinessSubscribe extends OwnerEntityHome<MoneyBackBusiness> {

    @In
    protected OwnerBusinessHome ownerBusinessHome;

    @In(create = true)
    protected OwnerEntityLoader ownerEntityLoader;

    @In(create = true)
    private NumberBuilder numberBuilder;

    @In(create = true)
    private BankList bankList;

    private BusinessBuild businessBuild;

    private boolean haveMoneySafe;

    private String bankName;


    public String getBankName() {
        return bankName;
    }
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public BusinessBuild getBusinessBuild() {
        return businessBuild;
    }
    public void setBusinessBuild(BusinessBuild businessBuild) {
        this.businessBuild = businessBuild;
    }

    public boolean isHaveMoneySafe() {
        return haveMoneySafe;
    }
    public void setHaveMoneySafe(boolean haveMoneySafe) {
        this.haveMoneySafe = haveMoneySafe;
    }


    protected abstract MoneyBackBusiness.MoneyBackType getBackType();

    @Override
    public Class<MoneyBackBusiness> getEntityClass() {
        return MoneyBackBusiness.class;
    }

    @Override
    public void create() {
        bankList.SearchBusinessBuild(ownerBusinessHome.getInstance().getSingleHoues().getAfterBusinessHouse().getBuildCode());
        businessBuild =bankList.getBusinessBuild();
        if (businessBuild!= null && businessBuild.getBusinessProject()!=null && businessBuild.getBusinessProject().getMoneySafe()!=null){
            Bank bank = bankList.getBank();
            if (bank!=null && bank.getBankName()!=null){
                setBankName(bank.getBankName());
            }else{
                setBankName(null);
            }
            setHaveMoneySafe(true);
        }else {
            setHaveMoneySafe(false);
        }

        if (isHaveMoneySafe()) {
            super.create();
            if(!ownerBusinessHome.getInstance().getMoneyBackBusinesses().isEmpty()){
                for (MoneyBackBusiness moneyBackBusiness:ownerBusinessHome.getInstance().getMoneyBackBusinesses()){
                    if (moneyBackBusiness.getBackType().equals(getBackType())){
                        if (moneyBackBusiness.getId()!=null){
                            setId(moneyBackBusiness.getId());
                        }else {
                            setInstance(moneyBackBusiness);
                        }
                    }
                }
            }else{
                SimpleDateFormat numberDateformat = new SimpleDateFormat("yyyyMMdd");
                String datePart = numberDateformat.format(new Date());
                String site = RunParam.instance().getStringParamValue("SiteShort");
                String no='T'+site+datePart + numberBuilder.getNumber("TKBH",4);
                getInstance().setId(no);
                getInstance().setBackMoney(ownerBusinessHome.getInstance().getSelectBusiness().getHouseContract().getSumPrice());
                getInstance().setBackType(getBackType());
                getInstance().setContract(ownerBusinessHome.getInstance().getSelectBusiness().getHouseContract().getContractNumber());
                getInstance().setOwnerBusiness(ownerBusinessHome.getInstance());
                ownerBusinessHome.getInstance().getMoneyBackBusinesses().add(getInstance());
            }
        }
    }







}
