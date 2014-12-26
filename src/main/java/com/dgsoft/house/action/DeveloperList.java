package com.dgsoft.house.action;

import com.dgsoft.house.HouseEntityQuery;
import com.dgsoft.house.model.Developer;
import org.jboss.seam.annotations.Name;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: cooperlee
 * Date: 14-6-13
 * Time: 下午1:33
 */
@Name("developerList")
public class DeveloperList extends HouseEntityQuery<Developer> {

    private static final String EJBQL = "select developer from Developer developer";

    private static final String[] RESTRICTIONS = {
            "lower(developer.name) like lower(concat('%',#{baseMapDataMgr.developerName},'%'))",
            "lower(developer.pyCode) like lower(concat('%',#{baseMapDataMgr.developerName},'%'))",
            "lower(developer.id) like lower(concat('%',#{baseMapDataMgr.developerId},'%'))"

    };

    private static final String[] SORT_COLUMNS = {
            "developer.createTime", "developer.name", "developer.id"
    };

    public List<String> getSortColumns() {
        return Arrays.asList(SORT_COLUMNS);
    }

    public DeveloperList() {
        setEjbql(EJBQL);
        setRestrictionLogicOperator("or");
        setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
        setOrderColumn("developer.createTime");
        setOrderDirection("desc");
        setMaxResults(25);
    }
}
