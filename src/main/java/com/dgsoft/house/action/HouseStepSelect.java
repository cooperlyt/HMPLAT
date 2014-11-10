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


    @In(create = true)
    private HouseEntityLoader houseEntityLoader;


    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        if ((district == null) || !district.equals(this.district)) {
            section = null;
            project = null;
            buildHome.clearInstance();
        }
        this.district = district;
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
            project = null;
            buildHome.clearInstance();
        }

        this.section = section;
    }
}
