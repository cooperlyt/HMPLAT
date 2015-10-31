package com.dgsoft.house.owner.business;

import com.dgsoft.common.system.action.BusinessDefineHome;
import com.dgsoft.common.system.business.BusinessCreateComponent;
import com.dgsoft.common.system.business.Subscribe;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.FlushModeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.web.RequestParameter;

import java.util.Date;

/**
 * Created by cooper on 8/11/15.
 */
public abstract class OwnerBusinessCreateComponent implements BusinessCreateComponent {

    private static final String MODIFY_SEARCH_PAGE = "/business/houseOwner/HouseBusinessModify.xhtml";

    @In
    private BusinessDefineHome businessDefineHome;

    @Out(value = "businessCreateTime", scope = ScopeType.CONVERSATION)
    private Date businessCreateTime;


    @RequestParameter
    private boolean normalBiz;

    protected abstract String getNormalBusinessPage();

    protected abstract String getPatchBusinessPage();

    @Override
    @Begin(flushMode = FlushModeType.MANUAL)
    public String startCreate() {
        businessCreateTime = new Date();
        if (normalBiz){
            businessDefineHome.setTaskName(Subscribe.CREATE_TASK_NAME);
            return getNormalBusinessPage();
        }else{
            businessDefineHome.setTaskName(Subscribe.PATCH_TASK_NAME);
            return getPatchBusinessPage();
        }
    }

    @Override
    public String searchModify(){
        businessCreateTime = new Date();
        return MODIFY_SEARCH_PAGE;

    }

}
