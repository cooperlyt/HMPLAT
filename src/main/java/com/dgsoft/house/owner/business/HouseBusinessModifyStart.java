package com.dgsoft.house.owner.business;

import com.dgsoft.common.system.action.BusinessDefineHome;
import com.dgsoft.common.system.business.BusinessInstance;
import com.dgsoft.house.HouseEntityLoader;
import com.dgsoft.house.HouseInfo;
import com.dgsoft.house.model.House;
import com.dgsoft.house.owner.HouseInfoCompare;
import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessProject;
import com.dgsoft.house.owner.model.HouseBusiness;
import com.dgsoft.house.owner.model.OwnerBusiness;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.web.RequestParameter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cooper on 7/31/15.
 */
@Name("houseBusinessModifyStart")
@Scope(ScopeType.CONVERSATION)
public class HouseBusinessModifyStart {


    @RequestParameter
    private String selectBusinessId;


    @In(create = true)
    private OwnerEntityLoader ownerEntityLoader;

    @In(create = true)
    private HouseEntityLoader houseEntityLoader;

    @In(create = true)
    private OwnerBusinessHome ownerBusinessHome;

    @In(create = true)
    private BusinessDefineHome businessDefineHome;


    private Map<String,List<HouseInfoCompare.ChangeData>> houseChangeDatas;

    public String startModify(){
        //TODO ROLE
        OwnerBusiness selectOwnerBusiness = ownerEntityLoader.getEntityManager().find(OwnerBusiness.class, selectBusinessId);
        cloneData(selectOwnerBusiness);

        houseChangeDatas = new HashMap<String, List<HouseInfoCompare.ChangeData>>();
        for (HouseBusiness houseBusiness: selectOwnerBusiness.getHouseBusinesses()) {
            HouseInfo mapHouse = houseEntityLoader.getEntityManager().find(House.class,houseBusiness.getHouseCode());
            if (mapHouse != null) {
                HouseInfoCompare.compare(houseBusiness.getStartBusinessHouse(), mapHouse);
            }

        }
        for(BusinessProject project: selectOwnerBusiness.getBusinessProjects()){

        }

        return null;
    }



    private void cloneData(OwnerBusiness ownerBusiness){
        //TODO needFile
        ownerBusiness.setStatus(BusinessInstance.BusinessStatus.MODIFYING);

        businessDefineHome.setId(ownerBusiness.getDefineId());
        ownerBusinessHome.clearInstance();
        ownerBusinessHome.getInstance().setSelectBusiness(ownerBusiness);
        ownerBusinessHome.getInstance().setType(BusinessInstance.BusinessType.MODIFY_BIZ);

        //TODO clone

    }



}
