package com.dgsoft.house.owner.action;

import com.dgsoft.common.utils.seam.MultiOperatorEntityQuery;
import com.dgsoft.common.utils.seam.RestrictionGroup;
import com.dgsoft.house.owner.model.BusinessProject;
import org.jboss.seam.annotations.Name;

import java.util.Arrays;

/**
 * Created by cooper on 10/12/2016.
 */
@Name("projectBusinessList")
public class ProjectBusinessList extends MultiOperatorEntityQuery<BusinessProject> {

    public static final String EJBQL = "select biz from BusinessProject biz  left join fetch biz.ownerBusiness ob  " ;


    public ProjectBusinessList() {
        setEjbql(EJBQL);
        setRestrictionLogicOperator("and");
        setOrderColumn(SortCol.ORDER_BY_CREATE_TIME.colPath);
        setOrderDirection("desc");
        setMaxResults(20);
    }

    public enum SearchType {
        ALL(EJBQL,new RestrictionGroup("or", Arrays.asList(new String[] {
                "lower(biz.searchKey) like lower(concat('%',concat('%',#{projectBusinessList.searchKey},'%')))",
                "lower(biz.projectCode) = lower(#{projectBusinessList.searchKey})",
                "lower(biz.developerCode) = lower(#{projectBusinessList.searchKey})",
                "lower(biz.sectionCode) = lower(#{projectBusinessList.searchKey})",
                "lower(ob.id) = lower(#{projectBusinessList.searchKey})"
        }))),
        OWNER_BIZ_ID(EJBQL,new RestrictionGroup("and", Arrays.asList(new String[]{"lower(ob.id) = lower(#{projectBusinessList.searchKey})"}))),
        PROJECT_ID(EJBQL,new RestrictionGroup("and", Arrays.asList(new String[]{"lower(biz.projectCode) = lower(#{projectBusinessList.searchKey})"}))),
        PROJECT_NAME(EJBQL,new RestrictionGroup("and",Arrays.asList(new String[]{"lower(biz.name) = lower(#{projectBusinessList.searchKey})"}))),
        DEVELOPER_ID(EJBQL,new RestrictionGroup("and", Arrays.asList(new String[]{"lower(biz.developerCode) = lower(#{projectBusinessList.searchKey})"}))),
        DEVELOPER_NAME(EJBQL,new RestrictionGroup("and",Arrays.asList(new String[]{"lower(biz.developerName) = lower(#{projectBusinessList.searchKey})"}))),
        PROJECT_CARD(EJBQL,new RestrictionGroup("and",Arrays.asList(new String[]{"lower(biz.projectSellInfo.projectCard.makeCard.number) = lower(#{projectBusinessList.searchKey})"}))),
        HOUSE_MBBH("select distinct biz from BusinessProject biz  left join fetch biz.ownerBusiness ob left join biz.businessBuilds build ",
                new RestrictionGroup("and",Arrays.asList(new String[]{
                        "lower(build.mapNumber) = lower(#{projectBusinessList.mapNumber})",
                        "lower(build.blockNo) = lower(#{projectBusinessList.blockNumber})",
                        "lower(build.buildNo) = lower(#{projectBusinessList.buildNumber})"})));

        private RestrictionGroup restrictionGroup;

        private String jpql;

        public RestrictionGroup getRestrictionGroup() {
            return restrictionGroup;
        }

        public String getJpql() {
            return jpql;
        }

        SearchType(String jpql, RestrictionGroup restrictionGroup) {
            this.restrictionGroup = restrictionGroup;
            this.jpql = jpql;
        }
    }

    public SearchType[] getAllSearchTypes(){
        return SearchType.values();
    }

    private SearchType searchType = SearchType.ALL;

    private String searchKey;

    private String mapNumber;

    private String blockNumber;

    private String buildNumber;

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public String getMapNumber() {
        return mapNumber;
    }

    public void setMapNumber(String mapNumber) {
        this.mapNumber = mapNumber;
    }

    public String getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(String blockNumber) {
        this.blockNumber = blockNumber;
    }

    public String getBuildNumber() {
        return buildNumber;
    }

    public void setBuildNumber(String buildNumber) {
        this.buildNumber = buildNumber;
    }

    public SearchType getSearchType() {
        return searchType;
    }

    public void setSearchType(SearchType searchType) {
        this.searchType = searchType;
    }

    public void setSearchTypeName(String type){
        if ((type == null) || type.trim().equals("")){
            setSearchType(null);
        }else{
            setSearchType(SearchType.valueOf(type));
        }
    }

    public String getSearchTypeName(){
        if (searchType == null){
            return null;
        }
        return getSearchType().name();
    }

    public void resetPage(){
        setFirstResult(0);
    }

    @Override
    protected String getPersistenceContextName() {
        return "ownerEntityManager";
    }

    public enum SortCol{
        ORDER_BY_CREATE_TIME("ob.createTime"),
        ORDER_BY_BUSINESS_NAME("ob.defineName");


        private String colPath;

        SortCol(String colPath) {
            this.colPath = colPath;
        }

        public String getColPath() {
            return colPath;
        }
    }

    @Override
    protected void createResultList(){
        setEjbql(getSearchType().getJpql());
        setRestrictionGroup(getSearchType().getRestrictionGroup());
    }

    public SortCol[] getAllSortCol(){
        return SortCol.values();
    }

    public SortCol getSortCol(){
        for(SortCol col: SortCol.values()){
            if (getOrderColumn().equals(col.colPath)){
                return col;
            }
        }
        return null;
    }

    public void searchAction(){

        setFirstResult(0);
    }

}
