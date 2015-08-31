package com.dgsoft.house.action;

import com.dgsoft.house.model.AttachCorporation;
import org.jboss.seam.annotations.Name;

/**
 * Created by cooper on 8/30/15.
 */
@Name("developerAttachCorpHome")
public class DeveloperAttachCorpHome extends AttachCorporationHome {


    @Override
    protected AttachCorporation createInstance(){
        return new AttachCorporation(String.valueOf(HouseNumberBuilder.instance().useNumber(NUMBER_KEY)), AttachCorporation.AttachCorpType.DEVELOPER,true);
    }

}
