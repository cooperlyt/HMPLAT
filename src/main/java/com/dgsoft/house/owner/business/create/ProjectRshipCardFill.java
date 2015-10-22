package com.dgsoft.house.owner.business.create;

import com.dgsoft.common.system.business.BusinessDataFill;
import com.dgsoft.common.system.business.BusinessInstance;
import com.dgsoft.house.HouseEntityLoader;
import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.ContractOwner;
import com.dgsoft.house.owner.model.HouseBusiness;
import com.dgsoft.house.owner.model.ProjectCard;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2015-10-22.
 * 提取项目预售许可证编号到备案人信息表
 */
@Name("projectRshipCardFill")
public class ProjectRshipCardFill implements BusinessDataFill {


    @In
    private OwnerBusinessHome ownerBusinessHome;


    @In(create = true)
    private OwnerEntityLoader ownerEntityLoader;

    @Override
    public void fillData() {
        if (!ownerBusinessHome.getInstance().getType().equals(BusinessInstance.BusinessType.MODIFY_BIZ)) {
            for (HouseBusiness houseBusiness : ownerBusinessHome.getInstance().getHouseBusinesses()) {
                ProjectCard projectCard = ownerEntityLoader.getEntityManager().find(ProjectCard.class,houseBusiness.getAfterBusinessHouse().getProjectCode());
                if (projectCard!=null && projectCard.getMakeCard()!=null && projectCard.getMakeCard().getNumber()!=null){
                    ContractOwner contractOwner = new ContractOwner();
                    contractOwner.setProjectRshipNumber(projectCard.getMakeCard().getNumber());
                    contractOwner.setOwnerBusiness(ownerBusinessHome.getInstance());
                    ownerBusinessHome.getInstance().getContractOwners().add(contractOwner);
                    houseBusiness.getAfterBusinessHouse().setContractOwner(contractOwner);
                }
            }

        }

    }
}
