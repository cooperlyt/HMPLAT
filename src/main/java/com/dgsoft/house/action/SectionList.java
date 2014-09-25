package com.dgsoft.house.action;


import com.dgsoft.house.HouseEntityQuery;
import com.dgsoft.house.model.Section;
import org.jboss.seam.annotations.Name;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: cooperlee
 * Date: 14-6-6
 * Time: 下午4:24
 */
@Name("sectionList")
public class SectionList extends HouseEntityQuery<Section> {

    private static final String EJBQL = "select section from Section section left join fetch section.district";

    private static final String[] RESTRICTIONS = {
            "lower(section.name) like lower(concat('%',#{sectionSearchCondition.sectionName},'%'))",
            "section.district.id = #{sectionSearchCondition.districtId}"};


    public SectionList() {
        setEjbql(EJBQL);
        setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
        setMaxResults(25);
        setOrderColumn("section.createTime");
    }




}
