package com.dgsoft.house.owner.business;

import com.dgsoft.common.system.action.BusinessDefineHome;
import com.dgsoft.common.system.business.BusinessCreate;
import com.dgsoft.common.system.business.TaskSubscribeComponent;
import com.dgsoft.common.system.model.BusinessDefine;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.HouseBusiness;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.international.StatusMessage;
import org.jboss.seam.log.Logging;
import sun.rmi.runtime.Log;

/**
 * Created by cooper on 1/15/15.
 */
@Name("ownerBusinessCreate")
public class OwnerBusinessCreate extends BusinessCreate {


    @In
    private BusinessDefineHome businessDefineHome;

    @In
    private OwnerBusinessHome ownerBusinessHome;

    @Override
    protected boolean verifyCreate() {
        boolean verify;
        if (businessDefineHome.isSubscribesPass() && businessDefineHome.isCompletePass()){
            businessDefineHome.completeTask();

            String result = ownerBusinessHome.persist();
            verify = ((result != null) && result.equals("persisted") && (businessDefineHome.getInstance().getWfName() != null) &&
                    !businessDefineHome.getInstance().getWfName().trim().equals(""));

        }else{
            verify = false;
        }

        if (!verify){
            throw new IllegalArgumentException("create business fail");
        }

        return verify;
    }


    @Override
    protected String getBusinessKey() {
        return ownerBusinessHome.getInstance().getId();
    }

    @Override
    protected BusinessDefine getBusinessDefine() {
        return businessDefineHome.getInstance();
    }
}
