package com.dgsoft.house.action;

import com.dgsoft.house.model.Build;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import static com.dgsoft.house.model.House.HouseDataSource.MAPPING;

/**
 * Created by cooper on 8/10/16.
 */
@Name("createHouse")
public class CreateHouse {

    @In(create = true)
    private HouseHome houseHome;

    @In(create = true)
    private BuildHome buildHome;

    @In
    private FacesMessages facesMessages;

    private String mapNumber;

    private String blockNumber;

    private String buildNumber;

    public String getMapNumber() {
        return mapNumber;
    }

    public void setMapNumber(String mapNumber) {
        this.mapNumber = mapNumber;
    }

    public String getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(String blockNumber) {
        this.blockNumber = blockNumber;
    }

    public String getBuildNumber() {
        return buildNumber;
    }

    public void setBuildNumber(String buildNumber) {
        this.buildNumber = buildNumber;
    }

    public String saveHouse(){

        Build build;
        try {
            build = houseHome.getEntityManager().createQuery("select build from Build build where build.mapNumber =:mapNumber and build.blockNo = :blockNumber and build.buildNo = :buildNumber", Build.class)
                    .setParameter("mapNumber", mapNumber).setParameter("blockNumber", blockNumber).setParameter("buildNumber", buildNumber).getSingleResult();

           if( houseHome.getEntityManager().createQuery("select count(house) from House house where house.build.id =:buildId and house.houseOrder = :houseOrder",Long.class)
                    .setParameter("buildId",build.getId())
                    .setParameter("houseOrder",houseHome.getInstance().getHouseOrder()).getSingleResult().intValue() > 0){

               facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR,"HouseOrderConflictTemplate",houseHome.getInstance().getHouseOrder());
               return null;
           }
        }catch (NoResultException e){
            facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR,"buildNotExists");

            return null;
        }catch (NonUniqueResultException e){
            facesMessages.addFromResourceBundle(StatusMessage.Severity.FATAL,"ConflictMBB");

            return null;
        }

        buildHome.setId(build.getId());
        houseHome.getInstance().setId(buildHome.genHouseOrder());
        houseHome.getInstance().setBuild(buildHome.getInstance());
        houseHome.getInstance().setDataSource(MAPPING);
        buildHome.getInstance().getHouses().add(houseHome.getInstance());

        return buildHome.update();
    }
}
