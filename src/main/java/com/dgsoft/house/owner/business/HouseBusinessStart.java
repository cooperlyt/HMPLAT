package com.dgsoft.house.owner.business;

import com.dgsoft.common.system.action.BusinessDefineHome;
import com.dgsoft.house.HouseEntityLoader;
import com.dgsoft.house.model.House;
import com.dgsoft.house.owner.action.HouseBusinessHome;
import com.dgsoft.house.owner.model.BusinessHouse;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Logging;

/**
 * Created by cooper on 8/28/14.
 */
@Name("houseBusinessStart")
@Scope(ScopeType.CONVERSATION)
public class HouseBusinessStart {

    @In
    private BusinessDefineHome businessDefineHome;

    @In(create = true)
    private HouseBusinessHome houseBusinessHome;

    private static final String BUSINESS_START_PAGE = "/business/houseOwner/BizStartSubscribe.xhtml";

    private String selectHouseId;

    public String getSelectHouseId() {
        return selectHouseId;
    }

    public void setSelectHouseId(String selectHouseId) {
        this.selectHouseId = selectHouseId;
    }

    @In(create = true)
    private HouseEntityLoader houseEntityLoader;

    public String singleHouseSelectet() {
        Logging.getLog(getClass()).debug("singleHouseSelectet:" + selectHouseId);

        houseBusinessHome.getInstance().getBusinessHouses().clear();
        houseBusinessHome.getInstance().getBusinessHouses().add(new BusinessHouse(houseEntityLoader.getEntityManager().find(House.class,selectHouseId)));

        return BUSINESS_START_PAGE;
    }


    public String mulitHouseSelect() {


        return BUSINESS_START_PAGE;
    }

    //TODO valid House



}
