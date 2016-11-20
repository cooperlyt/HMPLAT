package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.house.DescriptionDisplay;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.ContractNumber;
import com.dgsoft.house.owner.model.PowerPerson;
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
        DescriptionDisplay businessDisplay = new DescriptionDisplay();
        List<DescriptionDisplay.DisplayData> dds = new ArrayList<DescriptionDisplay.DisplayData>();
        dds.add(new DescriptionDisplay.DisplayData(DescriptionDisplay.DisplayStyle.NORMAL,"合同编号:" + ownerBusinessHome.getSingleHoues().getHouseContract().getContractNumber()));
        if(ownerBusinessHome.getSingleHoues().getHouseContract().getContractSubmit() != null){
            for (ContractNumber cn: ownerBusinessHome.getSingleHoues().getHouseContract().getContractSubmit().getContractNumbers()){
                dds.add(new DescriptionDisplay.DisplayData(DescriptionDisplay.DisplayStyle.NORMAL,cn.getContractNumber()));
            }
        }

        businessDisplay.addLine(DescriptionDisplay.DisplayStyle.NORMAL,dds.toArray(new DescriptionDisplay.DisplayData[0]));

        // List<DescriptionDisplay.DisplayData> dds2 = new ArrayList<DescriptionDisplay.DisplayData>();
        String contractPersonNames = "";
        for (PowerPerson pp: ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getPowerPersons()){
            if (pp.getType().equals(PowerPerson.PowerPersonType.CONTRACT) && !pp.isOld()){
                if (!"".equals(contractPersonNames)){
                    contractPersonNames += ",";
                }
                contractPersonNames += pp.getPersonName();
            }
        }

        businessDisplay.addLine(DescriptionDisplay.DisplayStyle.NORMAL,new DescriptionDisplay.DisplayData(DescriptionDisplay.DisplayStyle.NORMAL,contractPersonNames));

        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonData = mapper.writeValueAsString(businessDisplay);
            ownerBusinessHome.getSingleHoues().setDisplay(jsonData);
            ownerBusinessHome.getInstance().setDisplay(jsonData);
        } catch (IOException e) {
            throw new IllegalArgumentException("display json data gen fail!",e);
        }

    }
}
