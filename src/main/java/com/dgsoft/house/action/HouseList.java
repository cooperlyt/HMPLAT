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
            "lower(house.id) = lower(#{houseList.searchHouseId})",
            "lower(house.address) = lower(#{houseList.searchAddress})",
            "lower(build.name) like lower(concat('%',#{houseList.searchBuild},'%'))",
            "lower(build.doorNo) like lower(concat('%',#{houseList.searchBuild},'%'))",
            "lower(project.address) like lower(concat('%',#{houseList.searchProject},'%'))",
            "lower(developer.name) like lower(concat('%',#{houseList.searchDeveloper},'%'))",
            "lower(developer.pyCode) like lower(concat('%',#{houseList.searchDeveloper},'%'))",
            "lower(section.id) = lower(#{houseList.searchProject})",
            "lower(section.name) like lower(concat('%',#{houseList.searchProject},'%'))",
            "lower(section.address) like lower(concat('%',#{houseList.searchProject},'%'))",
            "lower(section.pyCode) like lower(concat('%',#{houseList.searchProject},'%'))",
            "lower(build.id) = lower(#{houseList.searchBuild})",
            "lower(project.id) = lower(#{houseList.searchProject})",
            "lower(developer.id) = lower(#{houseList.searchDeveloper})",

    };
    private static final String[] MBBH_RESTRICTIONS = {
            "lower(build.mapNumber) = lower(#{houseList.searchMapNumber})",
            "lower(build.blockNo) = lower(#{houseList.searchBlockNumber})",
            "lower(build.buildNo) = lower(#{houseList.searchBuildNumber})",
            "lower(house.houseOrder) = lower(#{houseList.searchHouseNumber})"
    };




    public enum SearchType{
        HOUSE_CODE("房屋编号"),BUILD("按楼幢"),PROJECT("按项目"),DEVELOPER("按开发商"),ADDRESS("按地址"),MBBH("图丘幢房");

        private String label;

        public String getLabel() {
            return label;
        }

        SearchType(String label) {
            this.label = label;
        }
    }

    public SearchType[] getAllSearchTypes(){
        return SearchType.values();
    }

    private SearchType searchType;

    private String searchKey;

    public String getSearchHouseId(){
        if ((searchType == null) || SearchType.HOUSE_CODE.equals(searchType)){
            return searchKey;
        }else {
            return null;
        }
    }

    public String getSearchAddress(){
        if ((searchType == null) || SearchType.ADDRESS.equals(searchType)){
            return searchKey;
        }else {
            return null;
        }
    }

    public String getSearchBuild(){
        if ((searchType == null) || SearchType.BUILD.equals(searchType) || SearchType.ADDRESS.equals(searchType)){
            return searchKey;
        }else{
            return null;
        }
    }

    public String getSearchProject(){
        if ((searchType == null) || SearchType.PROJECT.equals(searchType) || SearchType.ADDRESS.equals(searchType)){
            return searchKey;
        }else{
            return null;
        }
    }

    public String getSearchDeveloper(){
        if ((searchType == null) || SearchType.DEVELOPER.equals(searchType)){
            return searchKey;
        }else{
            return null;
        }
    }

    private String mapNumber;

    private String blockNumber;

    private String buildNumber;

    private String houseNumber;

    public String getSearchMapNumber() {
        if(searchType != null && SearchType.MBBH.equals(searchType)){
            return mapNumber;
        }else{
            return null;
        }
    }

    public String getMapNumber() {
        return mapNumber;
    }

    public void setMapNumber(String mapNumber) {
        this.mapNumber = mapNumber;
    }

    public String getSearchBlockNumber() {
        if(searchType != null && SearchType.MBBH.equals(searchType)){
            return blockNumber;
        }else{
            return null;
        }
    }

    public String getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(String blockNumber) {
        this.blockNumber = blockNumber;
    }

    public String getSearchBuildNumber() {
        if(searchType != null && SearchType.MBBH.equals(searchType)){
            return buildNumber;
        }else{
            return null;
        }
    }

    public String getBuildNumber() {
        return buildNumber;
    }

    public void setBuildNumber(String buildNumber) {
        this.buildNumber = buildNumber;
    }

    public String getSearchHouseNumber() {
        if(searchType != null && SearchType.MBBH.equals(searchType)){
            return houseNumber;
        }else{
            return null;
        }
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }



    public SearchType getSearchType() {
        return searchType;
    }

    public void setSearchType(SearchType searchType) {
        this.searchType = searchType;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public void searchAction(){
        if(searchType != null && SearchType.MBBH.equals(searchType)){
            setRestrictionGroup(new RestrictionGroup("and",Arrays.asList(MBBH_RESTRICTIONS)));
        }else{
            setRestrictionGroup(new RestrictionGroup("or",Arrays.asList(RESTRICTIONS)));
        }
        first();
    }

    public HouseList() {
        setEjbql(EJBQL);
        setRestrictionLogicOperator("and");

        setRestrictionGroup(new RestrictionGroup("or",Arrays.asList(RESTRICTIONS)));
        setOrderExpress("build.id,house.houseOrder,house.id");

        setMaxResults(70);

    }


    @Override
    protected String getPersistenceContextName() {
        return "houseEntityManager";
    }


}
