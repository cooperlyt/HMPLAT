package com.dgsoft.house.action;

import com.dgsoft.common.system.RunParam;
import com.dgsoft.house.HouseEntityHome;
import com.dgsoft.house.HouseInfo;
import com.dgsoft.house.HouseProperty;
import com.dgsoft.house.HouseStatus;
import com.dgsoft.house.model.Build;
import com.dgsoft.house.model.House;
import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.model.BusinessBuild;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by cooper on 8/14/14.
 */
@Name("houseHome")
public class HouseHome extends HouseEntityHome<House> {

    @Factory("houseStatus")
    public HouseStatus[] getStatuses(){
        return HouseStatus.values();
    }

    @In(create = true)
    private OwnerEntityLoader ownerEntityLoader;

    public String updateHouse(){
        if (RunParam.instance().getBooleanParamValue("CHECK_PROJECT_SELL")){
            List<BusinessBuild> businessBuilds = ownerEntityLoader.getEntityManager().createQuery("select b from BusinessBuild b where b.buildCode = :buildCode and b.businessProject.ownerBusiness.status = 'COMPLETE'",BusinessBuild.class).
                    setParameter("buildCode",getInstance().getBuild().getId()).getResultList();
            if (!businessBuilds.isEmpty()){
                BusinessBuild build = businessBuilds.get(0);
                BigDecimal sellArea = BigDecimal.ZERO;
                int sellCount = 0;
                for(House h: getInstance().getBuild().getHouses()){
                    if (!h.isDeleted() && HouseProperty.SALE_HOUSE.equals(h.getHouseType())){
                        sellCount++;
                        sellArea = sellArea.add(h.getHouseArea());
                    }
                }
                if(sellCount != build.getHouseCount() || !sellArea.equals(build.getArea())){

                    getInstance().getBuild().getProject().setEnable(false);
                }
            }

        }

        return update();
    }
}
