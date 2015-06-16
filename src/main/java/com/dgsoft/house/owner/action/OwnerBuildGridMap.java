package com.dgsoft.house.owner.action;

import com.dgsoft.house.HouseEntityHome;
import com.dgsoft.house.HouseEntityQuery;
import com.dgsoft.house.action.BuildHome;
import com.dgsoft.house.model.BuildGridMap;
import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.model.HouseRecord;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import java.util.List;

/**
 * Created by cooper on 6/12/15.
 */

@Name("ownerBuildGridMap")
@Scope(ScopeType.CONVERSATION)
public class OwnerBuildGridMap extends HouseEntityQuery<BuildGridMap>{


    @In
    private BuildHome buildHome;


    @In(create = true)
    private OwnerEntityLoader ownerEntityLoader;





}
