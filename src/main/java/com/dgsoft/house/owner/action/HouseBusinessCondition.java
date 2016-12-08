package com.dgsoft.house.owner.action;

import com.dgsoft.common.SearchDateArea;
import com.dgsoft.common.utils.seam.RestrictionGroup;
import org.jboss.seam.annotations.Name;

import java.util.Arrays;

/**
 * Created by cooper on 7/31/15.
 */
@Name("houseBusinessCondition")
public class HouseBusinessCondition extends BusinessHouseCondition {

    public static final String POWER_PERSON_EJBQL = "select distinct biz from HouseBusiness biz " +
            "left join fetch biz.ownerBusiness ob " +
            "left join fetch biz.ownerBusiness ob " +
            "left join biz.afterBusinessHouse house " +
            "left join house.powerPersons owner " ;

    public static final String EJBQL = "select biz from HouseBusiness biz  left join fetch biz.ownerBusiness ob  " ;


    public enum SearchType {
        ALL(EJBQL,new RestrictionGroup("or",Arrays.asList(new String[] {
                "lower(biz.key) like lower(concat('%',concat('%',#{houseBusinessCondition.searchKey},'%')))",
                "lower(biz.houseCode) = lower(#{houseBusinessCondition.searchKey})",
                "lower(ob.id) = lower(#{houseBusinessCondition.searchKey})"
        }))),
        OWNER_BIZ_ID(EJBQL,new RestrictionGroup("and", Arrays.asList(new String[]{"lower(ob.id) = lower(#{houseBusinessCondition.searchKey})"}))),
        HOUSE_CODE(EJBQL,new RestrictionGroup("and",Arrays.asList(new String[]{"lower(biz.houseCode) = lower(#{houseBusinessCondition.searchKey})"}))),
        HOUSE_OWNER(POWER_PERSON_EJBQL,new RestrictionGroup("and", Arrays.asList(new String[]{ "owner.old = false", "owner.personName = #{houseBusinessCondition.searchKey}"}))),
        PERSON(POWER_PERSON_EJBQL,new RestrictionGroup("and",Arrays.asList(new String[]{ "owner.old = false",
                "owner.credentialsType = #{houseBusinessCondition.searchCredentialsType}",
                "lower(owner.credentialsNumber) = lower(#{houseBusinessCondition.searchCredentialsNumber})"}))),
        HOUSE_MBBH("select biz from HouseBusiness biz  left join fetch biz.ownerBusiness ob left join biz.afterBusinessHouse house ",
                new RestrictionGroup("and",Arrays.asList(new String[]{"lower(house.mapNumber) = lower(#{houseBusinessCondition.searchMapNumber})",
                "lower(house.blockNo) = lower(#{houseBusinessCondition.searchBlockNumber})",
                "lower(house.buildNo) = lower(#{houseBusinessCondition.searchBuildNumber})",
                "lower(house.houseOrder) = lower(#{houseBusinessCondition.searchHouseNumber})"})));

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


//    public RestrictionGroup getRestrictionGroup(String[] rootRestriction) {
//        if (!isHaveCondition()){
//            return null;
//        }
//
//        if (getSearchType() != null) {
//            if (BusinessHouseCondition.SearchType.PERSON.equals(getSearchType())) {
//
//                if (personRestriction == null) {
//                    personRestriction = new RestrictionGroup("or");
//                    personRestriction.getChildren().add(new RestrictionGroup("and", Arrays.asList(HouseBusinessCondition.RESTRICTIONS_PERSON_POOL)));
//                    personRestriction.getChildren().add(new RestrictionGroup("and", Arrays.asList(HouseBusinessCondition.RESTRICTIONS_PERSON_OWNER)));
//                    personRestriction.getChildren().add(new RestrictionGroup("and" ,Arrays.asList(RESTRICTIONS_CONTRACT_OWNER)));
//                    if (rootRestriction != null){
//                        RestrictionGroup last = new RestrictionGroup("and" ,Arrays.asList(rootRestriction));
//                        last.getChildren().add(personRestriction);
//                        personRestriction = last;
//                    }
//                }
//
//                return personRestriction;
//            } else if (BusinessHouseCondition.SearchType.HOUSE_MBBH.equals(getSearchType())) {
//                if (restrictionGroupMMBH == null){
//
//                    restrictionGroupMMBH = new RestrictionGroup("and", Arrays.asList(HouseBusinessCondition.RESTRICTIONS_MBBH));
//
//                    if (rootRestriction != null){
//                        RestrictionGroup last = new RestrictionGroup("and" ,Arrays.asList(rootRestriction));
//                        last.getChildren().add(restrictionGroupMMBH);
//                        restrictionGroupMMBH = last;
//                    }
//                }
//                return restrictionGroupMMBH;
//
//
//            } else if (BusinessHouseCondition.SearchType.HOUSE_CARD.equals(getSearchType())) {
//                if ( MakeCard.CardType.OWNER_RSHIP.equals(getCardType()) || MakeCard.CardType.NOTICE.equals(getCardType()) ||
//                        MakeCard.CardType.PROJECT_MORTGAGE.equals(getCardType())){
//                    if (restrictionGroupOwnerCard == null){
//                        restrictionGroupOwnerCard = new RestrictionGroup("and", Arrays.asList(HouseBusinessCondition.ORESTRICTIONS_OWNER_CARD));
//                        if (rootRestriction != null){
//                            RestrictionGroup last = new RestrictionGroup("and" ,Arrays.asList(rootRestriction));
//                            last.getChildren().add(restrictionGroupOwnerCard);
//                            restrictionGroupOwnerCard = last;
//                        }
//                    }
//
//                    return restrictionGroupOwnerCard;
//
//                }else if (MakeCard.CardType.POOL_RSHIP.equals(getCardType()) ){
//                    if (restrictionGroupPoolCard == null){
//                        restrictionGroupPoolCard = new RestrictionGroup("and", Arrays.asList(HouseBusinessCondition.ORESTRICTIONS_POOL_CARD));
//                        if (rootRestriction != null){
//                            RestrictionGroup last = new RestrictionGroup("and" ,Arrays.asList(rootRestriction));
//                            last.getChildren().add(restrictionGroupPoolCard);
//                            restrictionGroupPoolCard = last;
//                        }
//                    }
//                    return restrictionGroupPoolCard;
//                }else{
//                    if (restrictionGroupHouseCard == null){
//                        restrictionGroupHouseCard = new RestrictionGroup("and", Arrays.asList(HouseBusinessCondition.RESTRICTIONS_HOUSE_CARD));
//                        if (rootRestriction != null){
//                            RestrictionGroup last = new RestrictionGroup("and" ,Arrays.asList(rootRestriction));
//                            last.getChildren().add(restrictionGroupHouseCard);
//                            restrictionGroupHouseCard = last;
//                        }
//                    }
//                    return restrictionGroupHouseCard;
//                }
//            }
//        }
//
//        if (restrictionGroupDefault == null) {
//            List<String> allConditionList = new ArrayList<String>(Arrays.asList(RESTRICTIONS));
//            allConditionList.addAll(Arrays.asList(RESTRICTIONS_MBBH));
//
//            restrictionGroupDefault = new RestrictionGroup("or", allConditionList);
//            if (rootRestriction != null){
//                RestrictionGroup last = new RestrictionGroup("and" ,Arrays.asList(rootRestriction));
//                last.getChildren().add(restrictionGroupDefault);
//                restrictionGroupDefault = last;
//            }
//        }
//
//        return restrictionGroupDefault;
//    }


    protected boolean isHaveCondition(){
        if (SearchType.HOUSE_MBBH.equals(getSearchType())) {
            return (getMapNumber() != null && !getMapNumber().trim().equals("")) ||
                    (getBlockNumber() != null && !getBlockNumber().trim().equals("")) ||
                    (getBuildNumber() != null && !getBuildNumber().trim().equals("")) ||
                    (getHouseNumber() != null && !getHouseNumber().trim().equals(""));
        }else {
            return (getSearchKey() != null) && (!getSearchKey().trim().equals(""));
        }

    }

}
