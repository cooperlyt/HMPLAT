package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.house.DescriptionDisplay;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cooper on 20/11/2016.
 */
@Name("saleContractDisplayGen")
public class SaleContractDisplayGen implements TaskCompleteSubscribeComponent {

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
            for (PowerPerson pp: bh.getAfterBusinessHouse().getAllNewPowerPersonList()){
                if (pp.getType().equals(PowerPerson.PowerPersonType.CONTRACT)){
                    if (!"".equals(contractPersonNames)){
                        contractPersonNames += ",";
                    }
                    contractPersonNames += pp.getPersonName();
                }
            }
            businessDisplay.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH, contractPersonNames);

            businessDisplay.newLine(DescriptionDisplay.DisplayStyle.NORMAL);
            businessDisplay.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH,bh.getAfterBusinessHouse().getAddress());

            businessDisplay.addData(DescriptionDisplay.DisplayStyle.IMPORTANT,bh.getAfterBusinessHouse().getMapNumber());
            businessDisplay.addData(DescriptionDisplay.DisplayStyle.DECORATE,"图");
            businessDisplay.addData(DescriptionDisplay.DisplayStyle.IMPORTANT,bh.getAfterBusinessHouse().getBlockNo());
            businessDisplay.addData(DescriptionDisplay.DisplayStyle.DECORATE,"丘");
            businessDisplay.addData(DescriptionDisplay.DisplayStyle.IMPORTANT,bh.getAfterBusinessHouse().getBuildNo());
            businessDisplay.addData(DescriptionDisplay.DisplayStyle.DECORATE,"幢");
            businessDisplay.addData(DescriptionDisplay.DisplayStyle.IMPORTANT,bh.getAfterBusinessHouse().getHouseOrder());
            businessDisplay.addData(DescriptionDisplay.DisplayStyle.DECORATE,"房");

            bh.setDisplay(DescriptionDisplay.toStringValue(businessDisplay));
        }



    }
}
