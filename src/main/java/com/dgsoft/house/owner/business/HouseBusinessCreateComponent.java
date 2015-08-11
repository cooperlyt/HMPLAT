package com.dgsoft.house.owner.business;

import com.dgsoft.common.system.action.BusinessDefineHome;
import com.dgsoft.common.system.business.BusinessCreateComponent;
import com.dgsoft.common.system.business.Subscribe;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.FlushModeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.web.RequestParameter;

/**
 * Created by cooper on 8/11/15.
 */
public abstract class HouseBusinessCreateComponent implements BusinessCreateComponent {

    private static final String MODIFY_SEARCH_PAGE = "/business/houseOwner/HouseBusinessModify.xhtml";

    @In
    private BusinessDefineHome businessDefineHome;

    @RequestParameter
    private boolean normalBiz;

    protected abstract String getNormalBusinessPage();

    protected abstract String getPatchBusinessPage();

    @Override
    @Begin(flushMode = FlushModeType.MANUAL)
    public String startCreate() {
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
        return MODIFY_SEARCH_PAGE;

    }

}
