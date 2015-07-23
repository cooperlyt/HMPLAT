package com.dgsoft.house.action;

import com.dgsoft.common.utils.seam.MultiOperatorEntityQuery;
import com.dgsoft.common.utils.seam.RestrictionGroup;
import com.dgsoft.common.utils.seam.TestEntityQuery;
import com.dgsoft.house.HouseEntityQuery;
import com.dgsoft.house.model.Section;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.framework.EntityQuery;
import org.jboss.seam.log.Logging;
import org.json.Test;

import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.List;

/**
 * Created by cooper on 6/11/15.
 */
@Name("sectionSelectList")
@Scope(ScopeType.CONVERSATION)
public class SectionSelectList extends TestEntityQuery<Section> {

    private static final String EJBQL = "select section from Section section";

    private static final int PAGE_COUNT = 12;

    private static final String[] RESTRICTIONS1 = {
            "lower(section.district.id) = lower(#{sectionSelectList.districtId})",

    };

    private static final String[] RESTRICTIONS2 ={
            "(lower(section.pyCode) like lower(concat('%',#{sectionSelectList.searchKey},'%')) ) " ,
            " (lower(section.name) like lower(concat('%','#{sectionSelectList.searchKey}','%')))"
    };

    public SectionSelectList() {
        setEjbql(EJBQL);

        setOrderColumn("section.createTime");
        setOrderDirection("desc");

        setRestrictionLogicOperator("and");

        RestrictionGroup districtRestriction = new RestrictionGroup("and",Arrays.asList(RESTRICTIONS1));

        districtRestriction.getChildren().add(new RestrictionGroup("or",Arrays.asList(RESTRICTIONS2)));

        setMaxResults(PAGE_COUNT);

    }

    public void moreResult(){
        if (getMaxResults() == null){
            setMaxResults(PAGE_COUNT);
        }else{
            setMaxResults(getMaxResults() + PAGE_COUNT);
            Logging.getLog(getClass()).debug("section moreResult count:" + getMaxResults());
        }
    }

    private String districtId;

    private String searchKey;

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;

    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;

    }

    @Override
    protected String getPersistenceContextName() {
        return "houseEntityManager";
    }
}
