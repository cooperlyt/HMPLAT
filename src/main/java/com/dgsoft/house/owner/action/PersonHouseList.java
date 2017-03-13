package com.dgsoft.house.owner.action;

import com.dgsoft.common.utils.seam.MultiOperatorEntityQuery;
import com.dgsoft.common.utils.seam.RestrictionGroup;
import com.dgsoft.house.owner.model.HouseRecord;
import org.jboss.seam.annotations.Name;

import java.util.Arrays;

/**
 * Created by cooper on 4/25/16.
 */
@Name("personHouseList")
public class PersonHouseList extends MultiOperatorEntityQuery<HouseRecord>{

    private static final String EJBQL = "select distinct hr from HouseRecord hr " +
            "left join fetch hr.businessHouse h " +
            "left join h.powerPersons pool " +
            "left join fetch h.mainOwner owner";


    private static final String[] RESTRICTIONS = {
            "lower(pool.personName) = lower(#{personHouseList.searchKey})",
            "lower(pool.credentialsNumber) = lower(#{personHouseList.searchKey})",
            "lower(owner.personName) = lower(#{personHouseList.searchKey})",
            "lower(owner.credentialsNumber) = lower(#{personHouseList.searchKey})",
    };

    private static final String[] RESTRICTIONS2 = {
            "lower(h.mapNumber) = lower(#{personHouseList.searchMapNumber})",
            "lower(h.blockNo) = lower(#{personHouseList.searchBlockNumber})",
            "lower(h.buildNo) = lower(#{personHouseList.searchBuildNumber})",
            "lower(h.houseOrder) = lower(#{personHouseList.searchHouseNumber})"
    };



    public PersonHouseList() {
        setEjbql(EJBQL);
        setRestrictionLogicOperator("or");

        RestrictionGroup restrictionGroup = new RestrictionGroup("or",Arrays.asList(RESTRICTIONS));
        restrictionGroup.getChildren().add(new RestrictionGroup("and",Arrays.asList(RESTRICTIONS2)));
        setRestrictionGroup(restrictionGroup);
        //setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
        setOrderColumn("hr.houseCode");
        setMaxResults(50);
    }

    private String searchKey;

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    private String getSearchPart(int index){
        if (searchKey == null){
            return null;
        }
        String[] result = searchKey.split("-");
        if (result.length == 4){
            return result[index];
        }

        return null;
    }

    public String getSearchMapNumber(){
        return getSearchPart(0);
    }

    public String getSearchBlockNumber(){
        return getSearchPart(1);
    }

    public String getSearchBuildNumber(){
        return getSearchPart(2);
    }

    public String getSearchHouseNumber(){
        return getSearchPart(3);
    }

    @Override
    protected String getPersistenceContextName() {
        return "ownerEntityManager";
    }
}
