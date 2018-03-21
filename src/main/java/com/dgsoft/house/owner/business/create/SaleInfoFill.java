//package com.dgsoft.house.owner.business.create;
//
//import com.dgsoft.common.system.business.BusinessDataFill;
//import com.dgsoft.common.system.business.BusinessInstance;
//import com.dgsoft.house.owner.action.OwnerBusinessHome;
//import com.dgsoft.house.owner.model.HouseBusiness;
//import com.dgsoft.house.owner.model.SaleInfo;
//import org.jboss.seam.annotations.In;
//import org.jboss.seam.annotations.Name;
//
///**
// * Created by wxy on 2015-11-26.
// * 购房款信息提取
// */
//@Name("saleInfoFill")
//public class SaleInfoFill implements BusinessDataFill {
//    @In
//    private OwnerBusinessHome ownerBusinessHome;
//
//    @Override
//    public void fillData() {
//        if (!ownerBusinessHome.getInstance().getType().equals(BusinessInstance.BusinessType.MODIFY_BIZ)) {
//
//            for (HouseBusiness houseBusiness : ownerBusinessHome.getInstance().getHouseBusinesses()) {
//                if (houseBusiness.getStartBusinessHouse().getSaleInfo()!=null){
//                   SaleInfo SaleInfo = houseBusiness.getStartBusinessHouse().getSaleInfo();
//                   houseBusiness.getAfterBusinessHouse().setSaleInfo(new SaleInfo(SaleInfo,houseBusiness.getAfterBusinessHouse()));
//                }
//            }
//        }
//
//    }
//}
