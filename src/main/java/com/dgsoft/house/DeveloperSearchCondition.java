package com.dgsoft.house;

import org.jboss.seam.annotations.Name;

/**
 * Created by cooper on 10/28/14.
 */
@Name("developerSearchCondition")
public class DeveloperSearchCondition {

    private String developerName;

    private String developerId;

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
}
