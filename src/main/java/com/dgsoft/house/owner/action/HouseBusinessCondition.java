package com.dgsoft.house.owner.action;

import com.dgsoft.common.SearchDateArea;
import com.dgsoft.common.system.PersonEntity;
import com.dgsoft.common.utils.seam.RestrictionGroup;
import com.dgsoft.house.owner.model.MakeCard;
import org.jboss.seam.annotations.Name;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

/**
 * Created by cooper on 7/31/15.
 */
@Name("houseBusinessCondition")
public class HouseBusinessCondition{

    public static final String EJBQL = "select distinct biz from OwnerBusiness biz left join biz.houseBusinesses houseBusiness left join biz.makeCards cards left join houseBusiness.afterBusinessHouse house left join house.businessHouseOwner owner left join owner.makeCard ownerCard left join house.businessPools pool left join pool.makeCard poolCard" ;

    public static final String SHORT_EJBQL = "select biz from OwnerBusiness biz ";



    public static final String[] RESTRICTIONS_PERSON_POOL = {
            "pool.credentialsType = #{houseBusinessCondition.searchCredentialsType}",
            "lower(pool.credentialsNumber) = lower(#{houseBusinessCondition.searchCredentialsNumber})"
    };

    public static final String[] RESTRICTIONS_PERSON_OWNER = {
            "owner.credentialsType = #{houseBusinessCondition.searchCredentialsType}",
            "lower(owner.credentialsNumber) = lower(#{houseBusinessCondition.searchCredentialsNumber})"
    };


    public static final String[] RESTRICTIONS_HOUSE_CARD = {
            "lower(cards.number) = lower(#{houseBusinessCondition.searchCardNumber})",
            "cards.type = #{houseBusinessCondition.searchCardType}"
    };

    public static final String[] ORESTRICTIONS_OWNER_CARD = {
            "lower(ownerCard.number) = lower(#{houseBusinessCondition.searchCardNumber})",
            "ownerCard.type = #{houseBusinessCondition.searchCardType}"
    };

    public static final String[] ORESTRICTIONS_POOL_CARD = {
            "lower(poolCard.number) = lower(#{houseBusinessCondition.searchCardNumber})",
            "poolCard.type = #{houseBusinessCondition.searchCardType}"
    };


    public static final String[] RESTRICTIONS_MBBH = {
            "lower(house.mapNumber) = lower(#{houseBusinessCondition.searchMapNumber})",
            "lower(house.blockNo) = lower(#{houseBusinessCondition.searchBlockNumber})",
            "lower(house.buildNo) = lower(#{houseBusinessCondition.searchBuildNumber})",
            "lower(house.houseOrder) = lower(#{houseBusinessCondition.searchHouseNumber})"
    };

    public static final String[] RESTRICTIONS = {
            "biz.applyTime >= #{houseBusinessCondition.searchDateArea.dateFrom}",
            "biz.applyTime <= #{houseBusinessCondition.searchDateArea.searchDateTo}",
            "lower(pool.personName) like lower(concat('%',concat(#{houseBusinessCondition.searchOwnerName},'%')))",
            "lower(owner.personName) like lower(concat('%',concat(#{houseBusinessCondition.searchOwnerName},'%')))",
            "lower(pool.credentialsNumber) = lower(#{houseBusinessCondition.searchCredentialsNumber})",
            "lower(owner.credentialsNumber) = lower(#{houseBusinessCondition.searchCredentialsNumber})",
            "lower(house.buildName) like lower(concat('%',concat(#{houseBusinessCondition.searchProjectName},'%')))",
            "lower(biz.id) = lower(#{houseBusinessCondition.searchBizId})",
            "lower(houseBusiness.houseCode) = lower(#{houseBusinessCondition.searchHouseCode})",
            "lower(cards.number) = lower(#{houseBusinessCondition.searchCardNumber})",
            "lower(ownerCard.number) = lower(#{houseBusinessCondition.searchCardNumber})",
            "lower(poolCard.number) = lower(#{houseBusinessCondition.searchCardNumber})"
    };

