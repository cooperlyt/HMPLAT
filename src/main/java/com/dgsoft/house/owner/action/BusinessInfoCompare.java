package com.dgsoft.house.owner.action;

import com.dgsoft.common.helper.CollectionTools;
import com.dgsoft.house.HouseEntityLoader;
import com.dgsoft.house.HouseInfo;
import com.dgsoft.house.model.House;
import com.dgsoft.house.owner.HouseInfoCompare;
import com.dgsoft.house.owner.model.HouseBusiness;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cooper on 7/22/15.
 */
@Name("businessInfoCompare")
@Scope(ScopeType.CONVERSATION)
public class BusinessInfoCompare {

    @In
    private OwnerBusinessHome ownerBusinessHome;

    @In(create = true)
    private HouseEntityLoader houseEntityLoader;

    public List<Map.Entry<String, List<HouseInfoCompare.ChangeData>>> getChangeInfo(){
        Map<String,List<HouseInfoCompare.ChangeData>> result = new HashMap<String, List<HouseInfoCompare.ChangeData>>();
        for(HouseBusiness houseBusiness: ownerBusinessHome.getInstance().getHouseBusinesses()){
            House house = houseEntityLoader.getEntityManager().find(House.class,houseBusiness.getHouseCode());
            if (house != null){
                List<HouseInfoCompare.ChangeData> changeDataList = HouseInfoCompare.compare(houseBusiness.getStartBusinessHouse(),house, true);
                if ((changeDataList != null) && !changeDataList.isEmpty())
                    result.put(houseBusiness.getHouseCode(), changeDataList);
            }
        }


        //TODO PROJECT
        return CollectionTools.instance().mapToList(result);
    }


}
