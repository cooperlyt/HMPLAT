package com.dgsoft.house.action;

import com.dgsoft.house.AttachCorpType;
import com.dgsoft.house.model.AttachCorporation;
import com.dgsoft.house.model.HouseSellCompany;
import org.jboss.seam.annotations.Name;

import java.util.Date;

/**
 * Created by cooper on 9/14/16.
 */
@Name("agenciesAttachCorpHome")
public class AgenciesAttachCorpHome extends AttachCorporationHome {


    private HouseSellCompany houseSellCompany;

    public HouseSellCompany getHouseSellCompany() {
        if (houseSellCompany == null){
            initInstance();
        }
        return houseSellCompany;
    }

    @Override
    protected AttachCorporation createInstance(){
        AttachCorporation result =  new AttachCorporation(String.valueOf(HouseNumberBuilder.instance().useNumber(NUMBER_KEY)), AttachCorpType.AGENCIES,true, new Date());
        houseSellCompany = new HouseSellCompany(result, new Date());
        result.setHouseSellCompany(houseSellCompany);

        return result;
    }

    @Override
    protected boolean verifyPersistAvailable() {
        boolean result = super.verifyPersistAvailable();
        if (result){
            houseSellCompany.setId(getInstance().getId());
        }
        return result;
    }


    @Override
    protected void initInstance(){
        super.initInstance();
        if (isIdDefined()){
            houseSellCompany = getInstance().getHouseSellCompany();
        }
    }
}
