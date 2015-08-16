package com.dgsoft.house.owner.business.subscribe.complete;


import com.dgsoft.common.system.AuthenticationInfo;
import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessEmp;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import java.util.Date;

/**
 * Created by wxy on 2015-08-16.
 * 受理人添加
 */
@Name("peopleApplyFill")
public class PeopleApplyFill implements TaskCompleteSubscribeComponent {

    @In
    private OwnerBusinessHome ownerBusinessHome;

    @In
    private AuthenticationInfo authInfo;


    @Override
    public void valid() {

    }

    @Override
    public boolean isPass() {
        if (authInfo.getLoginEmployee()!=null ){
            return true;
        }else
        return false;
    }

    @Override
    public void complete() {

        BusinessEmp businessEmp = new BusinessEmp();
        businessEmp.setOwnerBusiness(ownerBusinessHome.getInstance());
        businessEmp.setEmpName(authInfo.getLoginEmployee().getPersonName());
        businessEmp.setType(BusinessEmp.EmpType.APPLY_EMP);
        businessEmp.setEmpCode(authInfo.getLoginEmployee().getId());
        businessEmp.setOperDate(new Date());
        


    }
}
