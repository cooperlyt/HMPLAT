package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.house.DescriptionDisplay;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.HouseBusiness;
import com.dgsoft.house.owner.model.PowerPerson;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2017-07-21.
 */
@Name("fcNotemortgageDisplayGen")
public class FcNotemortgageDisplayGen implements TaskCompleteSubscribeComponent {

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

            businessDisplay.addData(DescriptionDisplay.DisplayStyle.LABEL, "房屋编号 ");
            businessDisplay.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH,bh.getHouseCode());

            businessDisplay.addData(DescriptionDisplay.DisplayStyle.LABEL, "预告登记号 ");
            if(ownerBusinessHome.getCardNoByType("NOTICE")!=null) {
                businessDisplay.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH, ownerBusinessHome.getCardNoByType("NOTICE").getNumber());
            }else {
                businessDisplay.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH,"未知");
            }

            if(ownerBusinessHome.getCardNoByType("NOTICE_MORTGAGE")!=null) {
                businessDisplay.addData(DescriptionDisplay.DisplayStyle.LABEL, "预抵登记号 ");
                businessDisplay.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH, ownerBusinessHome.getCardNoByType("NOTICE_MORTGAGE").getNumber());
            }





            businessDisplay.addData(DescriptionDisplay.DisplayStyle.LABEL, "抵押备案人 ");

            String contractPersonNames = "";
            for (PowerPerson pp: bh.getAfterBusinessHouse().getAllNewPowerPersonList()){
                if (pp.getType().equals(PowerPerson.PowerPersonType.PREPARE)){
                    if (!"".equals(contractPersonNames)){
                        contractPersonNames += ",";
                    }
                    contractPersonNames += pp.getPersonName()+'['+pp.getCredentialsNumber()+']';;
                }
            }

            businessDisplay.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH,contractPersonNames);

            if(ownerBusinessHome.getInstance().getMortgaegeRegiste()!=null && ownerBusinessHome.getInstance().getMortgaegeRegiste().getFinancial()!=null){
                businessDisplay.newLine(DescriptionDisplay.DisplayStyle.NORMAL);
                businessDisplay.addData(DescriptionDisplay.DisplayStyle.LABEL, "抵押权人 ");
                businessDisplay.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH,ownerBusinessHome.getInstance().getMortgaegeRegiste().getFinancial().getName());
            }

            if(ownerBusinessHome.getInstance().getMortgaegeRegiste()!=null && ownerBusinessHome.getInstance().getMortgaegeRegiste().getOldFinancial()!=null){
                businessDisplay.newLine(DescriptionDisplay.DisplayStyle.NORMAL);
                businessDisplay.addData(DescriptionDisplay.DisplayStyle.LABEL, "抵押权人 ");
                businessDisplay.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH,ownerBusinessHome.getInstance().getMortgaegeRegiste().getOldFinancial().getName());
            }

            businessDisplay.newLine(DescriptionDisplay.DisplayStyle.NORMAL);
            businessDisplay.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH,bh.getAfterBusinessHouse().getAddress());
            bh.setDisplay(DescriptionDisplay.toStringValue(businessDisplay));

        }

    }
}
