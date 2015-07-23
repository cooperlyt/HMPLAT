package com.dgsoft.house.owner.action;

import com.dgsoft.common.system.business.BusinessInstance;
import com.dgsoft.house.owner.HouseInfoCompare;
import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.model.*;
import org.jboss.seam.annotations.Name;

import java.util.*;

/**
 * Created by cooper on 8/25/14.
 */
@Name("ownerBusinessHome")
public class OwnerBusinessHome extends OwnerEntityHome<OwnerBusiness> {

    @Override
    public OwnerBusiness createInstance(){

        return new OwnerBusiness(OwnerBusiness.BusinessSource.BIZ_CREATE,
                OwnerBusiness.BusinessStatus.RUNNING,new Date(),false, OwnerBusiness.BusinessType.NORMAL_BIZ);
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

    public List<MakeCard> getMakeCardByType(EnumSet<MakeCard.CardType> types){
        List<MakeCard> result = new ArrayList<MakeCard>();
        for (MakeCard makeCard:getInstance().getMakeCards()){
            if(types.contains(makeCard.getType())){
                result.add(makeCard);
            }
        }
        return result;
    }

    public List<MakeCard> getMakeCardByType(MakeCard.CardType type){

        return getMakeCardByType(EnumSet.of(type));
    }



    public List<MakeCard> getMakeCardByType(String typeName){

        return getMakeCardByType(MakeCard.CardType.valueOf(typeName));
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


    public CardInfo getCardInfo(){
        if (!getInstance().getMakeCards().isEmpty() &&
            getInstance().getMakeCards().iterator().next().getCardInfo()!=null){

            return  getInstance().getMakeCards().iterator().next().getCardInfo();
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


    public BusinessEmp getCreateEmp(){
        for(BusinessEmp emp: getInstance().getBusinessEmps()){
            if(emp.getType().equals(BusinessEmp.EmpType.CREATE_EMP)){
                return emp;
            }
        }
        throw new IllegalArgumentException("not have createEmp");
    }

    public List<TaskOper> getTaskOperList(){
        List<TaskOper> result = new ArrayList<TaskOper>(getInstance().getTaskOpers());
        Collections.sort(result, new Comparator<TaskOper>() {
            @Override
            public int compare(TaskOper o1, TaskOper o2) {
                return o1.getOperTime().compareTo(o2.getOperTime());
            }
        });
        return result;
    }


}
