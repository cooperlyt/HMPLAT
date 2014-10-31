package com.dgsoft.house.owner;

import com.dgsoft.house.HouseEntityLoader;
import com.dgsoft.house.HouseInfo;
import com.dgsoft.house.model.House;
import com.dgsoft.house.owner.model.BusinessHouse;
import com.dgsoft.house.owner.model.HouseRecord;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import javax.persistence.NoResultException;

/**
 * Created by cooper on 10/15/14.
 */
@Name("houseLinkHome")
public class HouseLinkHome {

    @In(create = true)
    private HouseEntityLoader houseEntityLoader;

    @In(create = true)
    private OwnerEntityLoader ownerEntityLoader;

    private String houseCode;

    private HouseInfo houseInfo;

    public String getHouseCode() {
        return houseCode;
    }

    public void setHouseCode(String houseCode) {
        if ((houseCode == null) || houseCode.trim().equals("") || !houseCode.equals(this.houseCode)) {
            houseInfo = null;
        }
        this.houseCode = houseCode;
    }

    public boolean isIdDefine() {
        return (houseCode != null) && (!houseCode.trim().equals(""));
    }

    public HouseInfo getInstance() {
        if (houseInfo == null) {
            if (isIdDefine()) {
                try {
                    houseInfo =
                            ownerEntityLoader.getEntityManager().createQuery("select houseRecord from HouseRecord houseRecord left join fetch houseRecord.businessHouse where houseRecord.houseCode = :houseCode", HouseRecord.class).
                                    setParameter("houseCode", houseCode).getSingleResult();
                } catch (NoResultException e) {
                    houseInfo = houseEntityLoader.getEntityManager().find(House.class, houseCode);
                }
            }
        }
        return houseInfo;
    }

    public boolean isRecord(){
        return (getInstance() != null) && (getInstance() instanceof HouseRecord);
    }


}
