package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.common.system.AuthenticationInfo;
import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessEmp;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import java.util.Date;

/**
 * Created by wxy on 2015-08-17.
 * 登记薄 意见 审核人
 */
@Name("peopleRegEmpSubscribe")
public class PeopleRegEmpSubscribe extends OwnerEntityHome<BusinessEmp> {

    @In
    private OwnerBusinessHome ownerBusinessHome;

    @In
    private AuthenticationInfo authInfo;


    @Override
    public BusinessEmp createInstance(){
        return new BusinessEmp(BusinessEmp.EmpType.REG_EMP);
    }

    @Override
    public void create(){
        super.create();
        for(BusinessEmp businessEmp: ownerBusinessHome.getInstance().getBusinessEmps()){
            if (businessEmp.getType().equals(BusinessEmp.EmpType.REG_EMP)){
                ownerBusinessHome.getInstance().getBusinessEmps().remove(businessEmp);
                break;
            }

        }
        getInstance().setEmpName(authInfo.getLoginEmployee().getPersonName());
        getInstance().setEmpCode(authInfo.getLoginEmployee().getId());
        getInstance().setOperDate(new Date());
        getInstance().setOwnerBusiness(ownerBusinessHome.getInstance());
        ownerBusinessHome.getInstance().setRegTime(getInstance().getOperDate());
        ownerBusinessHome.getInstance().getBusinessEmps().add(getInstance());

    }





}
