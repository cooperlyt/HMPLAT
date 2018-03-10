package com.dgsoft.house.action;

import com.dgsoft.common.system.PersonHelper;
import com.dgsoft.house.HouseEntityHome;
import com.dgsoft.house.model.AttachCorporation;
import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.model.BusinessPersion;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by cooper on 8/30/15.
 */
@Name("attachCorporationHome")
public class AttachCorporationHome extends HouseEntityHome<AttachCorporation> {

    protected final static String NUMBER_KEY = "ATTACH_CORP";

    public Class<AttachCorporation> getEntityClass(){
        return AttachCorporation.class;
    }

    @Override
    protected boolean verifyPersistAvailable() {
        GregorianCalendar gc = new GregorianCalendar(Locale.CHINA);
        gc.setTime(new Date());
        gc.add(Calendar.YEAR, 1);
        getInstance().setDateTo(gc.getTime());
        return true;
    }

    @In(create = true)
    private OwnerEntityLoader ownerEntityLoader;

    public int getContractNumberCount(){
        return ownerEntityLoader.getEntityManager().createQuery("select count(cn) from ContractNumber cn where cn.status = 'FREE' and cn.groupNumber = :groupNumber",Long.class)
                .setParameter("groupNumber",getInstance().getId()).getSingleResult().intValue();
    }



    private PersonHelper<AttachCorporation> ownerPersonHelper;

    public PersonHelper<AttachCorporation> getOwnerPersonInstance() {
        if ((ownerPersonHelper == null) || (ownerPersonHelper.getPersonEntity() != getInstance())) {
            ownerPersonHelper = new PersonHelper<AttachCorporation>(getInstance());
        }
        return ownerPersonHelper;
    }

}
