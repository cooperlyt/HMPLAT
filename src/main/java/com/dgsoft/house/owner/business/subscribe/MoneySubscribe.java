package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.common.OrderBeanComparator;
import com.dgsoft.common.system.action.BusinessDefineHome;
import com.dgsoft.common.system.business.TaskSubscribeComponent;
import com.dgsoft.common.system.model.Fee;
import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessMoney;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;

import java.math.BigDecimal;
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


    private List<BusinessMoney> businessMoneyList;

    @Override
    public void initSubscribe() {
        businessMoneyList = new ArrayList<BusinessMoney>();

        for (Fee fee:businessDefineHome.getInstance().getFees()){

            boolean exists = false;
            for(BusinessMoney bm: ownerBusinessHome.getInstance().getBusinessMoneys()) {
                if (fee.getId().equals(bm.getMoneyTypeId())){
                    businessMoneyList.add(bm);
                    exists = true;
                    break;
                }
            }
            if (!exists){
                BusinessMoney businessMoney = new BusinessMoney(ownerBusinessHome.getInstance(),fee.getId(),fee.getName(),new BigDecimal(0),null,fee.getPriority());
                ownerBusinessHome.getInstance().getBusinessMoneys().add(businessMoney);
                businessMoneyList.add(businessMoney);
            }

        }

        Collections.sort(businessMoneyList, OrderBeanComparator.getInstance());


    }


    public List<BusinessMoney> getBusinessMoneyList(){
        return businessMoneyList;
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
        for(BusinessMoney money: businessMoneyList){
            result.setShouldMoney(result.getShouldMoney().add(money.getShouldMoney()));
            result.setFactMoney(result.getFactMoney().add(money.getFactMoney()));
        }
        return result;
    }


}
