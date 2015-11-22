package com.dgsoft.house.action;

import com.dgsoft.common.utils.seam.MultiOperatorEntityQuery;
import com.dgsoft.common.utils.seam.RestrictionGroup;
import com.dgsoft.house.HouseEntityQuery;
import com.dgsoft.house.model.House;
import org.jboss.seam.annotations.Name;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-8-15
 * Time: 上午10:48
 * To change this template use File | Settings | File Templates.
 */
@Name("houseList")
public class HouseList extends MultiOperatorEntityQuery<House> {

    private static final String EJBQL = "select house from House house " +
            "left join fetch house.build build left join fetch build.project project " +
            "left join fetch project.developer developer left join fetch project.section section " +
            " where deleted = false";

    private static final String[] RESTRICTIONS = {
            "lower(house.id) = lower(#{houseList.searchKey})",
            "lower(build.name) like lower(concat('%',#{houseList.searchKey},'%'))",
            "lower(build.doorNo) like lower(concat('%',#{houseList.searchKey},'%'))",
            "lower(project.address) like lower(concat('%',#{houseList.searchKey},'%'))",
            "lower(developer.name) like lower(concat('%',#{houseList.searchKey},'%'))",
            "lower(section.id) = lower(#{houseList.searchKey})",
            "lower(section.name) like lower(concat('%',#{houseList.searchKey},'%'))",
            "lower(section.address) like lower(concat('%',#{houseList.searchKey},'%'))",
            "lower(section.pyCode) like lower(concat('%',#{houseList.searchKey},'%'))",
            "lower(build.id) = lower(#{houseList.searchKey})",
            "lower(project.id) = lower(#{houseList.searchKey})",
            "lower(developer.id) = lower(#{houseList.searchKey})",
            "lower(project.address) = lower(concat('%',#{houseList.searchKey},'%'))"

    };

    private String searchKey;

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public HouseList() {
        setEjbql(EJBQL);
        setRestrictionLogicOperator("and");

        setRestrictionGroup(new RestrictionGroup("or",Arrays.asList(RESTRICTIONS)));
        setOrderExpress("build.id,house.houseOrder,house.id");

        setMaxResults(25);

    }


    @Override
    protected String getPersistenceContextName() {
        return "houseEntityManager";
    }


}