    public static List<String> getAllConditionList(){
        List<String> result = new ArrayList<String>(Arrays.asList(RESTRICTIONS));
        result.addAll(Arrays.asList(RESTRICTIONS_MBBH));
        return result;
    }

    private String searchKey;

    private String mapNumber;

    private String blockNumber;

    private String buildNumber;

    private String houseNumber;

    private MakeCard.CardType cardType;

    private PersonEntity.CredentialsType credentialsType;

    public String getMapNumber() {
        return mapNumber;
    }

    public void setMapNumber(String mapNumber) {
        this.mapNumber = mapNumber;
    }

    public String getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(String blockNumber) {
        this.blockNumber = blockNumber;
    }

    public String getBuildNumber() {
        return buildNumber;
    }

    public void setBuildNumber(String buildNumber) {
        this.buildNumber = buildNumber;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey){
        this.searchKey = searchKey;
    }

    public MakeCard.CardType getCardType() {
        return cardType;
    }

    public void setCardType(MakeCard.CardType cardType) {
        this.cardType = cardType;
    }

    public String getCardTypeName(){
        if (cardType == null){
            return null;
        }
        return cardType.name();
    }

    public void setCardTypeName(String name){
        if ((name == null) || name.trim().equals("")){
            cardType = null;
        }else{
            cardType = MakeCard.CardType.valueOf(name);
        }
    }

    public MakeCard.CardType getSearchCardType(){
        if (SearchType.HOUSE_CARD.equals(searchType)){
            return cardType;
        }
        return null;
    }

    public String getSearchCardNumber(){
        if ((searchType == null) || (searchType.equals(SearchType.HOUSE_CARD))){
            return searchKey;
        }
        return null;
    }


    public String getSearchCredentialsNumber(){
        if ((searchType == null) || searchType.equals(SearchType.PERSON)){
            return searchKey;
        }
        return null;
    }


    public String getSearchBizId(){
        if((searchType == null) || searchType.equals(SearchType.OWNER_BIZ_ID)){
            return searchKey;
        }
        return null;
    }

    public String getSearchHouseCode(){
        if ((searchType == null) || (searchType.equals(SearchType.HOUSE_CODE))){
            return searchKey;
        }
        return null;
    }

    public String getSearchOwnerName(){
        if ((searchType == null) || (searchType.equals(SearchType.HOUSE_OWNER))){
            return searchKey;
        }
        return null;
    }

    public String getSearchProjectName(){
        if ((searchType == null) || (searchType.equals(SearchType.PROJECT_NAME))){
            return searchKey;
        }
        return null;
    }

    public String getSearchMapNumber(){
        if (SearchType.HOUSE_MBBH.equals(searchType)){
            return mapNumber;
        }
        return null;
    }

    public String getSearchBlockNumber(){
        if (SearchType.HOUSE_MBBH.equals(searchType)){
            return blockNumber;
        }
        return null;
    }

    public String getSearchBuildNumber(){
        if (SearchType.HOUSE_MBBH.equals(searchType)){
            return buildNumber;
        }
        return null;
    }

    public String getSearchHouseNumber(){
        if (SearchType.HOUSE_MBBH.equals(searchType)){
            return houseNumber;
        }
        return null;
    }


    public PersonEntity.CredentialsType getCredentialsType() {
        return credentialsType;
    }

    public void setCredentialsType(PersonEntity.CredentialsType credentialsType) {
        this.credentialsType = credentialsType;
    }

    public String getCredentialsTypeName(){
        if (credentialsType == null){
            return null;
        }
        return credentialsType.name();
    }

    public void setCredentialsTypeName(String name){
        if ((name == null) || name.trim().equals("")){
            credentialsType = null;
        }else{
            credentialsType = PersonEntity.CredentialsType.valueOf(name);
        }
    }

    public PersonEntity.CredentialsType getSearchCredentialsType(){
        if (SearchType.PERSON.equals(searchType)){
            return credentialsType;
        }
        return null;
    }


