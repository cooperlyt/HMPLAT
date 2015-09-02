package com.dgsoft.house.action;

import com.dgsoft.common.system.PersonHelper;
import com.dgsoft.house.HouseEntityHome;
import com.dgsoft.house.model.AttachEmployee;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by cooper on 9/2/15.
 */
@Name("attachEmployeeHome")
public class AttachEmployeeHome extends HouseEntityHome<AttachEmployee> {

    private static final String NUMBER_KEY = "ATTACH_EMPLOYEE_ID";

    @In
    private AttachCorporationHome attachCorporationHome;

    @Override
    protected boolean verifyPersistAvailable() {
        getInstance().setAttachCorporation(attachCorporationHome.getInstance());
        GregorianCalendar gc = new GregorianCalendar(Locale.CHINA);
        gc.setTime(new Date());
        gc.add(Calendar.YEAR, 1);
        getInstance().setLicenseTimeTo(gc.getTime());
        return true;
    }

    @Override
    protected AttachEmployee createInstance(){
        return new AttachEmployee(String.valueOf(HouseNumberBuilder.instance().useNumber(NUMBER_KEY)),true,new Date());
    }

    private PersonHelper<AttachEmployee> personHelper;

    public PersonHelper<AttachEmployee> getPersonInstance() {
        if ((personHelper == null) || (personHelper.getPersonEntity() != getInstance())) {
            personHelper = new PersonHelper<AttachEmployee>(getInstance());
        }
        return personHelper;
    }
}
