package com.dgsoft.house.action;

import com.dgsoft.house.HouseEntityLoader;
import com.dgsoft.house.model.Section;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;
import org.jboss.seam.log.Logging;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by cooper on 1/7/15.
 */
@Name("sectionSearch")
@Scope(ScopeType.CONVERSATION)
public class SectionSearch {

    @In(create = true)
    private HouseEntityLoader houseEntityLoader;

    @In(create = true)
    private DistrictHome districtHome;

    @In(create = true)
    private SectionHome sectionHome;

    @In
    private FacesMessages facesMessages;

    private String searchName;

    private String newSectionName;

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public String getNewSectionName() {
        return newSectionName;
    }

    public void setNewSectionName(String newSectionName) {
        this.newSectionName = newSectionName;
    }

    public List<Section> getSearchResult() {
        if ((searchName == null) || (searchName.trim().equals(""))) {


            TypedQuery<Section> query = houseEntityLoader.getEntityManager().createQuery("select section from Section section where section.district.id = :districtId order by section.createTime desc", Section.class).setParameter("districtId", districtHome.getInstance().getId());
            query.setMaxResults(10);
            return query.getResultList();
        } else {
            List<Section> result = houseEntityLoader.getEntityManager().createQuery("select section from Section section where section.district.id = :districtId and  ((section.id = :prefix ) or (lower(section.pyCode) like lower(concat('%',:prefix,'%'))) or (lower(section.name) like lower(concat('%',:prefix,'%')))) ", Section.class).
                    setParameter("prefix", searchName).setParameter("districtId", districtHome.getInstance().getId()).getResultList();

            if (result.isEmpty()) {
                result = houseEntityLoader.getEntityManager().createQuery("select section from Section section where ((section.id = :prefix ) or (lower(section.pyCode) like lower(concat('%',:prefix,'%'))) or (lower(section.name) like lower(concat('%',:prefix,'%')))) ", Section.class).
                        setParameter("prefix", searchName).getResultList();
            }
            return result;
        }
    }

    public void showSectionList() {
        searchName = null;
    }

    public void createBySearchName() {
        if (sectionHome.isIdDefined()) {
            sectionHome.clearInstance();
        }
        newSectionName = searchName;
        sectionHome.setNameAndPy(searchName);
        Logging.getLog(getClass()).debug("create section by searchName:" + searchName);

    }


}
