package com.dgsoft.house.action;

import com.dgsoft.house.HouseSimpleEntityHome;
import com.dgsoft.house.model.District;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;

/**
 * Created with IntelliJ IDEA.
 * User: cooperlee
 * Date: 14-6-5
 * Time: 上午9:49
 */
@Name("districtHome")
public class DistrictHome extends HouseSimpleEntityHome<District> {

    @In
    private FacesMessages facesMessages;

    @Override
    protected boolean verifyRemoveAvailable(){
        if (getEntityManager().createQuery("select coalesce(Count(section.id),0) from Section section where section.district.id = :districtId",Long.class)
                .setParameter("districtId",getInstance().getId()).getSingleResult() > 0){
            facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR,"District_cant_remove");
            return false;
        }
        return true;
    }

}
