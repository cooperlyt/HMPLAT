package com.dgsoft.house.owner.action;

import org.jboss.seam.annotations.Name;

import java.util.EnumSet;

/**
 * Created by cooper on 10/2/15.
 */

@Name("houseRecordCondition")
public class HouseRecordCondition {

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
}
