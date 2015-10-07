package com.dgsoft.house.owner.action;

import com.dgsoft.common.system.NumberBuilder;
import com.dgsoft.common.system.action.BusinessDefineHome;
import com.dgsoft.common.system.business.BusinessInstance;
import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.model.*;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.log.Logging;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by cooper on 8/25/14.
 */
@Name("ownerBusinessHome")
public class OwnerBusinessHome extends OwnerEntityHome<OwnerBusiness> {

    @In(required = false)
    private BusinessDefineHome businessDefineHome;


    @Transactional
    public void doNodeAction(String name){
        businessDefineHome.doNodeAction(name);
        update();
    }

    @Override
    public OwnerBusiness createInstance(){
        OwnerBusiness result = new OwnerBusiness(OwnerBusiness.BusinessSource.BIZ_CREATE,
                OwnerBusiness.BusinessStatus.RUNNING, new Date(), false, OwnerBusiness.BusinessType.NORMAL_BIZ);

        result.setDefineId(businessDefineHome.getInstance().getId());
        result.setDefineName(businessDefineHome.getInstance().getName());


        result.setId(businessDefineHome.getInstance().getId() + "-" + NumberBuilder.instance().getDateNumber("businessId"));
        Logging.getLog(getClass()).debug("businessID:" + result.getId());

        return result;
    }

    public MakeCard getCardByType(String typeName){
        for (MakeCard makeCard:getInstance().getMakeCards()){
              if (makeCard.getType().equals(MakeCard.CardType.valueOf(MakeCard.CardType.class,typeName))){
                return makeCard;
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

    public FactMoneyInfo getFactMoneyInfo(){
        if (!getInstance().getFactMoneyInfos().isEmpty()){
             return getInstance().getFactMoneyInfos().iterator().next();
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

    public ProjectCard getProjectCardInfo(){
        if (!getInstance().getMakeCards().isEmpty() &&
                getInstance().getMakeCards().iterator().next().getProjectCard()!=null){

            return  getInstance().getMakeCards().iterator().next().getProjectCard();
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

    public BusinessMoney getTotal(){
        BusinessMoney result = new BusinessMoney();
        result.setCheckMoney(BigDecimal.ZERO);
        result.setShouldMoney(BigDecimal.ZERO);
        result.setFactMoney(BigDecimal.ZERO);
        if (!getInstance().getBusinessMoneys().isEmpty()) {
            for (BusinessMoney money : getInstance().getBusinessMoneys()) {
                result.setCheckMoney(result.getShouldMoney().add(money.getCheckMoney()));
                result.setShouldMoney(result.getShouldMoney().add(money.getShouldMoney()));
                result.setFactMoney(result.getFactMoney().add(money.getFactMoney()));
            }
        }
        return result;
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

    public boolean isHaveLaterUploadFile(){
        for(BusinessFile file: getInstance().getUploadFileses()){
            if (file.isNoFile() && file.getUploadFiles().isEmpty()){
                return true;
            }
        }
        return false;
    }

    public boolean isCanModify(){
        if (isIdDefined()){
            for(HouseBusiness business: getInstance().getHouseBusinesses()){
                if (business.getAfterBusinessHouse().getHouseRecord() == null){
                    return false;
                }
            }
            if (getInstance().getSource().equals(BusinessInstance.BusinessSource.BIZ_OUTSIDE)){
                return false;
            }
            return getInstance().getStatus().equals(BusinessInstance.BusinessStatus.COMPLETE) &&
                    (getInstance().getType().equals(BusinessInstance.BusinessType.MODIFY_BIZ) ||
                    getInstance().getType().equals(BusinessInstance.BusinessType.NORMAL_BIZ));
        }
        return false;

    }

    public boolean isCanCancel(){
        return isCanModify();
    }


    public Reason getReasonByType(String typeName){
        return getInstance().getReason(Reason.ReasonType.valueOf(typeName));
    }

    public BusinessPersion getBusinessPersionByType(String typeName){
        return getInstance().getBusinessPersion(BusinessPersion.PersionType.valueOf(typeName));
    }

    public BusinessEmp getBusinessEmpByType(String typeName){
        return getInstance().getOperEmp(BusinessEmp.EmpType.valueOf(typeName));
    }

    /**
     * 申请人
     */
    @Deprecated
    public BusinessPersion getApplyPersion(){
        return getInstance().getApplyPersion();
    }

    /**
     * 抵押权人代理人
     */
    @Deprecated
    public BusinessPersion getMortgageObligee(){
        return getInstance().getMortgageObligee();
    }

    /**
     * 抵押人代理人
     */
    @Deprecated
    public BusinessPersion getMortgage(){
        return getInstance().getMortgage();
    }

    /**
     * 债务人
     */
    @Deprecated
    public BusinessPersion getMortgageObligor(){
        return getInstance().getMortgageObligor();
    }

    /**
     * 测绘公司
     */
    @Deprecated
    public MappingCorp getMappingCorp(){
        return getInstance().getMappingCorp();
    }

    /**
     * 评估公司
     */
    @Deprecated
    public Evaluate getEvaluate(){
        return getInstance().getEvaluate();
    }

    @Deprecated
    public BusinessPersion getSellEntrust(){
        return getInstance().getSellEntrust();
    }

    @Deprecated
    public BusinessPersion getOwnerEntrust(){
        return getInstance().getOwnerEntrust();
    }

    @Deprecated
    public Reason getReceive(){
        return getInstance().getReceive();
    }

    @Deprecated
    public Reason getHighDebtor(){
        return getInstance().getHighDebtor();
    }

    @Deprecated
    public Reason getFillChange(){
        return getInstance().getFillChange();
    }

    /**
     * 异议事项
     */
    @Deprecated
    public Reason getDifficulty(){
        return getInstance().getDifficulty();
    }

    /**
     * 注销原因
     */
    @Deprecated
    public Reason getLogout(){
        return getInstance().getLogout();
    }

    @Deprecated
    public Reason getChangBeforReason(){
        return getInstance().getChangBeforReason();
    }
    @Deprecated
    public Reason getChangAfterReason(){
        return getInstance().getChangAfterReason();
    }
    @Deprecated
    public Reason getModifyBeforReason(){
        return getInstance().getModifyBeforReason();
    }
    @Deprecated
    public Reason getModifyAfterReason(){
        return getInstance().getModifyAfterReason();
    }

    @Deprecated
    public BusinessEmp getCreateEmp(){
        return getInstance().getCreateEmp();
    }
    @Deprecated
    public BusinessEmp getApplyEmp(){
        return getInstance().getApplyEmp();
    }
    @Deprecated
    public BusinessEmp getCheckEmp(){
        return getInstance().getCheckEmp();
    }
    @Deprecated
    public BusinessEmp getRegisterEmp(){
        return getInstance().getRegisterEmp();
    }
    @Deprecated
    public BusinessEmp getCardPrinterEmp(){
        return getInstance().getCardPrinterEmp();
    }


}
