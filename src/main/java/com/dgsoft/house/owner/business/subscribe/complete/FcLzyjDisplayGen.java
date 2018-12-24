package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.house.DescriptionDisplay;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.HouseBusiness;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2018-12-24.
 */
@Name("fcLzyjDisplayGen")
public class FcLzyjDisplayGen implements TaskCompleteSubscribeComponent {

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


            businessDisplay.addData(DescriptionDisplay.DisplayStyle.LABEL, "申请人 ");

            String contractPersonNames = "";

            if (ownerBusinessHome.getApplyPersion()!=null && ownerBusinessHome.getApplyPersion().getPersonName()!=null){
                contractPersonNames=ownerBusinessHome.getApplyPersion().getPersonName()+"["+ownerBusinessHome.getApplyPersion().getCredentialsNumber()+"]";
            }
            businessDisplay.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH, contractPersonNames);

            if (ownerBusinessHome.getInstance().getReceive()!=null && !ownerBusinessHome.getInstance().getReceive().equals("")){
                businessDisplay.newLine(DescriptionDisplay.DisplayStyle.NORMAL);
                businessDisplay.addData(DescriptionDisplay.DisplayStyle.LABEL, "业务备注 ");
                businessDisplay.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH, ownerBusinessHome.getInstance().getReceive().getReason());
            }


            businessDisplay.newLine(DescriptionDisplay.DisplayStyle.NORMAL);
            businessDisplay.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH,bh.getAfterBusinessHouse().getAddress());

            bh.setDisplay(DescriptionDisplay.toStringValue(businessDisplay));

        }

    }
}
