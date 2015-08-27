package com.dgsoft.house.action;

import com.dgsoft.house.HouseEntityQuery;
import com.dgsoft.house.model.AttachCorporation;
import org.jboss.seam.annotations.Name;

import java.util.Arrays;

/**
 * Created by cooper on 8/26/15.
 */
@Name("developerAttachCorporationList")
public class DeveloperAttachCorporationList extends HouseEntityQuery<AttachCorporation>{

    private static final String EJBQL = "select aCorp from AttachCorporation aCorp left join fetch aCorp.developer developer";

    private static final String[] RESTRICTIONS = {
            "lower(developer.name) like lower(concat('%',#{attachCorpMgr.searchKey},'%'))"
    };


    public DeveloperAttachCorporationList() {
        setEjbql(EJBQL);
        setRestrictionLogicOperator("or");
        setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
        setOrderColumn("aCorp.recordDate");
        setOrderDirection("desc");
        setMaxResults(25);
    }



}
