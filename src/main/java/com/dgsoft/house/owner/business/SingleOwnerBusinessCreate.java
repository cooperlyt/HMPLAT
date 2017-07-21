package com.dgsoft.house.owner.business;

import com.dgsoft.common.system.business.BusinessInstance;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import org.jboss.seam.Component;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Logging;

/**
 * Created by cooper on 8/11/15.
 */
@Name("singleHouseBusinessCreate")
public class SingleOwnerBusinessCreate extends OwnerBusinessCreateComponent {


    private static final String NORMAL_BIZ_BEGIN_PAGE = "/business/houseOwner/SingleHouseStart.xhtml";
    private static final String PATCH_BIZ_BEGIN_PAGE = "/business/houseOwner/SingleHouseBusinessPatch.xhtml";



    @Override
    protected String getNormalBusinessPage() {
        return NORMAL_BIZ_BEGIN_PAGE;
    }

    @Override
    protected String getPatchBusinessPage() {
        Logging.getLog(getClass()).debug("singleHouseBusinessCreate get patch page:" + PATCH_BIZ_BEGIN_PAGE);

        ((OwnerBusinessHome) Component.getInstance("ownerBusinessHome",true,true)).getInstance().setSource(BusinessInstance.BusinessSource.BIZ_AFTER_SAVE);

        return PATCH_BIZ_BEGIN_PAGE;
    }

}
