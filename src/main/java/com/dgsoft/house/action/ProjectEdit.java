package com.dgsoft.house.action;

import com.dgsoft.house.model.District;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;
import org.jboss.seam.log.Logging;

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

    @In(create = true)
    private SectionSelectList sectionSelectList;

    @In(create = true)
    private DeveloperSearchList developerSearchList;

    @In
    private FacesMessages facesMessages;

    private String newSectionName;

    public String getNewSectionName() {
        return newSectionName;
    }

    public void setNewSectionName(String newSectionName) {
        this.newSectionName = newSectionName;
    }

    public void createSectionBySearchName() {
        if (sectionHome.isIdDefined()) {
            sectionHome.clearInstance();
        }
        newSectionName = sectionSelectList.getSearchKey();

        sectionHome.setNameAndPy(newSectionName);
        Logging.getLog(getClass()).debug("create section by searchName:" + newSectionName);

    }


    private String newDeveloperName;

    public String getNewDeveloperName() {
        return newDeveloperName;
    }

    public void setNewDeveloperName(String newDeveloperName) {
        this.newDeveloperName = newDeveloperName;
    }


    public void createDeveloperBySearchName() {
        if (developerHome.isIdDefined()) {
            developerHome.clearInstance();
        }

        newDeveloperName = developerSearchList.getSearchKey();
        developerHome.setNameAndPy(newDeveloperName);
        Logging.getLog(getClass()).debug("create developer by searchName:" + newDeveloperName);
    }


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
            sectionHome.getInstance().setDistrict(sectionHome.getEntityManager().find(District.class, sectionSelectList.getDistrictId()));
        }
        projectHome.getInstance().setSection(sectionHome.getInstance());

        projectHome.getInstance().setDeveloper(developerHome.getInstance());

        if(projectHome.isManaged()){
            return projectHome.update();
        }else
            return projectHome.persist();
    }
}
