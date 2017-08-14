package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.house.DescriptionDisplay;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.HouseBusiness;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2017-07-21.
 */
@Name("fcCloseHouseDisplayGen")
public class FcCloseHouseDisplayGen implements TaskCompleteSubscribeComponent {

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



            businessDisplay.addData(DescriptionDisplay.DisplayStyle.LABEL, "查封法院");
            businessDisplay.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH, ownerBusinessHome.getCloseHouse().getCloseDownClour());




            if (ownerBusinessHome.getInstance().getApplyPersion()!=null) {
                businessDisplay.addData(DescriptionDisplay.DisplayStyle.LABEL, "产权备案人");
                String contractPersonNames = ownerBusinessHome.getInstance().getApplyPersion().getPersonName()+"["+ownerBusinessHome.getInstance().getApplyPersion().getCredentialsNumber()+"]";
                businessDisplay.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH, contractPersonNames);
            }

            businessDisplay.newLine(DescriptionDisplay.DisplayStyle.NORMAL);
            if (ownerBusinessHome.getCloseHouse()!=null && ownerBusinessHome.getCloseHouse().getExecutCardNo()!=null){

                businessDisplay.addData(DescriptionDisplay.DisplayStyle.LABEL, "法律文书");
                businessDisplay.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH, ownerBusinessHome.getCloseHouse().getLegalDocuments());

            }
            if (ownerBusinessHome.getCloseHouse()!=null && ownerBusinessHome.getCloseHouse().getExecutionNotice()!=null){
                businessDisplay.addData(DescriptionDisplay.DisplayStyle.LABEL, "协助执行通知书");
                businessDisplay.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH, ownerBusinessHome.getCloseHouse().getExecutionNotice());
            }

            if (ownerBusinessHome.getCloseHouse()!=null && ownerBusinessHome.getCloseHouse().getHouseCardNo()!=null){
                businessDisplay.addData(DescriptionDisplay.DisplayStyle.LABEL, "所有权证号");
                businessDisplay.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH, ownerBusinessHome.getCloseHouse().getHouseCardNo());
            }

            businessDisplay.newLine(DescriptionDisplay.DisplayStyle.NORMAL);
            businessDisplay.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH,bh.getAfterBusinessHouse().getAddress());



            bh.setDisplay(DescriptionDisplay.toStringValue(businessDisplay));
        }

    }
}