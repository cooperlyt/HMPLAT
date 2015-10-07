package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.common.DataFormat;
import com.dgsoft.common.OrderBeanComparator;
import com.dgsoft.common.system.RunParam;
import com.dgsoft.common.system.action.BusinessDefineHome;
import com.dgsoft.common.system.business.TaskSubscribeComponent;
import com.dgsoft.common.system.model.Fee;
import com.dgsoft.common.system.model.FeeTimeArea;
import com.dgsoft.common.utils.seam.ExpressionsUtils;
import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessMoney;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;
import org.jboss.seam.core.Expressions;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * Created by Administrator on 15-7-25.
 *
 */
@Name("calcBusinessMoney")
@Scope(ScopeType.CONVERSATION)
public class MoneySubscribe implements TaskSubscribeComponent {


    @In
    private OwnerBusinessHome ownerBusinessHome;

    @In
    private BusinessDefineHome businessDefineHome;


    @In
    private FacesMessages facesMessages;


    @DataModel
    private List<BusinessMoney> businessMoneyList;

    @DataModelSelection
    private BusinessMoney selectBusinessMoney;


    @Override
    public void initSubscribe() {
        businessMoneyList = new ArrayList<BusinessMoney>();

        for (Fee fee:businessDefineHome.getInstance().getFees()){
            BusinessMoney businessMoney = null;
            boolean exists = false;
            for(BusinessMoney bm: ownerBusinessHome.getInstance().getBusinessMoneys()) {
                if (fee.getId().equals(bm.getMoneyTypeId())){
                    businessMoney = bm;
                    businessMoneyList.add(businessMoney);
                    exists = true;
                    break;
                }
            }
            if (!exists){
                businessMoney = new BusinessMoney(ownerBusinessHome.getInstance(),fee.getId(),fee.getName(),null,fee.getPriority());
                ownerBusinessHome.getInstance().getBusinessMoneys().add(businessMoney);
                businessMoneyList.add(businessMoney);
            }

            if (RunParam.instance().getBooleanParamValue("AutoCalcMoney")){
                String feeEL = null;
                String detailsEL = null;
                String forEachValueEl = null;
                String forEachVarName = null;


                for  (FeeTimeArea feeTimeArea: fee.getFeeTimeAreaList()){
                    if ( ((DataFormat.getDayBeginTime(feeTimeArea.getBeginTime()).compareTo(ownerBusinessHome.getInstance().getApplyTime())) >= 0) &&
                            (DataFormat.getDayEndTime(feeTimeArea.getEndTime()).compareTo(ownerBusinessHome.getInstance().getApplyTime()) <=0 )){
                        feeEL = feeTimeArea.getFeeEl();
                        forEachValueEl = feeTimeArea.getForEachValues();
                        forEachVarName = feeTimeArea.getForEachVar();
                        detailsEL = feeTimeArea.getDetailsEl();
                        break;
                    }
                }
                if (feeEL == null){
                    feeEL = fee.getFeeEl();
                    forEachValueEl = fee.getForEachValues();
                    forEachVarName = fee.getForEachVar();
                    detailsEL = fee.getDetailsEl();
                }


                if (forEachValueEl != null && !forEachValueEl.trim().equals("")){
                    businessMoney.setCheckMoney(ExpressionsUtils.instance().foreachSum(Expressions.instance().createValueExpression(forEachValueEl,Collection.class),forEachVarName,feeEL));
                }else{
                    businessMoney.setCheckMoney(new BigDecimal(Expressions.instance().createValueExpression(feeEL, Double.class).getValue()).setScale(3, RoundingMode.HALF_UP));
                }

                calcShouldMoney(businessMoney);

                if (detailsEL != null && !detailsEL.trim().equals(""))
                    businessMoney.setChargeDetails(Expressions.instance().createValueExpression(detailsEL, String.class).getValue());

            }
        }

        Collections.sort(businessMoneyList, OrderBeanComparator.getInstance());


    }


    private void calcShouldMoney(BusinessMoney businessMoney){
        switch (RunParam.instance().getIntParamValue("ShouldMoneyCalcType")){
            case 3:
                businessMoney.setShouldMoney(businessMoney.getCheckMoney().setScale(0, RoundingMode.HALF_UP));
                break;
            case 4:
                businessMoney.setShouldMoney(businessMoney.getCheckMoney().setScale(1, RoundingMode.HALF_UP));
                break;
            case 5:
                businessMoney.setShouldMoney(businessMoney.getCheckMoney().setScale(2, RoundingMode.HALF_UP));
                break;
            case 6:
                businessMoney.setShouldMoney(businessMoney.getCheckMoney().setScale(0, RoundingMode.DOWN));
                break;
            case 7:
                businessMoney.setShouldMoney(businessMoney.getCheckMoney().setScale(0, RoundingMode.UP));
                break;
            default:
                businessMoney.setShouldMoney(businessMoney.getCheckMoney());
        }
    }

    public void checkMoneyChangeListener(){
        calcShouldMoney(selectBusinessMoney);
    }

    @Override
    public void validSubscribe() {
        if (businessDefineHome.getInstance().getFees().size() !=  ownerBusinessHome.getInstance().getBusinessMoneys().size()){
            facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR,"");
        }
    }

    @Override
    public boolean isPass() {
        return businessDefineHome.getInstance().getFees().size() ==  ownerBusinessHome.getInstance().getBusinessMoneys().size();
    }



    @Override
    public boolean saveSubscribe() {

        return true;
    }

    public BusinessMoney getTotal(){
        BusinessMoney result = new BusinessMoney();
        result.setShouldMoney(BigDecimal.ZERO);
        result.setFactMoney(BigDecimal.ZERO);
        result.setCheckMoney(BigDecimal.ZERO);
        for(BusinessMoney money: businessMoneyList){
            result.setShouldMoney(result.getShouldMoney().add(money.getShouldMoney()));
            result.setFactMoney(result.getFactMoney().add(money.getFactMoney()));
            result.setCheckMoney(result.getCheckMoney().add(money.getCheckMoney()));
        }
        return result;
    }


}
