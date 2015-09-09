package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.common.system.PersonEntity;
import com.dgsoft.house.owner.model.BusinessPersion;
import com.dgsoft.house.owner.model.BusinessProject;
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

    @Override
    public void create() {
        super.create();
        if (!isHave()) {
            clearInstance();

            BusinessProject businessProject = ownerBusinessHome.getInstance().getBusinessProject();
            if (businessProject != null) {
                getInstance().setCredentialsType(PersonEntity.CredentialsType.COMPANY_CODE);
                getInstance().setCredentialsNumber(businessProject.getDeveloperCode());
                getInstance().setPersonName(businessProject.getDeveloperName());
            }

            getInstance().setOwnerBusiness(ownerBusinessHome.getInstance());
            ownerBusinessHome.getInstance().getBusinessPersions().add(getInstance());
            setHave(true);
        }
    }
}
