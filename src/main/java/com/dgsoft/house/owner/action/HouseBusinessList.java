package com.dgsoft.house.owner.action;

import com.dgsoft.common.SearchDateArea;
import com.dgsoft.common.system.PersonEntity;
import com.dgsoft.common.system.business.BusinessInstance;
import com.dgsoft.common.system.model.BusinessDefine;
import com.dgsoft.house.owner.OwnerEntityQuery;
import com.dgsoft.house.owner.model.HouseBusiness;
import com.dgsoft.house.owner.model.MakeCard;
import com.dgsoft.house.owner.model.OwnerBusiness;
import org.jboss.seam.annotations.Name;

import java.util.Arrays;
import java.util.EnumSet;

/**
 * Created by cooper on 7/6/15.
 */
@Name("houseBusinessList")
public class HouseBusinessList extends OwnerEntityQuery<HouseBusiness>{

    private static final String EJBQL = "select houseBusiness from HouseBusiness houseBusiness left join houseBusiness.ownerBusiness biz left join fetch houseBusiness.afterBusinessHouse house left join fetch house.businessHouseOwner owner left join fetch house.businessPools pool left join fetch house.otherPowerCards cards left join fetch cards.makeCard makeCard";

    private static final String[] RESTRICTIONS = {
            "biz.applyTime >= #{houseBusinessList.searchDateArea.dateFrom}",
            "biz.applyTime <= #{houseBusinessList.searchDateArea.searchDateTo}",
            "lower(pool.personName) like lower(concat('%',concat(#{houseBusinessList.searchOwnerName},'%')))",
            "lower(owner.personName) like lower(concat('%',concat(#{houseBusinessList.searchOwnerName},'%')))",
            "pool.credentialsType = #{houseBusinessList.searchCredentialsType} ",
            "owner.credentialsType = #{houseBusinessList.searchCredentialsType}",
            "lower(pool.credentialsNumber) = lower(#{houseBusinessList.searchCredentialsNumber})",
            "lower(owner.credentialsNumber) = lower(#{houseBusinessList.searchCredentialsNumber})",
            "lower(house.projectName) like lower(concat('%',concat(#{houseBusinessList.searchProjectName},'%')))",
            "lower(biz.id) = lower(#{houseBusinessList.searchBizId})",
            "lower(houseBusiness.houseCode) = lower(#{houseBusinessList.searchHouseCode})",
            "lower(cards.id) = lower(#{houseBusinessList.searchCardNumber})",
            "makeCard.type = #{houseBusinessList.searchCardType}",
            "lower(house.mapNumber) = lower(#{houseBusinessList.searchMapNumber})",
            "lower(house.blockNo) = lower(#{houseBusinessList.searchBlockNumber})",
            "lower(house.buildNo) = lower(#{houseBusinessList.searchBuildNumber})",
            "lower(house.houseOrder) = lower(#{houseBusinessList.searchHouseNumber})"
    };


    public enum SearchType {
        OWNER_BIZ_ID,
        HOUSE_CODE,
        HOUSE_OWNER,

        HOUSE_MBBH,
        HOUSE_CARD,

        PERSON,
        PROJECT_NAME

    }

    private SearchDateArea searchDateArea = new SearchDateArea(null,null);

    private String searchKey;

    private SearchType searchType;

    public SearchType getSearchType() {
        return searchType;
    }

    public void setSearchType(SearchType searchType) {
        this.searchType = searchType;
    }

    public void setSearchTypeName(String type){
        if ((type == null) || type.trim().equals("")){
            searchType = null;
        }else{
            searchType = SearchType.valueOf(type);
        }
    }

    public String getSearchTypeName(){
        if (searchType == null){
            return null;
        }
        return searchType.name();
    }

    public String getSearchKey() {
        return searchKey;
    }


    public SearchDateArea getSearchDateArea() {
        return searchDateArea;
    }

    public void setSearchDateArea(SearchDateArea searchDateArea) {
        this.searchDateArea = searchDateArea;
    }

    public void refresh(){
        super.refresh();
        if (SearchType.HOUSE_MBBH.equals(searchType) ||
                SearchType.PERSON.equals(searchType) ||
                SearchType.HOUSE_CARD.equals(searchType)){
            setRestrictionLogicOperator("and");
        }else{
            setRestrictionLogicOperator("or");
        }
    }

    public HouseBusinessList() {
        setEjbql(EJBQL);
        setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
        setRestrictionLogicOperator("or");
        setOrderColumn("houseBusiness.ownerBusiness.createTime");
        setOrderDirection("desc");
        setMaxResults(25);
    }

    private MakeCard.CardType cardType;

    public MakeCard.CardType getCardType() {
        return cardType;
    }

    public void setCardType(MakeCard.CardType cardType) {
        this.cardType = cardType;
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

    private PersonEntity.CredentialsType credentialsType;

    public PersonEntity.CredentialsType getCredentialsType() {
        return credentialsType;
    }

    public void setCredentialsType(PersonEntity.CredentialsType credentialsType) {
        this.credentialsType = credentialsType;
    }

    public PersonEntity.CredentialsType getSearchCredentialsType(){
        if (SearchType.PERSON.equals(searchType)){
            return credentialsType;
        }
        return null;
    }

    public String getSearchCredentialsNumber(){
        if ((searchType == null) || searchType.equals(SearchType.PERSON)){
            return searchKey;
        }
        return null;
    }

    private String mapNumber;

    private String blockNumber;

    private String buildNumber;

    private String houseNumber;

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


}
