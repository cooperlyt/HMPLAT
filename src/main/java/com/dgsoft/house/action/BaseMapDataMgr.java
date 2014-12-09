package com.dgsoft.house.action;

import com.dgsoft.house.BaseMapDataCondition;
import com.dgsoft.house.model.Developer;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import java.util.EnumSet;
import java.util.Set;

/**
 * Created by cooper on 12/9/14.
 */
@Name("baseMapDataMgr")
public class BaseMapDataMgr {

    public enum OneSearchType{
        SEARCH_NAME,SEARCH_ADDRESS,SEARCH_ID,
        SEARCH_PROJECT_NAME,SEARCH_PROJECT_ID,SEARCH_PROJECT_ADDRESS,
        SEARCH_SECTION_NAME,SEARCH_SECTION_ID,SEARCH_SECTION_ADDRESS,
        SEARCH_DISTRICT_NAME,SEARCH_DISTRICT_ID,
        SEARCH_DEVELOPER_NAME,SEARCH_DEVELOPER_ID;
    }

    public enum DataType{
        PROJECT_MGR,SECTION_MGR,DISTRICT_MGR,DEVELOPER_MGR;
    }

    public Set<OneSearchType> getCommonTypes(){
        return EnumSet.of(OneSearchType.SEARCH_NAME,OneSearchType.SEARCH_ADDRESS,OneSearchType.SEARCH_ID);
    }

    public Set<OneSearchType> getProjectTypes(){
        return EnumSet.of(OneSearchType.SEARCH_PROJECT_NAME,OneSearchType.SEARCH_PROJECT_ID,OneSearchType.SEARCH_PROJECT_ADDRESS);
    }

    public Set<OneSearchType> getSectionTypes(){
        return EnumSet.of(OneSearchType.SEARCH_SECTION_NAME,OneSearchType.SEARCH_SECTION_ID,OneSearchType.SEARCH_SECTION_ADDRESS);
    }

    public Set<OneSearchType> getDistrictTypes(){
        return EnumSet.of(OneSearchType.SEARCH_DISTRICT_NAME,OneSearchType.SEARCH_DISTRICT_ID);
    }

    private OneSearchType oneSearchType;

    private DataType dataType;

    private String oneCondition;

    public OneSearchType getOneSearchType() {
        return oneSearchType;
    }

    public void setOneSearchType(OneSearchType oneSearchType) {
        this.oneSearchType = oneSearchType;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public String getDataTypeName(){
        if (dataType == null){
            return null;
        }
        return dataType.name();
    }

    public void setDataTypeName(String type){
        if ((type == null) || type.trim().equals("")){
            dataType = null;
        }else{
            dataType = DataType.valueOf(type);
        }
    }

    public String getOneCondition() {
        return oneCondition;
    }

    public void setOneCondition(String oneCondition) {
        this.oneCondition = oneCondition;
    }

    public String getDistrictId(){
        if ((oneSearchType == null) ||
                OneSearchType.SEARCH_ID.equals(oneSearchType) ||
                OneSearchType.SEARCH_DISTRICT_ID.equals(oneSearchType)){
            return oneCondition;
        }else{
            return null;
        }
    }

    public String getDistrictName(){
        if ((oneSearchType == null) ||
                OneSearchType.SEARCH_NAME.equals(oneSearchType) ||
                OneSearchType.SEARCH_ADDRESS.equals(oneSearchType) ||
                OneSearchType.SEARCH_DISTRICT_NAME.equals(oneSearchType)){
            return oneCondition;
        }else{
            return null;
        }
    }

    public String getSectionName(){
        if ((oneSearchType == null) ||
                OneSearchType.SEARCH_SECTION_NAME.equals(oneSearchType) ||
                OneSearchType.SEARCH_NAME.equals(oneSearchType)){
            return oneCondition;
        }else{
            return null;
        }
    }

    public String getSectionId(){
        if ((oneSearchType == null) ||
                OneSearchType.SEARCH_ID.equals(oneSearchType) ||
                OneSearchType.SEARCH_SECTION_ID.equals(oneSearchType)){
            return oneCondition;
        }else{
            return null;
        }
    }

    public String getSectionAddress(){
        if ((oneSearchType == null) ||
                OneSearchType.SEARCH_ADDRESS.equals(oneSearchType) ||
                OneSearchType.SEARCH_SECTION_ADDRESS.equals(oneSearchType)){
            return oneCondition;
        }else{
            return null;
        }
    }

    public String getProjectName(){
        if ((oneSearchType == null) ||
                OneSearchType.SEARCH_NAME.equals(oneSearchType) ||
                OneSearchType.SEARCH_PROJECT_NAME.equals(oneSearchType)){
            return oneCondition;
        }else{
            return null;
        }
    }

    public String getProjectId(){
        if ((oneSearchType == null) ||
                OneSearchType.SEARCH_ID.equals(oneSearchType) ||
                OneSearchType.SEARCH_PROJECT_ID.equals(oneSearchType)){
            return oneCondition;
        }else{
            return null;
        }
    }

    public String getProjectAddress(){
        if ((oneSearchType == null) ||
                OneSearchType.SEARCH_ADDRESS.equals(oneSearchType) ||
                OneSearchType.SEARCH_PROJECT_ADDRESS.equals(oneSearchType)){
            return oneCondition;
        }else{
            return null;
        }
    }

    public String getDeveloperName(){
        if ((oneSearchType == null) ||
                OneSearchType.SEARCH_NAME.equals(oneSearchType) ||
                OneSearchType.SEARCH_DEVELOPER_NAME.equals(oneSearchType)){
            return oneCondition;
        }else{
            return null;
        }
    }

    public String getDeveloperId(){
        if ((oneSearchType == null) ||
                OneSearchType.SEARCH_ID.equals(oneSearchType) ||
                OneSearchType.SEARCH_DEVELOPER_ID.equals(oneSearchType)){
            return oneCondition;
        }else{
            return null;
        }
    }

    @In(required = false)
    public BaseMapDataCondition baseMapDataCondition;

    @In(create = true)
    public ProjectList projectList;

    @In(create = true)
    public SectionList sectionList;

    @In(create = true)
    public DistrictList districtList;

    @In(create = true)
    public DeveloperList developerList;

    public void searchByOne(){
        if(baseMapDataCondition != null){
            baseMapDataCondition.reset();
        }
        developerList.setRestrictionLogicOperator("or");
        districtList.setRestrictionLogicOperator("or");
        sectionList.setRestrictionLogicOperator("or");
        projectList.setRestrictionLogicOperator("or");
    }
}
