package com.dgsoft.house;

import org.jboss.seam.annotations.Name;

/**
 * Created by cooper on 6/25/14.
 */
@Name("projectSearchCondition")
public class ProjectSearchCondition {

    private String projectId;

    private String projectName;

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

}
