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
public class HouseBusinessCondition extends BusinessHouseCondition {

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


    @Override
    public List<SearchType> getAllSearchTypes(){
        return new ArrayList<SearchType>(EnumSet.of( SearchType.OWNER_BIZ_ID,
                SearchType.HOUSE_CODE,
                SearchType.HOUSE_OWNER,
                SearchType.PROJECT_NAME,
                SearchType.HOUSE_CARD,
                SearchType.PERSON,
                SearchType.HOUSE_MBBH));
    }

    @Override
    public List<MakeCard.CardType> getAllCardTypes(){
        return new ArrayList<MakeCard.CardType>( EnumSet.of(MakeCard.CardType.NOTICE,
                MakeCard.CardType.OWNER_RSHIP, MakeCard.CardType.MORTGAGE_CARD,
                MakeCard.CardType.PROJECT_MORTGAGE, MakeCard.CardType.POOL_RSHIP));
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
        if (!isHaveCondition()){
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

    private RestrictionGroup personRestriction;

    private RestrictionGroup restrictionGroupMMBH;

    private RestrictionGroup restrictionGroupHouseCard;

    private RestrictionGroup restrictionGroupPoolCard;

    private RestrictionGroup restrictionGroupOwnerCard;


    private RestrictionGroup restrictionGroupDefault;





    @Override
    public RestrictionGroup getRestrictionGroup() {
        return getRestrictionGroup(null);
    }


    public RestrictionGroup getRestrictionGroup(String[] rootRestriction) {
        if (!isHaveCondition()){
            return null;
        }

        if (getSearchType() != null) {
            if (BusinessHouseCondition.SearchType.PERSON.equals(getSearchType())) {

                if (personRestriction == null) {
                    personRestriction = new RestrictionGroup("or");
                    personRestriction.getChildren().add(new RestrictionGroup("and", Arrays.asList(HouseBusinessCondition.RESTRICTIONS_PERSON_POOL)));
                    personRestriction.getChildren().add(new RestrictionGroup("and", Arrays.asList(HouseBusinessCondition.RESTRICTIONS_PERSON_OWNER)));
                    if (rootRestriction != null){
                        RestrictionGroup last = new RestrictionGroup("and" ,Arrays.asList(rootRestriction));
                        last.getChildren().add(personRestriction);
                        personRestriction = last;
                    }
                }

                return personRestriction;
            } else if (BusinessHouseCondition.SearchType.HOUSE_MBBH.equals(getSearchType())) {
                if (restrictionGroupMMBH == null){

                    restrictionGroupMMBH = new RestrictionGroup("and", Arrays.asList(HouseBusinessCondition.RESTRICTIONS_MBBH));

                    if (rootRestriction != null){
                        RestrictionGroup last = new RestrictionGroup("and" ,Arrays.asList(rootRestriction));
                        last.getChildren().add(restrictionGroupMMBH);
                        restrictionGroupMMBH = last;
                    }
                }
                return restrictionGroupMMBH;


            } else if (BusinessHouseCondition.SearchType.HOUSE_CARD.equals(getSearchType())) {
                if ( MakeCard.CardType.OWNER_RSHIP.equals(getCardType()) || MakeCard.CardType.NOTICE.equals(getCardType()) ||
                        MakeCard.CardType.PROJECT_MORTGAGE.equals(getCardType())){
                    if (restrictionGroupOwnerCard == null){
                        restrictionGroupOwnerCard = new RestrictionGroup("and", Arrays.asList(HouseBusinessCondition.ORESTRICTIONS_OWNER_CARD));
                        if (rootRestriction != null){
                            RestrictionGroup last = new RestrictionGroup("and" ,Arrays.asList(rootRestriction));
                            last.getChildren().add(restrictionGroupOwnerCard);
                            restrictionGroupOwnerCard = last;
                        }
                    }

                    return restrictionGroupOwnerCard;

                }else if (MakeCard.CardType.POOL_RSHIP.equals(getCardType()) ){
                    if (restrictionGroupPoolCard == null){
                        restrictionGroupPoolCard = new RestrictionGroup("and", Arrays.asList(HouseBusinessCondition.ORESTRICTIONS_POOL_CARD));
                        if (rootRestriction != null){
                            RestrictionGroup last = new RestrictionGroup("and" ,Arrays.asList(rootRestriction));
                            last.getChildren().add(restrictionGroupPoolCard);
                            restrictionGroupPoolCard = last;
                        }
                    }
                    return restrictionGroupPoolCard;
                }else{
                    if (restrictionGroupHouseCard == null){
                        restrictionGroupHouseCard = new RestrictionGroup("and", Arrays.asList(HouseBusinessCondition.RESTRICTIONS_HOUSE_CARD));
                        if (rootRestriction != null){
                            RestrictionGroup last = new RestrictionGroup("and" ,Arrays.asList(rootRestriction));
                            last.getChildren().add(restrictionGroupHouseCard);
                            restrictionGroupHouseCard = last;
                        }
                    }
                    return restrictionGroupHouseCard;
                }
            }
        }

        if (restrictionGroupDefault == null) {
            List<String> allConditionList = new ArrayList<String>(Arrays.asList(RESTRICTIONS));
            allConditionList.addAll(Arrays.asList(RESTRICTIONS_MBBH));

            restrictionGroupDefault = new RestrictionGroup("or", allConditionList);
            if (rootRestriction != null){
                RestrictionGroup last = new RestrictionGroup("and" ,Arrays.asList(rootRestriction));
                last.getChildren().add(restrictionGroupDefault);
                restrictionGroupDefault = last;
            }
        }

        return restrictionGroupDefault;
    }

}
