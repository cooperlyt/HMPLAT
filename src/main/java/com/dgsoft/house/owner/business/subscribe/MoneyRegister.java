package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.common.system.action.BusinessDefineHome;
import com.dgsoft.common.system.business.TaskSubscribeComponent;
import com.dgsoft.common.system.model.Fee;
import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessMoney;
import com.sun.javafx.collections.MappingChange;
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
 * 登记费
 */
@Name("moneyRegister")
@Scope(ScopeType.CONVERSATION)
public class MoneyRegister implements TaskSubscribeComponent {


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
        final Map<String,Fee> feeMap = new HashMap<String, Fee>();

        for (Fee fee:businessDefineHome.getInstance().getFees()){

            feeMap.put(fee.getId(),fee);
            BusinessMoney businessMoney = new BusinessMoney();
            businessMoney.setTypeName(fee.getName());
            businessMoney.setMoneyTypeId(fee.getId());
            businessMoney.setShouldMoney(new BigDecimal(0));
            businessMoney.setFactMoney(new BigDecimal(0));
            businessMoney.setOwnerBusiness(ownerBusinessHome.getInstance());
            businessMoney.setChargeDetails(fee.getDetailsEl());
            businessMoneyList.add(businessMoney);
            ownerBusinessHome.getInstance().getBusinessMoneys().add(businessMoney);
        }

        Collections.sort(businessMoneyList,new Comparator<BusinessMoney>() {

            public int compare(BusinessMoney b1,BusinessMoney b2){
                int p1 = feeMap.get(b1.getMoneyTypeId()).getPriority();
                int p2 = feeMap.get(b2.getMoneyTypeId()).getPriority();
                return Integer.valueOf(p1).compareTo(p2);

            }


        });


    }


    public List<BusinessMoney> getBusinessMoneyList(){
        return businessMoneyList;
    }


    @Override
    public void validSubscribe() {
       //if (businessDefineHome.getInstance().getFees().isEmpty() && businessDefineHome.getInstance().getFees()==null){
      //     facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR, "fee_not");
     //  }
    }

    @Override
    public boolean isPass() {
        return false;
    }



    @Override
    public boolean saveSubscribe() {

        return true;
    }
}
