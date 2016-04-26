package com.dgsoft.house.owner.total;

import com.dgsoft.house.HouseInfo;
import com.dgsoft.house.model.House;
import com.dgsoft.house.owner.model.HouseRecord;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cooper on 4/26/16.
 */
@Name("totalHouseLimit")
public class TotalHouseLimit {

    @In(create = true)
    private EntityManager houseEntityManager;

    @In(create = true)
    private EntityManager ownerEntityManager;

    private String searchKey;

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public void export(){

        String[] keys = searchKey.split(",");
        Map<String,HouseInfo> data = new HashMap<String, HouseInfo>();


        for(String key : keys){
            String[] mbbh = key.split("-");
            if (mbbh.length == 4){
                try {
                    data.put(key,ownerEntityManager.createQuery("select houseRecord from HouseRecord houseRecord where houseRecord.businessHouse.mapNumber = :mapNumber and houseRecord.businessHouse.blockNo = :blockNumber and houseRecord.businessHouse.buildNo = :buildNumber and houseRecord.businessHouse.houseOrder = :houseOrder ", HouseRecord.class)
                            .setParameter("mapNumber", mbbh[0]).setParameter("blockNumber", mbbh[1]).setParameter("buildNumber", mbbh[2]).setParameter("houseOrder", mbbh[3]).getSingleResult().getBusinessHouse());
                }catch (NoResultException e){
                    data.put(key,null);
                }
            }
        }

        for(Map.Entry<String,HouseInfo> entry: data.entrySet()){
            if (entry.getValue() == null){
                String[] mbbh = entry.getKey().split("-");
                if (mbbh.length == 4){
                    try {
                        entry.setValue(houseEntityManager.createQuery("select house from House house where house.deleted = false and house.build.mapNumber = :mapNumber and house.build.blockNo = :blockNumber and house.build.buildNo = :buildNumber and house.houseOrder = :houseOrder", House.class)
                                .setParameter("mapNumber", mbbh[0]).setParameter("blockNumber", mbbh[1]).setParameter("buildNumber", mbbh[2]).setParameter("houseOrder", mbbh[3]).getSingleResult());
                    }catch (NoResultException e){
                    }
                }//WP151
              //  ownerEntityManager.createQuery("select houseBusiness from HouseBusiness houseBusiness where houseBusiness.canceled = false and houseBusiness.houseCode =:houseCode and houseBusiness.ownerBusiness.status in ('COMPLETE','MODIFYING') and houseBusiness.ownerBusiness.defineId = 'WP73'");
            }
        }




    }


}
