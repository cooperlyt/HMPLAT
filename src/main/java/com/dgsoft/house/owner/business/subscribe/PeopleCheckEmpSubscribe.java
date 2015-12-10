package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.common.system.AuthenticationInfo;
import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessEmp;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import java.util.Date;

/**
 * Created by wxy on 2015-12-10.
 * 审核意见编辑
 */
@Name("peopleCheckEmpSubscribe")
public class PeopleCheckEmpSubscribe extends OwnerEntityHome<BusinessEmp>{


    @In
    private OwnerBusinessHome ownerBusinessHome;

    @In
    private AuthenticationInfo authInfo;

    @Override
    public BusinessEmp createInstance(){
        return new BusinessEmp(BusinessEmp.EmpType.CHECK_EMP);
    }

    @Override
    public void create() {
        super.create();
        for (BusinessEmp businessEmp : ownerBusinessHome.getInstance().getBusinessEmps()) {
            if (businessEmp.getType().equals(BusinessEmp.EmpType.CHECK_EMP)) {
                setId(businessEmp.getId());
                return;

            }

        }

        getInstance().setEmpName(authInfo.getLoginEmployee().getPersonName());
        getInstance().setEmpCode(authInfo.getLoginEmployee().getId());
        getInstance().setOperDate(new Date());
        getInstance().setOwnerBusiness(ownerBusinessHome.getInstance());
        ownerBusinessHome.getInstance().setApplyTime(getInstance().getOperDate());
        ownerBusinessHome.getInstance().getBusinessEmps().add(getInstance());
    }

}
