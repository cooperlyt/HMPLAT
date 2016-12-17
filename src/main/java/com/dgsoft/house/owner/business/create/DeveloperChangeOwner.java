package com.dgsoft.house.owner.business.create;

import com.dgsoft.common.system.OwnerPersonEntity;
import com.dgsoft.common.system.PersonEntity;
import com.dgsoft.common.system.business.BusinessDataFill;
import com.dgsoft.common.system.business.BusinessInstance;
import com.dgsoft.house.HouseEntityLoader;
import com.dgsoft.house.PoolType;
import com.dgsoft.house.model.Developer;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.HouseBusiness;
import com.dgsoft.house.owner.model.PowerPerson;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by Administrator on 15-8-1.
 * 开发商转化成现产权人
 */
@Name("developerChangeOwner")
public class DeveloperChangeOwner implements BusinessDataFill {

    @In
    private OwnerBusinessHome ownerBusinessHome;

    @In(create = true)
    private HouseEntityLoader houseEntityLoader;


    @Override
    public void fillData() {

        if (!ownerBusinessHome.getInstance().getType().equals(BusinessInstance.BusinessType.MODIFY_BIZ)) {
            for (HouseBusiness houseBusiness : ownerBusinessHome.getInstance().getHouseBusinesses()) {
                PowerPerson powerPerson = new PowerPerson(PowerPerson.PowerPersonType.INIT,false);

                powerPerson.setPersonName(houseBusiness.getStartBusinessHouse().getDeveloperName());
                powerPerson.setCredentialsType(PersonEntity.CredentialsType.COMPANY_CODE);

                Developer developer = houseEntityLoader.getEntityManager().find(Developer.class,houseBusiness.getStartBusinessHouse().getDeveloperCode());

                powerPerson.setCredentialsNumber(developer.getAttachCorporation().getLicenseNumber());
                powerPerson.setLegalType(cc.coopersoft.house.sale.data.PowerPerson.LegalType.LEGAL_OWNER);
                powerPerson.setLegalPerson(developer.getAttachCorporation().getOwnerName());
                powerPerson.setPhone(developer.getAttachCorporation().getOwnerTel());

                houseBusiness.getAfterBusinessHouse().addPowerPerson(powerPerson);
                houseBusiness.getAfterBusinessHouse().setPoolType(PoolType.SINGLE_OWNER);
            }
        }
    }
}
