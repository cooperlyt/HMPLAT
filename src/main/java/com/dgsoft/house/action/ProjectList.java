package com.dgsoft.house.action;

import com.dgsoft.house.HouseEntityQuery;
import com.dgsoft.house.model.Project;
import org.jboss.seam.annotations.Name;

import java.util.Arrays;

/**
 * Created by cooper on 6/25/14.
 */
@Name("projectList")
public class ProjectList extends HouseEntityQuery<Project>{

    private static final String EJBQL = "select project from Project project " +
            "left join fetch project.section section left join fetch section.district " +
            "left join fetch project.developer";

    private static final String[] RESTRICTIONS = {
            "lower(project.section.name) like lower(concat('%',#{sectionSearchCondition.sectionName},'%'))",
            "project.section.district.id = #{sectionSearchCondition.districtId}",
            "project.id = #{projectSearchCondition.projectId}",
            "lower(project.developer.name) like lower(concat('%',#{developerSearchCondition.developerName},'%'))",
            "project.developer.id = #{developerSearchCondition.developerId}",
            "lower(project.name) like lower(concat('%',#{projectSearchCondition.projectName},'%'))"};


    public ProjectList() {
        setEjbql(EJBQL);
        setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
        setMaxResults(25);
    }




}
