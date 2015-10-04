package com.dgsoft.house.owner.action;

import com.dgsoft.common.system.PersonEntity;
import com.dgsoft.common.utils.seam.RestrictionGroup;
import com.dgsoft.house.owner.model.MakeCard;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 * Created by cooper on 10/2/15.
 */
public abstract class BusinessHouseCondition {


    private String searchKey;

    private String mapNumber;

    private String blockNumber;

    private String buildNumber;

    private String houseNumber;


    private MakeCard.CardType cardType;

    private PersonEntity.CredentialsType credentialsType;

    public abstract List<SearchType> getAllSearchTypes();

    public abstract List<MakeCard.CardType> getAllCardTypes();

    public abstract String getEjbql();

    public abstract RestrictionGroup getRestrictionGroup();

    private SearchType searchType;

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

    public void resetCondition(){
        searchKey = null;
        mapNumber = null;
        blockNumber = null;
        buildNumber = null;
        houseNumber = null;
    }

    public enum SearchType {
        OWNER_BIZ_ID,
        HOUSE_CODE,
        HOUSE_OWNER,
        PROJECT_NAME,
        HOUSE_CARD,
        PERSON,
        HOUSE_MBBH,
        RECORD_NUMBER,
        RECORD_LOCATION;


        public boolean isSearchByOne(){
            return EnumSet.of(OWNER_BIZ_ID, HOUSE_CODE, HOUSE_OWNER, PROJECT_NAME).contains(this);
        }
    }

    protected boolean isHaveCondition(){
        if (SearchType.HOUSE_MBBH.equals(searchType)) {
            return (mapNumber != null && !mapNumber.trim().equals("")) ||
                    (blockNumber != null && !blockNumber.trim().equals("")) ||
                    (buildNumber != null && !buildNumber.trim().equals("")) ||
                    (houseNumber != null && !houseNumber.trim().equals(""));
        }else {
            return (searchKey != null) && (!searchKey.trim().equals(""));
        }

    }
}
