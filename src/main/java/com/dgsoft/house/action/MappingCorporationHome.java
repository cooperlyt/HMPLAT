package com.dgsoft.house.action;

import com.dgsoft.house.HouseSimpleEntityHome;
import com.dgsoft.house.model.EvaluateCorporation;
import com.dgsoft.house.model.MappingCorporation;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-8-11
 * Time: 上午10:37
 * To change this template use File | Settings | File Templates.
 */
@Name("mappingCorporationHome")

public class MappingCorporationHome extends HouseSimpleEntityHome<MappingCorporation> {
    @In
    private FacesMessages facesMessages;

    @Override
    protected boolean verifyRemoveAvailable(){
       if(getEntityManager().createQuery("select coalesce(Count(attachCorporation.id),0) from AttachCorporation attachCorporation where attachCorporation.id = :MappId",Long.class)
        .setParameter("MappId",getInstance().getAttachCorporation().getId()).getSingleResult()>0){
            facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR,"MappingCorporation_cant_remove");
            return false;
        }
        return true;
    }

}
