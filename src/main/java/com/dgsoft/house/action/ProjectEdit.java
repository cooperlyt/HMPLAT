package com.dgsoft.house.action;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;

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

    public String sectionSelectComplete() {
        projectHome.getInstance().setSection(sectionHome.getInstance());
        if ((projectHome.getInstance().getAddress() == null) ||
                "".equals(projectHome.getInstance().getAddress().trim()))
        projectHome.getInstance().setAddress(sectionHome.getInstance().getAddress());
        if (projectHome.getInstance().getName() == null || projectHome.getInstance().getName().trim().equals("")){
            projectHome.getInstance().setName(sectionHome.getInstance().getName());
        }
        if(projectHome.isManaged() && (projectHome.getInstance().getDeveloper() != null)){
            developerHome.setId(projectHome.getInstance().getDeveloper().getId());
        }
        return "success";
    }

    @Transactional
    public String saveProject(){
        projectHome.getInstance().setDeveloper(developerHome.getInstance());
        if(projectHome.isManaged()){
            return projectHome.update();
        }else
            return projectHome.persist();
    }
}
