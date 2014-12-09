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

    private static final String[] OR_RESTRICTIONS = {
        "lower(project.section.name) like "
    };

    private static final String[] RESTRICTIONS = {
            "lower(project.section.district.name) like lower(concat('%',#{baseMapDataMgr.districtName},'%'))",
            "lower(project.section.district.id) like lower(concat('%',#{baseMapDataMgr.districtId},'%'))",

            "lower(project.section.id) like lower(concat('%',#{baseMapDataMgr.sectionId},'%'))",
            "lower(project.section.name) like lower(concat('%',#{baseMapDataMgr.sectionName},'%'))",
            "lower(project.section.address) like lower(concat('%',#{baseMapDataMgr.sectionAddress},'%'))",

            "lower(project.id) like lower(concat('%',#{baseMapDataMgr.projectId},'%'))",
            "lower(project.name) like lower(concat('%',#{baseMapDataMgr.projectName},'%'))",
            "lower(project.address) like lower(concat('%',#{baseMapDataMgr.projectAddress},'%'))",
            "lower(project.developer.name) like lower(concat('%',#{baseMapDataMgr.developerName},'%'))",
            "lower(project.developer.id) like lower(concat('%',#{baseMapDataMgr.developerId},'%'))",

            "lower(project.section.name) like lower(concat('%',#{baseMapDataCondition.sectionName},'%'))",
            "project.section.district.id = #{baseMapDataCondition.districtId}",
            "project.id = #{baseMapDataCondition.projectId}",
            "lower(project.developer.name) like lower(concat('%',#{baseMapDataCondition.developerName},'%'))",
            "project.developer.id = #{baseMapDataCondition.developerId}",
            "lower(project.name) like lower(concat('%',#{baseMapDataCondition.projectName},'%'))"};


    public ProjectList() {
        setEjbql(EJBQL);
        setRestrictionLogicOperator("or");
        setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
        setMaxResults(25);
    }




}
