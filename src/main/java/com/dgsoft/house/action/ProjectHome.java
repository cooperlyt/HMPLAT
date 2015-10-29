package com.dgsoft.house.action;

import com.dgsoft.common.SetLinkList;
import com.dgsoft.common.helper.ActionExecuteState;
import com.dgsoft.house.HouseEntityHome;
import com.dgsoft.house.model.Build;
import com.dgsoft.house.model.Project;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: cooperlee
 * Date: 14-6-12
 * Time: 下午4:49
 */
@Name("projectHome")
public class ProjectHome extends HouseEntityHome<Project> {

    private static final String NUMBER_KEY = "PROJECT_ID";

//    private SetLinkList<Build> projectBuilds;

    @In
    private FacesMessages facesMessages;


    @Override
    protected Project createInstance() {
        return new Project(String.valueOf(HouseNumberBuilder.instance().useNumber(NUMBER_KEY)), new Date());
    }

    @Override
    protected boolean verifyRemoveAvailable() {
        if (getEntityManager().createQuery("select count(build.id) from Build build where build.project.id = :projectId", Long.class).
                setParameter("projectId", getInstance().getId()).getSingleResult() > 0) {
            facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR, "ProjectCantDelete");
            return false;
        } else
            return true;
    }

}
