package com.dgsoft.house.owner.helper;

import com.dgsoft.common.system.RunParam;
import com.dgsoft.house.UseTypeWordAdapter;
import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.total.data.HouseSaleTotalData;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by cooper on 12/20/15.
 */
@Name("newHouseRecordTotalDataProvider")
public class NewHouseRecordTotalDataProvider implements RestDataProvider{


    @In(create = true)
    private OwnerEntityLoader ownerEntityLoader;


    @Override
    public JSONObject getJsonData() {

        HouseSaleTotalData all =  ownerEntityLoader.getEntityManager().createQuery("select new com.dgsoft.house.owner.total.data.HouseSaleTotalData(sum(psi.houseCount),sum(psi.area)) from ProjectSellInfo psi where psi.businessProject.ownerBusiness.status in ('MODIFYING','COMPLETE') ",HouseSaleTotalData.class).getSingleResult();


        HouseSaleTotalData home =  ownerEntityLoader.getEntityManager().createQuery("select new com.dgsoft.house.owner.total.data.HouseSaleTotalData(sum(stt.count),sum(stt.area)) from SellTypeTotal stt left join stt.businessBuild bb where stt.useType = 'DWELLING_KEY' and  bb.businessProject.ownerBusiness.status in ('MODIFYING','COMPLETE')", HouseSaleTotalData.class).getSingleResult();

        HouseSaleTotalData sale = ownerEntityLoader.getEntityManager().createQuery("select new com.dgsoft.house.owner.total.data.HouseSaleTotalData(count(hb.id),sum(hb.afterBusinessHouse.houseArea),sum(hb.houseContract.sumPrice) ) from HouseBusiness hb where hb.ownerBusiness.defineId =:newRecordBizId and hb.ownerBusiness.status in ('RUNNING', 'COMPLETE', 'SUSPEND', 'MODIFYING')", HouseSaleTotalData.class).setParameter("newRecordBizId", RunParam.instance().getStringParamValue("NewHouseContractBizId")).getSingleResult();

        HouseSaleTotalData saleHome = ownerEntityLoader.getEntityManager().createQuery("select new com.dgsoft.house.owner.total.data.HouseSaleTotalData(count(hb.id),sum(hb.afterBusinessHouse.houseArea),sum(hb.houseContract.sumPrice) ) from HouseBusiness hb where hb.afterBusinessHouse.useType = 'DWELLING_KEY' and hb.ownerBusiness.defineId =:newRecordBizId and hb.ownerBusiness.status in ('RUNNING', 'COMPLETE', 'SUSPEND', 'MODIFYING')", HouseSaleTotalData.class).setParameter("newRecordBizId", RunParam.instance().getStringParamValue("NewHouseContractBizId")).getSingleResult();


        Long projectCount = ownerEntityLoader.getEntityManager().createQuery("select count(distinct bp.projectCode) from BusinessProject bp where bp.ownerBusiness.status in ('MODIFYING','COMPLETE')",Long.class).getSingleResult();

        Long buildCount = ownerEntityLoader.getEntityManager().createQuery("select count(distinct bb.buildCode) from BusinessBuild bb where bb.businessProject.ownerBusiness.status in ('MODIFYING','COMPLETE')",Long.class).getSingleResult();


        JSONObject result = new JSONObject();

        try {
            result.put("allCount",all.getCount());
            result.put("allArea", all.getArea().toString());
            result.put("homeCount",home.getCount());
            result.put("homeArea",home.getArea().toString());
            result.put("saleCount", sale.getCount());
            result.put("saleArea", sale.getArea().toString());
            result.put("saleMoney", sale.getMoney().toString());
            result.put("saleHomeCount",saleHome.getCount());
            result.put("saleHomeArea",saleHome.getArea().toString());
            result.put("saleHomeMoney",sale.getMoney().toString());
            result.put("projectCount", projectCount);
            result.put("buildCount",buildCount);

            return result;
        } catch (JSONException e) {
            return null;
        }

    }
}
