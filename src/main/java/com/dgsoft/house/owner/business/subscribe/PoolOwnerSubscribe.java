package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.common.system.NumberBuilder;
import com.dgsoft.common.system.PersonHelper;
import com.dgsoft.common.system.RunParam;
import com.dgsoft.common.system.business.TaskSubscribeComponent;
import com.dgsoft.house.PoolType;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessHouse;
import com.dgsoft.house.owner.model.BusinessPool;
import com.dgsoft.house.owner.model.MakeCard;
import com.dgsoft.house.owner.model.OwnerBusiness;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;
import org.jboss.seam.log.Logging;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by cooper on 9/19/14.
 */
@Name("poolOwnerSubscribe")
@Scope(ScopeType.CONVERSATION)
public class PoolOwnerSubscribe implements TaskSubscribeComponent {

    public class PoolOwnerAdapert extends PersonHelper<BusinessPool> {

        private OwnerBusiness ownerBusiness;


        public PoolOwnerAdapert(OwnerBusiness ownerBusiness, BusinessPool entity) {
            super(entity);
            this.ownerBusiness = ownerBusiness;
        }

        public boolean isPrintCard() {
            return getPersonEntity().getMakeCard() != null;
        }

        public void setPrintCard(boolean printCard) {
            if (printCard){
                SimpleDateFormat numberDateformat = new SimpleDateFormat("yyyyMMdd");
                String datePart = numberDateformat.format(new Date());
                Integer typeCard = RunParam.instance().getIntParamValue("CreateCradNumberType");
                String no;
                if (typeCard==2){
                    no= datePart+ Long.toString(NumberBuilder.instance().getNumber(MakeCard.CardType.OWNER_RSHIP.name()));
                }else {
                    no = datePart + '-' + Long.toString(NumberBuilder.instance().getNumber(MakeCard.CardType.OWNER_RSHIP.name()));
                }
                getPersonEntity().setMakeCard(new MakeCard(ownerBusiness, MakeCard.CardType.POOL_RSHIP,no));
            }else{
                getPersonEntity().setMakeCard(null);
            }
        }

        public boolean isRecordPrintCard() {
            return getPersonEntity().getMakeCard() != null;
        }

        public void setRecordPrintCard(boolean printCard) {
            if (printCard){
                MakeCard poolMakeCard = new MakeCard(MakeCard.CardType.POOL_RSHIP);
                poolMakeCard.setOwnerBusiness(ownerBusiness);
                getPersonEntity().setMakeCard(poolMakeCard);
            }else{
                getPersonEntity().setMakeCard(null);
            }
        }



        public String getCardNumber(){
            if (getPersonEntity().getMakeCard() == null){
                return null;
            }else{
                Logging.getLog(getClass()).debug("11111");
                return getPersonEntity().getMakeCard().getNumber();
            }

        }

        public void setCardNumber(String value){

            if (getPersonEntity().getMakeCard() != null){
                Logging.getLog(getClass()).debug("22222");
                getPersonEntity().getMakeCard().setNumber(value);
            }
        }


    }

    private boolean choice = true;


    public boolean isChoice() {
        return choice;
    }

    public void setChoice(boolean choice) {
        this.choice = choice;
        if (!choice){
            ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().setPoolType(null);
        }
    }

    private List<PoolOwnerAdapert> poolOwners;

    @DataModelSelection
    private PersonHelper<BusinessPool> selectPoolOwner;

    @In
    private OwnerBusinessHome ownerBusinessHome;


    @In
    private FacesMessages facesMessages;

    @Create
    public void initPoolOwners() {


        poolOwners = new ArrayList<PoolOwnerAdapert>();
        for (BusinessPool pool : ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getBusinessPools()) {
            poolOwners.add(new PoolOwnerAdapert(ownerBusinessHome.getInstance(),pool));
        }
        Collections.sort(poolOwners, new Comparator<PersonHelper<BusinessPool>>() {
            @Override
            public int compare(PersonHelper<BusinessPool> o1, PersonHelper<BusinessPool> o2) {
                return o1.getPersonEntity().getCreateTime().compareTo(o2.getPersonEntity().getCreateTime());
            }
        });
    }

    @DataModel(value = "newEditPoolOwners")
    public List<PoolOwnerAdapert> getPoolOwners() {
        return poolOwners;
    }

    public void deleteSelectOwner() {
        if (selectPoolOwner != null) {
            ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getBusinessPools().remove(selectPoolOwner.getPersonEntity());
            poolOwners.remove(selectPoolOwner);
        }
    }

    public void addNewOwner() {
        BusinessPool newOwner = new BusinessPool(new Date());
        newOwner.setOwnerBusiness(ownerBusinessHome.getInstance());
        ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getBusinessPools().add(newOwner);
        poolOwners.add(0,new PoolOwnerAdapert(ownerBusinessHome.getInstance(),newOwner));
    }

    public void clearOwner(){
        poolOwners.clear();
        ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getBusinessPools().clear();
    }

    @Override
    public void initSubscribe() {
    }

    @Override
    public void validSubscribe() {
        if (!choice)
            return;
        if (ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getPoolType() == null){
            facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR, "PoolNotInput");
        }
        if (!PoolType.SINGLE_OWNER.equals(ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getPoolType()) && poolOwners.isEmpty()) {
            facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR, "PoolIsEmptyError");
        }
    }

    @Override
    public boolean isPass() {
        if(!choice){
            return true;
        }
        if (ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getPoolType() == null){
            return false;
        }
        if (!PoolType.SINGLE_OWNER.equals(ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getPoolType()) && poolOwners.isEmpty()) {
            return false;
        }
        return true;
    }


    @Override
    public boolean saveSubscribe() {
        if(!choice){
            poolOwners.clear();
            return true;
        }

        if (!PoolType.SINGLE_OWNER.equals(ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getPoolType()) && poolOwners.isEmpty()) {
            facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR, "PoolIsEmptyError");
            return false;
        }
        if (ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getPoolType().equals(PoolType.SINGLE_OWNER)) {
            ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getBusinessPools().clear();
            poolOwners.clear();
        } else if (ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getPoolType().equals(PoolType.TOGETHER_OWNER)) {
            for (BusinessPool pool : ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getBusinessPools()) {
                pool.setPerc(null);
                pool.setPoolArea(null);
            }
        }
        return true;
    }
}
