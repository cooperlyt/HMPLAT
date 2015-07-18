package com.dgsoft.common.system.action;

import com.dgsoft.common.system.SystemEntityHome;
import com.dgsoft.common.system.model.BusinessCategory;
import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.*;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.bpm.ManagedJbpmContext;
import org.jboss.seam.framework.EntityQuery;
import org.jbpm.graph.def.ProcessDefinition;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: cooperlee
 * Date: 6/25/13
 * Time: 1:28 PM
 */

@Name("businessDefineConfig")
public class BusinessDefineConfig extends SystemEntityHome<BusinessCategory>{



    @In(create = true)
    private BusinessDefineHome businessDefineHome;


    public void createBusinessDefine(){
        businessDefineHome.clearInstance();
        businessDefineHome.getInstance().setBusinessCategory(getInstance());
    }

    @Factory(value = "jpdlNameList", scope = ScopeType.CONVERSATION)
    public List<String> getJpdlNameList() {
        List<String> result = new ArrayList<String>();
        for (Object pd : ManagedJbpmContext.instance().getGraphSession().findLatestProcessDefinitions()) {
            result.add(String.valueOf(((ProcessDefinition) pd).getName()));
        }
        return result;
    }

    @Override
    public void create(){
        super.create();
         if(!((EntityQuery)Component.getInstance("businessCategoryList",true,true)).getResultList().isEmpty()){
             setId(((BusinessCategory) ((EntityQuery) Component.getInstance("businessCategoryList", true, true)).getResultList().get(0)).getId());
         }
    }


}
