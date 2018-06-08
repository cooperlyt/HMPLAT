package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.common.system.AuthenticationInfo;
import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.CardInfo;
import com.dgsoft.house.owner.model.MakeCard;
import com.dgsoft.house.owner.model.ProjectCard;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import java.util.Date;

/**
 * Created by wxy on 2018-06-08.
 */
@Name("fcMakeCardProjectSubsrcibe")
public class FcMakeCardProjectSubsrcibe extends OwnerEntityHome<MakeCard> {
    @In
    private OwnerBusinessHome ownerBusinessHome;

    @Override
    public MakeCard createInstance() {
        return new MakeCard(MakeCard.CardType.PROJECT_RSHIP);
    }

    @Override
    public void create() {
        super.create();
        for (MakeCard makeCard : ownerBusinessHome.getInstance().getMakeCards()) {
            if (makeCard.getType().equals(MakeCard.CardType.PROJECT_RSHIP)) {
                setId(makeCard.getId());
                return;
            }
        }

        getInstance().setEnable(true);
        ProjectCard projectCard = new ProjectCard();
        projectCard.setMakeCard(getInstance());
        projectCard.setPrintTime(new Date());
        projectCard.setProjectSellInfo(ownerBusinessHome.getInstance().getBusinessProject().getProjectSellInfo());
        getInstance().setProjectCard(projectCard);
        getInstance().setOwnerBusiness(ownerBusinessHome.getInstance());
        ownerBusinessHome.getInstance().getMakeCards().add(getInstance());




    }


}
