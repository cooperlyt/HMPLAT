package com.dgsoft.house.action;

import com.dgsoft.house.HouseEntityHome;
import com.dgsoft.house.model.AttachCorporation;
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


}
