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
 * 东港预告抵押查询显示
 */
@Name("notemortgageDisplayGen")
public class NotemortgageDisplayGen implements TaskCompleteSubscribeComponent {

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

            businessDisplay.addData(DescriptionDisplay.DisplayStyle.LABEL, "抵押备案人");

            String contractPersonNames = "";
            for (PowerPerson pp: bh.getAfterBusinessHouse().getAllNewPowerPersonList()){
                if (pp.getType().equals(PowerPerson.PowerPersonType.PREPARE)){
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

            if(ownerBusinessHome.getInstance().getMortgaegeRegiste()!=null && ownerBusinessHome.getInstance().getMortgaegeRegiste().getFinancial()!=null){
                businessDisplay.newLine(DescriptionDisplay.DisplayStyle.NORMAL);
                businessDisplay.addData(DescriptionDisplay.DisplayStyle.LABEL, "抵押权人");
                businessDisplay.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH,ownerBusinessHome.getInstance().getMortgaegeRegiste().getFinancial().getName());
            }
            if(ownerBusinessHome.getInstance().getMortgaegeRegiste()!=null && ownerBusinessHome.getInstance().getMortgaegeRegiste().getOldFinancial()!=null){
                businessDisplay.newLine(DescriptionDisplay.DisplayStyle.NORMAL);
                businessDisplay.addData(DescriptionDisplay.DisplayStyle.LABEL, "抵押权人");
                businessDisplay.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH,ownerBusinessHome.getInstance().getMortgaegeRegiste().getOldFinancial().getName());
            }

            bh.setDisplay(DescriptionDisplay.toStringValue(businessDisplay));

        }

    }
}
