package com.dgsoft.house.action;

import com.dgsoft.common.GBT;
import com.dgsoft.common.system.RunParam;
import com.dgsoft.common.system.model.SystemParam;
import com.dgsoft.house.HouseEntityHome;
import com.dgsoft.house.model.Build;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by cooper on 7/29/14.
 */
@Name("buildHome")
public class BuildHome extends HouseEntityHome<Build> {

    @In
    private RunParam runParam;

    public boolean isHaveHouse(){
        return !getInstance().getHouses().isEmpty();
    }

    private String genBuildCode(){
        switch (GBT.HouseIdGenType.valueOf(runParam.getStringParamValue("house.id.gentype"))){
            case JDJT246_3:
                getInstance().getCompleteDate().trim();
                break;
            case JDJT246_4:
                break;
            case JDJT246_5:
                break;
            case JDJT246_6:
                break;
        }


    }

    public String genHouseOrder(String order){
        return GBT.getJDJT246(getInstance().getProject().getSection().getDistrict().getId(),genBuildCode(),order);
    }



}
