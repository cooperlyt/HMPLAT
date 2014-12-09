package com.dgsoft.house;

import org.jboss.seam.annotations.Name;

/**
 * Created by cooper on 12/9/14.
 */
@Name("baseMapDataCondition")
public class BaseMapDataCondition {

    private String districtId;

    private String sectionName;

    private String projectId;

    private String projectName;

    private String developerName;

    private String developerId;

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getDeveloperName() {
        return developerName;
    }

    public void setDeveloperName(String developerName) {
        this.developerName = developerName;
    }

    public String getDeveloperId() {
        return developerId;
    }

    public void setDeveloperId(String developerId) {
        this.developerId = developerId;
    }

    public void reset(){
        districtId = null;
        sectionName = null;
        projectId = null;
        projectName = null;
        developerName = null;
        developerId = null;
    }
}
