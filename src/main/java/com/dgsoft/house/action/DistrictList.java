package com.dgsoft.house.action;

import com.dgsoft.house.HouseEntityQuery;
import com.dgsoft.house.model.District;
import org.jboss.seam.annotations.Name;

import java.util.Arrays;

/**
 * Created by cooper on 12/9/14.
 */
@Name("districtList")
public class DistrictList extends HouseEntityQuery<District>{

    private static final String EJBQL = "select district from District district";

    private static final String[] RESTRICTIONS = {
            "lower(district.name) like lower(concat('%',#{baseMapDataMgr.districtName},'%'))",
            "lower(district.id) like lower(concat('%',#{baseMapDataMgr.districtId},'%'))"
    };

    public DistrictList() {
        setEjbql(EJBQL);
        setRestrictionLogicOperator("or");
        setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
        setMaxResults(25);
    }
}
