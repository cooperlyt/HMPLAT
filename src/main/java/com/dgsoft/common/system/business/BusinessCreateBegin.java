package com.dgsoft.common.system.business;

import com.dgsoft.common.system.action.BusinessDefineHome;
import com.dgsoft.common.system.model.BusinessDefine;
import org.jboss.seam.Component;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.web.RequestParameter;

/**
 * Created by cooper on 8/11/15.
 */
@Name("businessCreateBegin")
public class BusinessCreateBegin {

    @In
    private BusinessDefine businessDefine;

    @RequestParameter
    private String businessDefineId;


    @In(create = true)
    private BusinessDefineHome businessDefineHome;

    public String startCreate(){
        businessDefineHome.setId(businessDefineId);
        return ((BusinessCreateComponent) Component.getInstance(businessDefine.getStartPage(),true,true)).startCreate();
    }


    public String searchModify(){
        businessDefineHome.setId(businessDefineId);
        return ((BusinessCreateComponent) Component.getInstance(businessDefine.getStartPage(),true,true)).searchModify();

    }

}
