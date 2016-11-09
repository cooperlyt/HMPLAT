package com.dgsoft.house.owner.business;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by cooper on 4/24/16.
 */
@Name("singleHouseInBusinessCreate")
public class SingleHouseInBusinessCreate extends OwnerBusinessCreateComponent{

    private static final String NORMAL_BIZ_BEGIN_PAGE = "/business/houseOwner/HouseInBusinessPick.xhtml";
    //private static final String PATCH_BIZ_BEGIN_PAGE = "/business/houseOwner/MulitHouseBusinessPatch.xthml";
    private static final String PATCH_BIZ_BEGIN_PAGE = "/business/houseOwner/SingleHouseBusinessPatch.xthml";

    @In(create = true)
    private HouseInBusinessStart houseInBusinessStart;


    @Override
    protected String getNormalBusinessPage() {
        houseInBusinessStart.setSingleHouse(true);
        return NORMAL_BIZ_BEGIN_PAGE;
    }

    @Override
    protected String getPatchBusinessPage() {
        return PATCH_BIZ_BEGIN_PAGE;
    }

}
