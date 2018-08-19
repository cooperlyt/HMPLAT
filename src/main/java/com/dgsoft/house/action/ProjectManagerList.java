package com.dgsoft.house.action;

import com.dgsoft.house.HouseEntityQuery;
import com.dgsoft.house.model.Project;
import org.jboss.seam.annotations.Name;

import java.util.Arrays;

@Name("projectManagerList")
public class ProjectManagerList extends HouseEntityQuery<Project> {


    private static final String EJBQL = "select project from Project project " +
            "left join fetch project.section section " +
            "left join fetch section.district district " +
            "left join fetch project.developer developer ";

    private static final String[] RESTRICTIONS =
            {
                    "lower(district.name) like lower(concat('%',#{projectManagerList.searchKey},'%'))",


                    "lower(section.id) = lower(#{projectManagerList.searchKey})",
                    "lower(section.name) like lower(concat('%',#{projectManagerList.searchKey},'%'))",
                    "lower(section.address) like lower(concat('%',#{projectManagerList.searchKey},'%'))",
                    "lower(section.pyCode) like lower(concat('%',#{projectManagerList.searchKey},'%'))",

                    "lower(project.id) = lower(#{projectManagerList.searchKey})",
                    "lower(project.name) like lower(concat('%',#{projectManagerList.searchKey},'%'))",
                    "lower(project.address) like lower(concat('%',#{projectManagerList.searchKey},'%'))",

                    "lower(developer.name) like lower(concat('%',#{projectManagerList.searchKey},'%'))",
                    "lower(developer.id) = lower(#{projectManagerList.searchKey})",
                    "lower(developer.pyCode) like lower(concat('%',#{projectManagerList.searchKey},'%'))",
            };


    public ProjectManagerList() {
        setEjbql(EJBQL);
        setRestrictionLogicOperator("or");
        setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
        setOrderColumn("project.createTime");
        setOrderDirection("desc");
        setMaxResults(25);
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    private String searchKey;


    public void searchAction(){
        first();
    }

}
