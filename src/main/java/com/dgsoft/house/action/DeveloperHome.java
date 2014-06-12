package com.dgsoft.house.action;

import com.dgsoft.house.HouseSimpleEntityHome;
import com.dgsoft.house.model.Developer;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;

/**
 * Created with IntelliJ IDEA.
 * User: cooperlee
 * Date: 14-6-11
 * Time: 下午4:42
 */
@Name("developerHome")
public class DeveloperHome extends HouseSimpleEntityHome<Developer> {

    @In
    private FacesMessages facesMessages;

    @Override
    protected boolean verifyRemoveAvailable(){
        if (getEntityManager().createQuery("select coalesce(Count(project.id),0) from Project project where project.developer.id = :developerId",Long.class)
                .setParameter("developerId",getInstance().getId()).getSingleResult() > 0){
            facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR,"Developer_cant_remove");
            return false;
        }
        return true;
    }

}
