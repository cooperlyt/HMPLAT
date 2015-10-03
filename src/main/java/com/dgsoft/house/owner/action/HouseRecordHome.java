package com.dgsoft.house.owner.action;

import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.model.HouseRecord;
import org.jboss.seam.annotations.Name;

import javax.persistence.NoResultException;

/**
 * Created by cooper on 10/3/15.
 */
@Name("houseRecordHome")
public class HouseRecordHome extends OwnerEntityHome<HouseRecord> {


    @Override
    protected HouseRecord loadInstance(){
        try {
            return getEntityManager().createQuery("select hr from HouseRecord hr " +
                    "left join fetch hr.businessHouse house " +
                    "left join fetch house.businessHouseOwner owner " +
                    "left join fetch owner.makeCard ownerCard " +
                    "left join fetch house.houseBusinessForAfter houseBusiness " +
                    "left join fetch houseBusiness.ownerBusiness ownerBusiness " +
                    "left join fetch houseBusiness.recordStore rs " +
                    "left join fetch house.businessPools pool " +
                    "left join fetch pool.makeCard poolCard where hr.houseCode = :houseCode", HouseRecord.class)
                    .setParameter("houseCode", getId()).getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }

}
