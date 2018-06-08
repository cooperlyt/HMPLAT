package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.house.DescriptionDisplay;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.HouseBusiness;
import com.dgsoft.house.owner.model.PowerPerson;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2018-06-08.
 * 凤城租赁登记查询显示页
 */
@Name("fcLeaseHouseDisplayGen")
public class FcLeaseHouseDisplayGen implements TaskCompleteSubscribeComponent {
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

            businessDisplay.addData(DescriptionDisplay.DisplayStyle.LABEL, "出租人");
            String ycontractPersonNames = "";
            if(ownerBusinessHome.getLessorPersion()!=null){
                ycontractPersonNames = ownerBusinessHome.getLessorPersion().getPersonName()+'['+ownerBusinessHome.getLessorPersion().getCredentialsNumber()+']';
            }
            businessDisplay.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH, ycontractPersonNames);



            businessDisplay.newLine(DescriptionDisplay.DisplayStyle.NORMAL);
            businessDisplay.addData(DescriptionDisplay.DisplayStyle.LABEL, "产权证号 ");
            if(ownerBusinessHome.getCardNoByType("OWNER_RSHIP")!=null) {
                businessDisplay.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH, ownerBusinessHome.getCardNoByType("OWNER_RSHIP").getNumber());
            }else {
                businessDisplay.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH,"未知");
            }

            businessDisplay.addData(DescriptionDisplay.DisplayStyle.LABEL, "承租人");
            String contractPersonNames = "";
            if(ownerBusinessHome.getLesseePersion()!=null){
                contractPersonNames = ownerBusinessHome.getLesseePersion().getPersonName()+'['+ownerBusinessHome.getLesseePersion().getCredentialsNumber()+']';
            }
            businessDisplay.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH, contractPersonNames);


            businessDisplay.newLine(DescriptionDisplay.DisplayStyle.NORMAL);
            businessDisplay.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH,bh.getAfterBusinessHouse().getAddress());
            bh.setDisplay(DescriptionDisplay.toStringValue(businessDisplay));
        }

    }
}
