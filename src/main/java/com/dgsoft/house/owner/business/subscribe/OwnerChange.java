package com.dgsoft.house.owner.business.subscribe;


import com.dgsoft.house.owner.HouseOwnerPersonHelper;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.business.subscribe.complete.MakeCardOwnerRship;
import com.dgsoft.house.owner.model.BusinessHouseOwner;
import com.dgsoft.house.owner.model.MakeCard;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

/**
 * Created by cooper on 4/26/16.
 */
@Name("ownerChange")
@Scope(ScopeType.CONVERSATION)
public class OwnerChange {

    private boolean change;

    @In
    private OwnerBusinessHome ownerBusinessHome;

    @Create
    public void init(){
        change = ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getBusinessHouseOwner().getOwnerBusiness() == ownerBusinessHome.getInstance();
    }


    public boolean isChange() {
        return change;
    }

    public void setChange(boolean change) {
        this.change = change;
    }

    public void changeListener(){
        if (change){

            ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().setBusinessHouseOwner(new BusinessHouseOwner(ownerBusinessHome.getInstance(),ownerBusinessHome.getSingleHoues().getStartBusinessHouse().getBusinessHouseOwner()));

            MakeCard makeCard = new MakeCard(MakeCard.CardType.OWNER_RSHIP, MakeCardOwnerRship.genOwnerCardNo());


            ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getBusinessHouseOwner().setMakeCard(makeCard);


            makeCard.setOwnerBusiness(ownerBusinessHome.getInstance());
            ownerBusinessHome.getInstance().getMakeCards().add(makeCard);

        }else if (ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getBusinessHouseOwner().getOwnerBusiness() == ownerBusinessHome.getInstance()){
            ownerBusinessHome.getInstance().getMakeCards().remove(ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getBusinessHouseOwner().getMakeCard());
            ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().setBusinessHouseOwner(ownerBusinessHome.getSingleHoues().getStartBusinessHouse().getBusinessHouseOwner());

        }
    }


    private HouseOwnerPersonHelper personHelper;

    public HouseOwnerPersonHelper getPersonInstance() {
        if ((personHelper == null) || (personHelper.getPersonEntity() != ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getBusinessHouseOwner())) {
            personHelper = new HouseOwnerPersonHelper(ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getBusinessHouseOwner());
        }
        return personHelper;
    }
}
