package com.dgsoft.house.action;

import com.dgsoft.house.AttachCorpType;
import com.dgsoft.house.model.AttachCorporation;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;

import java.util.Date;

/**
 * Created by cooper on 8/30/15.
 */
@Name("developerAttachCorpHome")
public class DeveloperAttachCorpHome extends AttachCorporationHome {

    @In
    private FacesMessages facesMessages;

    @In(create = true)
    private DeveloperHome developerHome;

    @Override
    protected AttachCorporation createInstance(){
        return new AttachCorporation(String.valueOf(HouseNumberBuilder.instance().useNumber(NUMBER_KEY)), AttachCorpType.DEVELOPER,true, new Date());
    }



    public void developerSelectListener(){
        if (developerHome.isIdDefined()){
            if (developerHome.getInstance().getAttachCorporation() != null){
                setId(developerHome.getInstance().getAttachCorporation().getId());
                facesMessages.addFromResourceBundle(StatusMessage.Severity.WARN,"AttachCorpChangeToEdit");
            }
        }
    }

    public String save(){
        getInstance().setDeveloper(developerHome.getInstance());
        developerHome.getInstance().setAttachCorporation(getInstance());
        if (isManaged()){
           return super.update();
        }else{
            return super.persist();
        }
    }

}
