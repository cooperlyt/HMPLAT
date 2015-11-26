package com.dgsoft.house.action;

import com.dgsoft.house.HouseEntityQuery;
import com.dgsoft.house.model.Build;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import java.util.Arrays;

/**
 * Created by cooper on 11/2/15.
 */
@Name("buildList")
@Scope(ScopeType.CONVERSATION)
public class BuildList extends HouseEntityQuery<Build>{


    private static final String EJBQL = "select build from Build build " +
            "left join fetch build.project project " +
            "left join fetch project.section section " +
            "left join fetch project.developer developer ";


    private static final String[] RESTRICTIONS = {
            "lower(build.name) like lower(concat('%',#{buildList.searchKey},'%'))",
            "lower(build.doorNo) like lower(concat('%',#{buildList.searchKey},'%'))",
            "lower(project.address) like lower(concat('%',#{buildList.searchKey},'%'))",
            "lower(developer.name) like lower(concat('%',#{buildList.searchKey},'%'))",
            "lower(section.id) = lower(#{buildList.searchKey})",
            "lower(section.name) like lower(concat('%',#{buildList.searchKey},'%'))",
            "lower(section.address) like lower(concat('%',#{buildList.searchKey},'%'))",
            "lower(section.pyCode) like lower(concat('%',#{buildList.searchKey},'%'))",
            "lower(build.id) = lower(#{buildList.searchKey})",
            "lower(project.id) = lower(#{buildList.searchKey})",
            "lower(developer.id) = lower(#{buildList.searchKey})",
            "lower(project.address) = lower(concat('%',#{buildList.searchKey},'%'))"

    };

    @Override
    public void refresh(){
        super.refresh();
    }

    public enum SortCol{
        SORT_ID("build.id")
        ,SORT_MBB("build.mapNumber,build.blockNo,build.buildNo"),
        SORT_NAME("build.name,build.id"),SORT_DOOR_NO("build.doorNo,build.id"),
        SORT_SECTION("section.id,project.id,build.buildNo");

        private String orderStr;

        public String getOrderStr() {
            return orderStr;
        }

        SortCol(String orderStr) {
            this.orderStr = orderStr;
        }
    }

    public SortCol[] getSortCols(){
        return SortCol.values();
    }

    public SortCol getSortCol(){


        for(SortCol sortCol: SortCol.values()){
            if (sortCol.getOrderStr().equals(getOrderExpress())){
                return sortCol;
            }
        }
        return null;
    }


    private String searchKey;

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public BuildList() {
        setEjbql(EJBQL);
        setRestrictionLogicOperator("or");
        setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
        setOrderColumn("build.id");
        setOrderDirection("desc");
        setMaxResults(50);
    }



}
