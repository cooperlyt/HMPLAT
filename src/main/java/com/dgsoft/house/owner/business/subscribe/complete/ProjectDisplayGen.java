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
 * Created by wxy on 2016-12-19.
 */
@Name("projectDisplayGen")
public class ProjectDisplayGen implements TaskCompleteSubscribeComponent {

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





            if (bh.getAfterBusinessHouse().getNewMainInitPerson() != null && bh.getAfterBusinessHouse().getNewMainInitPerson().getMakeCard()!=null) {
                businessDisplay.addData(DescriptionDisplay.DisplayStyle.LABEL, "项目备案号");
                businessDisplay.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH,bh.getAfterBusinessHouse().getNewMainInitPerson().getMakeCard().getNumber());
            }

            businessDisplay.addData(DescriptionDisplay.DisplayStyle.LABEL, "项目备案人");

            String contractPersonNames = bh.getAfterBusinessHouse().getNewMainInitPerson().getPersonName();

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

        System.out.println("bh-----");

    }
}
