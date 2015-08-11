package com.dgsoft.common.system.business;

import com.dgsoft.common.system.action.BusinessDefineHome;
import com.dgsoft.common.system.model.BusinessDefine;
import org.jboss.seam.Component;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.log.Logging;

/**
 * Created by cooper on 8/11/15.
 */
@Name("businessCreateBegin")
public class BusinessCreateBegin {


    @RequestParameter
    private String bussinessDefineId;


    @In(create = true)
    private BusinessDefineHome businessDefineHome;

    public String startCreate(){

        businessDefineHome.setId(bussinessDefineId);
        return ((BusinessCreateComponent) Component.getInstance(businessDefineHome.getInstance().getStartPage(),true,true)).startCreate();
    }


    public String searchModify(){
        businessDefineHome.setId(bussinessDefineId);
        return ((BusinessCreateComponent) Component.getInstance(businessDefineHome.getInstance().getStartPage(),true,true)).searchModify();

    }

}