    private SearchType searchType;

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

    public enum SearchType {
        OWNER_BIZ_ID,
        HOUSE_CODE,
        HOUSE_OWNER,
        PROJECT_NAME,
        HOUSE_CARD,
        PERSON,
        HOUSE_MBBH;

        public boolean isSearchByOne(){
            return EnumSet.of(OWNER_BIZ_ID, HOUSE_CODE, HOUSE_OWNER, PROJECT_NAME).contains(this);
        }

    }

    public SearchType[] getAllSearchTypes(){
        return SearchType.values();
    }

    public void resetCondition(){
        searchKey = null;
        mapNumber = null;
        blockNumber = null;
        buildNumber = null;
        houseNumber = null;
    }


    private SearchDateArea searchDateArea = new SearchDateArea(null,null);


    public MakeCard.CardType[] getAllCardTypes(){
        return MakeCard.CardType.values();
    }


    public SearchDateArea getSearchDateArea() {
        return searchDateArea;
    }

    public void setSearchDateArea(SearchDateArea searchDateArea) {
        this.searchDateArea = searchDateArea;
    }


    public String getEjbql(){
        if (getSearchKey() == null || getSearchKey().trim().equals("")){
            return HouseBusinessCondition.SHORT_EJBQL;
        }else{
            return HouseBusinessCondition.EJBQL;
        }
//        if (getSearchType() == null){
//            return HouseBusinessCondition.EJBQL;
//        }
//        if (HouseBusinessCondition.SearchType.PERSON.equals(getSearchType())) {
//            return HouseBusinessCondition.EJBQL;
//        }
//
//        if (EnumSet.of(HouseBusinessCondition.SearchType.OWNER_BIZ_ID,
//                HouseBusinessCondition.SearchType.HOUSE_CODE,
//                HouseBusinessCondition.SearchType.PROJECT_NAME,
//                HouseBusinessCondition.SearchType.HOUSE_MBBH).contains(getSearchType())){
//            return HouseBusinessCondition.EJBQL;
//        }else{
//            return HouseBusinessCondition.EJBQL;
//        }
    }

    public RestrictionGroup getRestrictionGroup() {
        if (getSearchKey() == null || getSearchKey().trim().equals("")){
            return null;
        }

        if (getSearchType() != null) {
            if (HouseBusinessCondition.SearchType.PERSON.equals(getSearchType())) {

                RestrictionGroup personRestriction = new RestrictionGroup("or");
                personRestriction.getChildren().add(new RestrictionGroup("and", Arrays.asList(HouseBusinessCondition.RESTRICTIONS_PERSON_POOL)));
                personRestriction.getChildren().add(new RestrictionGroup("and", Arrays.asList(HouseBusinessCondition.RESTRICTIONS_PERSON_OWNER)));


                return personRestriction;
            } else if (HouseBusinessCondition.SearchType.HOUSE_MBBH.equals(getSearchType())) {
                return new RestrictionGroup("and", Arrays.asList(HouseBusinessCondition.RESTRICTIONS_MBBH));


            } else if (HouseBusinessCondition.SearchType.HOUSE_CARD.equals(getSearchType())) {
                if ( MakeCard.CardType.OWNER_RSHIP.equals(cardType) || MakeCard.CardType.NOTICE.equals(cardType)){

                    return new RestrictionGroup("and", Arrays.asList(HouseBusinessCondition.ORESTRICTIONS_OWNER_CARD));

                }else if (MakeCard.CardType.POOL_RSHIP.equals(cardType) ){
                    return new RestrictionGroup("and", Arrays.asList(HouseBusinessCondition.ORESTRICTIONS_POOL_CARD));
                }else{
                    return new RestrictionGroup("and", Arrays.asList(HouseBusinessCondition.RESTRICTIONS_HOUSE_CARD));
                }

            }
        }

        return new RestrictionGroup("or",getAllConditionList());
    }

}
