package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.common.system.*;
import com.dgsoft.common.system.business.TaskSubscribeComponent;
import com.dgsoft.house.OwnerShareCalcType;
import com.dgsoft.house.PoolType;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.PowerPerson;
import com.dgsoft.house.owner.model.ProxyPerson;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Logging;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cooper on 24/11/2016.
 */
@Scope(ScopeType.CONVERSATION)
public abstract class BasePowerPersonInput implements TaskSubscribeComponent {

    protected abstract PowerPerson.PowerPersonType getPowerPersonType();

    protected PowerPerson.PowerPersonType getToType(){
        return getPowerPersonType();
    }

    protected abstract boolean isOld();

    @In
    private OwnerBusinessHome ownerBusinessHome;

    private OwnerShareCalcType ownerShareCalcType;

    public OwnerShareCalcType getOwnerShareCalcType() {
        switch (RunParam.instance().getIntParamValue("OWNER_SHARE_CALC_TYPE")) {
            case 1:
                return OwnerShareCalcType.SCALE;
            case 2:
                return OwnerShareCalcType.AREA;
            default:
                return ownerShareCalcType;
        }
    }

    public void setOwnerShareCalcType(OwnerShareCalcType ownerShareCalcType) {
        this.ownerShareCalcType = ownerShareCalcType;
    }

    private Integer personCount = null;


    public PoolType getPoolType(){
        if (isOld()){
            return ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getOldPoolType();
        }else
            return ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getPoolType();
    }

    public void setPoolType(PoolType poolType){
//        if (poolType == null || (isOld() && !poolType.equals()) !poolType.equals(ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getPoolType())){
//            ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getPowerPersons().removeAll(ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getPowerPersonListByType(getPowerPersonType(),isOld()));
//        }
        if (isOld()){
            ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().setOldPoolType(poolType);
        }else
            ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().setPoolType(poolType);
    }

    public int getPersonCount(){
        if (getPoolType() == null || PoolType.SINGLE_OWNER.equals(getPoolType())){
            return 1;
        }else if (personCount == null){
            personCount = ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getPowerPersonListByType(getPowerPersonType(),isOld()).size();
            if (personCount.equals(0)){
                personCount = 1;
            }
        }
        return personCount;
    }

    public void setPersonCount(int count){
        this.personCount = count;
    }

    private List<PowerPersonHelper<PowerPerson>> powerPersonList;

    public List<PowerPersonHelper<PowerPerson>> getPowerPersonList(){
        if (powerPersonList == null){
            powerPersonList = new ArrayList<PowerPersonHelper<PowerPerson>>();
            for(PowerPerson powerPerson: ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getPowerPersonListByType(getPowerPersonType(),isOld())){
                powerPersonList.add(new PowerPersonHelper<PowerPerson>(powerPerson, ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getHouseArea()) {
                    @Override
                    protected ProxyPersonEntity createProxyPerson() {
                        return new ProxyPerson();
                    }
                });
            }
        }
            while (powerPersonList.size() >  getPersonCount()){
                PersonHelper<PowerPerson> powerPerson = powerPersonList.remove(powerPersonList.size() - 1);
                ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().removePowerPerson(powerPerson.getPersonEntity());
            }

            while (powerPersonList.size() < getPersonCount()){
                PowerPerson powerPerson = new PowerPerson(getToType(), isOld());
                ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().addPowerPerson(powerPerson);
                powerPersonList.add(new PowerPersonHelper<PowerPerson>(powerPerson, ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getHouseArea()) {
                    @Override
                    protected ProxyPersonEntity createProxyPerson() {
                        return new ProxyPerson();
                    }
                });
            }

        return powerPersonList;
    }

    @Override
    public void initSubscribe() {
    }

    @Override
    public void validSubscribe() {
    }

    @Override
    public boolean isPass() {

        return ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getPowerPersonListByType(getToType(),isOld()).size() == getPersonCount();
    }

    @Override
    public boolean saveSubscribe() {
        return isPass();
    }
}
