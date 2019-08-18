package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.common.system.PersonEntity;
import com.dgsoft.house.HouseEntityLoader;
import com.dgsoft.house.model.Developer;
import com.dgsoft.house.owner.model.BusinessPersion;
import com.dgsoft.house.owner.model.BusinessProject;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2015-09-09.
 * 预售许可证开开发商转成申请人
 */
@Name("persionCorrectProjectRshipSubscribe")
public class PersionCorrectProjectRshipSubscribe extends BaseBusinessPersionSubscribe {
    @Override
    protected BusinessPersion.PersionType getType() {
        return  BusinessPersion.PersionType.CORRECT;
    }

    @In(create = true)
    private HouseEntityLoader houseEntityLoader;


    @Override
    public void create() {
        super.create();
        if (!isHave()) {
            clearInstance();

            if (ownerBusinessHome.getInstance().getBusinessProject() != null){
                BusinessProject businessProject = ownerBusinessHome.getInstance().getBusinessProject();
                if (businessProject!=null) {
                    getInstance().setCredentialsType(PersonEntity.CredentialsType.COMPANY_CODE);
                    getInstance().setCredentialsNumber(businessProject.getDeveloperCode());
                    getInstance().setPersonName(businessProject.getDeveloperName());
                }

            }else {
                Developer developer = houseEntityLoader.getEntityManager().find(Developer.class, ownerBusinessHome.getSelectBusiness().getSingleHoues().getAfterBusinessHouse().getDeveloperCode());
                if (developer != null) {
                    getInstance().setCredentialsType(PersonEntity.CredentialsType.COMPANY_CODE);
                    getInstance().setCredentialsNumber(developer.getId());
                    getInstance().setPersonName(developer.getName());
                }
            }




            getInstance().setOwnerBusiness(ownerBusinessHome.getInstance());
            ownerBusinessHome.getInstance().getBusinessPersions().add(getInstance());
            setHave(true);
        }
    }
}
