package com.dgsoft.house.owner.action;

import com.dgsoft.common.utils.seam.RestrictionGroup;
import org.jboss.seam.annotations.Name;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

/**
 * Created by cooper on 10/2/15.
 */

@Name("houseRecordCondition")
public class HouseRecordCondition extends BusinessHouseCondition {

    public static final String POWER_PERSON_EJBQL = "select distinct hr from HouseRecord hr " +
            "left join fetch hr.businessHouse house " +
            "left join house.powerPersons owner where owner.old = false" ;

    public static final String EJBQL = "select hr from HouseRecord hr left join fetch hr.businessHouse house " ;

    private static RestrictionGroup personRestrictionGroup = new RestrictionGroup("and",Arrays.asList(new String[]{
            "owner.credentialsType = #{houseRecordCondition.credentialsType}"}));

    static {
        personRestrictionGroup.getChildren().add(new RestrictionGroup("or", Arrays.asList(new String[]{"lower(owner.credentialsNumber) = lower(#{houseRecordCondition.credentialsNumber18})",
                "lower(owner.credentialsNumber) = lower(#{houseRecordCondition.credentialsNumber15})" })));
    }


    public enum SearchType{
        ALL(EJBQL,new RestrictionGroup("or",Arrays.asList(new String[] {
                "lower(hr.searchKey) like lower(concat('%',concat('%',#{houseRecordCondition.searchKey},'%')))",
                "lower(hr.houseCode) = lower(#{houseRecordCondition.searchKey})"
        }))),
        HOUSE_CODE(EJBQL,new RestrictionGroup("and",Arrays.asList(new String[]{"lower(hr.houseCode) = lower(#{houseRecordCondition.searchKey})"}))),
        HOUSE_OWNER(POWER_PERSON_EJBQL,new RestrictionGroup("and", Arrays.asList(new String[]{ "owner.personName = #{houseRecordCondition.searchKey}"}))),
        PERSON(POWER_PERSON_EJBQL,personRestrictionGroup),
        HOUSE_MBBH("select hr from HouseRecord hr left join hr.businessHouse house ",
                new RestrictionGroup("and",Arrays.asList(new String[]{
                "lower(house.mapNumber) = lower(#{houseRecordCondition.mapNumber})",
                "lower(house.blockNo) = lower(#{houseRecordCondition.blockNumber})",
                "lower(house.buildNo) = lower(#{houseRecordCondition.buildNumber})",
                "lower(house.houseOrder) = lower(#{houseRecordCondition.houseNumber})"})));

        private RestrictionGroup restrictionGroup;

        private String jpql;

        public String getJpql() {
            return jpql;
        }

        public RestrictionGroup getRestrictionGroup() {
            return restrictionGroup;
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

//
//    private String frameNumber;
//
//    private String cabinetNumber;
//
//    private String boxNumber;
//
//    public String getFrameNumber() {
//        return frameNumber;
//    }
//
//    public void setFrameNumber(String frameNumber) {
//        this.frameNumber = frameNumber;
//    }
//
//    public String getCabinetNumber() {
//        return cabinetNumber;
//    }
//
//    public void setCabinetNumber(String cabinetNumber) {
//        this.cabinetNumber = cabinetNumber;
//    }
//
//    public String getBoxNumber() {
//        return boxNumber;
//    }
//
//    public void setBoxNumber(String boxNumber) {
//        this.boxNumber = boxNumber;
//    }
//
//


    public List<SearchType> getAllSearchTypes() {
        return new ArrayList<SearchType>(EnumSet.allOf(SearchType.class));
    }


    public String getEjbql() {
        return getSearchType().getJpql();
    }

    @Override
    public RestrictionGroup getRestrictionGroup() {
        return getSearchType().getRestrictionGroup();
    }


}
