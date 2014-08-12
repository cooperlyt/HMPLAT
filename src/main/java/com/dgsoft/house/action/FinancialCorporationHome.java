package com.dgsoft.house.action;

import com.dgsoft.house.HouseSimpleEntityHome;
import com.dgsoft.house.model.FinancialCorporation;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-8-12
 * Time: 下午2:49
 * To change this template use File | Settings | File Templates.
 */
@Name("financialCorporationHome")
public class FinancialCorporationHome extends HouseSimpleEntityHome<FinancialCorporation> {
    @In
    private FacesMessages facesMessages;

    @Override
    protected boolean verifyRemoveAvailable(){
        if(getInstance().getAttachCorporation()!=null){
            facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR,"FinancialCorporation_cant_remove");
            return false;
        }
        return true;
    }
}
