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
            List<DescriptionDisplay.DisplayData> dds = new ArrayList<DescriptionDisplay.DisplayData>();
            HouseContract houseContract = bh.getHouseContract();

            if (houseContract == null && ownerBusinessHome.getInstance().getSelectBusiness() != null){
                for(HouseBusiness shb: ownerBusinessHome.getInstance().getSelectBusiness().getHouseBusinesses()){
                    if (shb.getHouseCode().equals(bh.getHouseCode())){
                        houseContract = shb.getHouseContract();
                    }
                }
            }

            if (houseContract != null) {
                dds.add(new DescriptionDisplay.DisplayData(DescriptionDisplay.DisplayStyle.NORMAL, "合同编号:" + houseContract.getContractNumber()));
                if (houseContract.getContractSubmit() != null) {
                    for (ContractNumber cn : houseContract.getContractSubmit().getContractNumbers()) {
                        dds.add(new DescriptionDisplay.DisplayData(DescriptionDisplay.DisplayStyle.NORMAL, cn.getContractNumber()));
                    }
                }


                businessDisplay.addLine(DescriptionDisplay.DisplayStyle.NORMAL,dds.toArray(new DescriptionDisplay.DisplayData[0]));
            }

            businessDisplay.addLine(DescriptionDisplay.DisplayStyle.NORMAL, new DescriptionDisplay.DisplayData(DescriptionDisplay.DisplayStyle.NORMAL,"图:" + bh.getAfterBusinessHouse().getMapNumber() + " 丘:" + bh.getAfterBusinessHouse().getBlockNo() + " 幢:" + bh.getAfterBusinessHouse().getBuildNo() + " 房: " + bh.getAfterBusinessHouse().getHouseOrder()));

            businessDisplay.addLine(DescriptionDisplay.DisplayStyle.NORMAL, new DescriptionDisplay.DisplayData(DescriptionDisplay.DisplayStyle.NORMAL,bh.getAfterBusinessHouse().getAddress()));

            // List<DescriptionDisplay.DisplayData> dds2 = new ArrayList<DescriptionDisplay.DisplayData>();
            String contractPersonNames = "";
            for (PowerPerson pp: bh.getAfterBusinessHouse().getAllNewPowerPersonList()){
                if (pp.getType().equals(PowerPerson.PowerPersonType.CONTRACT)){
                    if (!"".equals(contractPersonNames)){
                        contractPersonNames += ",";
                    }
                    contractPersonNames += pp.getPersonName();
                }
            }

            businessDisplay.addLine(DescriptionDisplay.DisplayStyle.NORMAL,new DescriptionDisplay.DisplayData(DescriptionDisplay.DisplayStyle.NORMAL,contractPersonNames));

            String result = DescriptionDisplay.toStringValue(businessDisplay);
            bh.setDisplay(result);
        }



    }
}
