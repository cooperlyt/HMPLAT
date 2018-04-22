package com.dgsoft.house.owner.action;

import com.dgsoft.common.SearchDateArea;
import com.dgsoft.common.utils.seam.RestrictionGroup;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Logging;

import java.util.Arrays;

/**
 * Created by wxy on 2018-04-16.
 */
@Name("moneyBusinessCondition")
public class MoneyBusinessCondition extends  BusinessHouseCondition{





     // MoneyBusiness.status<>CHANGED
    //ownerBusiness.status<>RUNNING MODIFYING SUSPEND
    //建立的时候把 把所选的业务状态 修改成MODIFYING，完成后把所选的业改成COMPLETE

    public static final String POWER_PERSON_EJBQL = "select distinct mb from MoneyBusiness mb " +
            "left join mb.houseContract hc " +
            "left join mb.ownerBusiness ob " +
            "left join hc.houseBusiness biz " +
            "left join biz.afterBusinessHouse house "+
            "left join house.powerPersons owner";





    public static final String EJBQL = "select mb from MoneyBusiness mb left join mb.houseContract hc left join mb.ownerBusiness ob left join hc.houseBusiness biz" ;


    private static RestrictionGroup personRestrictionGroup = new RestrictionGroup("and", Arrays.asList(new String[]{
            "owner.credentialsType = #{moneyBusinessCondition.credentialsType}"}));

    static {
        personRestrictionGroup.getChildren().add(new RestrictionGroup("or", Arrays.asList(new String[]{"lower(owner.credentialsNumber) = lower(#{moneyBusinessCondition.credentialsNumber18})",
                "lower(owner.credentialsNumber) = lower(#{moneyBusinessCondition.credentialsNumber15})" })));
    }

    public enum SearchType {
        ALL(EJBQL,new RestrictionGroup("or",Arrays.asList(new String[] {
                "lower(mb.searchKey) like lower(concat('%',concat('%',#{moneyBusinessCondition.searchKey},'%')))",
                "lower(biz.houseCode) = lower(#{moneyBusinessCondition.searchKey})",
                "lower(ob.id) = lower(#{moneyBusinessCondition.searchKey})"
        }))),
        HOUSE_CONTRACT(EJBQL,new RestrictionGroup("and", Arrays.asList(new String[]{"lower(hc.contractNumber) = lower(#{moneyBusinessCondition.searchKey})"}))),
        OWNER_BIZ_ID(EJBQL,new RestrictionGroup("and", Arrays.asList(new String[]{"lower(ob.id) = lower(#{moneyBusinessCondition.searchKey})"}))),
        HOUSE_CODE(EJBQL,new RestrictionGroup("and",Arrays.asList(new String[]{"lower(biz.houseCode) = lower(#{moneyBusinessCondition.searchKey})"}))),
        HOUSE_OWNER(POWER_PERSON_EJBQL,new RestrictionGroup("and", Arrays.asList(new String[]{ "owner.personName = #{moneyBusinessCondition.searchKey}"}))),
        PERSON(POWER_PERSON_EJBQL,personRestrictionGroup),
        HOUSE_MBBH("select mb from MoneyBusiness mb left join mb.houseContract hc left join hc.houseBusiness biz left join mb.ownerBusiness ob left join biz.afterBusinessHouse house ",
                           new RestrictionGroup("and",Arrays.asList(new String[]{"lower(house.mapNumber) = lower(#{moneyBusinessCondition.mapNumber})",
                "lower(house.blockNo) = lower(#{moneyBusinessCondition.blockNumber})",
                "lower(house.buildNo) = lower(#{moneyBusinessCondition.buildNumber})",
                "lower(house.houseOrder) = lower(#{moneyBusinessCondition.houseNumber})"})));

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
