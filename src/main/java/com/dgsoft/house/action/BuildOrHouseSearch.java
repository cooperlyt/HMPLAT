package com.dgsoft.house.action;

import com.dgsoft.house.model.House;
import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.action.OwnerBuildGridMap;
import com.dgsoft.house.owner.model.HouseRecord;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

/**
 * Created by cooper on 11/2/15.
 */
@Name("buildOrHouseSearch")
@Scope(ScopeType.CONVERSATION)
public class BuildOrHouseSearch {


    @In(create = true)
    private OwnerEntityLoader ownerEntityLoader;

    @In(create = true)
    private BuildList buildList;

    @In(create = true)
    private OwnerBuildGridMap ownerBuildGridMap;

    private boolean returnHouse;

    public boolean isReturnHouse() {
        return returnHouse;
    }


    public void search(){
        if (buildList.getEntityManager().find(House.class,buildList.getSearchKey()) != null ||
                ownerEntityLoader.getEntityManager().find(HouseRecord.class,buildList.getSearchKey()) != null){
            ownerBuildGridMap.setSelectBizHouseId(buildList.getSearchKey());
            returnHouse = true;
        }else{
            returnHouse = false;
        }

    }

}
