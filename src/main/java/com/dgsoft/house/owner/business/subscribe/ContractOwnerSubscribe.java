package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.common.system.PersonHelper;
import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.ContractOwner;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2015-08-14.
 * 备案人
 */
@Name("contractOwnerSubscribe")
public class ContractOwnerSubscribe extends OwnerEntityHome<ContractOwner> {

    @In
    private OwnerBusinessHome ownerBusinessHome;



    private boolean have;


    public boolean isHave() {
        return have;
    }

    public void setHave(boolean have) {
        this.have = have;
    }

    public void checkHave(){

            setHave(true);
        //}
    }

    @Override
    public  ContractOwner createInstance(){
        ContractOwner result = new ContractOwner();
        result.setOwnerBusiness(ownerBusinessHome.getInstance());
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
            ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().setContractOwner(getInstance());
        }
    }


    private PersonHelper<ContractOwner> personHelper;

    public PersonHelper<ContractOwner> getPersonInstance() {
        if ((personHelper == null) || (personHelper.getPersonEntity() != getInstance())) {
            personHelper = new PersonHelper<ContractOwner>(getInstance());
        }
        return personHelper;
    }





}
