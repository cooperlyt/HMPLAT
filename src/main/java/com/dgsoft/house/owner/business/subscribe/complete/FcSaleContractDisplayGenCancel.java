package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.house.DescriptionDisplay;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.HouseBusiness;
import com.dgsoft.house.owner.model.HouseContract;
import com.dgsoft.house.owner.model.PowerPerson;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2017-07-21.
 */
@Name("fcSaleContractDisplayGenCancel")
public class FcSaleContractDisplayGenCancel implements TaskCompleteSubscribeComponent {
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

        for(HouseBusiness bh: ownerBusinessHome.getInstance().getHouseBusinesses()){

            DescriptionDisplay businessDisplay = new DescriptionDisplay();

            businessDisplay.newLine(DescriptionDisplay.DisplayStyle.NORMAL);

            businessDisplay.addData(DescriptionDisplay.DisplayStyle.LABEL, "房屋编号");
            businessDisplay.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH,bh.getHouseCode());

            HouseContract houseContract = bh.getAfterBusinessHouse().getSaleContract();

            if (houseContract == null){
                houseContract = bh.getStartBusinessHouse().getSaleContract();
            }

            if (houseContract != null) {
                businessDisplay.addData(DescriptionDisplay.DisplayStyle.LABEL, "合同编号");
                businessDisplay.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH,houseContract.getContractNumber());
            }

            businessDisplay.addData(DescriptionDisplay.DisplayStyle.LABEL, "备案人");

            String contractPersonNames = "";
            for (PowerPerson pp: bh.getAfterBusinessHouse().getPowerPersonListByType(PowerPerson.PowerPersonType.CONTRACT,true)){
                if (pp.getType().equals(PowerPerson.PowerPersonType.CONTRACT)){
                    if (!"".equals(contractPersonNames)){
                        contractPersonNames += ",";
                    }
                    contractPersonNames += pp.getPersonName()+"["+pp.getCredentialsNumber()+"]";
                }
            }
            businessDisplay.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH, contractPersonNames);

            if (bh.getAfterBusinessHouse().getDeveloperName()!=null && !bh.getAfterBusinessHouse().getDeveloperName().equals("")){
                businessDisplay.newLine(DescriptionDisplay.DisplayStyle.NORMAL);
                businessDisplay.addData(DescriptionDisplay.DisplayStyle.LABEL, "开发商");
                businessDisplay.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH,bh.getAfterBusinessHouse().getDeveloperName());

            }

            businessDisplay.newLine(DescriptionDisplay.DisplayStyle.NORMAL);
            businessDisplay.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH,bh.getAfterBusinessHouse().getAddress());



            bh.setDisplay(DescriptionDisplay.toStringValue(businessDisplay));
        }



    }
}
