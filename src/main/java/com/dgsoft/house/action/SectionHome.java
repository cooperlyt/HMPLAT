package com.dgsoft.house.action;

import com.dgsoft.common.SetLinkList;
import com.dgsoft.house.HouseEntityHome;
import com.dgsoft.house.model.Project;
import com.dgsoft.house.model.Section;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;
import org.jboss.seam.log.Logging;

/**
 * Created with IntelliJ IDEA.
 * User: cooperlee
 * Date: 14-6-11
 * Time: 下午3:24
 */
@Name("sectionHome")
public class SectionHome extends HouseEntityHome<Section>{

    @In(required = false)
    private ProjectHome projectHome;

    @In
    private FacesMessages facesMessages;

    @DataModel(value = "projects")
    private SetLinkList<Project> projects;

    @DataModelSelection
    private Project project;


    @Override
    protected void initInstance(){
        super.initInstance();
        projects = new SetLinkList<Project>(getInstance().getProjects());
    }


    public void addNewProject(){
        Project project = projectHome.getReadyInstance();
        project.setSection(getInstance());
        projects.add(project);
        projectHome.clearInstance();
        Logging.getLog(getClass()).debug("project added");
    }

    public void removeNewProject(){
        projects.remove(project);
    }

    @Override
    protected boolean verifyRemoveAvailable() {
        if (getInstance().getSmsubcompanies().isEmpty() && getInstance().getOwnerGroups().isEmpty() && getInstance().getProjects().isEmpty()){
            return true;
        }
        facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR,"SectionCantRemove");
        return false;

    }
}
