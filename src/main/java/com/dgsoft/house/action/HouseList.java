package com.dgsoft.house.action;

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
public class HouseList extends HouseEntityQuery<House> {

    private static final String EJBQL = "select house from House house " +
            "left join fetch house.build build left join fetch build.project project " +
            "left join fetch project.developer left join fetch project.section left join fetch house.houseOwner";

    private static final String[] RESTRICTIONS = {
            "lower(house.build.project.name) like lower(concat('%',#{projectSearchCondition.projectName},'%'))",
            "house.build.project.id = #{projectSearchCondition.projectId}",
            "lower(house.build.project.developer.name) like lower('%',#{developerSearchCondition.developerName},'%')",
            "house.build.project.developer.id = #{developerSearchCondition.developerId})",
            "house.build.id = #{buildSearchCondition.buildid}",
            "lower(house.build.name) like lower(concat('%',#{buildSearchCondition.buildName},'%')",
            "house.build.streetCode = #{buildSearchCondition.streetCode}",
            "house.build.mapNumber = #{buildSearchCondition.mapNumber}",
            "house.build.blockNo = #{buildSearchCondition.blockNo}",
            "house.build.buildNo = #{buildSearchCondition.buildNo}",
            "house.houseOrder = #{houseSearchCondition.houseOrder}",
            "house.id = #{houseSearchCondition.houseId}",
            "lower(build.doorNo) like lower(concat('%',#{buildSearchCondition.doorNo},'%')",
            "lower(house.address) like lower('%',#{houseSearchCondition.houseAddres},'%')"};

    public HouseList(){
        setEjbql(EJBQL);
        setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
        setMaxResults(25);

    }
}
