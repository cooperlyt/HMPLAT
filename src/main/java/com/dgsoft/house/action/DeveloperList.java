package com.dgsoft.house.action;

import com.dgsoft.house.HouseEntityQuery;
import com.dgsoft.house.model.Developer;
import org.jboss.seam.annotations.Name;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: cooperlee
 * Date: 14-6-13
 * Time: 下午1:33
 */
@Name("developerList")
public class DeveloperList extends HouseEntityQuery<Developer>{

    private static final String EJBQL = "select developer from Developer developer";

    private static final String[] RESTRICTIONS = {
            "lower(developer.name) like lower(concat('%',#{developerList.name},'%'))"};

    private String name;

    public String getName() {
        return  name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DeveloperList() {
        setEjbql(EJBQL);
        setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
        setMaxResults(25);
    }
}
