package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.common.system.PersonEntity;
import com.dgsoft.house.HouseEntityLoader;
import com.dgsoft.house.model.Developer;
import com.dgsoft.house.owner.model.BusinessPersion;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2015-09-05.
 * 在建工程抵抵押申请人
 */
@Name("persionCorrectProjectSubscribe")
public class PersionCorrectProjectSubscribe extends BaseBusinessPersionSubscribe {

    @In(create = true)
    private HouseEntityLoader houseEntityLoader;


    @Override
    protected BusinessPersion.PersionType getType() {
        return  BusinessPersion.PersionType.CORRECT;
    }

    @Override
    public void create() {
        super.create();
        if (!isHave()) {
            clearInstance();

            Developer developer = houseEntityLoader.getEntityManager().find(Developer.class, ownerBusinessHome.getInstance().getHouseBusinesses().iterator().next().getAfterBusinessHouse().getDeveloperCode()) ;


                    if (developer.getAttachCorporation()!=null && developer.getAttachCorporation().getLicenseNumber()!=null) {
                        getInstance().setCredentialsNumber(developer.getAttachCorporation().getLicenseNumber());
                        getInstance().setPhone(developer.getAttachCorporation().getPhone());
                        getInstance().setCredentialsType(PersonEntity.CredentialsType.COMPANY_CODE);
                    }

                    getInstance().setPersonName(developer.getName());




            getInstance().setOwnerBusiness(ownerBusinessHome.getInstance());
            ownerBusinessHome.getInstance().getBusinessPersions().add(getInstance());
            setHave(true);
        }
    }
}