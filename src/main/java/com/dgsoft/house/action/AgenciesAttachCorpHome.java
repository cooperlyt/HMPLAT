package com.dgsoft.house.action;

import com.dgsoft.house.AttachCorpType;
import com.dgsoft.house.model.AttachCorporation;
import com.dgsoft.house.model.Agencies;
import org.jboss.seam.annotations.Name;

import java.util.Date;

/**
 * Created by cooper on 9/14/16.
 */
@Name("agenciesAttachCorpHome")
public class AgenciesAttachCorpHome extends AttachCorporationHome {


    private Agencies agencies;

    public Agencies getAgencies() {
        if (agencies == null){
            initInstance();
        }
        return agencies;
    }

    @Override
    protected AttachCorporation createInstance(){
        AttachCorporation result =  new AttachCorporation(String.valueOf(HouseNumberBuilder.instance().useNumber(NUMBER_KEY)), AttachCorpType.AGENCIES,true, new Date());
        agencies = new Agencies(result, new Date());
        result.setAgencies(agencies);

        return result;
    }

    @Override
    protected boolean verifyPersistAvailable() {
        boolean result = super.verifyPersistAvailable();
        if (result){
            agencies.setId(getInstance().getId());
        }
        return result;
    }


    @Override
    protected void initInstance(){
        super.initInstance();
        if (isIdDefined()){
            agencies = getInstance().getAgencies();
        }
    }
}
