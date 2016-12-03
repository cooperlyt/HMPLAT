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


        for(BusinessBuild bb: ownerBusinessHome.getInstance().getBusinessProject().getBusinessBuilds()){
            businessKey.addWord(bb.getMapNumber() + "-" + bb.getBlockNo() + "-" + bb.getBuildNo());
            businessKey.addWord(bb.getBuildName());
            businessKey.addWord(bb.getBuildCode());
            businessKey.addWord(bb.getDoorNo());
        }

        for(MakeCard makeCard: ownerBusinessHome.getInstance().getMakeCards()){
            businessKey.addWord(makeCard.getNumber());
        }

        Logging.getLog(getClass()).debug("set project key:" + businessKey.getKey());
        ownerBusinessHome.getInstance().getBusinessProject().setSearchKey(businessKey.getKey());

        DescriptionDisplay businessDisplay = new DescriptionDisplay();

        businessDisplay.addLine(DescriptionDisplay.DisplayStyle.NORMAL,new DescriptionDisplay.DisplayData(DescriptionDisplay.DisplayStyle.NORMAL,ownerBusinessHome.getInstance().getBusinessProject().getProjectName()));

        businessDisplay.addLine(DescriptionDisplay.DisplayStyle.NORMAL,
                        new DescriptionDisplay.DisplayData(DescriptionDisplay.DisplayStyle.NORMAL,"开发商编号:" + ownerBusinessHome.getInstance().getBusinessProject().getDeveloperCode()),
                        new DescriptionDisplay.DisplayData(DescriptionDisplay.DisplayStyle.NORMAL,"开发商名称:" + ownerBusinessHome.getInstance().getBusinessProject().getDeveloperName()));

        DecimalFormat df = new DecimalFormat("#0.000");
        df.setGroupingUsed(true);
        df.setRoundingMode(RoundingMode.HALF_UP);

        for(BusinessBuild bb: ownerBusinessHome.getInstance().getBusinessProject().getBusinessBuildList()){
            businessDisplay.addLine(DescriptionDisplay.DisplayStyle.NORMAL,
                    new DescriptionDisplay.DisplayData(DescriptionDisplay.DisplayStyle.NORMAL,"图:" + bb.getMapNumber() + " 丘:" + bb.getBlockNo() + " 幢:" + bb.getBuildNo()),
                    new DescriptionDisplay.DisplayData(DescriptionDisplay.DisplayStyle.NORMAL,bb.getBuildName()),
                    new DescriptionDisplay.DisplayData(DescriptionDisplay.DisplayStyle.NORMAL,"套数:" + bb.getHouseCount()),
                    new DescriptionDisplay.DisplayData(DescriptionDisplay.DisplayStyle.NORMAL,"面积:" + df.format(bb.getArea()) + "㎡"));
        }

        ownerBusinessHome.getInstance().getBusinessProject().setDisplay(DescriptionDisplay.toStringValue(businessDisplay));
    }
}
