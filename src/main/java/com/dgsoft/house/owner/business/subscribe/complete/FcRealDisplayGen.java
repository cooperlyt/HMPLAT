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
 * Created by wxy on 2017-07-20.
 * 凤城转移登记查询显示页
 */
@Name("fcRealDisplayGen")
public class FcRealDisplayGen implements TaskCompleteSubscribeComponent {

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

            businessDisplay.addData(DescriptionDisplay.DisplayStyle.LABEL, "原产权备案人 ");
            String ycontractPersonNames = "";
            for (PowerPerson pp: bh.getAfterBusinessHouse().getPowerPersonListByType(PowerPerson.PowerPersonType.OWNER,true)){
                if (pp.getType().equals(PowerPerson.PowerPersonType.OWNER)){
                    if (!"".equals(ycontractPersonNames)){
                        ycontractPersonNames += ",";
                    }
                    ycontractPersonNames += pp.getPersonName()+'['+pp.getCredentialsNumber()+']';
                }
            }
            businessDisplay.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH, ycontractPersonNames);



            businessDisplay.newLine(DescriptionDisplay.DisplayStyle.NORMAL);
            businessDisplay.addData(DescriptionDisplay.DisplayStyle.LABEL, "原权证号 ");
            if(ownerBusinessHome.getCardNoByType("OWNER_RSHIP")!=null) {
                businessDisplay.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH, ownerBusinessHome.getCardNoByType("OWNER_RSHIP").getNumber());
            }else {
                businessDisplay.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH,"未知");
            }

            businessDisplay.addData(DescriptionDisplay.DisplayStyle.LABEL, "现产权备案人");
            String contractPersonNames = "";
            for (PowerPerson pp: bh.getAfterBusinessHouse().getAllNewPowerPersonList()){
                if (pp.getType().equals(PowerPerson.PowerPersonType.OWNER)){
                    if (!"".equals(contractPersonNames)){
                        contractPersonNames += ",";
                    }
                    contractPersonNames += pp.getPersonName()+'['+pp.getCredentialsNumber()+']';
                }
            }
            businessDisplay.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH, contractPersonNames);


            businessDisplay.newLine(DescriptionDisplay.DisplayStyle.NORMAL);
            businessDisplay.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH,bh.getAfterBusinessHouse().getAddress());
            bh.setDisplay(DescriptionDisplay.toStringValue(businessDisplay));
        }



    }
}
