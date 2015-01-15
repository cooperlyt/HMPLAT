package com.dgsoft.house.owner.business;

import com.dgsoft.common.system.action.BusinessDefineHome;
import com.dgsoft.common.system.business.BusinessCreate;
import com.dgsoft.common.system.model.BusinessDefine;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

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

        String result = ownerBusinessHome.persist();
        return ((result != null) && result.equals("persisted") && (businessDefineHome.getInstance().getWfName() != null) &&
                !businessDefineHome.getInstance().getWfName().trim().equals(""));
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
