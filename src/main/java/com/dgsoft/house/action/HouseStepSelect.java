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

@Name("houseStepSelect")
@Scope(ScopeType.CONVERSATION)
public class HouseStepSelect {

    @In(create = true)
    private BuildHome buildHome;

    private District district;

    private Section section;

    private Project project;

    private String houseDisplayCode;

    private String mapCode;

    private String blockCode;

    private String buildCode;

    private String houseOrder;

    private String sectionName;

    private List<Project> projects;

    private static final String PROJECTS_DISTRICT_SQL = "select section.name from Section section where section.district.id = :districtId and  ((lower(section.pyCode) like lower(concat('%',:prefix,'%'))) or (lower(section.name) like lower(concat('%',:prefix,'%'))))";

    private static final String PROJECTS_ALL_SQL = "select section.name from Section section where ((lower(section.pyCode) like lower(concat('%',:prefix,'%'))) or (lower(section.name) like lower(concat('%',:prefix,'%'))))";


    @In(create = true)
    private HouseEntityLoader houseEntityLoader;


    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        if ((district == null) || !district.equals(this.district)) {
            setSection(null);
            setSectionName(null);
        }
        this.district = district;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        if ((sectionName == null) || sectionName.equals("") || (!sectionName.equals(this.sectionName))){
            projects = null;
            setProject(null);
        }
        this.sectionName = sectionName;
    }

    public List<Project> getProjects() {
        if(projects == null){
            if (section == null) {
                projects = houseEntityLoader.getEntityManager().createQuery("select project from Project project where ((lower(project.section.pyCode) like lower(concat('%',:prefix,'%'))) or (lower(project.section.name) like lower(concat('%',:prefix,'%'))))", Project.class).
                        setParameter("prefix", sectionName).getResultList();
            }else{
                projects = section.getProjectList();
            }
        }
        return projects;
    }

    public List<String> sectionNameAutoComplete(String prefix){
        if (getDistrict() != null){
           return houseEntityLoader.getEntityManager().createQuery(PROJECTS_DISTRICT_SQL,String.class).
                setParameter("districtId", getDistrict().getId()).setParameter("prefix",prefix).getResultList();
        }else{
           return houseEntityLoader.getEntityManager().createQuery(PROJECTS_ALL_SQL,String.class).
                setParameter("prefix", prefix).getResultList();
        }
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        if ((project == null) || !project.equals(this.project)) {
            buildHome.clearInstance();
        }
        this.project = project;
    }

    public List<Section> sectionAutoComplete(String prefix) {
        if (district != null) {
            return houseEntityLoader.getEntityManager().createQuery("select section from Section section where section.district.id = :districtId and  ((lower(section.pyCode) like lower(concat('%',:prefix,'%'))) or (lower(section.name) like lower(concat('%',:prefix,'%')))) ", Section.class).
                    setParameter("prefix", prefix).setParameter("districtId",district.getId()).getResultList();
        }else{
            return new ArrayList<Section>(0);
        }
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        if ((section == null) || !section.equals(this.section)) {
            projects = null;
            setProject(null);
        }

        this.section = section;
    }

    public String getHouseDisplayCode() {
        return houseDisplayCode;
    }

    public void setHouseDisplayCode(String houseDisplayCode) {
        this.houseDisplayCode = houseDisplayCode;
    }

    public String getMapCode() {
        return mapCode;
    }

    public void setMapCode(String mapCode) {
        this.mapCode = mapCode;
    }

    public String getBlockCode() {
        return blockCode;
    }

    public void setBlockCode(String blockCode) {
        this.blockCode = blockCode;
    }

    public String getBuildCode() {
        return buildCode;
    }

    public void setBuildCode(String buildCode) {
        this.buildCode = buildCode;
    }

    public String getHouseOrder() {
        return houseOrder;
    }

    public void setHouseOrder(String houseOrder) {
        this.houseOrder = houseOrder;
    }
}
