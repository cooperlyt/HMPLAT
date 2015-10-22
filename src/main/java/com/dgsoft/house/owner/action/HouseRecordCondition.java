package com.dgsoft.house.owner.action;

import com.dgsoft.common.utils.seam.RestrictionGroup;
import com.dgsoft.house.owner.model.MakeCard;
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


    public static final String[] RESTRICTIONS_PERSON_POOL = {
            "pool.credentialsType = #{houseRecordCondition.searchCredentialsType}",
            "lower(pool.credentialsNumber) = lower(#{houseRecordCondition.searchCredentialsNumber})"
    };

    public static final String[] RESTRICTIONS_PERSON_OWNER = {
            "owner.credentialsType = #{houseRecordCondition.searchCredentialsType}",
            "lower(owner.credentialsNumber) = lower(#{houseRecordCondition.searchCredentialsNumber})"
    };


    public static final String[] ORESTRICTIONS_OWNER_CARD = {
            "lower(ownerCard.number) = lower(#{houseRecordCondition.searchCardNumber})",
            "ownerCard.type = #{houseRecordCondition.searchCardType}"
    };

    public static final String[] ORESTRICTIONS_POOL_CARD = {
            "lower(poolCard.number) = lower(#{houseRecordCondition.searchCardNumber})",
            "poolCard.type = #{houseRecordCondition.searchCardType}"
    };


    public static final String[] RESTRICTIONS_MBBH = {
            "lower(house.mapNumber) = lower(#{houseRecordCondition.searchMapNumber})",
            "lower(house.blockNo) = lower(#{houseRecordCondition.searchBlockNumber})",
            "lower(house.buildNo) = lower(#{houseRecordCondition.searchBuildNumber})",
            "lower(house.houseOrder) = lower(#{houseRecordCondition.searchHouseNumber})"
    };


    public static final String[] RESTRICTIONS_LOCATION = {
            "lower(rs.frame) = lower(#{houseRecordCondition.searchFrameNumber})",
            "lower(rs.cabinet) = lower(#{houseRecordCondition.searchCabinetNumber})",
            "lower(rs.box) = lower(#{houseRecordCondition.searchBoxNumber})"
    };

    public static final String[] RESTRICTIONS = {
            "lower(pool.personName) like lower(concat('%',concat(#{houseRecordCondition.searchOwnerName},'%')))",
            "lower(owner.personName) like lower(concat('%',concat(#{houseRecordCondition.searchOwnerName},'%')))",
            "lower(pool.credentialsNumber) = lower(#{houseRecordCondition.searchCredentialsNumber})",
            "lower(owner.credentialsNumber) = lower(#{houseRecordCondition.searchCredentialsNumber})",
            "lower(house.buildName) like lower(concat('%',concat(#{houseRecordCondition.searchProjectName},'%')))",
            "lower(houseBusiness.houseCode) = lower(#{houseRecordCondition.searchHouseCode})",
            "lower(ownerCard.number) = lower(#{houseRecordCondition.searchCardNumber})",
            "lower(poolCard.number) = lower(#{houseRecordCondition.searchCardNumber})",
            "lower(rs.recordCode) = lower(#{houseRecordCondition.searchRecordNumber})",
    };


    private String frameNumber;

    private String cabinetNumber;

    private String boxNumber;

    public String getFrameNumber() {
        return frameNumber;
    }

    public void setFrameNumber(String frameNumber) {
        this.frameNumber = frameNumber;
    }

    public String getCabinetNumber() {
        return cabinetNumber;
    }

    public void setCabinetNumber(String cabinetNumber) {
        this.cabinetNumber = cabinetNumber;
    }

    public String getBoxNumber() {
        return boxNumber;
    }

    public void setBoxNumber(String boxNumber) {
        this.boxNumber = boxNumber;
    }


    public String getSearchFrameNumber(){
        if ((getSearchType() == null) || getSearchType().equals(SearchType.RECORD_LOCATION)){
            return frameNumber;
        }
        return null;
    }

    public String getSearchCabinetNumber(){
        if ((getSearchType() == null) || getSearchType().equals(SearchType.RECORD_LOCATION)){
            return cabinetNumber;
        }
        return null;
    }

    public String getSearchBoxNumber(){
        if ((getSearchType() == null) || getSearchType().equals(SearchType.RECORD_LOCATION)){
            return boxNumber;
        }
        return null;
    }

    public String getSearchRecordNumber(){
        if ((getSearchType() == null) || getSearchType().equals(SearchType.RECORD_NUMBER)){
            return getSearchKey();
        }
        return null;
    }

    @Override
    public List<SearchType> getAllSearchTypes() {
        return new ArrayList<SearchType>(EnumSet.allOf(SearchType.class));
    }

    @Override
    public List<MakeCard.CardType> getAllCardTypes() {
        return new ArrayList<MakeCard.CardType>(EnumSet.of(MakeCard.CardType.NOTICE,
                MakeCard.CardType.OWNER_RSHIP,
                MakeCard.CardType.PROJECT_MORTGAGE,
                MakeCard.CardType.POOL_RSHIP));
    }

    @Override
    public String getEjbql() {
        return null;
    }

    @Override
    protected boolean isHaveCondition(){
        boolean result = super.isHaveCondition();
        if (!result){
            if (SearchType.RECORD_LOCATION.equals(getSearchType())){
                return (frameNumber != null && !frameNumber.trim().equals("")) ||
                        (cabinetNumber != null && !cabinetNumber.trim().equals("")) ||
                        (boxNumber != null && !boxNumber.trim().equals(""));
            }
        }
        return result;
    }

    private RestrictionGroup restrictionDefault;
    private RestrictionGroup restrictionLocation;
    private RestrictionGroup restrictionOwnerCard;
    private RestrictionGroup restrictionPoolCard;
    private RestrictionGroup restrictionMBBH;
    private RestrictionGroup personRestriction;



    @Override
    public RestrictionGroup getRestrictionGroup() {
        if (!isHaveCondition()){
            return null;
        }

        if (getSearchType() != null) {
            if (SearchType.RECORD_NUMBER.equals(getSearchType()) ||
                    SearchType.RECORD_LOCATION.equals(getSearchType())) {
                return null;
            }

            if (BusinessHouseCondition.SearchType.PERSON.equals(getSearchType())) {

                if(personRestriction == null) {
                    personRestriction = new RestrictionGroup("or");
                    personRestriction.getChildren().add(new RestrictionGroup("and", Arrays.asList(RESTRICTIONS_PERSON_POOL)));
                    personRestriction.getChildren().add(new RestrictionGroup("and", Arrays.asList(RESTRICTIONS_PERSON_OWNER)));
                }


                return personRestriction;
            } else if (BusinessHouseCondition.SearchType.HOUSE_MBBH.equals(getSearchType())) {
                if (restrictionMBBH == null){
                    restrictionMBBH = new RestrictionGroup("and", Arrays.asList(RESTRICTIONS_MBBH));
                }

                return restrictionMBBH;


            } else if (BusinessHouseCondition.SearchType.HOUSE_CARD.equals(getSearchType())) {
                if (MakeCard.CardType.POOL_RSHIP.equals(getCardType()) ){
                    if (restrictionPoolCard == null){
                        restrictionPoolCard = new RestrictionGroup("and", Arrays.asList(ORESTRICTIONS_POOL_CARD));
                    }
                    return restrictionPoolCard;
                }else{
                    if (restrictionOwnerCard == null){
                        restrictionOwnerCard = new RestrictionGroup("and", Arrays.asList(ORESTRICTIONS_OWNER_CARD));
                    }
                    return restrictionOwnerCard;
                }
            } else if (BusinessHouseCondition.SearchType.RECORD_LOCATION.equals(getSearchType())){
                if (restrictionLocation == null){
                    restrictionLocation = new RestrictionGroup("and", Arrays.asList(RESTRICTIONS_LOCATION));
                }
                return restrictionLocation;
            }
        }

        if (restrictionDefault == null){
            List<String> allCondition = new ArrayList<String>(Arrays.asList(RESTRICTIONS));
            allCondition.addAll(Arrays.asList(RESTRICTIONS_MBBH));
            restrictionDefault = new RestrictionGroup("or",allCondition);
        }


        return restrictionDefault;
    }
}
