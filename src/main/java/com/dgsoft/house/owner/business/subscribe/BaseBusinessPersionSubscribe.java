package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.common.system.PersonEntityHomeHelper;
import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessHouseOwner;
import com.dgsoft.house.owner.model.BusinessPersion;
import org.jboss.seam.annotations.In;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-9-19
 * Time: 下午4:32
 * To change this template use File | Settings | File Templates.
 */

public abstract class BaseBusinessPersionSubscribe extends OwnerEntityHome<BusinessPersion> {

    @In
    private OwnerBusinessHome ownerBusinessHome;

    protected abstract BusinessPersion.PersionType getType();

    private PersonEntityHomeHelper<BusinessPersion> persionPersonEntityHomeHelper;

    public PersonEntityHomeHelper<BusinessPersion> getPersionPersonEntityHomeHelper() {
        return persionPersonEntityHomeHelper;
    }


    @Override
    public Class<BusinessPersion> getEntityClass() {
        return BusinessPersion.class;
    }

    @Override
    public void create(){
        super.create();
        persionPersonEntityHomeHelper = new PersonEntityHomeHelper<BusinessPersion>(this);
        for(BusinessPersion businessPersion:ownerBusinessHome.getInstance().getBusinessPersions()){
            if(businessPersion.getType().equals(getType())){
                setId(businessPersion.getId());
                return;
            }
        }
        getInstance().setOwnerBusiness(ownerBusinessHome.getInstance());
        ownerBusinessHome.getInstance().getBusinessPersions().add(getInstance());
    }

    @Override
    protected BusinessPersion createInstance() {
        return new BusinessPersion(getType());
    }

}
