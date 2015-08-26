package com.dgsoft.house.owner.business;

import com.dgsoft.common.BatchOperData;
import com.dgsoft.house.action.ProjectHome;
import com.dgsoft.house.model.Build;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cooper on 8/14/15.
 */

@Name("projectBusinessCreateComponent")
public class ProjectBusinessCreateComponent extends OwnerBusinessCreateComponent {

    private static final String PROJECT_BUSINESS_START_PAGE = "/business/houseOwner/ProjectBusinessStart.xhtml";
    private static final String PROJECT_BUSINESS_PATCH_PAGE = "";

    @In(create = true)
    private ProjectBusinessStart projectBusinessStart;

    @Override
    protected String getNormalBusinessPage() {
        projectBusinessStart.setForProject(true);
        return PROJECT_BUSINESS_START_PAGE;
    }

    @Override
    protected String getPatchBusinessPage() {
        projectBusinessStart.setForProject(true);
        return PROJECT_BUSINESS_PATCH_PAGE;
    }

    @Override
    public String searchModify(){
        return null;
    }


}
