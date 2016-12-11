package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.house.DescriptionDisplay;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessBuild;
import com.dgsoft.house.owner.model.MakeCard;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Logging;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by cooper on 02/12/2016.
 */
@Name("projectBusinessKeyAndDisplay")
public class ProjectBusinessKeyAndDisplay implements TaskCompleteSubscribeComponent {

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
        KeyGeneratorHelper businessKey = new KeyGeneratorHelper();
        DescriptionDisplay businessDisplay = new DescriptionDisplay();
        businessDisplay.newLine(DescriptionDisplay.DisplayStyle.NORMAL);
        businessDisplay.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH,ownerBusinessHome.getInstance().getBusinessProject().getProjectName() + "(" + ownerBusinessHome.getInstance().getBusinessProject().getProjectCode()  + ")");
        businessDisplay.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH,ownerBusinessHome.getInstance().getBusinessProject().getDeveloperName() + "(" + ownerBusinessHome.getInstance().getBusinessProject().getDeveloperCode() + ")");





        for(BusinessBuild bb: ownerBusinessHome.getInstance().getBusinessProject().getBusinessBuilds()){
            businessDisplay.newLine(DescriptionDisplay.DisplayStyle.NORMAL);
            businessKey.addWord(bb.getMapNumber() + "-" + bb.getBlockNo() + "-" + bb.getBuildNo());
            businessKey.addWord(bb.getBuildName());
            businessKey.addWord(bb.getBuildCode());
            businessKey.addWord(bb.getDoorNo());

            businessDisplay.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH,bb.getBuildName());

            businessDisplay.addData(DescriptionDisplay.DisplayStyle.IMPORTANT,bb.getMapNumber());
            businessDisplay.addData(DescriptionDisplay.DisplayStyle.DECORATE,"图");
            businessDisplay.addData(DescriptionDisplay.DisplayStyle.IMPORTANT,bb.getBlockNo());
            businessDisplay.addData(DescriptionDisplay.DisplayStyle.DECORATE,"丘");
            businessDisplay.addData(DescriptionDisplay.DisplayStyle.IMPORTANT,bb.getBuildNo());
            businessDisplay.addData(DescriptionDisplay.DisplayStyle.DECORATE,"幢");


        }

        businessDisplay.newLine(DescriptionDisplay.DisplayStyle.NORMAL);


        businessDisplay.addData(DescriptionDisplay.DisplayStyle.LABEL,"许可证号");

        for(MakeCard makeCard: ownerBusinessHome.getInstance().getMakeCards()){
            businessKey.addWord(makeCard.getNumber());
            businessDisplay.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH ,makeCard.getNumber());
        }

        businessDisplay.addData(DescriptionDisplay.DisplayStyle.LABEL,"总套数");
        businessDisplay.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH ,String.valueOf(ownerBusinessHome.getInstance().getBusinessProject().getProjectSellInfo().getHouseCount()));
        businessDisplay.addData(DescriptionDisplay.DisplayStyle.LABEL,"总面积");
        DecimalFormat df = new DecimalFormat("#0.000");
        df.setGroupingUsed(true);
        df.setRoundingMode(RoundingMode.HALF_UP);
        businessDisplay.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH ,String.valueOf(ownerBusinessHome.getInstance().getBusinessProject().getProjectSellInfo().getArea()));



        businessKey.addWord(ownerBusinessHome.getInstance().getBusinessProject().getDeveloperName());
        businessKey.addWord(ownerBusinessHome.getInstance().getBusinessProject().getProjectName());
        businessKey.addWord(ownerBusinessHome.getInstance().getBusinessProject().getSectionName());

        ownerBusinessHome.getInstance().getBusinessProject().setSearchKey(businessKey.getKey());

        ownerBusinessHome.getInstance().getBusinessProject().setDisplay(DescriptionDisplay.toStringValue(businessDisplay));
    }
}
