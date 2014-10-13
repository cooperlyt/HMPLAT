package com.dgsoft.house.action;

import com.dgsoft.common.GBT;
import com.dgsoft.common.SetLinkList;
import com.dgsoft.common.system.NumberBuilder;
import com.dgsoft.common.system.RunParam;
import com.dgsoft.common.system.model.NumberPool;
import com.dgsoft.common.system.model.SystemParam;
import com.dgsoft.house.HouseEntityHome;
import com.dgsoft.house.model.Build;
import com.dgsoft.house.model.BuildGridMap;
import com.dgsoft.house.model.House;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by cooper on 7/29/14.
 */
@Name("buildHome")
public class BuildHome extends HouseEntityHome<Build> {

    @In
    private FacesMessages facesMessages;

    private SetLinkList<House> houses;

    public SetLinkList<House> getHouses() {
        if (houses == null){
            houses = new SetLinkList<House>(getInstance().getHouses());
        }
        return houses;
    }

    @Override
    protected void initInstance(){
        super.initInstance();
        houses = null;
    }

    public boolean isHaveHouse() {
        return !getInstance().getHouses().isEmpty();
    }

    private String getBuildCode() {
        if (isManaged()) {
            return getInstance().getId().substring(0,21);
        } else {
            throw new IllegalArgumentException("build not manager!");
        }
    }


    private String genHouseOrder() {
        String result = GBT.getJDJT246(getBuildCode(), getInstance().getNextHouseOrder());
        getInstance().setNextHouseOrder(getInstance().getNextHouseOrder() + 1);
        return result;
    }

    @Override
    protected boolean wire(){
        for(House house: getHouses()){
            if ((house.getId() == null) || (house.getId().trim().equals(""))){
                house.setId(genHouseOrder());
            }
        }
        return true;
    }

    @Override
    protected boolean verifyRemoveAvailable() {
        if (isManaged()){
           if(getEntityManager().createQuery("select count(house.id) from House house where house.build.id = :buildId", Long.class).
                    setParameter("buildId",getInstance().getId()).getSingleResult() > 0){
               facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR,"BuildCantDelete");
               return false;
           }
        }
        return true;
    }


    public List<BuildGridMap> getBuildGridPages(){
        List<BuildGridMap> result = new ArrayList<BuildGridMap>(getInstance().getBuildGridMaps());
        Collections.sort(result,new Comparator<BuildGridMap>() {
            @Override
            public int compare(BuildGridMap o1, BuildGridMap o2) {
                return (new Integer(o1.getOrder())).compareTo(o2.getOrder());
            }
        });
        return result;
    }

    @Override
    protected boolean verifyUpdateAvailable() {
        //TODO verify House Data
        return true;
    }

}
