package com.dgsoft.house.owner.business;

import com.dgsoft.common.system.business.BusinessCreateComponent;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.FlushModeType;
import org.jboss.seam.annotations.Name;

/**
 * Created by cooper on 8/14/15.
 */

@Name("projectBusinessCreateComponent")
public class ProjectBusinessCreateComponent implements BusinessCreateComponent {

    private static final String PROJECT_START_PAGE = "";

    @Override
    @Begin(flushMode = FlushModeType.MANUAL)
    public String startCreate() {
        return PROJECT_START_PAGE;
    }

    @Override
    public String searchModify() {
        return null;
    }

}
