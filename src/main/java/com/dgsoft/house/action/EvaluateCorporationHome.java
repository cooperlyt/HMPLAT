package com.dgsoft.house.action;

import com.dgsoft.house.HouseSimpleEntityHome;
import com.dgsoft.house.model.EvaluateCorporation;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-8-12
 * Time: 上午9:32
 * To change this template use File | Settings | File Templates.
 */
@Name("evaluateCorporationHome")
public class EvaluateCorporationHome extends HouseSimpleEntityHome<EvaluateCorporation> {

    @In
    private FacesMessages facesMessages;


    @Override
    protected boolean verifyRemoveAvailable(){
        if (getInstance().getAttachCorporation()!=null) {
            facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR,"EvaluateCorporation_cant_remove");
            return false;
        }
        return true;
    }
}
