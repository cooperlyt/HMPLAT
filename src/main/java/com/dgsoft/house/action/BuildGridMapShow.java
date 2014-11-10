package com.dgsoft.house.action;

import com.dgsoft.house.HouseEntityLoader;
import com.dgsoft.house.model.District;
import com.dgsoft.house.model.Project;
import com.dgsoft.house.model.Section;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cooper on 10/31/14.
 */

@Name("buildGridMapShow")
@Scope(ScopeType.CONVERSATION)
public class BuildGridMapShow {

    @In(create = true)
    private BuildHome buildHome;

    private District district;

    private String sectionName;

    private Project project;

    private List<Project> projects;


    @In(create = true)
    private HouseEntityLoader houseEntityLoader;


    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        if ((district == null) || !district.equals(this.district)){
            sectionName = null;
            project = null;
            projects = null;
            buildHome.clearInstance();
        }
        this.district = district;
    }

    public String getSectionName() {
            return sectionName;
    }

    public void setSectionName(String name) {
        if ((sectionName == null) || !sectionName.equals(name)){
            project = null;
            projects = null;
            buildHome.clearInstance();
        }
        this.sectionName = name;
    }

    public List<Project> getProjectList(){
        if (projects == null) {
            projects = houseEntityLoader.getEntityManager().createQuery("select project from Project project where project.section.name = :sectionName", Project.class).setParameter("sectionName", this.sectionName).getResultList();
        }
        return projects;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        if ((project == null) || !project.equals(this.project)){
            buildHome.clearInstance();
        }
        this.project = project;
    }
}
