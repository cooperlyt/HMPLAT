package com.dgsoft.house.owner.business;

import org.jboss.seam.annotations.Name;

/**
 * Created by cooper on 9/30/15.
 */
@Name("houseBusinessSelectCreate")
public class HouseBusinessSelectCreate extends OwnerBusinessCreateComponent{

    private static final String NORMAL_BIZ_BEGIN_PAGE = "/business/houseOwner/HouseBusinessSelectStart.xhtml";
    /**
     * 档案补录单业务多房屋启动页
     */
    //private static final String PATCH_BIZ_BEGIN_PAGE = "/business/houseOwner/MulitHouseBusinessPatch.xthml";
    /**
     * 档案补录单业务单房屋启动页
     */
    private static final String PATCH_BIZ_BEGIN_PAGE = "/business/houseOwner/SingleHouseBusinessPatch.xthml";

    @Override
    protected String getNormalBusinessPage() {
        return NORMAL_BIZ_BEGIN_PAGE;
    }

    @Override
    protected String getPatchBusinessPage() {
        return null;
    }
}
