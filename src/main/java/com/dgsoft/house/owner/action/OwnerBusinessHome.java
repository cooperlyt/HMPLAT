package com.dgsoft.house.owner.action;

import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.model.*;
import org.jboss.seam.annotations.Name;

import java.util.Date;
import java.util.Set;

/**
 * Created by cooper on 8/25/14.
 */
@Name("ownerBusinessHome")
public class OwnerBusinessHome extends OwnerEntityHome<OwnerBusiness> {

    @Override
    public OwnerBusiness createInstance(){
        return new OwnerBusiness(OwnerBusiness.BusinessSource.BIZ_CAREATE,new Date(),
                OwnerBusiness.BusinessStatus.RUNNING,new Date(),new Date());
    }

    public Reason getReasonByType(String typeName){
           for(Reason reason:getInstance().getReasons()){
               if (reason.getType().equals(Reason.ReasonType.valueOf(Reason.ReasonType.class,typeName))){
                   return reason;
               }
           }
           return null;
    }

    public Evaluate getEvaluate(){
        if (!getInstance().getEvaluates().isEmpty()){
            return getInstance().getEvaluates().iterator().next();
        }
        return null;
    }

    public Card getCardByType(String typeName){
        for (Card card:getInstance().getCards()){
            if (card.getType().equals(Card.CardType.valueOf(Card.CardType.class,typeName))){
                return card;
            }
        }
        return null;
    }
    public MakeCard getMakeCard(String typeName){
        for (MakeCard makeCard:getInstance().getMakeCards()){
            if(makeCard.getType().equals(MakeCard.CardType.valueOf(MakeCard.CardType.class,typeName))){
                return makeCard;
            }
        }
        return null;
    }
    public Financial getFinancial(){
        if(! getInstance().getFinancials().isEmpty()){
            return getInstance().getFinancials().iterator().next();
        }
        return null;
    }

    public MappingCorp getMappingCorp(){
        if(!getInstance().getMappingCorps().isEmpty()){
            return getInstance().getMappingCorps().iterator().next();
        }
        return null;
    }
    public BusinessPersion getBusinessPersionByType(String typeName){
        for (BusinessPersion businessPersion:getInstance().getBusinessPersions()){
            if(businessPersion.getType().equals(BusinessPersion.PersionType.valueOf(BusinessPersion.PersionType.class,typeName))){
                return businessPersion;
            }
        }
        return null;
    }
    public CloseHouse getCloseHouse(){
        if(!getInstance().getCloseHouses().isEmpty()){
            return getInstance().getCloseHouses().iterator().next();
        }
        return null;
    }

    public SaleInfo getSaleInfo(){
        if(!getInstance().getSaleInfos().isEmpty()){
            return getInstance().getSaleInfos().iterator().next();
        }
        return null;
    }


    public HouseBusiness getSingleHoues() {

        Set<HouseBusiness> houseBusinesses = getInstance().getHouseBusinesses();
        if (houseBusinesses.size() > 1) {
            throw new IllegalArgumentException("HouseBusiness count > 1");
        } else if (houseBusinesses.size() == 1) {
            return houseBusinesses.iterator().next();
        } else
            return null;

    }


}
