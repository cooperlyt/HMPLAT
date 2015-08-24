package com.dgsoft.house.owner.business;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by cooper on 8/24/15.
 */
@Name("mapHouseBusinessCreateComponent")
public class MapHouseBusinessCreateComponent extends OwnerBusinessCreateComponent {

    private static final String PROJECT_BUSINESS_START_PAGE = "/business/houseOwner/ProjectBusinessStart.xhtml";
    private static final String PROJECT_BUSINESS_PATCH_PAGE = "";

    @In(create = true)
    private ProjectBusinessStart projectBusinessStart;

    @Override
    protected String getNormalBusinessPage() {
        projectBusinessStart.setForProject(false);
        return PROJECT_BUSINESS_START_PAGE;
    }

    @Override
    protected String getPatchBusinessPage() {
        projectBusinessStart.setForProject(false);
        return PROJECT_BUSINESS_PATCH_PAGE;
    }

    @Override
    public String searchModify(){
        return null;
    }


}