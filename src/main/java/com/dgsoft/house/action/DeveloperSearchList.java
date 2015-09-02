package com.dgsoft.house.action;

import com.dgsoft.common.utils.seam.MultiOperatorEntityQuery;
import com.dgsoft.common.utils.seam.RestrictionGroup;
import com.dgsoft.house.HouseEntityQuery;
import com.dgsoft.house.model.Developer;
import org.jboss.seam.Component;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Logging;

import java.util.Arrays;

/**
 * Created by cooper on 8/30/15.
 */
@Name("developerSearchList")
public class DeveloperSearchList extends MultiOperatorEntityQuery<Developer> {

    private static final int PAGE_COUNT = 12;

    private static final String EJBQL = "select developer from Developer developer where developer.destroyed = false";

    private static final String[] RESTRICTIONS ={
            "lower(developer.id) = lower(#{developerSearchList.searchKey}) " ,
            "lower(developer.pyCode) like lower(concat('%',#{developerSearchList.searchKey},'%')) " ,
            " (lower(developer.name) like lower(concat('%',#{developerSearchList.searchKey},'%')))"
    };

    private String searchKey;

    public DeveloperSearchList() {
        setEjbql(EJBQL);

        setOrderColumn("developer.createTime");
        setOrderDirection("desc");

        setRestrictionLogicOperator("and");
        RestrictionGroup districtRestriction = new RestrictionGroup("or",Arrays.asList(RESTRICTIONS));

        setRestrictionGroup(districtRestriction);

        setMaxResults(PAGE_COUNT);
    }


    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public void reset(){
        searchKey = null;
        setMaxResults(PAGE_COUNT);
    }

    public void moreResult(){
        if (getMaxResults() == null){
            setMaxResults(PAGE_COUNT);
        }else{
            setMaxResults(getMaxResults() + PAGE_COUNT);
        }
    }

    private String newDeveloperName;

    public String getNewDeveloperName() {
        return newDeveloperName;
    }

    public void setNewDeveloperName(String newDeveloperName) {
        this.newDeveloperName = newDeveloperName;
    }


    public void createDeveloperBySearchName() {
        DeveloperHome developerHome = (DeveloperHome) Component.getInstance(DeveloperHome.class,true);

        if (developerHome.isIdDefined()) {
            developerHome.clearInstance();
        }

        newDeveloperName = getSearchKey();
        developerHome.setNameAndPy(newDeveloperName);
        Logging.getLog(getClass()).debug("create developer by searchName:" + newDeveloperName);
    }

    @Override
    protected String getPersistenceContextName() {
        return "houseEntityManager";
    }

}
