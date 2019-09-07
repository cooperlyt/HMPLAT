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
            String developerCode = null;
            if (ownerBusinessHome.getInstance().getBusinessProject() != null){
                BusinessProject businessProject = ownerBusinessHome.getInstance().getBusinessProject();
                if (businessProject!=null) {
                     developerCode = businessProject.getDeveloperCode();
                }

            }else {
                if (ownerBusinessHome.getInstance().getSelectBusiness().getSingleHoues()!=null && !ownerBusinessHome.getInstance().getMoneyBackBusinesses().isEmpty()){
                     developerCode = ownerBusinessHome.getInstance().getSelectBusiness().getSingleHoues().getAfterBusinessHouse().getDeveloperCode();

                }else if (!ownerBusinessHome.getInstance().getProjectChecks().isEmpty()){
                    developerCode = ownerBusinessHome.getInstance().getSelectBusiness().getBusinessProject().getDeveloperCode();

                }
            }

            Developer developer = houseEntityLoader.getEntityManager().find(Developer.class, developerCode);
            if (developer!=null){
                if (developer.getAttachCorporation()!=null) {
                    getInstance().setCredentialsType(PersonEntity.CredentialsType.COMPANY_CODE);
                    getInstance().setCredentialsNumber(developer.getAttachCorporation().getLicenseNumber());
                    getInstance().setPersonName(developer.getName());
                }else {
                    getInstance().setCredentialsType(PersonEntity.CredentialsType.COMPANY_CODE);
                    getInstance().setCredentialsNumber("未知");
                    getInstance().setPersonName(developer.getName());
                }

            }

            getInstance().setOwnerBusiness(ownerBusinessHome.getInstance());
            ownerBusinessHome.getInstance().getBusinessPersions().add(getInstance());
            setHave(true);
        }
    }
}
