package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.house.DescriptionDisplay;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.HouseContract;
import com.dgsoft.house.owner.model.MoneyBackBusiness;
import com.dgsoft.house.owner.model.PowerPerson;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2019-08-19.
 */
@Name("moneyBackDisplayGen")
public class MoneyBackDisplayGen implements TaskCompleteSubscribeComponent {

    @In
    private OwnerBusinessHome ownerBusinessHome;

    @Override
    public void valid() {

    }

    @Override
    public boolean isPass() {
        return true;
    }

    @Override
    public void complete() {
        if (!ownerBusinessHome.getInstance().getMoneyBackBusinesses().isEmpty()){
            for (MoneyBackBusiness moneyBackBusiness:ownerBusinessHome.getInstance().getMoneyBackBusinesses()){

                if (ownerBusinessHome.getInstance().getSelectBusiness() != null){
                    DescriptionDisplay businessDisplay = new DescriptionDisplay();
                    HouseContract houseContract = ownerBusinessHome.getInstance().getSelectBusiness().getSingleHoues().getHouseContract();

                    businessDisplay.newLine(DescriptionDisplay.DisplayStyle.NORMAL);

                    businessDisplay.addData(DescriptionDisplay.DisplayStyle.LABEL, "房屋编号");
                    businessDisplay.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH, houseContract.getHouseBusiness().getHouseCode());

                    if (houseContract != null) {
                        businessDisplay.addData(DescriptionDisplay.DisplayStyle.LABEL, "合同编号");
                        businessDisplay.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH, houseContract.getContractNumber());
                    }

                    businessDisplay.addData(DescriptionDisplay.DisplayStyle.LABEL, "商品房备案人");

                    String contractPersonNames = "";
                    for (PowerPerson pp : houseContract.getHouseBusiness().getAfterBusinessHouse().getAllNewPowerPersonList()) {
                        if (pp.getType().equals(PowerPerson.PowerPersonType.CONTRACT)) {
                            if (!"".equals(contractPersonNames)) {
                                contractPersonNames += ",";
                            }
                            contractPersonNames += pp.getPersonName();
                        }
                    }

                    businessDisplay.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH, contractPersonNames);

                    businessDisplay.newLine(DescriptionDisplay.DisplayStyle.NORMAL);
                    businessDisplay.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH, houseContract.getHouseBusiness().getAfterBusinessHouse().getAddress());

                    businessDisplay.addData(DescriptionDisplay.DisplayStyle.IMPORTANT, houseContract.getHouseBusiness().getAfterBusinessHouse().getMapNumber());
                    businessDisplay.addData(DescriptionDisplay.DisplayStyle.DECORATE, "图");
                    businessDisplay.addData(DescriptionDisplay.DisplayStyle.IMPORTANT, houseContract.getHouseBusiness().getAfterBusinessHouse().getBlockNo());
                    businessDisplay.addData(DescriptionDisplay.DisplayStyle.DECORATE, "丘");
                    businessDisplay.addData(DescriptionDisplay.DisplayStyle.IMPORTANT, houseContract.getHouseBusiness().getAfterBusinessHouse().getBuildNo());
                    businessDisplay.addData(DescriptionDisplay.DisplayStyle.DECORATE, "幢");
                    businessDisplay.addData(DescriptionDisplay.DisplayStyle.IMPORTANT, houseContract.getHouseBusiness().getAfterBusinessHouse().getHouseOrder());
                    businessDisplay.addData(DescriptionDisplay.DisplayStyle.DECORATE, "房");

                    moneyBackBusiness.setDisplay(DescriptionDisplay.toStringValue(businessDisplay));




                }
            }

        }

    }
}
