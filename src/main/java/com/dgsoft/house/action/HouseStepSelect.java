package com.dgsoft.house.action;

import com.dgsoft.house.HouseEntityLoader;
import com.dgsoft.house.model.Build;
import com.dgsoft.house.model.BuildGridMap;
import com.dgsoft.house.model.Project;
import com.dgsoft.house.model.Section;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import javax.persistence.NoResultException;
import java.util.List;

/**
 * Created by cooper on 10/31/14.
 */

@Name("houseStepSelect")
@Scope(ScopeType.CONVERSATION)
public class HouseStepSelect {

    @In(create = true)
    private HouseEntityLoader houseEntityLoader;

    @In(required = false)
    private BuildHome buildHome;

    @In(required = false)
    private SectionSelectList sectionSelectList;

    private Section selectSection;

    private String selectProjectId;

    public Section getSelectSection() {
        return selectSection;
    }

    public String getSelectSectionId() {
        if (selectSection == null) {
            return null;
        }
        return selectSection.getId();
    }

    public void setSelectSectionId(String id) {
        if ((id == null) && id.trim().equals("")) {
            selectSection = null;
        } else {
            selectSection = houseEntityLoader.getEntityManager().find(Section.class, id);
        }
    }

    public String getSelectProjectId() {
        return selectProjectId;
    }

    public void setSelectProjectId(String selectProjectId) {
        this.selectProjectId = selectProjectId;
    }

    public Project getSelectProject(){
        if (selectProjectId == null || selectProjectId.trim().equals("")){
            return null;
        }

        return  houseEntityLoader.getEntityManager().find(Project.class,selectProjectId);
    }


    public void resetSection() {
        if (sectionSelectList != null){
            sectionSelectList.reset();
        }
        selectSection = null;
        resetPorject();

    }

    public void resetPorject() {
        selectProjectId = null;
        if (buildHome != null) {
            buildHome.clearInstance();
        }
    }


    public List<Build> getBuildList() {
        return houseEntityLoader.getEntityManager().createQuery("select build from Build build where build.project.id = :projectId order by build.buildNo ").setParameter("projectId", getSelectProjectId()).getResultList();
    }


}
