package com.dgsoft.house.action;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;

/**
 * Created by cooper on 1/8/15.
 */
@Name("projectEdit")
@Scope(ScopeType.CONVERSATION)
public class ProjectEdit {

    @In(create = true)
    private SectionHome sectionHome;

    @In(create = true)
    private ProjectHome projectHome;

    @In(create = true)
    private DeveloperHome developerHome;

    @In
    private DistrictHome districtHome;

    @In
    private FacesMessages facesMessages;

    @Transactional
    public String saveProject(){

        if (!sectionHome.isIdDefined() &&
                ((sectionHome.getInstance().getName() == null) || sectionHome.getInstance().getName().trim().equals(""))){
            facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR,"SectionRequired");
            return null;
        }

        if (!developerHome.isIdDefined() &&
                ((developerHome.getInstance().getName() == null) || developerHome.getInstance().getName().trim().equals(""))){
            facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR,"DeveloperRequired");
            return null;
        }


        if (!sectionHome.isIdDefined()){
            sectionHome.getInstance().setDistrict(districtHome.getInstance());
        }
        projectHome.getInstance().setSection(sectionHome.getInstance());

        projectHome.getInstance().setDeveloper(developerHome.getInstance());

        if(projectHome.isManaged()){
            return projectHome.update();
        }else
            return projectHome.persist();
    }
}
