package com.dgsoft.house.action;

import com.dgsoft.house.HouseEntityQuery;
import com.dgsoft.house.model.Project;
import org.jboss.seam.annotations.Name;

import java.util.Arrays;
import java.util.List;

/**
 * Created by cooper on 6/25/14.
 */
@Name("projectList")
public class ProjectList extends HouseEntityQuery<Project>{

    private static final String EJBQL = "select project from Project project " +
            "left join fetch project.section section " +
            "left join fetch section.district district " +
            "left join fetch project.developer developer ";


    private static final String[] RESTRICTIONS = {
            "lower(district.name) like lower(concat('%',#{baseMapDataMgr.districtName},'%'))",
            "lower(district.id) = lower(#{baseMapDataMgr.districtId})",
            "lower(district.shortName) like lower(concat('%',#{baseMapDataMgr.districtName},'%'))",

            "lower(section.id) = lower(#{baseMapDataMgr.sectionId})",
            "lower(section.name) like lower(concat('%',#{baseMapDataMgr.sectionName},'%'))",
            "lower(section.address) like lower(concat('%',#{baseMapDataMgr.sectionAddress},'%'))",
            "lower(section.pyCode) like lower(concat('%',#{baseMapDataMgr.sectionName},'%'))",

            "lower(project.id) = lower(#{baseMapDataMgr.projectId})",
            "lower(project.name) like lower(concat('%',#{baseMapDataMgr.projectName},'%'))",
            "lower(project.address) like lower(concat('%',#{baseMapDataMgr.projectAddress},'%'))",

            "lower(developer.name) like lower(concat('%',#{baseMapDataMgr.developerName},'%'))",
            "lower(developer.id) = lower(#{baseMapDataMgr.developerId})",
            "lower(developer.pyCode) like lower(concat('%',#{baseMapDataMgr.developerName},'%'))",

            "lower(section.name) like lower(concat('%',#{baseMapDataCondition.sectionName},'%'))",
            "district.id = #{baseMapDataCondition.districtId}",
            "project.id = #{baseMapDataCondition.projectId}",
            "lower(developer.name) like lower(concat('%',#{baseMapDataCondition.developerName},'%'))",
            "developer.id = #{baseMapDataCondition.developerId}",
            "lower(project.name) like lower(concat('%',#{baseMapDataCondition.projectName},'%'))"};

    private static final String[] SORT_COLUMNS = {
            "project.createTime","project.name","project.developer.id","project.id","project.area"
    };

    public List<String> getSortColumns(){
        return Arrays.asList(SORT_COLUMNS);
    }


    public ProjectList() {
        setEjbql(EJBQL);
        setRestrictionLogicOperator("or");
        setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
        setOrderColumn("project.createTime");
        setOrderDirection("desc");
        setMaxResults(25);
    }




}
