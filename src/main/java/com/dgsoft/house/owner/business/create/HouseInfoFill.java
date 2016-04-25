package com.dgsoft.house.owner.business.create;

import com.dgsoft.common.system.business.BusinessDataFill;
import com.dgsoft.common.system.business.BusinessInstance;
import com.dgsoft.house.HouseEntityLoader;
import com.dgsoft.house.model.House;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessHouse;
import com.dgsoft.house.owner.model.HouseBusiness;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;

import java.math.BigDecimal;

/**
 * Created by Administrator on 15-8-8.
 * 空间库的房屋信息 启动时填充到档案库房屋表中
 * 如面积，结构，房屋面积
 */
@Name("houseInfoFill")
public class HouseInfoFill implements BusinessDataFill {
    @In
    private OwnerBusinessHome ownerBusinessHome;


    @In(create = true)
    private HouseEntityLoader houseEntityLoader;

    @In
    private FacesMessages facesMessages;

    @Override
    public void fillData() {
      if (!ownerBusinessHome.getInstance().getType().equals(BusinessInstance.BusinessType.MODIFY_BIZ)) {
          for (HouseBusiness houseBusiness : ownerBusinessHome.getInstance().getHouseBusinesses()) {
              House house = houseEntityLoader.getEntityManager().find(House.class, houseBusiness.getHouseCode());
              if (house != null && house.getHouseArea() != null && (house.getHouseArea().compareTo(new BigDecimal(0)) > 0)) {
                  houseBusiness.getAfterBusinessHouse().setHouseArea(house.getHouseArea());
                  houseBusiness.getAfterBusinessHouse().setAddress(house.getAddress());
                  houseBusiness.getAfterBusinessHouse().setInFloorName(house.getInFloorName());
                  houseBusiness.getAfterBusinessHouse().setStructure(house.getStructure());
                  houseBusiness.getAfterBusinessHouse().setHouseType(house.getHouseType());
                  houseBusiness.getAfterBusinessHouse().setHouseOrder(house.getHouseOrder());

                  houseBusiness.getAfterBusinessHouse().setBuildNo(house.getBuildNo());
                  houseBusiness.getAfterBusinessHouse().setBlockNo(house.getBlockNo());
                  houseBusiness.getAfterBusinessHouse().setMapNumber(house.getMapNumber());

                  houseBusiness.getAfterBusinessHouse().setHouseUnitName(house.getHouseUnitName());
                  houseBusiness.getAfterBusinessHouse().setDoorNo(house.getHouseUnitName());
                  houseBusiness.getAfterBusinessHouse().setBuildType(house.getBuildType());
                  houseBusiness.getAfterBusinessHouse().setDeveloperName(house.getDeveloperName());
                  houseBusiness.getAfterBusinessHouse().setDeveloperCode(house.getDeveloperCode());
                  houseBusiness.getAfterBusinessHouse().setSectionName(house.getSectionName());
                  houseBusiness.getAfterBusinessHouse().setSectionCode(house.getSectionCode());
                  houseBusiness.getAfterBusinessHouse().setUseType(house.getUseType());

                  if (house.getBuild()!=null) {
                      houseBusiness.getAfterBusinessHouse().setFloorCount(house.getBuild().getFloorCount());
                      houseBusiness.getAfterBusinessHouse().setUpFloorCount(house.getBuild().getUpFloorCount());
                      houseBusiness.getAfterBusinessHouse().setDownFloorCount(house.getBuild().getDownFloorCount());
                  }
                  facesMessages.addFromResourceBundle(StatusMessage.Severity.WARN, "house_info_fill");
              }
          }
      }
    }
}
