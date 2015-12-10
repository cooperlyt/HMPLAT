package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.common.system.PersonHelper;
import com.dgsoft.common.system.SystemEntityLoader;
import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessPersion;
import com.dgsoft.house.owner.model.GiveCard;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import java.util.Date;

/**
 * Created by wxy on 2015-12-09.
 * 发证信息编辑
 */
@Name("giveCardSubscribe")
public class GiveCardSubscribe extends OwnerEntityHome<GiveCard> {

    @In
    private OwnerBusinessHome ownerBusinessHome;

    @In(create = true)
    private SystemEntityLoader systemEntityLoader;


    @Override
    public void create(){
        super.create();
        if(!ownerBusinessHome.getInstance().getGiveCards().isEmpty()){
            setId(ownerBusinessHome.getInstance().getGiveCards().iterator().next().getId());
        }else {
            getInstance().setOwnerBusiness(ownerBusinessHome.getInstance());
            getInstance().setGiveTime(new Date());
            ownerBusinessHome.getInstance().getGiveCards().add(getInstance());
        }

    }



    private PersonHelper<GiveCard> personHelper;

    public PersonHelper<GiveCard> getPersonInstance() {
        if ((personHelper == null) || (personHelper.getPersonEntity() != getInstance())) {
            personHelper = new PersonHelper<GiveCard>(getInstance());
        }
        return personHelper;
    }

}
