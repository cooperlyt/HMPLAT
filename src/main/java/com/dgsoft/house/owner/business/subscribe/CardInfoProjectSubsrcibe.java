package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.common.system.AuthenticationInfo;
import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.ProjectCard;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2015-09-09.
 * 预售许可证信息
 */
@Name("cardInfoProjectSubsrcibe")
public class CardInfoProjectSubsrcibe extends OwnerEntityHome<ProjectCard> {

    @In
    private OwnerBusinessHome ownerBusinessHome;

    @In
    private AuthenticationInfo authInfo;
    @Override
    public void create(){
        super.create();
        if(!ownerBusinessHome.getInstance().getMakeCards().isEmpty() &&
                ownerBusinessHome.getInstance().getMakeCards().iterator().next().getProjectCard()!=null){
            setId(ownerBusinessHome.getInstance().getMakeCards().iterator().next().getCardInfo().getId());
        }else{

            getInstance().setMakeEmpCode(authInfo.getLoginEmployee().getId());
            getInstance().setMakeEmpName(authInfo.getLoginEmployee().getPersonName());

            ownerBusinessHome.getInstance().getMakeCards().iterator().next().setProjectCard(getInstance());

            getInstance().setMakeCard(ownerBusinessHome.getInstance().getMakeCards().iterator().next());

            getInstance().setProjectSellInfo(ownerBusinessHome.getInstance().getBusinessProject().getProjectSellInfo());




        }
    }
}
