package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.common.system.PersonEntity;
import com.dgsoft.common.system.PersonHelper;
import com.dgsoft.common.system.RunParam;
import com.dgsoft.house.HouseEntityLoader;
import com.dgsoft.house.model.Developer;
import com.dgsoft.house.model.House;
import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.*;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Logging;

import javax.faces.event.ValueChangeEvent;
import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by wxy on 2015-09-18.
 * 在建工程抵押权人添加抵押人，将开发商转成抵押人
 */
@Name("FinancialProjectSubscribe")
public class FinancialProjectSubscribe extends FinancialBaseSubscribe {
    @In(create = true)
    private HouseEntityLoader houseEntityLoader;

    @Override
    protected void addMortgage() {
        if(mortgaegeRegiste.getBusinessHouseOwner()==null){
            BusinessHouseOwner businessHouseOwner = new BusinessHouseOwner();
            if (!ownerBusinessHome.getInstance().getHouseBusinesses().isEmpty()){
               HouseBusiness houseBusiness = ownerBusinessHome.getInstance().getHouseBusinesses().iterator().next();
               String developerCode = houseBusiness.getStartBusinessHouse().getDeveloperCode();
               if(developerCode==null || developerCode.trim().equals("")){
                   throw new IllegalArgumentException("developerCode = null");
               }
               Developer developer = houseEntityLoader.getEntityManager().find(Developer.class,developerCode);
               if (developer==null || developer.getAttachCorporation()==null){
                   businessHouseOwner.setCredentialsType(PersonEntity.CredentialsType.OTHER);
                   businessHouseOwner.setCredentialsNumber(UUID.randomUUID().toString());
                   businessHouseOwner.setPersonName(houseBusiness.getStartBusinessHouse().getDeveloperName());
               }else {
                   businessHouseOwner.setPersonName(developer.getName());

                   if (developer.getAttachCorporation().getLicenseNumber()==null || developer.getAttachCorporation().getLicenseNumber().trim().equals("")){
                       businessHouseOwner.setCredentialsType(PersonEntity.CredentialsType.OTHER);
                       businessHouseOwner.setCredentialsNumber(UUID.randomUUID().toString());

                   }else{
                       businessHouseOwner.setCredentialsType(PersonEntity.CredentialsType.COMPANY_CODE);
                       businessHouseOwner.setCredentialsNumber(developer.getAttachCorporation().getLicenseNumber());
                       businessHouseOwner.setLegalPerson(developer.getAttachCorporation().getOwnerName());
                       businessHouseOwner.setPhone(developer.getAttachCorporation().getPhone());
                   }
               }

            }
            businessHouseOwner.setOwnerBusiness(ownerBusinessHome.getInstance());
            mortgaegeRegiste.setBusinessHouseOwner(businessHouseOwner);
        }
    }
}
