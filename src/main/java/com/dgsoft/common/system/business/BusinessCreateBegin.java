package com.dgsoft.common.system.business;

import com.dgsoft.common.system.model.BusinessDefine;
import org.jboss.seam.Component;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by cooper on 8/11/15.
 */
@Name("businessCreateBegin")
public class BusinessCreateBegin {

    @In
    private BusinessDefine businessDefine;


    public String startCreate(){

        return ((BusinessCreateComponent) Component.getInstance(businessDefine.getStartPage(),true,true)).startCreate();
    }


    public String searchModify(){
        return ((BusinessCreateComponent) Component.getInstance(businessDefine.getStartPage(),true,true)).searchModify();

    }

}
