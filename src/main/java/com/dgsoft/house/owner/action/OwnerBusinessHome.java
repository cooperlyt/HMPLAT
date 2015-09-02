package com.dgsoft.house.owner.action;

import com.dgsoft.common.system.action.BusinessDefineHome;
import com.dgsoft.common.system.business.BusinessInstance;
import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.model.*;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Logging;

import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by cooper on 8/25/14.
 */
@Name("ownerBusinessHome")
public class OwnerBusinessHome extends OwnerEntityHome<OwnerBusiness> {

    @In(required = false)
    private BusinessDefineHome businessDefineHome;


    @Override
    public OwnerBusiness createInstance(){
        OwnerBusiness result = new OwnerBusiness(OwnerBusiness.BusinessSource.BIZ_CREATE,
                OwnerBusiness.BusinessStatus.RUNNING, new Date(), false, OwnerBusiness.BusinessType.NORMAL_BIZ);

        result.setDefineId(businessDefineHome.getInstance().getId());
        result.setDefineName(businessDefineHome.getInstance().getName());


        result.setId(businessDefineHome.getInstance().getId() + "-" + OwnerNumberBuilder.instance().useDayNumber("businessId"));
        Logging.getLog(getClass()).debug("businessID:" + result.getId());

        return result;
    }

    public Reason getReasonByType(String typeName){
           for(Reason reason:getInstance().getReasons()){
               if (reason.getType().equals(Reason.ReasonType.valueOf(Reason.ReasonType.class,typeName))){
                   return reason;
               }
           }
           return null;
    }

    public BusinessEmp getBusinessEmpByType(String typeName){
        for(BusinessEmp businessEmp:getInstance().getBusinessEmps()){
            if (businessEmp.getType().equals(BusinessEmp.EmpType.valueOf(BusinessEmp.EmpType.class,typeName))){
                return businessEmp;
            }
        }
        return null;

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




    public HouseBusiness getSingleHoues() {

        Set<HouseBusiness> houseBusinesses = getInstance().getHouseBusinesses();
        if (houseBusinesses.size() > 1) {
            throw new IllegalArgumentException("HouseBusiness count > 1");
        } else if (houseBusinesses.size() == 1) {
            return houseBusinesses.iterator().next();
        } else
            return null;

    }




    public BusinessEmp getApplyEmp(){
        return getOperEmp(BusinessEmp.EmpType.APPLY_EMP);
    }

    public BusinessEmp getCheckEmp(){
        return getOperEmp(BusinessEmp.EmpType.CHECK_EMP);
    }

    public BusinessEmp getRegisterEmp(){
        return getOperEmp(BusinessEmp.EmpType.REG_EMP);
    }

    public BusinessEmp getCardPrinterEmp(){
        return getOperEmp(BusinessEmp.EmpType.CARD_PRINTER);
    }

    public BusinessEmp getCreateEmp(){
        return getOperEmp(BusinessEmp.EmpType.CREATE_EMP);
    }

    public BusinessPersion getSellEntrust(){
        return getBusinessPersionByType("SELL_ENTRUST");
    }

    public BusinessPersion getOwnerEntrust(){
        return getBusinessPersionByType("OWNER_ENTRUST");
    }



    public Reason getReceive(){
        return getReasonByType("RECEIVE");
    }
    public Reason getModifyBeforReason(){
        return getReasonByType("MODIFY_BEFOR_RENSON");
    }
    public Reason getModifyAfterReason(){
        return getReasonByType("MODIFY_AFTER_RENSON");
    }
    public BusinessMoney getTotal(){
        if (!getInstance().getBusinessMoneys().isEmpty()) {
            BusinessMoney result = new BusinessMoney();
            result.setShouldMoney(BigDecimal.ZERO);
            result.setFactMoney(BigDecimal.ZERO);
            for (BusinessMoney money : getInstance().getBusinessMoneys()) {
                result.setShouldMoney(result.getShouldMoney().add(money.getShouldMoney()));
                result.setFactMoney(result.getFactMoney().add(money.getFactMoney()));
            }
            return result;
        }
        return null;
    }

    private BusinessEmp getOperEmp(BusinessEmp.EmpType empType){
        for(BusinessEmp emp: getInstance().getBusinessEmps()){
            if(emp.getType().equals(empType)){
                return emp;
            }
        }
        return null;
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



    public String getStarHouseOwnerRshipNo(){

        return getInstance().getStarHouseOwnerRshipNo();
    }

    public String getStarHouseNoticeNo(){

        return getInstance().getStarHouseNoticeNo();
    }


    public String getStarHouseMortgageNo(){

        return getInstance().getStarHouseMortgageNo();
    }
    /**
     * 申请人
     */

    public BusinessPersion getApplyPersion(){

        return getInstance().getApplyPersion();
    }

    /**
     * 抵押权人代理人
     */

    public BusinessPersion getMortgageObligee(){
        return getInstance().getMortgageObligee();
    }

    /**
     * 抵押人代理人
     */

    public BusinessPersion getMortgage(){

        return getInstance().getMortgage();

    }

    /**
     * 债务人
     */

    public BusinessPersion getMortgageObligor(){
        return getInstance().getMortgageObligor();
    }

    /**
     * 测绘公司
     */

    public MappingCorp getMappingCorp(){
        return getInstance().getMappingCorp();
    }

    /**
     * 评估公司
     */

    public Evaluate getEvaluate(){
        return getInstance().getEvaluate();
    }


    public boolean isCanCancel(){
        return isCanModify();
    }

}
