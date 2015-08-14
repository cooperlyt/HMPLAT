package com.dgsoft.house.owner.business;

import org.jboss.seam.annotations.Name;

/**
 * Created by cooper on 8/14/15.
 */

@Name("projectBusinessCreateComponent")
public class ProjectBusinessCreateComponent extends OwnerBusinessCreateComponent {

    private static final String PROJECT_BUSINESS_START_PAGE = "/business/houseOwner/ProjectBusinessStart.xhtml";
    private static final String PROJECT_BUSINESS_PATCH_PAGE = "";

    @Override
    protected String getNormalBusinessPage() {
        return PROJECT_BUSINESS_START_PAGE;
    }

    @Override
    protected String getPatchBusinessPage() {
        return PROJECT_BUSINESS_PATCH_PAGE;
    }

    @Override
    public String searchModify(){
        return null;
    }

}
