package com.dgsoft.house.action;

import com.dgsoft.house.HouseEntityQuery;
import com.dgsoft.house.model.MappingCorporation;
import org.jboss.seam.annotations.Name;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-8-11
 * Time: 下午2:32
 * To change this template use File | Settings | File Templates.
 */
@Name("mappingCorporationList")
public class MappingCorporationList extends HouseEntityQuery<MappingCorporation> {

    private static final String EJBQL = "select mappingCorporation from MappingCorporation mappingCorporation";

    private static final String[] RESTRICTIONS = {
            "lower(mappingCorporation.name) like lower(concat('%',#{mappingCorporationList.name},'%'))"};

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MappingCorporationList(){
        setEjbql(EJBQL);
        setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
        setMaxResults(25);
    }
}
