package com.dgsoft.house.owner.action;

import com.dgsoft.common.SearchDateArea;
import com.dgsoft.common.utils.seam.RestrictionGroup;
import org.jboss.seam.annotations.Name;

import java.util.Arrays;

/**
 * Created by wxy on 2019-08-18.
 */
@Name("moneyBackSearchCondition")
public class MoneyBackSearchCondition extends BusinessHouseCondition {

    public static final String POWER_PERSON_EJBQL = "select distinct mb from MoneyBackBusiness mb " +
            "left join mb.ownerBusiness ob " +
            "left join ob.selectBusiness sob " +
            "left join sob.houseBusinesses biz " +
            "left join biz.afterBusinessHouse house "+
            "left join house.powerPersons owner where owner.old = false";

    public static final String EJBQL = "select mb from MoneyBackBusiness mb left join mb.ownerBusiness ob left join mb.ownerBusiness.selectBusiness sob left join sob.houseBusinesses biz" ;

    private static RestrictionGroup personRestrictionGroup = new RestrictionGroup("and", Arrays.asList(new String[]{
            "owner.credentialsType = #{moneyBackSearchCondition.credentialsType}"}));

    static {
        personRestrictionGroup.getChildren().add(new RestrictionGroup("or", Arrays.asList(new String[]{"lower(owner.credentialsNumber) = lower(#{moneyBackSearchCondition.credentialsNumber18})",
                "lower(owner.credentialsNumber) = lower(#{moneyBackSearchCondition.credentialsNumber15})" })));
    }

    public enum SearchType {
        ALL(EJBQL,new RestrictionGroup("or",Arrays.asList(new String[] {
                "lower(mb.searchKey) like lower(concat('%',concat('%',#{moneyBackSearchCondition.searchKey},'%')))",
                "lower(biz.houseCode) = lower(#{moneyBackSearchCondition.searchKey})",
                "lower(mb.contract) = lower(#{moneyBackSearchCondition.searchKey})",
                "lower(ob.id) = lower(#{moneyBackSearchCondition.searchKey})"
        }))),
        HOUSE_CONTRACT(EJBQL,new RestrictionGroup("and", Arrays.asList(new String[]{"lower(mb.contract) = lower(#{moneyBackSearchCondition.searchKey})"}))),
        OWNER_BIZ_ID(EJBQL,new RestrictionGroup("and", Arrays.asList(new String[]{"lower(ob.id) = lower(#{moneyBackSearchCondition.searchKey})"}))),
        HOUSE_CODE(EJBQL,new RestrictionGroup("and",Arrays.asList(new String[]{"lower(biz.houseCode) = lower(#{moneyBackSearchCondition.searchKey})"}))),
        HOUSE_OWNER(POWER_PERSON_EJBQL,new RestrictionGroup("and", Arrays.asList(new String[]{ "owner.personName = #{moneyBackSearchCondition.searchKey}"}))),
        PERSON(POWER_PERSON_EJBQL,personRestrictionGroup),
        HOUSE_MBBH("select mb from MoneyBackBusiness mb left join mb.ownerBusiness ob left join mb.ownerBusiness.selectBusiness sob left join sob.houseBusiness biz left join biz.afterBusinessHouse house ",
                new RestrictionGroup("and",Arrays.asList(new String[]{"lower(house.mapNumber) = lower(#{moneyBackSearchCondition.mapNumber})",
                        "lower(house.blockNo) = lower(#{moneyBackSearchCondition.blockNumber})",
                        "lower(house.buildNo) = lower(#{moneyBackSearchCondition.buildNumber})",
                        "lower(house.houseOrder) = lower(#{moneyBackSearchCondition.houseNumber})"})));

        private RestrictionGroup restrictionGroup;

        private String jpql;

        public RestrictionGroup getRestrictionGroup() {
            return restrictionGroup;
        }

        public String getJpql() {
            return jpql;
        }

        SearchType(String jpql, RestrictionGroup restrictionGroup) {
            this.restrictionGroup = restrictionGroup;
            this.jpql = jpql;
        }
    }

    private SearchType searchType = SearchType.ALL;

    public SearchType getSearchType() {
        return searchType;
    }

    public void setSearchType(SearchType searchType) {
        this.searchType = searchType;
    }

    public void setSearchTypeName(String type){
        if ((type == null) || type.trim().equals("")){
            setSearchType(null);
        }else{
            setSearchType(SearchType.valueOf(type));
        }
    }

    public String getSearchTypeName(){
        if (searchType == null){
            return null;
        }
        return getSearchType().name();
    }



    public SearchType[] getAllSearchTypes(){
        return SearchType.values();
    }


    private SearchDateArea searchDateArea = new SearchDateArea(null,null);


    public SearchDateArea getSearchDateArea() {
        return searchDateArea;
    }

    public void setSearchDateArea(SearchDateArea searchDateArea) {
        this.searchDateArea = searchDateArea;
    }

    @Override
    public String getEjbql(){
        return getSearchType().getJpql();

    }


    @Override
    public RestrictionGroup getRestrictionGroup() {
        return getSearchType().getRestrictionGroup();
    }
}
