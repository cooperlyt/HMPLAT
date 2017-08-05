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
@Name("fcOpenHouseDisplayGen")
public class FcOpenHouseDisplayGen implements TaskCompleteSubscribeComponent {

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



            businessDisplay.addData(DescriptionDisplay.DisplayStyle.LABEL, "解封法院");
            businessDisplay.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH, ownerBusinessHome.getCloseHouseCancel().getClour());

            if (ownerBusinessHome.getInstance().getApplyPersion()!=null) {
                businessDisplay.addData(DescriptionDisplay.DisplayStyle.LABEL, "产权备案人");
                String contractPersonNames = ownerBusinessHome.getInstance().getApplyPersion().getPersonName()+"["+ownerBusinessHome.getInstance().getApplyPersion().getCredentialsNumber()+"]";
                businessDisplay.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH, contractPersonNames);
            }


            businessDisplay.newLine(DescriptionDisplay.DisplayStyle.NORMAL);

            if (ownerBusinessHome.getCloseHouseCancel()!=null && ownerBusinessHome.getCloseHouseCancel().getLegalDocuments()!=null){
                businessDisplay.addData(DescriptionDisplay.DisplayStyle.LABEL, "法律文书");
                businessDisplay.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH, ownerBusinessHome.getCloseHouseCancel().getLegalDocuments());

            }

            if (ownerBusinessHome.getCloseHouseCancel()!=null && ownerBusinessHome.getCloseHouseCancel().getExecutionNotice()!=null){
                businessDisplay.addData(DescriptionDisplay.DisplayStyle.LABEL, "协助执行通知书");
                businessDisplay.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH, ownerBusinessHome.getCloseHouseCancel().getExecutionNotice());
            }

            if (ownerBusinessHome.getCloseHouseCancel()!=null && ownerBusinessHome.getCloseHouseCancel().getHouseCardNo()!=null){
                businessDisplay.addData(DescriptionDisplay.DisplayStyle.LABEL, "所有权证号");
                businessDisplay.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH, ownerBusinessHome.getCloseHouseCancel().getHouseCardNo());
            }

            businessDisplay.newLine(DescriptionDisplay.DisplayStyle.NORMAL);
            businessDisplay.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH,bh.getAfterBusinessHouse().getAddress());


            bh.setDisplay(DescriptionDisplay.toStringValue(businessDisplay));
        }


    }
}
