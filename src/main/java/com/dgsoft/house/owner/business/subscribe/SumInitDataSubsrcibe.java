package com.dgsoft.house.owner.business.subscribe;

import cc.coopersoft.house.UseType;
import com.dgsoft.house.HouseEntityLoader;
import com.dgsoft.house.model.Build;
import com.dgsoft.house.model.House;
import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.model.BusinessHouse;
import com.dgsoft.house.owner.model.HouseBusiness;
import com.dgsoft.house.owner.total.data.InitCardTotalData;
import com.dgsoft.house.owner.total.data.InitToData;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wxy on 2017-01-15.
 */
@Name("sumInitDataSubsrcibe")
public class SumInitDataSubsrcibe {

    @In(create = true)
    private OwnerEntityLoader ownerEntityLoader;

    @In(create = true)
    private HouseEntityLoader houseEntityLoader;


    private BigDecimal sumArea=BigDecimal.ZERO;

    private int sumCount=0;

    private BigDecimal dwellingArea=BigDecimal.ZERO;//住宅

    private int dwellingConut=0;

    private BigDecimal shopArea=BigDecimal.ZERO;//商业

    private int shopCount=0;

    private BigDecimal otherArea=BigDecimal.ZERO;//其它

    private int otherCount=0;

    public Build getBuild() {
        return build;
    }

    public void setBuild(Build build) {
        this.build = build;
    }

    private Build build;



    List<BusinessHouse> houseList = new ArrayList<BusinessHouse>();

    public List<BusinessHouse> getHouseList() {
        return houseList;
    }

    public void setHouseList(List<BusinessHouse> houseList) {
        this.houseList = houseList;
    }

    public void serchByBuildCode(String BuildCode){

        Build build1 = houseEntityLoader.getEntityManager().find(Build.class,BuildCode);
        if (build1!=null){
            build=build1;
        }


    }


    public void serchBybizid(String bizid){

        List<BusinessHouse> businessHouseList = ownerEntityLoader.getEntityManager().createQuery("select bh from BusinessHouse bh where bh.houseBusinessForAfter.ownerBusiness.id=:bizid and bh.houseType = 'COMM_USE_HOUSE'",BusinessHouse.class)
                .setParameter("bizid",bizid)
                .getResultList();

        if (businessHouseList!=null && businessHouseList.size()>0){
            this.houseList = businessHouseList;
        }

        List<InitToData> initToDataList = ownerEntityLoader.getEntityManager().createQuery("select new com.dgsoft.house.owner.total.data.InitToData(count(hb.afterBusinessHouse.id),sum(hb.afterBusinessHouse.houseArea),hb.afterBusinessHouse.useType) from HouseBusiness hb where hb.ownerBusiness.id=:bizid group by hb.afterBusinessHouse.useType",InitToData.class)
                .setParameter("bizid",bizid)
                .getResultList();


        if (initToDataList!=null && initToDataList.size()>0){
            for (InitToData initToData:initToDataList){
                if (initToData.getUseType().equals(UseType.DWELLING_KEY)){
                    dwellingArea = dwellingArea.add(initToData.getHouseArea());

                    dwellingConut= dwellingConut+initToData.getCount().intValue();

                }else if (initToData.getUseType().equals(UseType.SHOP_HOUSE_KEY)){
                    shopArea = shopArea.add(initToData.getHouseArea());
                    shopCount = shopCount+initToData.getCount().intValue();

                }else{
                    otherArea = otherArea.add(initToData.getHouseArea());
                    otherCount= otherCount+initToData.getCount().intValue();
                }

                sumArea = sumArea.add(dwellingArea);
                sumArea = sumArea.add(shopArea);
                sumArea = sumArea.add(otherArea);

                sumCount = dwellingConut+shopCount+otherCount;
            }
        }
    }

    public BigDecimal getSumArea() {
        return sumArea;
    }

    public void setSumArea(BigDecimal sumArea) {
        this.sumArea = sumArea;
    }

    public int getSumCount() {
        return sumCount;
    }

    public void setSumCount(int sumCount) {
        this.sumCount = sumCount;
    }

    public BigDecimal getDwellingArea() {
        return dwellingArea;
    }

    public void setDwellingArea(BigDecimal dwellingArea) {
        this.dwellingArea = dwellingArea;
    }

    public int getDwellingConut() {
        return dwellingConut;
    }

    public void setDwellingConut(int dwellingConut) {
        this.dwellingConut = dwellingConut;
    }

    public BigDecimal getShopArea() {
        return shopArea;
    }

    public void setShopArea(BigDecimal shopArea) {
        this.shopArea = shopArea;
    }

    public int getShopCount() {
        return shopCount;
    }

    public void setShopCount(int shopCount) {
        this.shopCount = shopCount;
    }

    public BigDecimal getOtherArea() {
        return otherArea;
    }

    public void setOtherArea(BigDecimal otherArea) {
        this.otherArea = otherArea;
    }

    public int getOtherCount() {
        return otherCount;
    }

    public void setOtherCount(int otherCount) {
        this.otherCount = otherCount;
    }







}
