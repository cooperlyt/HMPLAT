package com.dgsoft.house.owner.out;

import com.dgsoft.common.helper.JsonDataProvider;
import com.dgsoft.developersale.SaleStatus;
import com.dgsoft.house.HouseEntityLoader;
import com.dgsoft.house.HouseStatus;
import com.dgsoft.house.model.House;
import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.model.HouseRecord;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.web.RequestParameter;

import javax.persistence.NoResultException;
import java.util.EnumSet;

/**
 * Created by cooper on 4/9/16.
 */
@Name("getHouseStateByMBBH")
@Scope(ScopeType.STATELESS)
public class GetHouseStateByMBBH implements JsonDataProvider.JsonDataProviderFunction{

    private final static EnumSet<HouseStatus> ALLOW_SALE_STATUS = EnumSet.of(HouseStatus.PROJECT_PLEDGE, HouseStatus.INIT_REG_CONFIRM, HouseStatus.INIT_REG);

    @RequestParameter
    private String mapNumber;

    @RequestParameter
    private String blockNumber;

    @RequestParameter
    private String buildNumber;

    @RequestParameter
    private String houseOrder;

    @In(create = true)
    private HouseEntityLoader houseEntityLoader;

    @In(create = true)
    private OwnerEntityLoader ownerEntityLoader;

    @Override
    public String getJsonData() {

        try {
             House house = houseEntityLoader.getEntityManager().createQuery("select house from House house where house.deleted = false and house.build.mapNumber = :mapNumber and house.build.blockNo = :blockNumber and house.build.buildNo = :buildNumber and house.houseOrder = :houseOrder", House.class)
                    .setParameter("mapNumber", mapNumber)
                    .setParameter("blockNumber", blockNumber)
                    .setParameter("buildNumber", buildNumber)
                    .setParameter("houseOrder", houseOrder).getSingleResult();


                if (!(house.getHouseType() == null || "".equals(house.getHouseType()))) {
                    return SaleStatus.NO_SALE.toString();
                }else if (ownerEntityLoader.getEntityManager().createQuery("select count(houseBusiness.id) from HouseBusiness houseBusiness where houseCode = :houseCode and houseBusiness.ownerBusiness.status in ('RUNNING','SUSPEND')",Long.class)
                        .setParameter("houseCode",house.getId()).getSingleResult().intValue() > 0){
                    return SaleStatus.NO_SALE.toString();
                }else if (ownerEntityLoader.getEntityManager().createQuery("select count(lh.id) from LockedHouse lh where lh.houseCode = :houseCode",Long.class)
                            .setParameter("houseCode",house.getId()).getSingleResult().intValue() > 0){
                    return SaleStatus.NO_SALE.toString();
                }else{
                    try {
                        HouseRecord houseRecord = ownerEntityLoader.getEntityManager().createQuery("select hr from HouseRecord hr where  hr.houseCode = :houseCode", HouseRecord.class)
                            .setParameter("houseCode",house.getId()).getSingleResult();



                        if (houseRecord.getHouseStatus() == null){
                            return SaleStatus.CAN_SALE.toString();
                        }else if (HouseStatus.COURT_CLOSE.equals(houseRecord.getHouseStatus())) {
                            return SaleStatus.COURT_CLOSE.toString();
                        } else if (HouseStatus.CONTRACTS_RECORD.equals(houseRecord.getHouseStatus())) {
                            return SaleStatus.CONTRACTS_RECORD.toString();
                        }else if (HouseStatus.PROJECT_PLEDGE.equals(houseRecord.getHouseStatus())){
                            return SaleStatus.PROJECT_PLEDGE.toString();
                        } else if(ALLOW_SALE_STATUS.contains(houseRecord.getHouseStatus())){
                            return SaleStatus.CAN_SALE.toString();
                        }else{
                            return SaleStatus.HAVE_SALE.toString();
                        }

                    }catch (NoResultException e){
                        return SaleStatus.CAN_SALE.toString();
                    }
                }

        }catch (NoResultException e){
            return "";
        }
    }
}
