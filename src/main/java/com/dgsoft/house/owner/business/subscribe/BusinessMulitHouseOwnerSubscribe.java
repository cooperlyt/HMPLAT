package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.common.system.PersonEntity;
import com.dgsoft.common.system.PersonHelper;
import com.dgsoft.common.system.business.TaskSubscribeComponent;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessHouse;
import com.dgsoft.house.owner.model.BusinessHouseOwner;
import com.dgsoft.house.owner.model.HouseBusiness;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

/**
 * Created by wxy on 2016-04-01.
 */
@Name("businessMulitHouseOwnerSubscribe")
@Scope(ScopeType.CONVERSATION)
public class BusinessMulitHouseOwnerSubscribe implements TaskSubscribeComponent {


    @In
    private OwnerBusinessHome ownerBusinessHome;


    private BusinessHouseOwner businessHouseOwner = new BusinessHouseOwner();




   @Create
   public void initHouseOwner(){
       if (ownerBusinessHome.getInstance().getHouseBusinesses()!=null
               && ownerBusinessHome.getInstance().getHouseBusinesses().size()>0){

           if (ownerBusinessHome.getInstance().getHouseBusinesses().iterator().next().getAfterBusinessHouse().getBusinessHouseOwner()== null) {

               BusinessHouse businessHouse = ownerBusinessHome.getInstance().getHouseBusinesses().iterator().next().getStartBusinessHouse();
               businessHouseOwner.setPersonName(businessHouse.getDeveloperName());

               businessHouseOwner.setCredentialsType(PersonEntity.CredentialsType.OTHER);
               if (businessHouse.getDeveloperCode() != null
                       && !businessHouse.getDeveloperCode().equals("")) {
                   businessHouseOwner.setCredentialsNumber(businessHouse.getDeveloperCode());
               }
           }else{
               businessHouseOwner  = ownerBusinessHome.getInstance().getHouseBusinesses().iterator().next().getAfterBusinessHouse().getBusinessHouseOwner();
           }

           businessHouseOwner.setOwnerBusiness(ownerBusinessHome.getInstance());

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
        if (ownerBusinessHome.getInstance().getHouseBusinesses()!=null
                && ownerBusinessHome.getInstance().getHouseBusinesses().size()>0) {
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean saveSubscribe() {
        for (HouseBusiness houseBusiness:ownerBusinessHome.getInstance().getHouseBusinesses()){
            BusinessHouseOwner houseOwner = new BusinessHouseOwner(ownerBusinessHome.getInstance(),businessHouseOwner);
            houseBusiness.getAfterBusinessHouse().setBusinessHouseOwner(houseOwner);
            houseBusiness.getAfterBusinessHouse().setOldOwner(houseOwner);
        }
        return true;

    }

    private PersonHelper<BusinessHouseOwner> personHelper;

    public PersonHelper<BusinessHouseOwner> getPersonInstance() {
        if ((personHelper == null) ||
                (personHelper.getPersonEntity() != businessHouseOwner)) {
            personHelper = new PersonHelper(businessHouseOwner);
        }
        return personHelper;
    }
}
