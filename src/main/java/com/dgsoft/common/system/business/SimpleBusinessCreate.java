package com.dgsoft.common.system.business;

import com.dgsoft.common.system.action.BusinessDefineHome;
import com.dgsoft.common.system.model.BusinessDefine;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created with IntelliJ IDEA.
 * User: cooperlee
 * Date: 12/04/14
 * Time: 14:46
 */
@Name("businessCreate")
public class SimpleBusinessCreate extends BusinessCreate{

    @In
    private BusinessDefineHome businessDefineHome;

    private String businessKey;

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    @Override
    protected String getBusinessKey() {
        return null;
    }

    @Override
    protected BusinessDefine getBusinessDefine() {
        return businessDefineHome.getInstance();
    }
}
