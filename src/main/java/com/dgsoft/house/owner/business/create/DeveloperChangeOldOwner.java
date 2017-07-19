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
import org.jboss.seam.log.Logging;

/**
 * Created by wxy on 2016-12-17.
 * 开发商转化成原产权人
 */
@Name("developerChangeOldOwner")
public class DeveloperChangeOldOwner implements BusinessDataFill {

    @In
    private OwnerBusinessHome ownerBusinessHome;

    @In(create = true)
    private HouseEntityLoader houseEntityLoader;


    @Override
    public void fillData() {


            for (HouseBusiness houseBusiness : ownerBusinessHome.getInstance().getHouseBusinesses()) {
                if (houseBusiness.getStartBusinessHouse()!=null && houseBusiness.getStartBusinessHouse().getDeveloperCode()!=null && !houseBusiness.getStartBusinessHouse().getDeveloperCode().equals("")) {
                    PowerPerson powerPerson = new PowerPerson(PowerPerson.PowerPersonType.INIT, true);

                    powerPerson.setPersonName(houseBusiness.getStartBusinessHouse().getDeveloperName());
                    powerPerson.setCredentialsType(PersonEntity.CredentialsType.COMPANY_CODE);

                    Logging.getLog(getClass()).debug("111---" + houseBusiness.getStartBusinessHouse().getDeveloperCode());

                    Developer developer = houseEntityLoader.getEntityManager().find(Developer.class, houseBusiness.getStartBusinessHouse().getDeveloperCode());
                    if (developer.getAttachCorporation() != null) {
                        if (developer.getAttachCorporation().getLicenseNumber() != null) {
                            powerPerson.setCredentialsNumber(developer.getAttachCorporation().getLicenseNumber());
                        } else {
                            powerPerson.setCredentialsNumber(developer.getId());
                        }
                        powerPerson.setLegalPerson(developer.getAttachCorporation().getOwnerName());
                        powerPerson.setPhone(developer.getAttachCorporation().getOwnerTel());
                        powerPerson.setLegalType(cc.coopersoft.house.sale.data.PowerPerson.LegalType.LEGAL_OWNER);

                    } else {
                        powerPerson.setCredentialsNumber(developer.getId());

                        powerPerson.setPhone("");

                    }

                    houseBusiness.getAfterBusinessHouse().getPowerPersons().add(powerPerson);

                    houseBusiness.getAfterBusinessHouse().setOldPoolType(PoolType.SINGLE_OWNER);
                }
            }

    }
}
