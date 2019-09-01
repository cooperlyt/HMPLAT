package com.dgsoft.house.owner.business;


import org.jboss.seam.annotations.*;

/**
 * Created by wxy on 2019-08-28.
 */
@Name("projectCheckCreateComponent")
public class ProjectCheckCreateComponent extends OwnerBusinessCreateComponent {

    private static final String PROJECT_BUSINESS_START_PAGE = "/business/houseOwner/ProjectCheckStart.xhtml";
    private static final String PROJECT_BUSINESS_PATCH_PAGE ="";

    @In(create = true)
    private ProjectCheckStart projectCheckStart;

    @Override
    protected String getNormalBusinessPage() {

        return PROJECT_BUSINESS_START_PAGE;
    }

    @Override
    protected String getPatchBusinessPage() {
        return PROJECT_BUSINESS_PATCH_PAGE;
    }
}
