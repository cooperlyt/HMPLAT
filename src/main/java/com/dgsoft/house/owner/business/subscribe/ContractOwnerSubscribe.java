package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.common.system.PersonHelper;
import com.dgsoft.common.system.business.TaskSubscribeComponent;
import com.dgsoft.house.SaleType;
import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.ContractOwner;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;

/**
 * Created by wxy on 2015-08-14.
 * 备案人
 */
@Name("contractOwnerSubscribe")
public class ContractOwnerSubscribe extends OwnerEntityHome<ContractOwner> implements TaskSubscribeComponent {

    @In
    private OwnerBusinessHome ownerBusinessHome;

    @In
    private FacesMessages facesMessages;

    private boolean have;


    public boolean isHave() {
        return have;
    }

    public void setHave(boolean have) {
        this.have = have;
    }

    public void checkHave(){

            setHave(true);
    }



    private boolean type=true;


    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    @Override
    public  ContractOwner createInstance(){
        ContractOwner result = new ContractOwner();
        result.setOwnerBusiness(ownerBusinessHome.getInstance());
        result.setHouseCode(ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getHouseCode());
        ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().setContractOwner(result);
        return result;

    }

    @Override
    public void create() {
        super.create();

        if (ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getContractOwner() != null) {
            setId(ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getContractOwner().getId());
        }else{
            clearInstance();
            if(type){
                getInstance().setType(SaleType.MAP_SELL);
            }else {
                getInstance().setType(SaleType.NOW_SELL);
            }

            ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().setContractOwner(getInstance());
            getInstance().setHouseCode(ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getHouseCode());
        }
    }


    private PersonHelper<ContractOwner> personHelper;

    public PersonHelper<ContractOwner> getPersonInstance() {
        if ((personHelper == null) || (personHelper.getPersonEntity() != getInstance())) {
            personHelper = new PersonHelper<ContractOwner>(getInstance());
        }
        return personHelper;
    }


    @Override
    public void initSubscribe() {

    }

    @Override
    public void validSubscribe() {
       if (getEntityManager().find(ContractOwner.class,getInstance().getId())!=null){
           facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR, "ContractOwner_conflict");
       }
    }

    @Override
    public boolean isPass() {

        if (getEntityManager().find(ContractOwner.class,getInstance().getId())!=null){
            return false;
        }else{
            return true;
        }

    }

    @Override
    public boolean saveSubscribe() {
        return false;
    }
}
