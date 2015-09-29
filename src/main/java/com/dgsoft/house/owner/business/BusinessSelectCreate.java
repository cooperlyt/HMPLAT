package com.dgsoft.house.owner.business;

import org.jboss.seam.annotations.Name;

/**
 * Created by cooper on 9/29/15.
 */
@Name("businessSelectCreate")
public class BusinessSelectCreate extends OwnerBusinessCreateComponent{

    private static final String NORMAL_BIZ_BEGIN_PAGE = "/business/houseOwner/MulitHouseStart.xhtml";
    private static final String PATCH_BIZ_BEGIN_PAGE = "/business/houseOwner/MulitHouseBusinessPatch.xthml";


    @Override
    protected String getNormalBusinessPage() {
        return null;
    }

    @Override
    protected String getPatchBusinessPage() {
        return null;
    }
}
