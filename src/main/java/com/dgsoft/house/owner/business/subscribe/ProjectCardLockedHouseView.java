package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.HouseEntityLoader;
import com.dgsoft.house.model.House;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessBuild;
import com.dgsoft.house.owner.model.ProjectExceptHouse;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cooper on 3/22/16.
 */
@Name("projectCardLockedHouseView")
public class ProjectCardLockedHouseView {

    @In()
    private OwnerBusinessHome ownerBusinessHome;

    @In(create = true)
    private HouseEntityLoader houseEntityLoader;

    private List<House> resultList;


    public List<House> getResultList() {
        if (resultList == null){
            List<String> exceptHouses = new ArrayList<String>();
            for (BusinessBuild build: ownerBusinessHome.getInstance().getBusinessProject().getBusinessBuilds()){
                for(ProjectExceptHouse exceptHouse: build.getProjectExceptHouses()){
                    exceptHouses.add(exceptHouse.getHouseCode());
                }
            }

            if (exceptHouses.isEmpty()){
                resultList = new ArrayList<House>(0);
            }else{

                resultList = houseEntityLoader.getEntityManager().createQuery("select h from House h where h.id in (:houseCodes)",House.class)
                        .setParameter("houseCodes",exceptHouses).getResultList();

            }


        }
        return resultList;
    }

}
