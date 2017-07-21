package com.dgsoft.house.owner.business;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Logging;

/**
 * Created by cooper on 4/24/16.
 */
@Name("singleHouseInBusinessCreate")
public class SingleHouseInBusinessCreate extends OwnerBusinessCreateComponent{

    private static final String NORMAL_BIZ_BEGIN_PAGE = "/business/houseOwner/HouseInBusinessPick.xhtml";
    //private static final String PATCH_BIZ_BEGIN_PAGE = "/business/houseOwner/MulitHouseBusinessPatch.xthml";
    private static final String PATCH_BIZ_BEGIN_PAGE = "/business/houseOwner/SingleHouseBusinessPatch.xhtml";

    @In(create = true)
    private HouseInBusinessStart houseInBusinessStart;


    @Override
    protected String getNormalBusinessPage() {
        houseInBusinessStart.setSingleHouse(true);
        return NORMAL_BIZ_BEGIN_PAGE;
    }

    @Override
    protected String getPatchBusinessPage() {
        Logging.getLog(getClass()).debug("call PatchBusinessPage ----------" + PATCH_BIZ_BEGIN_PAGE);
        return PATCH_BIZ_BEGIN_PAGE;
    }

}
