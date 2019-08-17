package com.dgsoft.house.owner.action;

import com.dgsoft.common.SearchDateArea;
import com.dgsoft.common.utils.seam.RestrictionGroup;
import org.jboss.seam.annotations.Name;

import java.util.Arrays;

/**
 * Created by wxy on 2019-08-17.
 */
@Name("moneyBackBusinessCondition")
public class MoneyBackBusinessCondition extends BusinessHouseCondition {



    private String contract;


    public String getContract() {

        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public static final String POWER_PERSON_EJBQL = "select distinct biz from HouseBusiness biz " +
            "left join fetch biz.ownerBusiness ob " +
            "left join biz.afterBusinessHouse house " +
            "left join house.powerPersons owner ";

    public static final String EJBQL = "select biz from HouseBusiness biz left join fetch biz.ownerBusiness ob  " ;

    private static RestrictionGroup personRestrictionGroup = new RestrictionGroup("and", Arrays.asList(new String[]{
            "owner.credentialsType = #{moneyBackBusinessCondition.credentialsType}"}));

    static {
        personRestrictionGroup.getChildren().add(new RestrictionGroup("or", Arrays.asList(new String[]{"lower(owner.credentialsNumber) = lower(#{houseBusinessCondition.credentialsNumber18})",
                "lower(owner.credentialsNumber) = lower(#{moneyBackBusinessCondition.credentialsNumber15})" })));
    }

    public enum SearchType {
        ALL(EJBQL,new RestrictionGroup("or",Arrays.asList(new String[] {
                "lower(biz.searchKey) like lower(concat('%',concat('%',#{moneyBackBusinessCondition.searchKey},'%')))",
                "lower(biz.houseCode) = lower(#{moneyBackBusinessCondition.searchKey})",
                "lower(ob.id) = lower(#{moneyBackBusinessCondition.searchKey})",
                "lower(biz.houseContract.contractNumber) = lower(#{moneyBackBusinessCondition.searchKey})"
        }))),
        OWNER_BIZ_ID(EJBQL,new RestrictionGroup("and", Arrays.asList(new String[]{"lower(ob.id) = lower(#{moneyBackBusinessCondition.searchKey})"}))),
        CONTRACT(EJBQL,new RestrictionGroup("and", Arrays.asList(new String[]{"lower(biz.houseContract.contractNumber) = lower(#{moneyBackBusinessCondition.searchKey})"}))),
        HOUSE_CODE(EJBQL,new RestrictionGroup("and",Arrays.asList(new String[]{"lower(biz.houseCode) = lower(#{moneyBackBusinessCondition.searchKey})"}))),
        HOUSE_OWNER(POWER_PERSON_EJBQL,new RestrictionGroup("and", Arrays.asList(new String[]{ "owner.personName = #{moneyBackBusinessCondition.searchKey}"}))),
        PERSON(POWER_PERSON_EJBQL,personRestrictionGroup),
        HOUSE_MBBH("select biz from HouseBusiness biz  left join fetch biz.ownerBusiness ob left join biz.afterBusinessHouse house ",
                new RestrictionGroup("and",Arrays.asList(new String[]{"lower(house.mapNumber) = lower(#{moneyBackBusinessCondition.mapNumber})",
                        "lower(house.blockNo) = lower(#{moneyBackBusinessCondition.blockNumber})",
                        "lower(house.buildNo) = lower(#{moneyBackBusinessCondition.buildNumber})",
                        "lower(house.houseOrder) = lower(#{moneyBackBusinessCondition.houseNumber})"})));

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

    @Override
    public void resetCondition(){
        setSearchKey(null);
        setMapNumber(null);
        setBlockNumber(null);
        setBuildNumber(null);
        setHouseNumber(null);
        setContract(null);
    }

}
