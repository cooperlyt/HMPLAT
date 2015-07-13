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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

/**
 * Created by cooper on 7/6/15.
 */
@Name("houseBusinessList")
public class HouseBusinessList extends OwnerEntityQuery<HouseBusiness>{

    private static final String EJBQL = "select houseBusiness from HouseBusiness houseBusiness left join houseBusiness.ownerBusiness biz left join fetch houseBusiness.afterBusinessHouse house left join fetch house.businessHouseOwner owner left join fetch house.businessPools pool left join fetch house.otherPowerCards cards ";

    private static final String SHORT_EJBQL = "select houseBusiness from HouseBusiness houseBusiness left join houseBusiness.ownerBusiness biz left join fetch houseBusiness.afterBusinessHouse house left join fetch house.businessHouseOwner owner ";

    private static final String PERSON_EJBQL = "select houseBusiness from HouseBusiness houseBusiness left join houseBusiness.ownerBusiness biz left join fetch houseBusiness.afterBusinessHouse house left join fetch house.businessHouseOwner owner left join fetch house.businessPools pool " +
            " where (pool.credentialsType = #{houseBusinessList.searchCredentialsType} and lower(pool.credentialsNumber) = lower(#{houseBusinessList.searchCredentialsNumber})) " +
            " or (owner.credentialsType = #{houseBusinessList.searchCredentialsType} and lower(owner.credentialsNumber) = lower(#{houseBusinessList.searchCredentialsNumber}))";



    private static final String[] RESTRICTIONS = {
            "biz.applyTime >= #{houseBusinessList.searchDateArea.dateFrom}",
            "biz.applyTime <= #{houseBusinessList.searchDateArea.searchDateTo}",
            "lower(pool.personName) like lower(concat('%',concat(#{houseBusinessList.searchOwnerName},'%')))",
            "lower(owner.personName) like lower(concat('%',concat(#{houseBusinessList.searchOwnerName},'%')))",
            "lower(pool.credentialsNumber) = lower(#{houseBusinessList.searchCredentialsNumber})",
            "lower(owner.credentialsNumber) = lower(#{houseBusinessList.searchCredentialsNumber})",
            "lower(house.projectName) like lower(concat('%',concat(#{houseBusinessList.searchProjectName},'%')))",
            "lower(biz.id) = lower(#{houseBusinessList.searchBizId})",
            "lower(houseBusiness.houseCode) = lower(#{houseBusinessList.searchHouseCode})",
            "lower(cards.id) = lower(#{houseBusinessList.searchCardNumber})",
            "cards.type = #{houseBusinessList.searchCardType}",
            "lower(house.mapNumber) = lower(#{houseBusinessList.searchMapNumber})",
            "lower(house.blockNo) = lower(#{houseBusinessList.searchBlockNumber})",
            "lower(house.buildNo) = lower(#{houseBusinessList.searchBuildNumber})",
            "lower(house.houseOrder) = lower(#{houseBusinessList.searchHouseNumber})"
    };

    public enum SortCol{
        ORDER_BY_CREATE_TIME("houseBusiness.ownerBusiness.createTime"),
        ORDER_BY_BUSINESS_NAME("houseBusiness.ownerBusiness.defineName"),
        ORDER_BY_PROJECT_NAME("houseBusiness.afterBusinessHouse.projectCode"),
        ORDER_BY_BUILD_NUMBER("houseBusiness.afterBusinessHouse.buildCode"),
        ORDER_BY_HOUSE_ORDER("houseBusiness.afterBusinessHouse.houseOrder"),
        ORDER_BY_MAIN_OWNER("houseBusiness.afterBusinessHouse.businessHouseOwner.personName");

        private String colPath;

        SortCol(String colPath) {
            this.colPath = colPath;
        }

        public String getColPath() {
            return colPath;
        }
    }

    public SortCol[] getAllSortCol(){
        return SortCol.values();
    }

    public SortCol getSortCol(){
        for(SortCol col: SortCol.values()){
            if (getOrderColumn().equals(col.colPath)){
                return col;
            }
        }
        return null;
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
            return EnumSet.of(OWNER_BIZ_ID,HOUSE_CODE,HOUSE_OWNER,PROJECT_NAME).contains(this);
        }

        public String getEjbql(){
            if (EnumSet.of(OWNER_BIZ_ID,HOUSE_CODE,PROJECT_NAME,HOUSE_MBBH).contains(this)){
                return SHORT_EJBQL;
            }else{
                return EJBQL;
            }
        }

    }

    public SearchType[] getAllSearchTypes(){
        return SearchType.values();
    }

    public MakeCard.CardType[] getAllCardTypes(){
        return MakeCard.CardType.values();
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

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey){
        this.searchKey = searchKey;
    }


    public SearchDateArea getSearchDateArea() {
        return searchDateArea;
    }

    public void setSearchDateArea(SearchDateArea searchDateArea) {
        this.searchDateArea = searchDateArea;
    }

    public HouseBusinessList() {
        setEjbql(SHORT_EJBQL);
        setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
        setRestrictionLogicOperator("or");
        setOrderColumn(SortCol.ORDER_BY_CREATE_TIME.colPath);
        setOrderDirection("desc");
        setMaxResults(25);
    }

    public void resetCondition(){
        searchKey = null;
        mapNumber = null;
        blockNumber = null;
        buildNumber = null;
        houseNumber = null;
    }

    private MakeCard.CardType cardType;

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

    private PersonEntity.CredentialsType credentialsType;

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


    private void resetEjbql(String sql){
        if (!getEjbql().equals(sql)){
            setEjbql(sql);
        }
    }

    private void clearRestrictionExpression(){
        if (!getRestrictionExpressionStrings().isEmpty()){
            setRestrictionExpressionStrings(new ArrayList<String>(0));
        }
    }

    private void setAllRestrictionExpression(){
        if (getRestrictionExpressionStrings().isEmpty()){
            setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
        }
    }

    private void resetRestrictionLogicOperator(String oper){
        if (!oper.equals(getRestrictionLogicOperator())){
            setRestrictionLogicOperator(oper);
        }
    }

    public List<HouseBusiness> getSearchResult(){
        if ((searchKey == null) || searchKey.trim().equals("")){
            resetEjbql(SHORT_EJBQL);
            clearRestrictionExpression();
            return getResultList();
        }else if (searchType == null) {
            resetEjbql(EJBQL);
        }else if (SearchType.PERSON.equals(searchType)) {
            resetEjbql(PERSON_EJBQL);
            clearRestrictionExpression();
            return getResultList();
        }else{
            resetEjbql(searchType.getEjbql());
        }

        setAllRestrictionExpression();
        if (SearchType.HOUSE_MBBH.equals(searchType) ||
                SearchType.PERSON.equals(searchType) ||
                SearchType.HOUSE_CARD.equals(searchType)){
            resetRestrictionLogicOperator("and");
        }else{
            resetRestrictionLogicOperator("or");
        }
        return getResultList();
    }


}
